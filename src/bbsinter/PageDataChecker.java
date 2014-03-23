package bbsinter;

import bbsutils.PageData;

/**
 * 该类用于检查页面是否是合法的故障，比如404错误，对方论坛服务器故障等。
 * @author mac
 *
 */
public interface PageDataChecker {
	/**
	 * 检测页面是否正常，如果错误将跳过该页的处理。
	 * 
	 * 一般论坛直接返回true即可。只有像中华网论坛这种动不动爆出404错误的需要单独设置。
	 * @param data：待检查数据
	 * @return
	 */
	public boolean check(PageData data);
}
