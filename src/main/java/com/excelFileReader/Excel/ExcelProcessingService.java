package com.excelFileReader.Excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.usermodel.PictureData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelProcessingService {

    private final QuestionRepository questionRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExcelProcessingService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ExcelProcessingService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void processExcelFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Map<String, PictureData> pictureDataMap = getPictureDataMap(sheet);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                try {
                    Question question = new Question();

                    question.setCode(getCellValue(row.getCell(0)));
                    question.setQuestionText(getCellValue(row.getCell(1)));
                    question.setQuestionImagePath(saveImage(row.getRowNum(), 2, pictureDataMap));

                    question.setAnswer1Text(getCellValue(row.getCell(3)));
                    question.setAnswer1ImagePath(saveImage(row.getRowNum(), 4, pictureDataMap));

                    question.setAnswer2Text(getCellValue(row.getCell(5)));
                    question.setAnswer2ImagePath(saveImage(row.getRowNum(), 6, pictureDataMap));

                    question.setAnswer3Text(getCellValue(row.getCell(7)));
                    question.setAnswer3ImagePath(saveImage(row.getRowNum(), 8, pictureDataMap));

                    question.setAnswer4Text(getCellValue(row.getCell(9)));
                    question.setAnswer4ImagePath(saveImage(row.getRowNum(), 10, pictureDataMap));

                    questionRepository.save(question);

                } catch (Exception e) {
                    logger.error("Error processing row " + row.getRowNum(), e);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading Excel file", e);
        }
    }

    private String getCellValue(Cell cell) {
        return (cell != null) ? cell.toString().trim() : "";
    }

    private String saveImage(int row, int col, Map<String, PictureData> pictureDataMap) {
        String cellAddress = new CellAddress(row, col).formatAsString();
        if (pictureDataMap.containsKey(cellAddress)) {
            PictureData pictureData = pictureDataMap.get(cellAddress);
            String extension = pictureData.suggestFileExtension();
            String fileName = "image_" + row + "_" + col + "." + extension;

            File outputDir = new File(uploadDir);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File outputFile = new File(uploadDir, fileName);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(pictureData.getData());
                return outputFile.getAbsolutePath();
            } catch (IOException e) {
                logger.error("Error saving image for " + cellAddress, e);
            }
        }
        return null;
    }

    private Map<String, PictureData> getPictureDataMap(Sheet sheet) {
        Map<String, PictureData> pictureDataMap = new HashMap<>();

        if (sheet instanceof XSSFSheet) {
            XSSFDrawing drawing = ((XSSFSheet) sheet).getDrawingPatriarch();
            if (drawing != null) {
                drawing.getShapes().forEach(shape -> {
                    if (shape instanceof Picture) {
                        Picture picture = (Picture) shape;
                        ClientAnchor anchor = picture.getClientAnchor();
                        String cellAddress = new CellAddress(anchor.getRow1(), anchor.getCol1()).formatAsString();
                        pictureDataMap.put(cellAddress, picture.getPictureData());
                    }
                });
            }
        }
        return pictureDataMap;
    }
}
