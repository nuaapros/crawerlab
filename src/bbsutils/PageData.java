package bbsutils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageData {
	// 存放用户自定义数据
	private Map<String, Object> mUserData = null;
	
	// 当前页面的URL
	private String mUrl;
	
	// 当前页面的html数据
	private String mHtml;
	
	// 存放已被解析的结果，以JSoup格式存放
	private Document mDocument;
	
	public PageData(String url, String html) {
		mHtml = html;
		mUrl = url;
		mDocument = null;
	}

	/**
	 * 仅作为测试使用，不建议在正式代码中使用
	 * @param url：待下载数据的URl
	 * @return
	 */
	public static PageData getPage(String url) {
		Document doc;
		
		doc = Jsoup.parse(url);
		return new PageData(url, doc);
	}
	
	public String url() {
		return mUrl;
	}
	
	public PageData(String url, Document doc) {
		mUrl = url;
		mDocument = doc;
		mHtml = mDocument.html();
	}
	
	/**
	 * 设置用户数据
	 * @param key：用户数据的键
	 * @param value：用户数据的值
	 */
	public void setData(String key, Object value) {
		if (mUserData == null)
			mUserData = new HashMap<String, Object>();
		mUserData.put(key, value);
	}
	
	/**
	 * 获取用户数据
	 */
	public Object getData(String key) {
		Object ret;
		
		if (null == mUserData || 
				null == (ret = mUserData.get(key)))
			return null;
		
		return ret;
	}
	
	/**
	 * 返回解析结果
	 */
	public Document doc() {
		if (mDocument == null) {
			mDocument = Jsoup.parse(mHtml);
		}
		
		return mDocument;
	}
	
	/**
	 * 返回HTML数据
	 * @return
	 */
	public String html() {
		return mHtml;
	}
	
}
