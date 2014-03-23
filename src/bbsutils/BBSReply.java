package bbsutils;

import java.util.Date;

public class BBSReply {
	// 回复发表日期
	private Date mPostDate = null;
	
	// 层主用户ID
	private String mAuthorId = null;
	
	// 层主昵称。某些论坛中和层主id可以一致
	private String mAuthorName = null;
	
	// 引用的id。当A回复了B时，我们认为A引用了B
	private String mRefUserId = null;
	
	// 层主的内容
	private String mContent = null;
	
	public String content() {
		return mContent;
	}
	
	public void setContent(String content) {
		this.mContent = content;
	}
	
	public String authorId() {
		return mAuthorId;
	}
	
	public void setAuthorId(String id) {
		this.mAuthorId = id;
	}
	
	public String authorName() {
		return mAuthorName;
	}
	
	public void setAuthorName(String authorName) {
		this.mAuthorName = authorName;
	}
	
	public String refUserId() {
		return mRefUserId;
	}
	
	public void setRefUserId(String id) {
		this.mRefUserId = id;
	}
	
	public Date postDate() {
		return mPostDate;
	}
	
	public void setPostDate(Date date) {
		this.mPostDate = date;
	}
}