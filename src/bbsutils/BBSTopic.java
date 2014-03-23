package bbsutils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 代表一个帖子
 * @author mac
 *
 */
public class BBSTopic {
	// 论坛标签
	private String mTag = null;
	
	// 本主题中所有出现用户的用户信息
	private Set<String> mUsers = null;
	
	// 主题内容
	private List< List<BBSReply> > mTmpContent = new ArrayList< List<BBSReply> >();
	
	private List<BBSReply> mContents = null;
	
	// 主题链接
	private String mUrl = null;
	
	// 主题的标题
	private String mTitle = null;
	
	// 帖子查看次数
	private int mViewCount = -1;
	
	public BBSTopic() {
	}
	
	public int viewCount() {
		return mViewCount;
	}
	
	public void setViewCount(int count) {
		this.mViewCount = count;
	}
	
	public void setTag(String tag) {
		this.mTag = tag;
	}
	
	public String tag() {
		return mTag;
	}
	
	public int getReplyCount() {
		return mContents.size();
	}
	
	public List<BBSReply> contents() {
		return mContents;
	}
	
	public BBSReply replay(int no) {
		if (no >= 0 && no < mContents.size())
			return mContents.get(no);
		
		return null;
	}
	
	public String authorId() {
		return mContents.get(0).authorId();
	}
	
	public String authorName() {
		return mContents.get(0).authorName();
	}
	
	public String title() {
		return mTitle;
	}
	
	public void setTitle(String title) {
		mTitle = title;
	}
	
	public void setUrl(String url) {
		this.mUrl = url;
	}
	
	public String url() {
		return mUrl;
	}

	public Set<String> relatedUsers() {
		return mUsers;
	}
	
	public void addReply(int pageno, List<BBSReply> data) {
		if (pageno < 0)
			return ;
		
		while (pageno >= mTmpContent.size())
			mTmpContent.add(null);
		
		mTmpContent.set(pageno, data);
	}
	
	public void appendReply(List<BBSReply> data) {
		mTmpContent.add(data);
	}

	public synchronized void addReplaySafely(int pos, List<BBSReply> data) {
		this.addReplaySafely(pos, data);
	}
	
	public synchronized void appendReplySafely(List<BBSReply> data) {
		this.appendReply(data);
	}

	/**
	 * 在帖子完成后务必调用该方法。
	 */
	public void trim() {
		mContents = new ArrayList<BBSReply>();
		mUsers = new HashSet<String>();
		
		for (List<BBSReply> page : mTmpContent)
			if (page != null)
				for (BBSReply reply : page) {
					mContents.add(reply);
					mUsers.add(reply.authorId());
				}
		
		mTmpContent.clear();
		mTmpContent = null;
	}
}
