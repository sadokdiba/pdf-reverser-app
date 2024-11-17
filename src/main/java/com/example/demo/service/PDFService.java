package com.example.demo.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class PDFService {

    public File reversePDF(MultipartFile file) throws IOException {
        // Load the PDF document from the uploaded file
        PDDocument document = PDDocument.load(file.getInputStream());
        PDPageTree pages = document.getPages();

        // Create a new PDF document with reversed pages
        PDDocument reversedDoc = new PDDocument();
        for (int i = pages.getCount() - 1; i >= 0; i--) {
            reversedDoc.addPage(pages.get(i));
        }

        // Save the reversed PDF to a temporary file
        File outputFile = File.createTempFile("reversed-", ".pdf");
        reversedDoc.save(outputFile);

        // Close documents
        reversedDoc.close();
        document.close();

        return outputFile;
    }
}
