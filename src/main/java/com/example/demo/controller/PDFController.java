package com.example.demo.controller;

import com.example.demo.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/reverse")
    public ResponseEntity<?> reversePDF(@RequestParam("file") MultipartFile file) {
        try {
            File reversedFile = pdfService.reversePDF(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reversedFile.getName())
                    .body(new InputStreamResource(new FileInputStream(reversedFile)));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing PDF: " + e.getMessage());
        }
    }
}
