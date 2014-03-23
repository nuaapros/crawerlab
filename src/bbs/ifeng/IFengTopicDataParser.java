package bbs.ifeng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bbsinter.TopicDataContext;
import bbsinter.TopicDataParser;
import bbsutils.BBSParseException;
import bbsutils.BBSReply;
import bbsutils.ExceptionBuilder;
import bbsutils.PageData;

public class IFengTopicDataParser implements TopicDataParser{
	private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private ExceptionBuilder mExceptionBuilder = 
			new ExceptionBuilder("ifeng", "解析帖子数据时发生格式异常");
	@Override
	public List<BBSReply> parse(PageData data) throws BBSParseException {
		Document doc=data.doc();
		List<BBSReply> repInfo=new ArrayList<BBSReply>();
		String authorUrl="";
		String postDate="";
		String replyContent="";
		String authorName="";
		BBSReply tmpInfo=new BBSReply();
		try {
		
			for (Element ele : doc.getElementsByClass("left_reply")) {
				if(!ele.select("li.cGray").text().equals("")){
				tmpInfo=new BBSReply();
				postDate=ele.select("li.cGray").text();
				authorName=ele.select("div.box108").select("h3").select("a").text();
				replyContent=ele.select("div.t_msgfont").text();
				authorUrl="http://bbs.ifeng.com/"+ele.select("div.box108").select("h3").select("a").attr("href");
				tmpInfo.setAuthorName(authorName);
				tmpInfo.setPostDate(mDateFormat.parse(postDate));
				tmpInfo.setContent(replyContent);
				tmpInfo.setRefUserId(authorUrl);
				
				repInfo.add(tmpInfo);
				}
			}
		} catch (Exception e) {
			throw new BBSParseException("ifeng", "帖子内容解析时发生错误", data.url());
		}
		
		return repInfo;
	}

	
	@Override
	public List<String> getPageLinks(PageData data) throws BBSParseException {
		Document doc=data.doc();
		List<String> nextPageUrlList=new ArrayList<String>();
		int num=0;
		String tmp1="";
		String tmp2="";
		String flag=doc.html();
		if(flag.contains("numb_post2")){
		try {
			tmp1=doc.select("div.numb_post2").select("span").get(0).text();
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
		  
			 String tid="";
			 tid=doc.select("div.numb_post2").select("a").attr("href");
		     Expression=Pattern.compile("tid=.*?&");
		     common=Expression.matcher(tid);
		     if (common.find()) {
				tid=common.group(0);
			}
		     for(int i = 1; i <= num; i ++) {	
		    	 String manualurl="";
		    	 manualurl="http://bbs.ifeng.com/viewthread.php?"+tid+"page="+String.valueOf(i);
		    	 nextPageUrlList.add(manualurl);
		     }
		} catch (Exception e) {
			throw mExceptionBuilder.build(e.toString(), data.url());
		}	
		return nextPageUrlList;
		}
		else {
			nextPageUrlList.add(data.url());
			System.out.println(data.url());
			return nextPageUrlList;
		}
	}

	@Override
	public List<BBSReply> parse(PageData data, TopicDataContext context)
			throws BBSParseException {
		return null;
	}

	@Override
	public List<String> getPageLinks(PageData data, TopicDataContext context)
			throws BBSParseException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public TopicDataContext initContext(PageData data) throws BBSParseException {
		TopicDataContext context = new TopicDataContext();
		return null;
	}

	@Override
	public boolean canGetAllLinksOnce() {
		// TODO Auto-generated method stub
		return true;
	}

}
