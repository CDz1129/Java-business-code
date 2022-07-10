package com.cdz.javacode.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PDFMain {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {

        //1。 写HTML
        //2。 导出PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("/Users/cdz/Documents/test-pdf.pdf"));
        document.open();
        document.addAuthor("CDZ");
        document.addHeader("这是header","header啊朋友");
//        document.setPageCount(3);
        document.add(new Paragraph("这是一个文本"));
        PdfPTable pdfPTable = new PdfPTable(10);
        document.add(pdfPTable);
        for (int i = 0; i < 30; i++) {
            pdfPTable.addCell("cell"+i);
        }
        PdfPTable pdfPTable1 = new PdfPTable(8);
        document.add(pdfPTable1);
        for (int i = 0; i < 30; i++) {
            pdfPTable1.addCell("table1 cell"+i);
        }

        document.close();
    }
}
