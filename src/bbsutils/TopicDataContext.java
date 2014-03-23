package bbsutils;

import java.util.HashMap;
import java.util.Map;

/**
 * 帖子上下文，用于跟踪当前帖子的下载进度。多数论坛不需要该结构，
 * 只有帖子的各个页不独立的情况下(比如下一页的链接需要依赖当前页
 * 的链接)的时候才需要该上下文的跟踪。
 *
 */
public class TopicDataContext {
	private Map<String, Object> mData;
	
	public TopicDataContext() {
		mData = new HashMap<String, Object>();
	}
	
	public Object getData(String key) {
		return mData.get(key);
	}
	
	public void setData(String key, Object data) {
		mData.put(key, data);
	}
	
	public int getAsInt(String key) {
		return (Integer)mData.get(key);
	}
	
	public void increase(String key, int value) {
		int ori = (Integer)mData.get(key);
		mData.put(key, ori + value);
	}
}