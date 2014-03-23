package bbs.china;

import bbsinter.BBSBlock;
import bbsutils.BBSParseException;
import bbsutils.PageData;

public class ChinaBBSBlock implements BBSBlock {
	private int mSize;
	
	private int mBegPageNo;
	
	private String mForumInfo;
	
	private int mCurPageNo;
	
	public ChinaBBSBlock(String forum, int begPageNo, int size) {
		this.mForumInfo = forum; 
		this.mSize = size;
		this.mBegPageNo = begPageNo;
		
		mCurPageNo = mBegPageNo;
	}
	
	@Override
	public String nextPage(PageData data) {
		if (mCurPageNo - mBegPageNo < mSize)
			return String.format("http://club.china.com/data/threads/type/%s/%d.html", 
							mForumInfo, mCurPageNo ++);
		else {
			return null;
		}
	}

	@Override
	public boolean hasNext(PageData data) {
		return (mCurPageNo - mBegPageNo) < mSize;
	}

	@Override
	public int size() {
		return mSize;
	}

	@Override
	public String format() {
		return mForumInfo + "," + mBegPageNo + "," + mSize;
	}

	@Override
	public void reset() {
		mCurPageNo = mBegPageNo;
	}
}
