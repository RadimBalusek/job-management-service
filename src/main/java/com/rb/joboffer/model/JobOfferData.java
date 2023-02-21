package com.rb.joboffer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JobOfferData {

    private Integer sumJobOffer;
    private Integer companyCount;
    private Integer agencyCount;
    private Integer bodyshopAgencyCount;
    private Integer hrIcoCount;
    private List<String> companyNames;
    private List<String> agencyNames;
    private List<String> bodyshopNames;
    private List<List<Object>> chartData;

}
