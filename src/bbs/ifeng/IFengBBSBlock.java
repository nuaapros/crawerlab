package bbs.ifeng;

import bbsinter.BBSBlock;
import bbsutils.PageData;

public class IFengBBSBlock implements BBSBlock{

	private int mSize;
	
	private int mBegPageNo;
	
	private String mForumInfo;
	
	private int mCurPageNo;
	public IFengBBSBlock(String forum,int begPageNo,int size)
	{
		this.mForumInfo = forum; 
		this.mSize = size;
		this.mBegPageNo = begPageNo;
		
		mCurPageNo = mBegPageNo;
	}
	@Override
	public String nextPage(PageData data) {
		if (data == null) {
			System.out.println("query first page of this block");
		} else {
			System.out.println("Previous page url = " + data.url());
		}
		
		String ifengUrString;
		//System.out.println(ifengUrString);
		//System.out.println("----");
		if(mCurPageNo-mBegPageNo<mSize)
		{
			mCurPageNo=mCurPageNo+1;
			ifengUrString="http://bbs.ifeng.com/forumdisplay.php?fid=218&page="+String.valueOf(mCurPageNo);
		//	System.out.println("fieng block:"+ifengUrString);
			return ifengUrString;
		}
		else {	
		return null;
		}
	}

	@Override
	public boolean hasNext(PageData data) {
		// TODO Auto-generated method stub
		return  (mCurPageNo - mBegPageNo) < mSize;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mSize;
	}

	@Override
	public String format() {
		// TODO Auto-generated method stub
		return mForumInfo + "," + mBegPageNo + "," + mSize;
	}

	@Override
	public void reset() {
		mCurPageNo = mBegPageNo;
		
	}

}
