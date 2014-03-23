package bbsutils;

/**
 * 异常构造器，用于构造一个BBSParseException并发出
 *
 */
public class ExceptionBuilder {
	// 标签名，标明当前是哪个bbs出错
	private String mTagName;
	
	// 默认的错误消息，如果不提供出错内容将使用该消息作为出错内容
	private String mDefaultErrMsg;
	
	/**
	 * 构造方法，提供构造异常所需要的信息
	 * @param tag：bbs标签
	 * @param defaultMsg：默认的出错消息
	 */
	public ExceptionBuilder(String tag, String defaultMsg) {
		this.mTagName = tag;
		this.mDefaultErrMsg = defaultMsg;
	}
	
	/**
	 * 构造异常并返回
	 * @param errMsg： 异常消息
	 * @param errData：出现异常的数据
	 */
	public BBSParseException build(String errMsg, String errData) {
		return new BBSParseException(mTagName, errMsg, errData);
	}
	
	/**
	 * 构造异常并返回，使用默认消息
	 * @param errData：出现异常的数据
	 */
	public BBSParseException build(String errData) {
		return new BBSParseException(mTagName, mDefaultErrMsg, errData);
	}
}
