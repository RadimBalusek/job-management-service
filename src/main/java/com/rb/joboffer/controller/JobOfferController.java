package com.rb.joboffer.controller;

import com.rb.joboffer.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @GetMapping("/index")
    public String greet(Model model) {
        model.addAttribute("jobOfferData", jobOfferService.getJobOfferData());
        return "index";
    }

}
