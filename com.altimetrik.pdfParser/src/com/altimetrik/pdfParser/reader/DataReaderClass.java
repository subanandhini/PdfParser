package com.altimetrik.pdfParser.reader;

public class DataReaderClass {
private String invoiceNumber;
private String invoiceDate;
private String customerPO;
private String address;
private String totalInvoice;

TextReaderClass TextReaderClass= new TextReaderClass();
public String findInvoiceNumber(String invoiceNumber) {
	
	if (TextReaderClass.text.contains(invoiceNumber)) {
			invoiceNumber = TextReaderClass.text.substring(TextReaderClass.text.indexOf(invoiceNumber) + invoiceNumber.length(),
				TextReaderClass.text.indexOf("Invoice Date"));
		return invoiceNumber.trim();
	} else
		return "Invoice number is not found.";
}

public String findInvoiceDate(String invoiceDateStr) {
	if (TextReaderClass.text.contains(invoiceDateStr)) {
		invoiceDate = TextReaderClass.text.substring(TextReaderClass.text.indexOf(invoiceDateStr) + invoiceDateStr.length(),
				TextReaderClass.text.indexOf(invoiceDateStr) + invoiceDateStr.length() + 10);
		return invoiceDate.trim();
	} else
		return "Invoice date not found.";
}

public String findCustomerPO(String customerPOStr) {
	if (TextReaderClass.text.contains(customerPOStr)) {
		customerPO = TextReaderClass.text.substring(TextReaderClass.text.indexOf(customerPOStr) + customerPOStr.length(),
				TextReaderClass.text.indexOf("Account No"));
		return customerPO.trim();
	} else
		return "Customer PO not found.";
}

public String findAddress(String addressStr) {
	if (TextReaderClass.text.contains(addressStr)) {
		try {
		address = TextReaderClass.text.substring(TextReaderClass.text.indexOf(addressStr) + addressStr.length(),
				TextReaderClass.text.indexOf("Remit To"));}
		catch(StringIndexOutOfBoundsException e) {
			System.out.println("");
		}
		finally {
			
		}
		return address.trim();
	} else
		return "Address not found for shiping.";
}

public String findTotalInvoice(String totalInvoiceStr) {
	if (TextReaderClass.text.contains(totalInvoiceStr) && TextReaderClass.text.contains("Total Invoice")) {
		for (int i = TextReaderClass.text.indexOf(totalInvoiceStr); i > 0; i--) {
			if (TextReaderClass.text.charAt(i) == '$') {
				totalInvoice = TextReaderClass.text.substring(i, TextReaderClass.text.indexOf(totalInvoiceStr));
				break;
			}
		}
		return totalInvoice.trim();
	} else
		return "Total Invoice not found.";
}


}
