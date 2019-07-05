package com.altimetrik.pdfParser.reader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.altimetrik.pdfParser.connector.DataRetriver;
import com.altimetrik.pdfParser.connector.JdbcClass;
import com.altimetrik.pdfParser.emailProcessing.GetEmailDocument;

public class TextReaderClass {
	static String text;
	public static String invoiceno;
	public static String invoicedate;
	public static String CustomerPO;
	public static String Address;
	public static String TotalInvoice;

	public static void main(String args[]) throws Exception {
		if (GetEmailDocument.name == null) {
			System.out.println("No mail with invoice is available This time \n");
			System.out.println("Do You wish to approve any invoice data..? y/n");

			Scanner sc = new Scanner(System.in);
			String answer = sc.next();
			if (answer.equalsIgnoreCase("y")) {
				System.out.println("Enter the invoice number...");
				String invoice=sc.next();
				JdbcClass.approve(invoice);
			} else {
				System.out.println(" you have no work enjoy your day  .. :)");
				System.exit(0);
			}
		}
		File file = new File("C:\\PdfInvoiceFile\\" + GetEmailDocument.name);
		PDDocument document = PDDocument.load(file);

		PDFTextStripper pdfStripper = new PDFTextStripper();
		text = pdfStripper.getText(document);

//		System.out.println(text);
		DataReaderClass readData = new DataReaderClass();
		invoiceno = readData.findInvoiceNumber("Invoice No");
		System.out.println("invoiceno+  " + invoiceno);
		invoicedate = readData.findInvoiceDate("Invoice Date");
		// System.out.println("invoicedate+ " + invoicedate);
		CustomerPO = readData.findCustomerPO("Customer P.O.");

		Address = readData.findAddress("Ship To");
		TotalInvoice = readData.findTotalInvoice("Invoices not paid");

		try {
			JdbcClass jd = new JdbcClass();
			JdbcClass.jdbcrunner();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataRetriver.Retrieve();
//
//		System.out.println("Invoice Number: " + invoiceno);
//		System.out.println("Invoice Date: " + invoicedate);
//		System.out.println();
//		System.out.println("Customer PO: " + CustomerPO);
//		System.out.println("Address: " + Address);
//		System.out.println("Total Invoice: ");
//		System.out.println(TotalInvoice);

		document.close();

	}
}
