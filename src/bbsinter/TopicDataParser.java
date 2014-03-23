package bbsinter;

import java.util.List;

import bbsutils.BBSParseException;
import bbsutils.BBSReply;
import bbsutils.BBSTopic;
import bbsutils.PageData;

public interface TopicDataParser {
	public List<BBSReply> parse(PageData data)  throws BBSParseException;
	
	public List<BBSReply> parse(PageData data, TopicDataContext context)  throws BBSParseException;
	
	/**
	 * 含有上下文信息
	 * @param data
	 * @param context
	 * @return
	 */
	public List<String> getPageLinks(PageData data, TopicDataContext context) throws BBSParseException;
	
	/**
	 * 一次性获取所有页的链接
	 * @param data
	 * @return
	 */
	public List<String> getPageLinks(PageData data);
	
	public TopicDataContext initContext(PageData data)  throws BBSParseException;
	
	public boolean canGetAllLinksOnce();
}
