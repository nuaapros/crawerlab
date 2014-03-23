package bbsinter;

import bbsutils.BBSConfig;

/**
 * 论坛挖掘器，其本身不直接提供任何功能，而是提供一组
 * 接口允许用户取得各项服务，本质上属于一个抽象工厂。
 * 
 * 各个实际BBS应该实现该抽象工厂并实现一批具体的产品类。
 * 
 * 此外，具体的实例类中可以考虑使用享元模式
 * @author mac
 *
 */
public interface BBSMiner {
	
	// 下面的获取组件的方法实现中请尽量使用缓冲，不要每次调用都创建一个新实例，会产生
	// 大量的小对象。如果组件类中没有状态属性，请实现为一个static对象，然后直接返回该
	// static对象。
	
	/**
	 * 获取用户信息解析器
	 */
	public UserParser getUserParser();

	/**
	 * 获取当前BBS中的帖子数据解析器，该解析器解析给定的帖子内容页面
	 * @return
	 */
	public TopicDataParser getTopicDataParser();

	/**
	 * 获取当前BBS中的帖子列表解析器，该解析器用于返回给定页面中的帖子
	 * 主题列表以及对应的url。
	 * @return
	 */
	public TopicListParser getTopicListParser();
	
	/**
	 * 获取当前BBS的划分器
	 * @return
	 */
	public BBSSplitter getSplitter();

	/**
	 * 获取页面数据检查器
	 * @return
	 */
	public PageDataChecker getPageChecker();
	
	/**
	 * 获取该BBS的配置信息表，多数论坛可直接返回默认配置，使用BBSConfig.getDefaultConfig()即可。
	 * @return
	 */
	public BBSConfig getConfig();
}
