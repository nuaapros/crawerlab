package bbs.china;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bbsinter.TopicDataContext;
import bbsinter.TopicDataParser;
import bbsutils.BBSParseException;
import bbsutils.BBSReply;
import bbsutils.ExceptionBuilder;
import bbsutils.PageData;

public class ChinaTopicDataParser implements TopicDataParser {
	private static Pattern author_url_p = Pattern.compile("\\d+");
	
	private ExceptionBuilder mExceptionBuilder = 
			new ExceptionBuilder("china", "解析帖子数据时发生格式异常");
	
	// 定义3个记号，用于在context中标记key

	// 回复数量
	private static final String ReplyCount = "REPLYCOUNT";
	
	// 已经访问的回复数量 
	private static final String ReplyVisited = "REPLYVISTED";
	
	// 已经探索的页面数量(已经通过getPageLinks返回的链接数)
	private static final String  PageCountExp = "PAGE_EXPED";
	
	// 上次访问时已经访问的回复数量
	private static final String LastVisted = "LAST_VISTED";
	
	// 记录格式内容
	private static final String FormatType = "FORMAT_TYPE";
	
	private static final int NormalFormat 	= 0;
	private static final int SpecialFormat	= 1;
	
	// 用于解析日期格式
	private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Override
	public List<String> getPageLinks(PageData data) {
		return null;
	}
	
	@Override
	public List<BBSReply> parse(PageData data) throws BBSParseException {
		return null;
	}

	@Override
	public List<BBSReply> parse(PageData data, TopicDataContext context)
			throws BBSParseException {
		Document doc = data.doc();
		List<BBSReply> ret = new ArrayList<BBSReply>();
		
		try {
			for(Element ele : doc.getElementsByClass("postBox")){
				String author_url = ele.select("div.author").select("a").first().attr("href");
				String author_name = ele.select("div.author").select("a").first().text();
				String comment = ele.select("div.postContent").text();
				
				String replytime = null;
				if (context.getAsInt(FormatType) == NormalFormat)
					replytime = ele.select("div.postTime").text();
				else {
					Elements spans = ele.select(".author").select("span");
					replytime = spans.get(spans.size() - 1).text();
				}
				replytime = replytime.replaceAll("发表于：", "");
				
				Matcher author_url_m = author_url_p.matcher(author_url);
				String authorurl = null;
				if(author_url_m.find()){
					authorurl = author_url_m.group();
				}
				
				BBSReply reply = new BBSReply();
				reply.setAuthorName(author_name);
				reply.setAuthorId(authorurl);
				reply.setContent(comment);
				reply.setPostDate(mDateFormat.parse(replytime));
				
				ret.add(reply);
			}
		} catch (Exception e) {
			throw new BBSParseException("china", "帖子内容解析时发生错误", data.url());
		}
		
		context.increase(ReplyVisited, ret.size());
		return ret;
	}

	@Override
	public List<String> getPageLinks(PageData data, TopicDataContext context) {
		List<String> ret = new ArrayList<String>();
		String[] urlSeps = data.url().split("_");
		
		int repCount = context.getAsInt(ReplyCount) - 1;
		int atLeast;
		
		if (context.getAsInt(PageCountExp) == 0) {
			atLeast = Math.max(1, (int)Math.ceil(repCount / 50.0));
		} else {
			int lastPos = context.getAsInt(LastVisted);
			int repVisted = context.getAsInt(ReplyVisited);
			
			if (repVisted == lastPos)
				return new ArrayList<String>();
			
			context.setData(LastVisted, repVisted);
			int left = repCount - repVisted;
			
			if (left <= 0)
				return new ArrayList<String>();
			
			atLeast = (int)Math.ceil(left / 50.0);
		}
		
		int oriPageExp = context.getAsInt(PageCountExp);
		context.increase(PageCountExp, atLeast);
		int pastPageExp = context.getAsInt(PageCountExp);
		
		for (int i = oriPageExp + 1; i <= pastPageExp; ++ i)
			ret.add(String.format("%s_%d.html", urlSeps[0], i));
		
		return ret;
	}

	@Override
	public TopicDataContext initContext(PageData data)  throws BBSParseException {
		TopicDataContext context = new TopicDataContext();
		
		int repCount = getReplyCount(data, context);

		context.setData(ReplyCount, repCount);
		context.setData(ReplyVisited, 0);
		context.setData(PageCountExp, 0);
		context.setData(LastVisted, 0);
		
		return context;
	}
	
	/**
	 * very ugly code.....I'm sorry
	 * @param data
	 * @return
	 * @throws BBSParseException
	 */
	private int getReplyCount(PageData data, TopicDataContext context) throws BBSParseException {
		Document doc = data.doc();
		Elements par;
		
		context.setData(FormatType, NormalFormat);
		
		try {
			// 普通帖子
			par = doc.select(".topPagesTwo");
			if (par != null && par.size() > 0) {
				Elements eles = par.select("td>strong");
				return Integer.parseInt(eles.get(2).text());
			} else {
				// 图形帖子
				par = doc.select(".imgTopicTit");
				if (par != null && par.size() > 0) {
					return Integer.parseInt(par.select("strong").get(2).text());
				}
				
				// 已经被锁定的情况
				par = doc.select(".topPagesOne");
				Elements eles = par.select("td>strong");
				String[] seps = eles.get(0).html().split("（");
				String repCountStr = seps[seps.length - 1];
				
				String countString = repCountStr.substring(0, repCountStr.length() - 1).trim();
				context.setData(FormatType, SpecialFormat);
				
				return Integer.parseInt(countString);
			}
		} catch (Exception e) {
			throw mExceptionBuilder.build("无法获取帖子回复数量", data.url());
		}
	}

	@Override
	public boolean canGetAllLinksOnce() {
		return false;
	}
	
}
