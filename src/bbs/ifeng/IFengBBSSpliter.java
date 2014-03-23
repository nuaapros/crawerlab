package bbs.ifeng;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import bbsinter.BBSBlock;
import bbsinter.BBSSplitter;
import bbsutils.BBSParseException;
import bbsutils.ExceptionBuilder;
import bbsutils.PageData;

public class IFengBBSSpliter implements BBSSplitter{
	private ExceptionBuilder mExceptionBuilder;
	private int mBlockSize;
	
	public IFengBBSSpliter()
	{
		mExceptionBuilder = new ExceptionBuilder("china", "版块分块时错误发生格式错误");
		mBlockSize=10;
	}
	@Override
	public List<BBSBlock> split(PageData data) throws BBSParseException {
		int pageCount = getPageCount(data);
		if (pageCount <= 0)
			throw mExceptionBuilder.build("分块时页数异常:" + pageCount, data.url());
		List<BBSBlock> ifengBbsBlocks=new ArrayList<BBSBlock>();
		for(int i=0;i < pageCount;i++){
			ifengBbsBlocks.add(new IFengBBSBlock("ifeng",i*mBlockSize+1 ,mBlockSize));
		}
		
		return ifengBbsBlocks;
	}
	/*
	 * 分块信息
	 * @see bbsinter.BBSSplitter#fromDescriptor(java.lang.String)
	 */
	@Override
	public BBSBlock fromDescriptor(String desc) {
		String[] seps = desc.split(",");
		if (seps.length != 3) {
			return null;
		}
		String forumInfo = seps[0];
		int begPageNo = Integer.parseInt(seps[1]);
		int size = Integer.parseInt(seps[2]);	
		IFengBBSBlock ret = new IFengBBSBlock(forumInfo, begPageNo, size);	
		return ret;

	}
	
	private int getPageCount(PageData data) throws BBSParseException {
		Document doc = data.doc();
		int num=0;
		String tmp1="";
		String tmp2="";
		tmp1=doc.select("div.numb_post").text();
		if(tmp1==null)
			throw mExceptionBuilder.build("ifeng", "获取总页数失败");
		Pattern Expression = Pattern.compile("共.*?页");
		Matcher common=Expression.matcher(tmp1);
		if(common.find())
		{
			tmp2=common.group(0);
	        	
	     }
	     Expression = Pattern.compile("[0-9].*?[0-9]");
	     common=Expression.matcher(tmp2);
	     if(common.find())
	     {
	    	 tmp2=common.group(0);
	     }
	     num=Integer.valueOf(tmp2);
		return num;
	}
	

}
