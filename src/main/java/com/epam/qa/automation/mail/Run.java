package com.epam.qa.automation.mail;

public class Run {
	
	public static void main (String[] args) {
		Gmail gmail = new Gmail();
		
		gmail.setLocalPath("C:\\QA\\Gmail\\Attachments");
		gmail.setEmail("yourEmail@gmail.com");
		gmail.setPassword("yourPassword");
		
		gmail.copyAttachmentsFromMessagesToLocalFolder();
	}
}
