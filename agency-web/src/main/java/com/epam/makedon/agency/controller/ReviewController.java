package com.epam.makedon.agency.controller;

import com.epam.makedon.agency.service.ReviewService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    @Setter
    private ReviewService reviewService;
}
