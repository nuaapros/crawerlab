package bbsinter;

import java.util.HashMap;
import java.util.Map;

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