package bbsutils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import bbsinter.BBSBlock;
import bbsinter.BBSMiner;
import bbsinter.BBSSplitter;
import bbsinter.PageDataChecker;
import bbsinter.TopicDataContext;
import bbsinter.TopicDataParser;
import bbsinter.TopicListParser;

/**
 * 用于完成基本的测试功能
 * @author mac
 *
 */
public class BBSTestor {
	protected BBSMiner mMiner;
	
	protected BBSSplitter mSpliter;
	
	protected TopicDataParser mDataParser;
	
	protected TopicListParser mListParser;
	
	protected PageDataChecker mDataChecker;
	
	protected BufferedWriter mWriter = null;
	
	//读取块标志位
	protected boolean mIsReadBlock	= true;
	protected boolean mHandleBlock	= true;
	protected boolean mHandleOnSplitted = true;
	
	// 读取帖子标志位
	protected boolean mIsReadTopic	= true;
	protected boolean mHandleTopic	= true;
	
	// 读取用户信息标志位
	protected boolean mIsReadUser 	= false;
	protected boolean mHandleUser	= true;
	
	// 解析帖子列表标志位
	protected boolean mIsReadList	= true;
	protected boolean mHandleList	= true;
	
	// 标志位控制
	public void setReadBlock(boolean flag) {
		this.mIsReadBlock = flag;
	}
	
	public void setHandleBlock(boolean flag) {
		this.mHandleBlock = flag;
	}
	
	public void setHanldeBlockSplitted(boolean flag) {
		this.mHandleOnSplitted = flag;
	}
	
	public void setReadTopic(boolean flag) {
		this.mIsReadTopic = flag;
	}
	
	public void setHandleTopic(boolean flag) {
		this.mHandleTopic = flag;
	}
	
	public void setReadUserInfo(boolean flag) {
		this.mIsReadUser = flag;
	}
	
	public void setHandleUserInfo(boolean flag) {
		this.mHandleUser =  flag;
	}
	
	public void setReadTopicList(boolean flag) {
		this.mIsReadList = flag;
	}
	
	public void setHandleTopicList(boolean flag) {
		this.mHandleList = flag;
	}
	
	/**
	 * 当读到用户信息时被执行。
	 * @param user
	 */
	public void onUserRead(BBSUser user) {
		if (!mHandleUser)
			return ;
	}
	
	/**
	 * 读取到一个帖子后执行
	 * @param topic
	 */
	public void onTopicRead(BBSTopic topic) {
		if (!mHandleTopic)
			return ;
		
		String title = topic.title();
		output(String.format("\n<<<<<<<<帖子：%s\n", title == null ? "" : title));
		
		List<BBSReply> contents = topic.contents();
		int i = 0;
		for (BBSReply reply : contents) {
			output(String.format("第%d楼 >> %s\n%s", ++ i, reply.authorName(), reply.content()));
		}
	}
	
	/**
	 * 读取帖子列表后执行
	 * @param tplst
	 */
	public void onTopicListRead(List<BBSTopic> tplst) {
		if (!mHandleList)
			return ;
		
		output(String.format("\n>>>>>>>>>>>>>>当前帖子列表：%d", tplst.size()));
		
		int i = 0;
		for (BBSTopic topic : tplst) {
			output(String.format("#%d : %s %s", ++ i, topic.title(), topic.url()));
		}
		
		output("<<<<<<<<<<<<<<<<<<<<<<帖子列表结束\n");
	}
	
	/**
	 * 在异常被抛出时调用。
	 * 
	 * 默认的实现是在控制台打印并在当前目录下输出
	 * 
	 * 注意：当前的实现在出现大量错误时容易变得很慢。
	 * 
	 * @param e：被抛出的异常
	 * @return：true表示继续执行，false表示退出。
	 */
	public boolean onException(Exception e) {
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter("./errlogs.txt", true));
			
			System.err.println(e.toString());
			writer.write(e.toString());
			writer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
					writer = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		return true;
	}
	
	public void onBlockRead(int n) {
		output(">>>当前块共计 " + n + " 页面");
	}
	
	/**
	 * 当一个版块被分割后调用。当前什么也不做
	 * @param block
	 */
	public void onBlockSplitted(List<BBSBlock> blocks) {
		if (!mHandleOnSplitted) {
			return ;
		}

		int i = 0;
		output(String.format("<<<<<<<<<<<<<<<<<<<分块信息，共计%d<<", blocks.size()));
		for (BBSBlock bb : blocks)
			output("block" + (++ i) + ":" + bb.format());
		output("<<<<<<<<<<<<<<<<<<<<<<分块信息结束");
	}
	
	public BBSTestor(BBSMiner miner) {
		mMiner		= miner;
		mSpliter 	= miner.getSplitter();
		mDataParser = miner.getTopicDataParser();
		mListParser = miner.getTopicListParser();
		mDataChecker = miner.getPageChecker();
	}
	
	/**
	 * 读取整个板块
	 * @param forumUrl
	 */
	public void readForum(String forumUrl) {
		PageData pData = PageData.getPage(forumUrl);
		if (!mDataChecker.check(pData)) {
			onException(new Exception("版块连接指向的数据未通过验证：" + forumUrl));
			return ;
		}
		List<BBSBlock> blocks = null;
		
		try {
			blocks = mSpliter.split(pData);
			onBlockSplitted(blocks);
			
			if (mIsReadBlock) {
				int counter = 0;
				for (BBSBlock block : blocks) {
					output(String.format("\n\n*********当前处理第 %d 块\n", ++ counter));
					readBlock(block);
				}
			}
			
		} catch (BBSParseException e) {
			if (!onException(e))
				return ;
		}
	}
	
	/**
	 * 读取一个帖子
	 */
	public void readTopic(String topicUrl) {
		if (!mIsReadTopic)
			return ;
		
		BBSTopic topic = new BBSTopic();
		topic.setUrl(topicUrl);
		readTopic(topic);
	}
	
	public void readTopic(BBSTopic topic) {
		if (!mIsReadTopic)
			return ;
		
		String topicUrl = topic.url();
		try {
			PageData pData = PageData.getPage(topicUrl);
			if (!mDataChecker.check(pData)) {
				throw new Exception("数据未通过检查，被跳过：" + topicUrl);
			}
			
			List<String> linkStrings;
			// 是否跳过首页下载。
			boolean skipDownload = true;
			
			if (!mDataParser.canGetAllLinksOnce()) {
				// 不能一次性获取整个帖子的所有页面，需要引用上下文

				// 初始化上下文
				TopicDataContext context = mDataParser.initContext(pData);
				
				// 获取各页的链接
				linkStrings = mDataParser.getPageLinks(pData, context);
				if (!mDataChecker.check(pData)) {
					throw new Exception("数据未通过检查，被跳过：" + topicUrl);
				}
				
				while (linkStrings.size() != 0) {
					for (String link : linkStrings) {
						// 防止第一页被重复下载，节约IO
						if (!skipDownload) {
							pData = PageData.getPage(link);
							if (!mDataChecker.check(pData)) {
								throw new Exception("数据未通过检查，被跳过：" + topicUrl);
							}
						} else
							skipDownload = false;
						
						try {
							List<BBSReply> reps = mDataParser.parse(pData, context);
							topic.appendReply(reps);
						} catch (Exception e) {
							if(!onException(e))
								return ;
						}
					}
					
					linkStrings = mDataParser.getPageLinks(pData, context);
				}
				
			} else {
				linkStrings = mDataParser.getPageLinks(pData);
				
				for (String link : linkStrings) {
					// 防止第一页被重复下载，节约IO
					if (!skipDownload) {
						pData = PageData.getPage(link);
						if (!mDataChecker.check(pData)) {
							throw new Exception("数据未通过检查，被跳过：" + topicUrl);
						}
					} else
						skipDownload = false;
					
					try {
						List<BBSReply> reps = mDataParser.parse(pData);
						topic.appendReply(reps);
					} catch (Exception e) {
						if(!onException(e))
							return ;
					}
				}
				
			}
			
			// 千万不要忘记调用此方法
			topic.trim();
			onTopicRead(topic);
			
		} catch (Exception e) {
			if (!onException(e))
				return ;
		}
	}
	
	/**
	 * 读取一个帖子列表
	 * @param pageUrl
	 */
	public PageData readTopicListPage(String pageUrl) {
		PageData pData = PageData.getPage(pageUrl);
		if (!mDataChecker.check(pData)) {
			onException(new Exception("数据未通过检查，被跳过：" + pageUrl));
			return null;
		}
		
		if (!mIsReadList)
			return pData;
		
		List<BBSTopic> topics = null;
		try {
			topics = mListParser.parse(pData);
			onTopicListRead(topics);
			
			if (mIsReadTopic) {
				int counter = 0;
				for (BBSTopic topic : topics) {
					output(String.format("\n*******处理当前页面中第%d个帖子:%s\n", ++ counter, topic.title()));
					readTopic(topic);
				}
			}
		} catch (BBSParseException e) {
			onException(e);
		}
		
		return pData;
	}
	
	/**
	 * 读取一个论坛分块
	 * @param blockDescriptor:块描述符
	 */
	public void readBlock(String blockDescriptor) {
		if (!mIsReadBlock)
			return ;
		
		BBSBlock block = mSpliter.fromDescriptor(blockDescriptor);
		
		if (block == null) {
			onException(new Exception("无法解析块描述符：" + blockDescriptor));
			return;
		}
			
		readBlock(block);
	}
	
	/**
	 * 读取一个论坛分块
	 * @param block: 待处理的块
	 */
	public void readBlock(BBSBlock block) {
		if (!mIsReadBlock) {
			return ;
		}
		
		PageData pageData = null;
		String url;
		
		int counter = 0;
		while (block.hasNext(pageData)) {
			++ counter;
			if (mIsReadList)
				output(String.format("\n*******进度：块中第%d页\n\n",  counter));
			
			url = block.nextPage(pageData);
			pageData = readTopicListPage(url);
			
//			if (pageData == null)
//				break;
		}
		
		onBlockRead(counter);
	}
	
	public void readUserInfo(String userId) {
		
	}
	
	/**
	 * 设置输出流
	 * @param writer
	 */
	public void setOuputStream(BufferedWriter writer) {
		this.mWriter = writer;
	}
	
	protected void output(String line) {
		if (mWriter == null)
			System.out.println(line);
		else {
			try {
				mWriter.write(line);
				mWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
