package com.example.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PdfTest {

    @Test
    public void readPdf() throws IOException {
        String filePath = "C:\\Users\\z1363\\Desktop\\1. MongoDB快速实战与基本原理.pdf";
        PDDocument doc = PDDocument.load(new File(filePath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(doc);
        System.out.println(text);
    }

}
