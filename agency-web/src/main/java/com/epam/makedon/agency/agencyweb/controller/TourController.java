package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.service.TourService;
import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

/**
 * Controller for {@link Tour} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller
@RequestMapping("/tour")

public class TourController {

    private static final String TOUR_LIST = "tourList";
    private static final String TOUR = "tour";

    @Autowired
    private TourService tourService;

    //public

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Tour> tourList = tourService.findAll();

        if (tourList.isEmpty()) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        } else {
            model.addAttribute(TOUR_LIST, tourList);
        }

        model.addAttribute(Constant.URL, Page.INDEX.getUrl());
        return Page.INDEX.getPage();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/findByCriteria", method = RequestMethod.GET)
    public String findByCriteria(Model model, @RequestParam BigDecimal cost,
                      @RequestParam Country country, @RequestParam TourType type,
                      @RequestParam Byte stars, @RequestParam String date, @RequestParam String durationString) {

        LocalDate localDate = null;
        if (!date.isEmpty()) {
            localDate = LocalDate.parse(date);
        }

        Duration duration = null;
        if (!durationString.isEmpty()) {
            duration = Duration.ofDays(Long.valueOf(durationString));
        }

        List<Tour> tourList = tourService.findByCriteria(localDate, duration, country, stars, type, cost);

        if (tourList.isEmpty()) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        } else {
            model.addAttribute(TOUR_LIST, tourList);
        }

        model.addAttribute(Constant.URL, Page.INDEX.getUrl());
        return Page.INDEX.getPage();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String get(Model model, @PathVariable long id) {
        Tour tour = tourService.get(id).get();
        model.addAttribute(TOUR, tour);
        model.addAttribute(Constant.URL, Page.TOUR.getUrl() + "/get/" + id);
        return Page.TOUR.getPage();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(Model model) {

        model.addAttribute(Constant.URL, Page.ADMIN_TOUR.getUrl());
        return Page.ADMIN_TOUR.getPage();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(Model model, @RequestParam long id) {
        try {
            Optional<Tour> opt = tourService.get(id);

            if (opt.isPresent()) {
                if (tourService.remove(opt.get())) {
                    model.addAttribute(Constant.RESULT, Constant.REMOVED);
                } else {
                    model.addAttribute(Constant.RESULT, Constant.NOT_REMOVED);
                }
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        }

        model.addAttribute(Constant.URL, Page.ADMIN_TOUR.getUrl());
        return Constant.REDIRECT + Page.ADMIN_TOUR.getUrl();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(RedirectAttributes redirectAttributes, @RequestParam long id,
                         @RequestParam BigDecimal cost, @RequestParam Country country,
                         @RequestParam TourType type, @RequestParam String date,
                         @RequestParam String duration, @RequestParam String description) {
        try {
            Optional<Tour> opt = tourService.get(id);

            if (opt.isPresent()) {
                Tour tour = opt.get();
                if (cost != null) tour.setCost(cost);
                if (country != null) tour.setCountry(country);
                if (type != null) tour.setType(type);
                if (!description.isEmpty()) tour.setDescription(description);
                if (!date.isEmpty()) tour.setDate(LocalDate.parse(date));
                if (!duration.isEmpty()) tour.setDuration(Duration.ofNanos(Long.valueOf(duration)));

                redirectAttributes.addFlashAttribute(Constant.RESULT, tourService.update(tour).orElse(null));
            } else {
                redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        }

        redirectAttributes.addAttribute(Constant.URL, Page.ADMIN_TOUR.getUrl());
        return Constant.REDIRECT + Page.ADMIN_TOUR.getUrl();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(Model model, @RequestParam MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File newFile = new File(file.getOriginalFilename());
        file.transferTo(newFile);
        List<Tour> tourList = mapper.readValue(newFile, new TypeReference<List<Tour>>(){});
        tourList.forEach(tourService::add);

        model.addAttribute(Constant.URL, Page.ADMIN_TOUR.getUrl());
        return Constant.REDIRECT + Page.ADMIN_TOUR.getUrl();
    }
}