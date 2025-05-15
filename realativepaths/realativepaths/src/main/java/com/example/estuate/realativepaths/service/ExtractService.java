package com.example.estuate.realativepaths.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ExtractService {
    public ByteArrayResource extractElements(String url) throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);

        List<WebElement> allElements = driver.findElements(By.xpath("//*"));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Elements");

        Row header = sheet.createRow(0);
        String[] cols = {"ID", "Name", "Relative Selectors", "Text", "Relative XPath"};
        for (int i = 0; i < cols.length; i++) {
            header.createCell(i).setCellValue(cols[i]);
        }

        int rowNum = 1;
        for (WebElement element : allElements) {
            Row row = sheet.createRow(rowNum++);

            String tag = element.getTagName();
            String id = element.getAttribute("id");
            String name = element.getAttribute("name");
            String type = element.getAttribute("type");
            String classAttr = element.getAttribute("class");
            String text = element.getText().replaceAll("\\s+", " ").trim();

            String relativeXPath;
            if (id != null && !id.isEmpty()) {
                relativeXPath = "//" + tag + "[@id='" + id + "']";
            } else if (name != null && !name.isEmpty()) {
                relativeXPath = "//" + tag + "[@name='" + name + "']";
            } else if (type != null && !type.isEmpty()) {
                relativeXPath = "//" + tag + "[@type='" + type + "']";
            } else if (classAttr != null && !classAttr.isEmpty()) {
                String firstClass = classAttr.split(" ")[0];
                relativeXPath = "//" + tag + "[contains(@class,'" + firstClass + "')]";
            } else {
                relativeXPath = "//" + tag;
            }

            String relativeSelector = tag;
            if (classAttr != null && !classAttr.isEmpty()) {
                String[] classes = classAttr.trim().split("\\s+");
                for (String cls : classes) {
                    relativeSelector += "." + cls;
                }
            }

            row.createCell(0).setCellValue(id != null ? id : "");
            row.createCell(1).setCellValue(name != null ? name : "");
            row.createCell(2).setCellValue(relativeSelector);
            row.createCell(3).setCellValue(text);
            row.createCell(4).setCellValue(relativeXPath);
        }

        for (int i = 0; i < cols.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Save to disk in "output" folder
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // Create folder if not exists
        }
        File fileOnDisk = new File(outputDir, "elements.xlsx");
        try (FileOutputStream fos = new FileOutputStream(fileOnDisk)) {
            workbook.write(fos);
            System.out.println("File has been extracted");
        }

        // Save to ByteArrayResource for download
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        driver.quit();

        return new ByteArrayResource(out.toByteArray());
    }
}
