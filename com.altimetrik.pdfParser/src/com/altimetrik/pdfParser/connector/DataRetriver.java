package com.altimetrik.pdfParser.connector;
import java.util.Scanner;

public class DataRetriver {
public static void Retrieve() throws Exception {
	
	System.out.println("Enter the invoice number ...");
	Scanner scan=new Scanner(System.in);
	String s=scan.next();
	JdbcClass jdbcobj=new 	JdbcClass();
    jdbcobj.display(s);
	
		
}

}
