package bbs.china;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bbsinter.TopicListParser;
import bbsutils.BBSParseException;
import bbsutils.BBSTopic;
import bbsutils.ExceptionBuilder;
import bbsutils.PageData;

public class ChinaTopicListParser implements TopicListParser{
	
	private ExceptionBuilder mExceptionBuilder
						 = new ExceptionBuilder("china", "解析帖子列表时格式出错");
	
	// 正则表达式莫要反复编译创建
	private static Pattern viewnum_p = Pattern.compile("(\\d+)/");
	private static Pattern replynum_p = Pattern.compile("/(\\d+)");
	
	@Override
	public List<BBSTopic> parse(PageData data) throws BBSParseException {
		Document doc = data.doc();
		
		Elements topics;
		try {
			topics = doc.getElementsByClass("threadMain").select("table").select("tr");
		} catch (NullPointerException e) {
			throw mExceptionBuilder.build(data.url());
		}
		
		List<BBSTopic> ret = new ArrayList<BBSTopic>();
		BBSTopic topic;
		
		Elements tmp;
		try {
			for(Element ele : topics){
				if ((tmp = ele.select("td")) == null)
					throw mExceptionBuilder.build(data.url());
					
				if(!tmp.toString().equals("")){
					String title = ele.select("td").get(1).select("a").text();
					String posturl = ele.select("td").get(1).select("a").attr("href");
					String num = ele.select("td").get(3).text();
					String viewnum = null;
					
					Matcher viewnum_m = viewnum_p.matcher(num);
					if(!viewnum_m.find()){
						throw mExceptionBuilder.build("不能找到阅读量", data.url());
					}
					viewnum = viewnum_m.group(1);
					
					topic = new BBSTopic();
					topic.setTitle(title);
					topic.setViewCount(Integer.parseInt(viewnum));
					topic.setUrl("http://club.china.com/" + posturl);
					
					ret.add(topic);
				}
			}
		} catch(NullPointerException e) {
			throw mExceptionBuilder.build(data.url());
		}
		
		return ret;
	}
}
