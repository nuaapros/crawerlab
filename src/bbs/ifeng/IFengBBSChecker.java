package bbs.ifeng;

import bbsinter.PageDataChecker;
import bbsutils.PageData;

public class IFengBBSChecker implements PageDataChecker{

	@Override
	public boolean check(PageData data) {
		return !(data.doc().title().contains("404错误页面"));
	}

}
