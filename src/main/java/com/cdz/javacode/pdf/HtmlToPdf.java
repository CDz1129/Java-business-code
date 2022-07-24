package com.cdz.javacode.pdf;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;

public class HtmlToPdf {

    static String htmlPath = "D:\\Users\\david.chen\\IdeaProjects\\Java-business-code\\src\\main\\resources\\pdf_demo.html";
    static String pdfPath = "D:\\Users\\david.chen\\IdeaProjects\\Java-business-code\\src\\main\\resources\\test.pdf";
    public static void main(String[] args) {
        String content = "";
        File htmlFile = new File(htmlPath);
        File pdfFile = new File(pdfPath);
        if(htmlFile.exists()){
            if(!pdfFile.exists()){
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 开始读取html 文件内容
            BufferedReader br;
            try {
//                br = new BufferedReader(new InputStreamReader(
//                        new FileInputStream(htmlFile), "UTF-8"));
//                String row = "";
//                while ((row = br.readLine()) != null) {
//                    // System.out.println(t);
//                    content += row;
//                }
                content = FileUtil.readString(htmlPath, Charset.forName("UTF-8"));
                htmlTransPdfChinese(pdfPath, content);
            }catch (Exception e){

            }
        }
    }
    public static void htmlTransPdf(InputStream inputStream, OutputStream outputStream) {
        try {
            Document document = new Document();
            // 为该Document创建一个Writer实例
            PdfWriter pdfwriter = PdfWriter.getInstance(document,
                    outputStream);
            pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
            // 打开当前的document
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, inputStream);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @throws
     * @Title: htmlTransPdfChinese
     * @Description: html 转 pdf, 简单中文
     * @param: @param pdfFile
     * @param: @param content
     * @return: void
     */
    public static void htmlTransPdfChinese(String pdfFile, String content) {
        try {
            Document document = new Document();
            // 为该Document创建一个Writer实例
            PdfWriter pdfwriter = PdfWriter.getInstance(document,
                    new FileOutputStream(pdfFile));
            pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
            // 打开当前的document
            document.open();
            // 解决PDF中文不显示
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register("jaspers"+File.separator+"fonts"+File.separator+"simsun.ttc");
            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter,
                    document,
                    new FileInputStream(htmlPath), null, Charset.forName("UTF-8"), fontImp);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
