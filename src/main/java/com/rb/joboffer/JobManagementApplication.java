package com.rb.joboffer;

import com.rb.joboffer.repository.JobOfferRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class JobManagementApplication  {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(JobManagementApplication.class, args);
        JobOfferRepository jobOfferRepository = context.getBean(JobOfferRepository.class);

        // Load data from excel to H2 DB
        ClassLoader classLoader = JobManagementApplication.class.getClassLoader();
        File file = new File(classLoader.getResource("files/job_offer.xlsx").getFile());
        String filePath = file.getAbsolutePath();
        initDBfromExcelJobOffer(jobOfferRepository, filePath);
        System.out.println("DB inicialized");
    }

    public static void initDBfromExcelJobOffer(JobOfferRepository jobOfferRepository, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        int rowNum = 0;
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            // skip first row with captions
            if (rowNum++ == 0) {
                continue;
            }
            String name = row.getCell(0) != null ? row.getCell(0).toString().trim() : "NULL";
            String contact_date = row.getCell(1) != null ? row.getCell(1).toString().trim() : "NULL";
            String company = row.getCell(2) != null ? row.getCell(2).toString().trim() : "NULL";
            String foreign = row.getCell(3) != null ? row.getCell(3).toString().trim() : "NULL";
            String company_type = row.getCell(4) != null ? row.getCell(4).toString().trim() : "NULL";
            String company_offer = row.getCell(5) != null ? row.getCell(5).toString().trim() : "NULL";
            String job_offer_description = row.getCell(6) != null ? row.getCell(6).toString().trim() : "NULL";
            jobOfferRepository.insertIntoTable(name, contact_date, company, foreign, company_type, company_offer, job_offer_description);
        }
    }


}