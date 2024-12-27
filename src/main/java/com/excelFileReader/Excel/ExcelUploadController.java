package com.excelFileReader.Excel;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/excel")
public class ExcelUploadController {

    private final ExcelProcessingService excelProcessingService;

    @Autowired
    public ExcelUploadController(ExcelProcessingService excelProcessingService) {
        this.excelProcessingService = excelProcessingService;
    }

    @PostMapping("/upload")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "No file uploaded";
        }
        try {
            excelProcessingService.processExcelFile(file);
            return "File uploaded and processed successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while processing the file";
        }
    }
}
