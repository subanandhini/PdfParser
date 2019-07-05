package com.altimetrik.pdfParser.connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.altimetrik.pdfParser.emailProcessing.MailSenderClass;

public class DataRetriver {
public static void Retrieve() throws Exception {
	
	System.out.println("Enter the invoice number ...");
	Scanner sc=new Scanner(System.in);
	String s=sc.next();
	JdbcClass.display(s);
	
		
}

}
