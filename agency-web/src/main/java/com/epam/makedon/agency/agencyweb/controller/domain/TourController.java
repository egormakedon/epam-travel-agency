package com.epam.makedon.agency.agencyweb.controller.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam BigDecimal cost,
                      @RequestParam Country country, @RequestParam TourType type,
                      @RequestParam Byte stars, @RequestParam String date, @RequestParam String durationString) {
        final String RESULT_TOUR_LIST = "resultTourList";

        LocalDate localDate = null;
        if (!date.isEmpty()) {
            localDate = LocalDate.parse(date);
        }

        Duration duration = null;
        if (!durationString.isEmpty()) {
            duration = Duration.ofDays(Long.valueOf(durationString));
        }

        List<Tour> tourList = service.findByCriteria(localDate, duration, country, stars, type, cost);
        if (tourList.isEmpty()) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        } else {
            model.addAttribute(RESULT_TOUR_LIST, tourList);
        }
        return Page.TOUR.getPage();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(Model model, @RequestParam long id) {
        try {
            Optional<Tour> opt = service.get(id);
            if (opt.isPresent()) {
                if (service.remove(opt.get())) {
                    model.addAttribute(Constant.RESULT, Constant.REMOVED);
                } else {
                    model.addAttribute(Constant.RESULT, Constant.NOT_REMOVED);
                }
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Constant.REDIRECT + Page.TOUR.getUrl();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(RedirectAttributes redirectAttributes, @RequestParam long id,
                         @RequestParam BigDecimal cost, @RequestParam Country country,
                         @RequestParam TourType type, @RequestParam String date,
                         @RequestParam String duration, @RequestParam String description) {
        try {
            Optional<Tour> opt = service.get(id);
            if (opt.isPresent()) {
                Tour tour = opt.get();
                if (cost != null) tour.setCost(cost);
                if (country != null) tour.setCountry(country);
                if (type != null) tour.setType(type);
                if (!description.isEmpty()) tour.setDescription(description);
                if (!date.isEmpty()) tour.setDate(LocalDate.parse(date));
                if (!duration.isEmpty()) tour.setDuration(Duration.ofNanos(Long.valueOf(duration)));
                redirectAttributes.addFlashAttribute(Constant.RESULT, service.update(tour).orElse(null));
            } else {
                redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Constant.REDIRECT + Page.TOUR.getUrl();
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