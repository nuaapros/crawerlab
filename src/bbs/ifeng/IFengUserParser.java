package bbs.ifeng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import bbsinter.UserParser;
import bbsutils.BBSUser;
import bbsutils.PageData;

public class IFengUserParser implements UserParser{

	@Override
	public BBSUser parse(PageData data) {
		Document doc=data.doc();
		SimpleDateFormat mDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		BBSUser tmpBbsUser=new BBSUser();
		String userName="";
		String registeTime="";
		String lastLogin="";
		String birthdate="";
		String sex="";
		String level="";
		userName=doc.select("div.text").select("span.tt1").text();
		sex=doc.select("div.picText05").select("h3").get(0).select("span").text();
		birthdate=doc.select("div.picText05").select("h3").get(2).select("span").text();
		level=doc.select("div.picText05").select("h3").get(5).select("span").text();
		registeTime=doc.select("div.picText05").select("h3").get(6).select("span").text();
		lastLogin=doc.select("div.picText05").select("h3").get(7).select("span").text();
		tmpBbsUser.setUserName(userName);	
		tmpBbsUser.setSex(sex);
		try {
			tmpBbsUser.setBirthDay(mDateFormat.parse(birthdate));
			tmpBbsUser.setRegDate(mDateFormat.parse(registeTime));
			tmpBbsUser.setLastLoginDate(mDateFormat.parse(lastLogin));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmpBbsUser.setLevel(level);
		
		return tmpBbsUser; 
	}

	@Override
	public String formUserInfoUrl(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
