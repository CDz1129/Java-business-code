package com.cdz.javacode.pdf;

import cn.hutool.core.io.FileUtil;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author david.chen
 * @date 2022/7/11 11:57
 */
public class OpenhtmltopdfDemo {
    static final String htmlPath= "D:\\Users\\david.chen\\IdeaProjects\\Java-business-code\\src\\main\\resources\\pdf_demo.html";
    public static void main(String[] args) {
        try (OutputStream os = new FileOutputStream("D:\\Users\\david.chen\\IdeaProjects\\Java-business-code\\src\\main\\resources\\test.pdf")) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            String content = FileUtil.readString(htmlPath, Charset.forName("UTF-8"));
            builder.useFont(ResourceUtils.getFile("classpath:fonts/simsun.ttf"), "simsun");
            builder.withHtmlContent(content,htmlPath);
            builder.toStream(os);
            builder.run();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
