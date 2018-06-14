package com.epam.makedon.agency.controller;

import com.epam.makedon.agency.service.HotelService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    @Setter
    private HotelService hotelService;
}
