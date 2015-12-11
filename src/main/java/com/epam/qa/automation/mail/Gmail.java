package com.epam.qa.automation.mail;

import java.util.Properties;
import java.io.File;
import java.io.IOException;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Part;

public class Gmail {

	private String host = "imap.gmail.com";
	private String protocol = "imap";
	private String propertyFile = "properties/imap.property";
	private String path, email, password;
	private File folderForDownloadingAttachments;
	private String emailFolder = "inbox";
	
	public void copyAttachmentsFromMessagesToLocalFolder(String userEmail, String userPassword, String localPath) {
		
		path = localPath;
		password = userPassword;
		email = userEmail;
		
		Generator gen = new Generator();
		gen.setEmailAddress(email);
		folderForDownloadingAttachments = new File (path + File.separator + gen.getEmailAddressWithTimeStamp());
		
		Properties props = new Properties();

		try {
			props.load(this.getClass().getResourceAsStream(propertyFile));
			Session session = Session.getDefaultInstance(props, null);

			Store store = session.getStore(protocol);
			store.connect(host, email, password);

			Folder folder = store.getFolder(emailFolder);
			folder.open(Folder.READ_ONLY);

			Message[] arrayMessages = folder.getMessages();
			for (Message message : arrayMessages)
				if (message.getContentType().contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();
					int count = multiPart.getCount();
					for (int i = 0; i < count; i++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()))
							saveAttachmentToLocalFolder(part);
					}
				}

			folder.close(true);
			store.close();

		} catch (Exception e) {
			// log exception and additional details
			e.printStackTrace();
		}
	}
	
	private void saveAttachmentToLocalFolder (MimeBodyPart part) {
		if (!folderForDownloadingAttachments.exists())
			folderForDownloadingAttachments.mkdirs();
		try {
			part.saveFile(new File (folderForDownloadingAttachments.getAbsolutePath() + File.separator + part.getFileName()));
		} catch (IOException e) {
			// log exception and additional details
			e.printStackTrace();
		} catch (MessagingException e) {
			// log exception and additional details
			e.printStackTrace();
		}
	}
}