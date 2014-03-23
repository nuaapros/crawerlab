package bbs.ifeng;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import bbsinter.TopicListParser;
import bbsutils.BBSParseException;
import bbsutils.BBSTopic;
import bbsutils.PageData;

public class IFengTopicListParser implements TopicListParser{

	@Override
	public List<BBSTopic> parse(PageData data) throws BBSParseException {
		Document doc=data.doc();
		List<BBSTopic> contentTopicLinkInfos=new ArrayList<BBSTopic>();
		BBSTopic tmpInfo;
		String topicUrlString="";
		int viewNumString=0;
		String title="";
		 for(Element ele : doc.getElementsByClass("postTable").get(0).select("tbody > tr")){  
             if(!ele.select("td").toString().equals("")){  
            	 tmpInfo=new BBSTopic();
            	 topicUrlString= "http://bbs.ifeng.com/"+ele.select("td").get(1).select("a").attr("href");  
                 title= ele.select("td").get(1).select("a").text();          
                 viewNumString=Integer.valueOf(ele.select("td").get(3).select("div.digit").get(0).select("span").get(1).text());
                 tmpInfo.setTitle(title);
                 tmpInfo.setUrl(topicUrlString);
                 tmpInfo.setViewCount(viewNumString);
                 contentTopicLinkInfos.add(tmpInfo);
             }  	 		
	 }
			return contentTopicLinkInfos;
	}

}
