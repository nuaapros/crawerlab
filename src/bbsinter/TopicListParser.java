package bbsinter;

import java.util.List;

import bbsutils.BBSParseException;
import bbsutils.BBSTopic;
import bbsutils.PageData;

/**
 * 负责解析帖子列表
 * @author mac
 *
 */
public interface TopicListParser {
	/**
	 * 返回给出帖子列表数据，完成解析并返回帖子列表
	 * @param data：待解析页面的地址
	 * @throws:BBSParseException 解析异常时被抛出
	 */
	public List<BBSTopic> parse(PageData data) throws BBSParseException; 
}
