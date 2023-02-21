package com.rb.joboffer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JobOfferRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertIntoJobOfferTable(String name, String contact_date, String company, String foreign, String company_type, String company_offer, String job_offer_description) {
        jdbcTemplate.update("INSERT INTO job_offer(name_, contact_date_, company_, foreign_, company_type_, company_offer_, job_offer_description_) VALUES (?,?,?,?,?,?,?)", name, contact_date, company, foreign, company_type, company_offer, job_offer_description);
    }

    public void insertIntoCompanyOfferTable(String company_name, String daily_rate, String short_description, String contact, String address, String location, String atmoskop_description, String company_type, String popularity ) {
        jdbcTemplate.update("INSERT INTO company_offer(company_name_, daily_rate_, short_description_, contact_, address_, location_, atmoskop_description_, company_type_, popularity_) VALUES (?,?,?,?,?,?,?,?,?)", company_name, daily_rate, short_description, contact, address, location, atmoskop_description, company_type, popularity);
    }

    public Integer getSumJobOffer() {
        // last row is empty reason for count(*)-1
        String sql = "SELECT COUNT(*) -1  FROM job_offer";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer getSumCompany() {
        String sql = "SELECT COUNT(*) FROM job_offer WHERE company_type_ ='Firma' ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer getSumAgency() {
        String sql = "SELECT COUNT(*) FROM job_offer WHERE company_type_ ='Agentura' ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer getSumBodyshopAgency() {
        String sql = "SELECT COUNT(*) FROM job_offer WHERE company_type_ ='Outsourcing_Agentura' ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer getSumHRICO() {
        String sql = "SELECT COUNT(*) FROM job_offer WHERE company_type_ ='HR_ICO' ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<String> getComapanyNames() {
        String sql = "SELECT company_ ,COUNT(*) as count FROM job_offer WHERE company_type_ ='Firma' GROUP BY company_ ORDER BY count DESC";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<String> companyNames = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            String name = (String) row.get("company_");
            companyNames.add(name);
        }
        return companyNames;
    }

    public List<String> getAgencyNames() {
        String sql = "SELECT company_ ,COUNT(*) as count  FROM job_offer WHERE company_type_ ='Agentura' GROUP BY company_ ORDER BY count DESC";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<String> agencyNames = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            String name = (String) row.get("company_");
            agencyNames.add(name);
        }
        return agencyNames;
    }

    public List<String> getBodyshopNames() {
        String sql = "SELECT company_ ,COUNT(*) as count FROM job_offer WHERE company_type_ ='Outsourcing_Agentura' GROUP BY company_ ORDER BY count DESC";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<String> bodyshopNames = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            String name = (String) row.get("company_");
            bodyshopNames.add(name);
        }
        return bodyshopNames;
    }

    public List<Map<String, Object>> getMostFrequentCompaniesWithLimitForGraph() {
        String sql = "SELECT company_, COUNT(*) as count FROM job_offer GROUP BY company_ ORDER BY count DESC LIMIT 10";
        return jdbcTemplate.queryForList(sql);
    }
}
