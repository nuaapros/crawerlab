package bbsinter;

import bbsutils.PageData;

/**
 * 代表bbs版块的一个分块
 */
public interface BBSBlock {
	/**
	 * 返回本块的下一页链接。
	 * 
	 * @param data ：当前访问页的数据，用来推算块中下一页的链接
	 * 				注意本参数可能为NULL，用来表示请求页为第一页，此时务必返回
	 * 				第一页的url
	 * 
	 * @return：如果已经到块的尾部，将返回null
	 */
	public String nextPage(PageData data);
	
	/**
	 * 判断该块是否已经被遍历完。
	 * @return：如果还有下一页返回true，否则返回false
	 */
	public boolean hasNext(PageData data);
	
//	public boolean canGetAllLinksOnce();
//	
//	public List<String> getAllLinks();
	
	/**
	 * 返回块的大小，即块内的页数
	 * 
	 * 注意该方法并不总是可用的。如果不可用必须返回一个负值
	 * @return
	 */
	public int size();
	
	/**
	 * 将块信息转换为字串，用于写入到结果文件中
	 */
	public String format();

	/**
	 * 重置当前的遍历进度，强制从头开始遍历
	 */
	public void reset();
}
