package no.ctrlc.hotels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import no.ctrlc.hotels.model.Hotel;
import no.ctrlc.hotels.model.Order;
import no.ctrlc.hotels.service.HotelsService;

@Controller
@RequestMapping("/hotel")
@SessionAttributes(names = { "order" })
public class HotelController {

    @Autowired
    HotelsService hotelsService;

    @RequestMapping(path = "/hotel", params = "action=Search", method = RequestMethod.GET)
    public String hotelSearch( Model model) {
        // --TODO implement a proper search like in customer search instead of returning
        // a list.
        model.addAttribute("hotels", hotelsService.hotelsList());
        return "hotelSearch";
    }

    @RequestMapping(path = "/hotel", params = { "hotelId", "action=Details" }, method = RequestMethod.GET)
    public String hotelShowDetails( @RequestParam int hotelId, Model model) {
        Hotel hotel = hotelsService.getHotelById(hotelId);
        model.addAttribute("hotel", hotel);
        return ("hotelShowDetails");
    }

    @RequestMapping(path = "/hotel", params = { "hotelId", "action=Select" }, method = RequestMethod.POST)
    public String hotelSelect( @RequestParam("hotelId") int hotelId, @ModelAttribute("order") Order order) {
        Hotel selectedHotel = hotelsService.getHotelById(hotelId);
        // check if the selected hotel exists and already is in the current new order
        try {
            if (selectedHotel != null && selectedHotel.compareTo(order.getHotel()) == 0) {
                // the selected hotel does not exist or is already in the order.
                // return view.
                return "redirect:/servlet/home/home";
            }
        } catch (NullPointerException | ClassCastException e) {
        }

        // add hotel to the order
        order.setHotel(selectedHotel);
        // if we change hotel we need to discard the room which belongs to the current
        // new order.
        order.setRoom(null);
        order.setTotalPrice(null);
        return "redirect:/servlet/home/home";
    }
}