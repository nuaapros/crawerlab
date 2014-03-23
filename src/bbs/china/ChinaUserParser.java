package bbs.china;

import java.util.Date;
import bbsinter.UserParser;
import bbsutils.BBSUser;
import bbsutils.ExceptionBuilder;
import bbsutils.PageData;

public class ChinaUserParser implements UserParser {
	private ExceptionBuilder mExceptionBuilder
					= new ExceptionBuilder("china", "版块分块时错误发生格式错误"); 
	
	@Override
	public BBSUser parse(PageData data) {
		//Document doc = data.doc();
		
		BBSUser user = new BBSUser();
		user.setBirthDay(new Date());
		user.setUserId("123");
		user.setUserName("呵呵");
		
		return user;
		
//		try {
//			String profile1 = doc.select("div.ir2-info").text();
//			String profile2 = doc.select("div.ir1-info").select("div.mood").text();
//			
//			System.out.println(profile1);
//			System.out.println(profile2);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public String formUserInfoUrl(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
