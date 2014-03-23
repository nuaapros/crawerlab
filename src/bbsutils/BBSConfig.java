package bbsutils;

import java.util.HashMap;
import java.util.Map;

/**
 * BBS配置信息，用于配置BBS的配置选项，比如是否启用特殊的下载工具
 *
 */
public final class BBSConfig {
	// 存放配置信息，使用哈希表存储
	private Map<String, Object> mConfigData;
	
	// 默认配置信息，大多数BBS可以使用该选项
	private static BBSConfig mDefaultConfig = null;
	
	/**
	 * 获取默认的配置选项，严谨对配置作修改！
	 * 
	 * 使用了单例模式
	 */
	public static BBSConfig getDefaultConfig() {
		if (null == mDefaultConfig) {
			synchronized (BBSConfig.class) {
				if (mDefaultConfig == null) {
					mDefaultConfig = new BBSConfig();
					
					// 此处添加代码用以初始化配置
				}
			}
		}
		
		return mDefaultConfig;
	}
	
	public BBSConfig() {
		mConfigData = new HashMap<String, Object>();
	}
	
	/**
	 * 检查选项表中是否含有指定的关键字
	 * @param key：待检查的关键字
	 * @return：如果包含该关键字将返回true，否则返回false
	 */
	public boolean contains(String key) {
		return mDefaultConfig.contains(key);
	}

	/**
	 * 获取指定关键字指向的选项值
	 * @param key
	 * @return: 如果包含该关键字，将返回关键字代表的值，否则返回null。
	 */
	public Object get(String key) {
		if (mConfigData.containsKey(key))
			return mConfigData.get(key);
		else
			return null;
	}
	
	/**
	 * 填写选项。如果已经存在该选项那么将被覆盖
	 * @param key：选项的关键字
	 * @param value：选项的值
	 */
	public void set(String key, Object value) {
		mConfigData.put(key, value);
	}
	
	/**
	 * 获取选项值，并将之转换为字符串
	 * @param key
	 * @return
	 */
	public String getAsString(String key) {
		if (mConfigData.containsKey(key))
			return mConfigData.get(key).toString();
		else
			 return null;
	}
	
	/**
	 * 获取选项，并将结果转换为指定的数据类型。
	 * 
	 * 注意，不负责检查关键字是否合法以及格式是否正确！如果
	 * 关键字不存在或者格式错误将引发异常！
	 */
	public int getAsInt(String key) {
		return (Integer)mConfigData.get(key); 
	}
	
	public float getAsFloat(String key) {
		return (Float)mConfigData.get(key);
	}
	
	public double getAsDouble(String key) {
		return (Double)mConfigData.get(key);
	}
	
	public long getAsLong(String key) {
		return (Long)mConfigData.get(key);
	}
	
	public char getAsCharacter(String key) {
		return (Character)mConfigData.get(key);
	}
	
	public boolean getAsBoolean(String key) {
		return (Boolean)mConfigData.get(key);
	}
}
