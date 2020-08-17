package no.ctrlc.hotels.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import no.ctrlc.hotels.model.Order;
import no.ctrlc.hotels.model.Room;
import no.ctrlc.hotels.service.HotelsService;

@Controller
@RequestMapping("/room")
@SessionAttributes(names = { "order", "availableRooms" })
public class RoomController {

    @Autowired
    HotelsService hotelsService;

    @RequestMapping(path = "/room", params = "action=Search", method = RequestMethod.POST)
    public String roomSearch( @Validated({ Order.DatesAndHotel.class }) @ModelAttribute("order") Order order,
            BindingResult bindingResult, Model model) {
        // if we do not have a valid hotel and valid dates, we can't search for an
        // available room.
        // Return to home.
        if (bindingResult.hasErrors()) {
            model.addAttribute("error.preRequirement",
                    new String("Choose hotel and dates before searching for a room"));
            return "home";
        } else {
            // get list with available rooms
            ArrayList<Room> availableRooms = (ArrayList<Room>) hotelsService.getAvailableRooms(order.getHotel().getId(),
                    order.getDates().getFromDate(), order.getDates().getToDate());
            // add list of available rooms to the model
            model.addAttribute("availableRooms", availableRooms);
            return "roomSearch";
        }
    }

    @RequestMapping(path = "/room", params = "action=Select", method = RequestMethod.POST)
    public String roomSelect( @RequestParam int roomIndex,
            @ModelAttribute("availableRooms") ArrayList<Room> availableRooms,
            @Validated({ Order.DatesAndHotel.class }) @ModelAttribute("order") Order order,
            BindingResult orderBindingResult, ModelMap map) {
        // if we do not have a valid hotel and valid dates, we can't select a room.
        // Return to home page.
        if (orderBindingResult.hasErrors()) {
            return "redirect:/servlet/home/home";
        }
        // get room from ArrayList of available rooms and add it to the order
        Room room = availableRooms.get(roomIndex);
        order.setRoom(room);
        // update orders total price
        order.calulateTotalPrice();
        // remove list of availableRooms from Model as it's no longer needed in the
        // model,
        // and it should be retrieved from database if needed the database state might
        // change.
        map.remove("availableRooms");
        return "redirect:/servlet/home/home";
    }

    @RequestMapping(path = "/room", params = "action=Details", method = RequestMethod.POST)
    public String showRoomDetails( @Validated({ Order.DatesAndHotel.class }) @ModelAttribute("order") Order order,
            BindingResult bindingResult, Model model) {
        // if we do not have a valid hotel and valid dates, do not show details for a
        // room.
        // Return to home page.
        if (bindingResult.hasErrors()) {
            model.addAttribute("error.preRequirement",
                    new String("Choose hotel and dates before trying to show the rooms details"));
            return "home";
        }
        // if we don't have a room we can't show the details
        if (order.room == null) {
            model.addAttribute("error.preRequirement",
                    new String("Choose a room before trying to show the rooms details"));
            return "home";
        }
        // return view
        return "roomShowDetails";
    }
}