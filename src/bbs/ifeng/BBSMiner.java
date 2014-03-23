package bbs.ifeng;

import bbsinter.BBSConfig;
import bbsinter.BBSSplitter;
import bbsinter.PageDataChecker;
import bbsinter.TopicDataParser;
import bbsinter.TopicListParser;
import bbsinter.UserParser;

public class BBSMiner implements bbsinter.BBSMiner{

	private IFengBBSSpliter mSpliter
		= new IFengBBSSpliter();

	private IFengTopicDataParser mTopicDataParser
		= new IFengTopicDataParser();

	private IFengUserParser mUserParser
		= new IFengUserParser();

	private IFengTopicListParser mTopicListParser
		= new IFengTopicListParser();

	private IFengBBSChecker mDataChecker 
		= new IFengBBSChecker();
	@Override
	public UserParser getUserParser() {
		
		return (UserParser) mUserParser;
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
		return 
	}

}
