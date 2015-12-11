package com.epam.qa.automation.mail;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Generator 
{
	private String emailAddressDomain, emailAddressLocal;
	
	public void setEmailAddress (String email) {
		String[] aEmail = email.split("@");
		if (aEmail[0].length() > 0 && aEmail[1].length() > 0) {
			emailAddressLocal = aEmail[0];
			emailAddressDomain = "@" + aEmail[1];
		}
	}

	public String getEmailAddress () {
		if(isReady())
			return emailAddressLocal + emailAddressDomain;
		return "";
	}
	
	public String getEmailAddressWithTimeStamp () {
		if (isReady())
			return emailAddressLocal + getTimeStamp() + emailAddressDomain;
		return "";
	}
	
	private boolean isReady () {
		if (emailAddressLocal.length() > 0)
			return true;
		return false;
	}
	
	private String getTimeStamp () {
		SimpleDateFormat sdf = new SimpleDateFormat("-yyyy-MM-dd-hh-mm-ss-S");
		return sdf.format(new Date().getTime());
	}
}
