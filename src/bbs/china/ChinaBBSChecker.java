package bbs.china;

import bbsinter.PageDataChecker;
import bbsutils.PageData;

public class ChinaBBSChecker implements PageDataChecker{
	/**
	 * 这里简单的查看下页面的title
	 */
	@Override
	public boolean check(PageData data) {
		return !(data.doc().title().contains("404错误页面"));
	}

}
