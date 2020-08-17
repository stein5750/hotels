package no.ctrlc.hotels.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/home")
@SessionAttributes(names = { "order" })
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        // return view
        return "home";
    }

    @RequestMapping(path = "/home", params = "action=Clear all", method = RequestMethod.POST)
    public String clearForm( SessionStatus sessionStatus) {
        // clear all spring model objects
        sessionStatus.setComplete();
        // return view
        return "redirect:/servlet/home/home";
    }
}