package bbsutils;

import java.util.Date;

public class BBSUser {
	
	// 注意，有些id和昵称不区分的论坛，我们令两者相同
	// 用户id
	private String mUserId;
	
	// 用户昵称
	private String mUserName;
	
	// 注册时间
	private Date mRegDate;
	
	// 最后登录时间
	private Date mLastLoginDate;
	
	// 生日
	private Date mBirthDay;
	
	// 地区
	//private String mProvince;
	
	// 用户等级
	private String mLevel;
	
	// 用户经验值
	private int mScore;
	
	// 用户性别
	private String mSex;
	
	public String useId() {
		return mUserId;
	}
	
	public void setUserId(String id) {
		this.mUserId = id;
	}
	
	public String userName() {
		return this.mUserName;
	}
	
	public void setUserName(String name) {
		this.mUserName = name;
	}
	
	public void setScore(int score) {
		this.mScore = score;
	}
	
	public int score() {
		return this.mScore;
	}
	
	public String sex() {
		return mSex;
	}
	
	public void setSex(String sex) {
		this.mSex = sex;
	}
	
	public void setRegDate(Date regt) {
		this.mRegDate = regt;
	}
	
	public Date regDate() {
		return this.mRegDate;
	}
	
	public void setLastLoginDate(Date lstlogin) {
		this.mLastLoginDate = lstlogin;
	}
	
	public Date lastLoginDate() {
		return mLastLoginDate;
	}
	
	public Date birthDay() {
		return mBirthDay;
	}
	
	public void setBirthDay(Date birthday) {
		this.mBirthDay = birthday;
	}
	
	public void setLevel(String level) {
		this.mLevel = level;
	}
	
	public String level() {
		return this.mLevel;
	}
}
