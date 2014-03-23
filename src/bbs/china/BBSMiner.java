package bbs.china;

import java.util.Map;

import bbsinter.BBSBlock;
import bbsinter.BBSSplitter;
import bbsinter.PageDataChecker;
import bbsinter.TopicDataParser;
import bbsinter.TopicListParser;
import bbsinter.UserParser;

public class BBSMiner implements bbsinter.BBSMiner {
	// 缓存信息表
	// 切记，如果这些类里面定义了数据属性，那么不能这样缓冲！多线程情况下
	// 可能引发故障。
	private ChinaBBSSpliter mSpliter
						= new ChinaBBSSpliter();
	
	private ChinaTopicDataParser mTopicDataParser
						= new ChinaTopicDataParser();
	
	private ChinaUserParser mUserParser
						= new ChinaUserParser();
	
	private ChinaTopicListParser mTopicListParser
						= new ChinaTopicListParser();
	
	private ChinaBBSChecker mDataChecker 
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
		// TODO Auto-generated method stub
		return mTopicListParser;
	}

	@Override
	public BBSSplitter getSplitter() {
		// TODO Auto-generated method stub
		return mSpliter;
	}
	
	@Override
	public PageDataChecker getPageChecker() {
		return mDataChecker;
	}
}
