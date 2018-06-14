package com.epam.makedon.agency.controller;

import com.epam.makedon.agency.service.TourService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tour")
public class TourController {

    @Autowired
    @Setter
    private TourService tourService;
}
