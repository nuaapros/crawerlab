package bbs.china;

import java.util.Map;

import bbsinter.BBSBlock;
import bbsinter.BBSSplitter;
import bbsinter.PageDataChecker;
import bbsinter.TopicDataParser;
import bbsinter.TopicListParser;
import bbsinter.UserParser;
import bbsutils.BBSConfig;

public class BBSMiner implements bbsinter.BBSMiner {
	// 缓存信息表
	// 切记，如果这些类里面定义了数据属性，那么不能这样缓冲！多线程情况下
	// 可能引发故障。
	private static ChinaBBSSpliter mSpliter
								= new ChinaBBSSpliter();
	
	private static ChinaTopicDataParser mTopicDataParser
								= new ChinaTopicDataParser();
	
	private static ChinaUserParser mUserParser
								= new ChinaUserParser();
	
	private static ChinaTopicListParser mTopicListParser
								= new ChinaTopicListParser();
	
	private static ChinaBBSChecker mDataChecker 
								= new ChinaBBSChecker();
	
	@Override
	public UserParser getUserParser() {
		return mUserParser;
	}

	@Override
	public TopicDataParser getTopicDataParser() {
		return mTopicDataParser;
	}

	@Override
	public TopicListParser getTopicListParser() {
		return mTopicListParser;
	}

	@Override
	public BBSSplitter getSplitter() {
		return mSpliter;
	}
	
	@Override
	public PageDataChecker getPageChecker() {
		return mDataChecker;
	}

	@Override
	public BBSConfig getConfig() {
		return BBSConfig.getDefaultConfig();
	}
}
