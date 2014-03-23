package bbsinter;

import bbsutils.BBSUser;
import bbsutils.PageData;

public interface UserParser {
	/**
	 * 解析一个用户数据
	 * @param data
	 * @return
	 */
	public BBSUser parse(PageData data);
	
	/**
	 * 生成用户信息链接
	 * @param uid：要形成的用户信息id
	 * @return
	 */
	public String formUserInfoUrl(String uid);
}
