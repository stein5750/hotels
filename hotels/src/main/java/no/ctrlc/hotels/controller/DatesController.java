package no.ctrlc.hotels.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import no.ctrlc.hotels.model.Dates;
import no.ctrlc.hotels.model.Order;

@Controller
@RequestMapping("/dates")
@SessionAttributes(names = { "order" })
public class DatesController {

    @RequestMapping(path = "/dates", params = "action=Search", method = RequestMethod.GET)
    public String datesSearch() {
        return "datesSearch";
    }

    @RequestMapping(path = "/dates", params = "action=Select", method = RequestMethod.POST)
    public String datesSearchSelect( @Validated(Dates.Total.class) @ModelAttribute("dates") Dates dates,
            BindingResult bindingResult, @ModelAttribute("order") Order order) {
        if (bindingResult.hasErrors()) {
            return "datesSearch";
        }
        try {
            if (dates.compareTo(order.getDates()) == 0) {
                // the selected dates are already in the order.
                // return view.
                return "redirect:/servlet/home/home";
            }
        } catch (NullPointerException | ClassCastException e) {
        }

        // add dates to the order
        order.setDates(dates);
        // if we change hotel we need to discard the room which belongs to the order.
        order.setRoom(null);
        order.setTotalPrice(null);
        return "redirect:/servlet/home/home";
    }
}