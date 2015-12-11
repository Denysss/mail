package com.epam.qa.automation.mail;

public class Run {
	
	public static void main (String[] args) {
		Gmail gmail = new Gmail();
		gmail.copyAttachmentsFromMessagesToLocalFolder("yourEmail@gmail.com", "yourPassword", "C:\\QA\\Gmail\\Attachments");
	}
}
