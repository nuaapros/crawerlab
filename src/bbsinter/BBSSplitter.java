package bbsinter;

import java.util.List;

import bbsutils.BBSParseException;
import bbsutils.PageData;

/**
 * 该接口用于实现将一个bbs版块中的帖子划分为若干块，从而降低任务分配
 * 粒度（从整个bbs版块降低到划分的一块），在磁盘IO和负载均衡中取得折
 * 中
 */
public interface BBSSplitter {
	/**
	 * 给定一个版块，划分该版块为若干块并返回
	 * @param url：版块的首页信息
	 * @return 被划分后的块列表
	 */
	public List<BBSBlock> split(PageData data) throws BBSParseException;
	
	/**
	 * 给定一个块描述符，返回一个论坛块
	 * @param desc：解析块描述符
	 * @return
	 */
	public BBSBlock fromDescriptor(String desc);
}
