package com.altimetrik.pdfParser.emailProcessing;


import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;

import javax.mail.Session;
import javax.mail.Store;

import org.apache.pdfbox.io.IOUtils;

import com.altimetrik.pdfParser.reader.TextReaderClass;

//import org.apache.commons.io.IOUtils;

/**
 * This class is used to receive email with attachment.
 * 
 * @author codesjava
 */

public class GetEmailDocument {

public static String name;
	public static void receiveEmail(String pop3Host, String mailStoreType, String userName, String password) {
		// Set properties
		Properties props = new Properties();
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");

		// Get the Session object.
		Session session = Session.getInstance(props);

		try {
			// Create the POP3 store object and connect to the pop store.
			Store store = session.getStore("pop3s");
			store.connect(pop3Host, userName, password);

			// Create the folder object and open it in your mailbox.
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve the messages from the folder object.
			Message[] messages = emailFolder.getMessages();
			System.out.println("Total Message" + messages.length);

			// Iterate the messages
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
				System.out.println("---------------------------------");
				System.out.println("Details of Email Message " + (i + 1) + " :");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);

				// Iterate recipients
				System.out.println("To: ");
				for (int j = 0; j < toAddress.length; j++) {
					System.out.println(toAddress[j].toString());
				}

				// Iterate multiparts
				Multipart multipart = (Multipart) message.getContent();
				for (int k = 0; k < multipart.getCount(); k++) {
					BodyPart bodyPart = multipart.getBodyPart(k);
					if (bodyPart.getDisposition() != null
							&& bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
						System.out.println("file name " + bodyPart.getFileName());
						System.out.println("size " + bodyPart.getSize());
						System.out.println("content type " + bodyPart.getContentType());
						InputStream stream = (InputStream) bodyPart.getInputStream();
						File targetFile = new File("C:\\PdfInvoiceFile\\"+ bodyPart.getFileName());
						name= bodyPart.getFileName();
						System.out.println(targetFile);
						try {
						java.nio.file.Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);}
						catch(FileSystemException e) {
							System.out.println("This Pdf is already parsed");
							System.exit(0);
						}
						 IOUtils.closeQuietly(stream);
					}
				}
			}

			// close the folder and store objects
			emailFolder.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		String pop3Host = "pop.gmail.com";// change accordingly
		String mailStoreType = "995";
		final String userName = "nsuba066@gmail.com";// change accordingly
		final String password = "12345678p=";// change accordingly
      
		// call receiveEmail
		receiveEmail(pop3Host, mailStoreType, userName, password);
		TextReaderClass Readerobj=new TextReaderClass();
		Readerobj.main(args);
	}
}