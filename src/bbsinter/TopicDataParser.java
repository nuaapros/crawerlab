package bbsinter;

import java.util.List;

import bbsutils.BBSParseException;
import bbsutils.BBSReply;
import bbsutils.PageData;
import bbsutils.TopicDataContext;

/**
 * 帖子数据解析器，用于解析一个帖子中的内容
 */
public interface TopicDataParser {
	/**
	 * 解析帖子，返回各个楼的数据
	 * @param data：当前页的数据
	 * @return 当前页中含有的所有楼
	 * @throws BBSParseException
	 */
	public List<BBSReply> parse(PageData data)  throws BBSParseException;
	
	/**
	 * Context版本的帖子解析，注意在解析的同时应该更新上下文
	 * @param data
	 * @param context
	 * @return
	 * @throws BBSParseException
	 */
	public List<BBSReply> parse(PageData data, TopicDataContext context)  throws BBSParseException;
	
	/**
	 * 含有上下文信息的版本，获取当前帖子的各个页的链接。由于不能一次性的获取
	 * 所有帖子的链接，所以该方法会被反复调用，相应的进度信息记录在context中。
	 * 
	 * 在没有后续页面的情况下，可以返回一个空的List<String>表示该帖子已经结束。
	 * @param data：最后读取的帖子页，保证不为空
	 * @param context：上下文信息
	 * @return：帖子各个页的链接
	 */
	public List<String> getPageLinks(PageData data, TopicDataContext context) throws BBSParseException;
	
	/**
	 * 一次性获取帖子所有页的链接
	 * 
	 * 注意：不要省略第一页的链接！即使它当前已经被访问过了
	 * 
	 * @param data：帖子第一页的数据，保证不会为null
	 * @return 帖子所有页的链接
	 */
	public List<String> getPageLinks(PageData data);
	
	/**
	 * 初始化上下文
	 * @param data：帖子第一页的数据，保证不为空
	 * @return：初始化好的帖子
	 * @throws BBSParseException
	 */
	public TopicDataContext initContext(PageData data)  throws BBSParseException;
	
	/**
	 * 询问是否根据帖子第一页的数据就可以得到当前帖子所有页的链接。该函数的返回值将决定
	 * 其后是否使用context版本。
	 * @return
	 */
	public boolean canGetAllLinksOnce();
}
