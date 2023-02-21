package com.rb.joboffer.service.impl;

import com.rb.joboffer.model.JobOfferData;
import com.rb.joboffer.repository.JobOfferRepository;
import com.rb.joboffer.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public JobOfferData getJobOfferData() {
        JobOfferData jobOfferData = new JobOfferData();
        jobOfferData.setSumJobOffer(jobOfferRepository.getSumJobOffer());
        jobOfferData.setCompanyCount(jobOfferRepository.getSumCompany());
        jobOfferData.setAgencyCount(jobOfferRepository.getSumAgency());
        jobOfferData.setHrIcoCount(jobOfferRepository.getSumHRICO());
        jobOfferData.setBodyshopAgencyCount(jobOfferRepository.getSumBodyshopAgency());
        jobOfferData.setCompanyNames(jobOfferRepository.getComapanyNames());
        jobOfferData.setAgencyNames(jobOfferRepository.getAgencyNames());
        jobOfferData.setBodyshopNames(jobOfferRepository.getBodyshopNames());
        jobOfferData.setChartData(getMostFrequentCompaniesWithLimitForGraph());
        return jobOfferData;
    }

    private List<List<Object>> getMostFrequentCompaniesWithLimitForGraph() {
        List<Map<String, Object>> dataRows = jobOfferRepository.getMostFrequentCompaniesWithLimitForGraph();
        List<List<Object>> dataList = dataRows.stream()
                .map(row -> {
                    List<Object> list = new ArrayList<>();
                    list.add(row.get("COMPANY_"));
                    list.add(row.get("COUNT"));
                    return list;
                })
                .collect(Collectors.toList());
        return dataList;
    }
}
