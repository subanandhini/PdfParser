package com.altimetrik.pdfParser.reader;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.altimetrik.pdfParser.emailProcessing.GetEmailDocument;
import com.altimetrik.pdfParser.connector.DataRetriver;
import com.altimetrik.pdfParser.connector.JdbcClass;

public class TextReaderClass {
	 String text;
	private  String invoiceno;
	private  String invoicedate;
	private  String CustomerPO;
	private  String Address;
	private  String TotalInvoice;
	Scanner scan = new Scanner(System.in);
	JdbcClass jdbcobj = new JdbcClass();


	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getCustomerPO() {
		return CustomerPO;
	}

	public void setCustomerPO(String customerPO) {
		CustomerPO = customerPO;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getTotalInvoice() {
		return TotalInvoice;
	}

	public void setTotalInvoice(String totalInvoice) {
		TotalInvoice = totalInvoice;
	}
	public void main(String args[]) throws Exception {
		if (GetEmailDocument.name == null) {
			System.out.println("No mail with invoice is available This time \n");
			System.out.println("Do You wish to approve any invoice data..? y/n");
			String answer = scan.next();
			if (answer.equalsIgnoreCase("y")) {
				System.out.println("Enter the invoice number...");
				String invoice = scan.next();
				JdbcClass jdbcobj = new JdbcClass();
				synchronized (jdbcobj) {
					jdbcobj.display(invoice);
				}
			} else {
				System.out.println(" you have no work enjoy your day  .. :)");
				System.exit(0);
			}
		} else {
			File file = new File("C:\\PdfInvoiceFile\\" + GetEmailDocument.name);
			PDDocument document = PDDocument.load(file);

			PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document);

			DataReaderClass readData = new DataReaderClass();
			setInvoiceno(readData.findInvoiceNumber("Invoice No"));
			setInvoicedate(readData.findInvoiceDate("Invoice Date"));
			setCustomerPO(readData.findCustomerPO("Customer P.O."));
			setAddress(readData.findAddress("Ship To"));
			setTotalInvoice(readData.findTotalInvoice("Invoices not paid"));

			try {

				jdbcobj.jdbcrunner();
			} catch (SQLException e) {

				e.printStackTrace();
			}

			DataRetriver.Retrieve();

			document.close();
		}
	}

}
