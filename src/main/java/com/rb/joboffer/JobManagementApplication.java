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

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(JobManagementApplication.class, args);
        JobOfferRepository jobOfferRepository = context.getBean(JobOfferRepository.class);

        // Load data from excel to H2 DB
        ClassLoader classLoader = JobManagementApplication.class.getClassLoader();

        File jobOfferfile = new File(classLoader.getResource("files/job_offer.xlsx").getFile());
        String jobOfferfilePath = jobOfferfile.getAbsolutePath();
        initDBfromExcelJobOffer(jobOfferRepository, jobOfferfilePath);
        System.out.println("Excel Job Offer initialized");

        File companyOfferfile = new File(classLoader.getResource("files/company_offer.xlsx").getFile());
        String companyOfferfilePath = companyOfferfile.getAbsolutePath();
        initDBfromExcelCompanyOffer(jobOfferRepository, companyOfferfilePath);
        System.out.println("Excel Company Offer initialized");
    }

    public static void initDBfromExcelJobOffer(JobOfferRepository jobOfferRepository, String filePath) throws Exception {
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
            validationExcelJobOfferBeforeInserting(name, contact_date, company, foreign, company_type, company_offer, job_offer_description);
            jobOfferRepository.insertIntoJobOfferTable(name, contact_date, company, foreign, company_type, company_offer, job_offer_description);
        }
    }

        public static void initDBfromExcelCompanyOffer(JobOfferRepository jobOfferRepository, String filePath) throws IOException {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            int rowNum = 0;
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                // skip first row with captions
                if (rowNum++ == 0) {
                    continue;
                }
                String company_name = row.getCell(0) != null ? row.getCell(0).toString().trim() : "NULL";
                String daily_rate = row.getCell(1) != null ? row.getCell(1).toString().trim() : "NULL";
                String short_description = row.getCell(2) != null ? row.getCell(2).toString().trim() : "NULL";
                String contact = row.getCell(3) != null ? row.getCell(3).toString().trim() : "NULL";
                String address = row.getCell(4) != null ? row.getCell(4).toString().trim() : "NULL";
                String location = row.getCell(5) != null ? row.getCell(5).toString().trim() : "NULL";
                String atmoskop_description = row.getCell(6) != null ? row.getCell(6).toString().trim() : "NULL";
                String company_type = row.getCell(7) != null ? row.getCell(7).toString().trim() : "NULL";
                String popularity = row.getCell(8) != null ? row.getCell(8).toString().trim() : "NULL";
                jobOfferRepository.insertIntoCompanyOfferTable(company_name, daily_rate, short_description, contact, address, location, atmoskop_description, company_type, popularity);
            }
        }
        private static void validationExcelJobOfferBeforeInserting(String name, String contact_date,String company,String foreign,String company_type,String company_offer,String job_offer_description) throws Exception {
            String[] validStrings = {"Firma", "Agentura", "Outsourcing_Agentura", "HR_ICO", ""};
            boolean isValid = false;

            for (String str : validStrings) {
                if (str.equals(company_type)) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                throw new IllegalArgumentException("In excel job Offer please fix row with these parameters. Name:"+ name +",contact_date:"+contact_date+",company:"+company+",foreign:"+foreign+",company_type"+company_type+",company_offer"+company_offer+",job_offer_description:"+job_offer_description);
            }

        }
    }
