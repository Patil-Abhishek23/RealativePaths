package com.example.estuate.realativepaths.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.estuate.realativepaths.service.ExtractService;

@RestController
public class ExtractController {
    @Autowired
    private ExtractService extractService;

    @GetMapping("/extract")
    public ResponseEntity<ByteArrayResource> extract(@RequestParam String url) throws Exception {
        ByteArrayResource excelFile = extractService.extractElements(url);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=elements.xlsx")
                .contentLength(excelFile.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);

    }
}

