package bbs.china;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import bbsinter.BBSBlock;
import bbsinter.BBSSplitter;
import bbsutils.BBSParseException;
import bbsutils.ExceptionBuilder;
import bbsutils.PageData;

/**
 * 中华网社区版块分块器
 *
 */
public class ChinaBBSSpliter implements BBSSplitter {
	private ExceptionBuilder mExceptionBuilder;
	
	// 分块的大小，这里为页数
	private int mBlockSize;
	
	public ChinaBBSSpliter() {
		mExceptionBuilder = new ExceptionBuilder("china", "版块分块时错误发生格式错误");
		mBlockSize = 10;
	}
	
	@Override
	public List<BBSBlock> split(PageData data) throws BBSParseException {
		// 分析版块的总页数
		int pageCount = getPageCount(data);
		if (pageCount <= 0)
			throw mExceptionBuilder.build("分块时页数异常:" + pageCount, data.url());
		
		// 切割得到基础URl
		String url = data.url();
		String[] urlSeps = url.split("/");
		if (urlSeps.length < 2) 
			throw mExceptionBuilder.build("分块时URL异常", data.url());
		
		String forumNo = urlSeps[urlSeps.length - 2];
		for (int i = 0; i < forumNo.length(); ++ i) {
			if (!Character.isDigit(forumNo.charAt(i)))
				throw mExceptionBuilder.build("分块时URL异常,板块号格式错误", data.url());
		}
		
		// 总共分块个数
		int blockCount = (int)Math.ceil((double)pageCount / mBlockSize);
		
		List<BBSBlock> ret = new ArrayList<BBSBlock>();
		for (int i = 0; i < blockCount - 1; ++ i)
			ret.add(new ChinaBBSBlock(forumNo, i * mBlockSize + 1, mBlockSize));
		
		// 最后一块
		int tmp = (blockCount - 1) * mBlockSize;
		ret.add(new ChinaBBSBlock(forumNo, tmp + 1, pageCount - tmp));
		
		return ret;
	}
	
	/**
	 * 得到总页数
	 * @param data ： 版块首页数据
	 * @return：
	 * @throws BBSParseException
	 */
	private int getPageCount(PageData data) throws BBSParseException {
		Document doc = data.doc();
		Elements eles = doc.select(".topPages > table");
		
		if (eles.size() == 0) {
			throw mExceptionBuilder.build("格式错误，未找到table标签", data.url());
		}
		eles = eles.get(0).select("td");
		
		if (eles.size() < 5)
			throw mExceptionBuilder.build(data.url());
		
		String pageTd = eles.get(eles.size() - 5).html();
		
		int beg = pageTd.indexOf("共");
		int end = pageTd.indexOf("页");
		
		if (beg == -1 || end == 1)
			throw mExceptionBuilder.build(data.url());
		
		String pageCountStr = pageTd.substring(beg + 1, end);
		int pageCount;
		
		try {
			pageCount = Integer.parseInt(pageCountStr);
		} catch(NumberFormatException e) {
			throw mExceptionBuilder.build(data.url());
		}
		
		return pageCount;
	}
	
	@Override
	public BBSBlock fromDescriptor(String desc) {
		String[] seps = desc.split(",");
		
		if (seps.length != 3) {
			return null;
		}
		
		String forumInfo = seps[0];
		int begPageNo = Integer.parseInt(seps[1]);
		int size = Integer.parseInt(seps[2]);
		
		ChinaBBSBlock ret = new ChinaBBSBlock(forumInfo, begPageNo, size);
		
		return ret;
	}

}
