package bbsutils;

public class BBSParseException extends Exception {
	
	/**
	 * 记录异常信息
	 * @param tagName : 出现异常的论坛名
	 * @param errMsg ： 异常信息
	 * @param dataInfo：出现异常时的数据
	 */
	public BBSParseException(String tagName, String errMsg, String dataInfo) {
		super(errMsg + ":" + dataInfo);
		
		// 应该记录当前的数据到数据库。。
	}
}
