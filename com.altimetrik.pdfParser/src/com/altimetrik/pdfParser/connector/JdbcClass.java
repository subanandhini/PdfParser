package com.altimetrik.pdfParser.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import com.altimetrik.pdfParser.emailProcessing.MailSenderClass;
import com.altimetrik.pdfParser.reader.DataReaderClass;
import com.altimetrik.pdfParser.reader.TextReaderClass;

public class JdbcClass {
	static Connection conn = null;
	public JdbcClass(){
		// TODO Auto-generated method stub
	
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String pwd = "hr";
			Properties connectionProps = new Properties();
			connectionProps.put("user", user);
			connectionProps.put("password", pwd);
			conn = DriverManager.getConnection(url, connectionProps);
			System.out.println("Database connected");
			}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		}
			public static void jdbcrunner() throws SQLException {
			PreparedStatement statement;
			try {
			
			String sql = "insert into invoice VALUES('"+TextReaderClass.invoiceno+"','"+TextReaderClass.invoicedate+"','"+TextReaderClass.CustomerPO+"','"+TextReaderClass.Address+"','"+TextReaderClass.TotalInvoice+"',"+1+")";
			statement = conn.prepareStatement(sql);
			statement.execute();
			//System.out.println(sql);
			} catch (SQLException e) {
				System.out.println(" Data with this invoice number  "+TextReaderClass.invoiceno+" has already stored So try with some other data");
			System.exit(0);
			}
			}
			public static void display(String invoice_no) throws Exception {
				String sql1 ="select * from invoice where invoiceno='"+invoice_no+"'";
				//System.out.println(sql1);
				PreparedStatement ps = conn.prepareStatement(sql1);
				ResultSet rs1 = ps.executeQuery(sql1);
				if(rs1.next()==false) {
					System.out.println("There is NO Details about this invoice number..");
				}else {
				int i=1;
				while(rs1.next() && i<=6) {
					System.out.println(rs1.getString(i));
					i++;
					
				}
				approve(invoice_no);
				}}
			public static void approve( String invoice_no) throws Exception {
				System.out.println("Do you want to approve "+invoice_no+" invoice ? Y/N");
				Scanner sc=new Scanner(System.in);
				String reply =sc.next();
				if(reply.equalsIgnoreCase("y")) {
					PreparedStatement statement;
					String sql2 ="update  invoice set status=0 where invoiceno='"+invoice_no+"'";
					try {
					statement = conn.prepareStatement(sql2);
					System.out.println(statement+"   " +sql2);
					statement.execute();
					}
					catch(NullPointerException e) {
						System.out.println("There is no  invoice number "+invoice_no);
						System.exit(0);		}
					System.out.println("Approving Invoice.... :)");
					MailSenderClass.sendMail("subanandhini08@gmail.com"); 
					
				}
				else if (reply.equalsIgnoreCase("n")) {
					System.out.println("This invoice is not approved...");}
				else {
					System.out.println("Enter a valid input");}
				
				}
				
				
			}

		
//		finally
//		{
//			conn.close();
//		}
	


