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
import com.altimetrik.pdfParser.reader.TextReaderClass;

public class JdbcClass {
	static Connection conn = null;

	// Scanner scan = new Scanner(System.in);
	public JdbcClass() {
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
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void jdbcrunner() throws SQLException {
		PreparedStatement statement;
		TextReaderClass TextReaderClass = new TextReaderClass();
		try {

			String insertsql = "insert into invoice VALUES('" + TextReaderClass.getInvoiceno() + "','"
					+ TextReaderClass.getInvoicedate() + "','" + TextReaderClass.getCustomerPO() + "','"
					+ TextReaderClass.getAddress() + "','" + TextReaderClass.getTotalInvoice() + "'," + 1 + ")";
			statement = conn.prepareStatement(insertsql);
			statement.execute();
			// System.out.println(insertsql);
		} catch (SQLException e) {
			System.out.println(" Data with this invoice number  " + TextReaderClass.getInvoiceno()
					+ " has already stored So try with some other data");
			System.exit(0);
		}
	}

	public void display(String invoice_no) throws Exception {
		System.out.println("Inside display");
		JdbcClass jdbcobj = new JdbcClass();
		String sql1 = "select * from invoice where invoiceno='" + invoice_no + "'";
		PreparedStatement ps = conn.prepareStatement(sql1);
		ResultSet rs1 = ps.executeQuery(sql1);

		if (rs1.next()) {

			System.out.println(rs1.getString(1));
			System.out.println(rs1.getString(2));
			System.out.println(rs1.getString(3));
			System.out.println(rs1.getString(4));
			System.out.println(rs1.getString(5));
			System.out.println(rs1.getString(6));

			approve(invoice_no);

		} else {
			System.out.println("There is NO Details about this invoice number..");
		}
	}

	public void approve(String invoice_no) throws Exception {
		System.out.println("Do you want to approve " + invoice_no + " invoice ? Y/N");
		Scanner scan = new Scanner(System.in);
		String reply = scan.next();
		if (reply.equalsIgnoreCase("y")) {
			try {
				String sql2 = "update  invoice set status=1 where invoiceno=" + invoice_no;
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.executeUpdate();
				

				System.out.println(sql2);
				System.out.println(ps2.executeUpdate(sql2));

				System.out.println("Approving Invoice.... :)");
				MailSenderClass.sendMail("subanandhini08@gmail.com");
			} catch (NullPointerException e) {
				e.printStackTrace();
				System.out.println("There is no  invoice number " + invoice_no);
				System.exit(0);
			} catch (Exception e) {
				System.out.println("Exception occured");
			} finally {
				scan.close();
			}

		} else if (reply.equalsIgnoreCase("n")) {
			System.out.println("This invoice is not approved...");
		} else {
			System.out.println("Enter a valid input");
		}

	}

}

//		finally
//		{
//			conn.close();
//		}
