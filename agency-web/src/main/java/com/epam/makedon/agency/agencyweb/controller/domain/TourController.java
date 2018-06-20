package com.epam.makedon.agency.agencyweb.controller.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.service.TourService;
import com.epam.makedon.agency.agencyweb.domain.Constant;
import com.epam.makedon.agency.agencyweb.domain.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Controller
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService service;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add() {

    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model) {
        List<Tour> tourList = service.findByCriteria(LocalDate.of(2018, Month.JUNE, 19), null, null, null, null, new BigDecimal(10000));
        model.addAttribute(Constant.RESULT, tourList);
        return Page.TOUR.getPage();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove() {

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update() {

    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File newFile = new File(file.getOriginalFilename());
        file.transferTo(newFile);
        List<Tour> tourList = mapper.readValue(newFile, new TypeReference<List<Tour>>(){});
        tourList.forEach(service::add);
        return Constant.REDIRECT + Page.TOUR.getUrl();
    }
}