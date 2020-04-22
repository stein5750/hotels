package no.ctrlc.hotels;

	import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//--TODO Change design and use @ModelAttribute(name = "name")


@Controller(value = "mappingController")
@RequestMapping("/mc")
public class MappingController {

	@Autowired 
	Tools tools;
	@Autowired
	Booking booking;

	   @ExceptionHandler
	   public ModelAndView handleException(Exception e) {
		   Map<String, String> modelData = new HashMap<String, String>();
		   modelData.put("errorMsg", e.getMessage());
	      return new ModelAndView ("defaultErrorPage", modelData);
	   }
	
	
	   @RequestMapping(value = "/home", method = RequestMethod.GET)
	   public String redirect() {
	      return "redirect:/servlet/mc/findCustomer";
	   }
	

		// /hotels/servlet/mc/rooms?action=availableRooms&hotelId=1&fromDate="2020-01-02"&todate="2020-01-03"
		@RequestMapping( 
				path="/findCustomer", 
				method = RequestMethod.GET
		)
		public String findCustomer() {
			return "findCustomer";
		}		
		
		
		// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&name=Neil+Armstrong
		@RequestMapping( 
				path="/findCustomer", 
				params = { "customerName"}, 
				method = RequestMethod.GET
		)
		public ModelAndView findCustomerByName(@RequestParam("customerName") String customerName) {
			// validate input
			boolean valid = validateName(customerName); 
			// Map
			Map<String, Object> modelData = new HashMap<String, Object>();
			modelData.put("customerName", customerName);
			// find customer by non unique name
			if (valid) {
				List<Customer> customers = booking.findCustomerByName(customerName);
				modelData.put("customers", customers);
			} else {
				modelData.put("customers",  null);
				modelData.put("error.customerName",  "Invalid characters");			
			}
			// Return Model and View
			return new ModelAndView("findCustomer", modelData);
		}			
		
		
		// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&name=Neil+Armstrong
		@RequestMapping( 
				path="/findCustomer", 
				params = { "customerPhoneNumber"}, 
				method = RequestMethod.GET
		)
		public ModelAndView findCustomerByPhoneNumber(@RequestParam("customerPhoneNumber") String customerPhoneNumber) {
			// validate input
			//-- TODO
			// Map
			Map<String, Object> modelData = new HashMap<String, Object>();
			modelData.put("customerPhoneNumber", customerPhoneNumber);
			// find customer by non unique phonenumber
			List<Customer> customers = booking.findCustomerByPhoneNumber(customerPhoneNumber);
			modelData.put("customers", customers);
			// Return Model and View
			return new ModelAndView("findCustomer", modelData);
		}			
		
		
		// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&customerEmailAddress=test@test.no
		@RequestMapping( 
				path="/findCustomer", 
				params = { "customerEmailAddress"}, 
				method = RequestMethod.GET
				)
		public ModelAndView findCustomerByEmailAddress( @RequestParam("customerEmailAddress") String customerEmailAddress) {
			// validate input
			//-- TODO
			// Map
			Map<String, Object> modelData = new HashMap<String, Object>();
			modelData.put("customerEmailAddress", customerEmailAddress);
			// find customer by non unique email address
			List<Customer> customers = booking.findCustomerByEmailAddress(customerEmailAddress);
			modelData.put("customers", customers);
			// Return Model and View
			return new ModelAndView("findCustomer", modelData);
		}			
		
		
		// Create customer
		//
		@RequestMapping( 
				path="/createCustomer", 
				method = RequestMethod.GET
				)
		public ModelAndView createCustomer() {
			System.out.println("running");
			// Return Model and View
			return new ModelAndView("createCustomer");
		}	
		
		
		// Create customer
		//
		@RequestMapping( 
				path="/saveNewCustomer", 
				params = { "name", "phoneNumber", "address", "emailAddress"}, 
				method = RequestMethod.GET
				)
		public String createCustomer(@RequestParam Map<String, String> params) {
				String name = params.get("name");
				String phoneNumber = params.get("phoneNumber");
				String address = params.get("address");
				String emailAddress = params.get("emailAddress");
				// Validate
				boolean validData = true; //--TODO
				if (validData) {
					Customer c = booking.createCustomer(name, phoneNumber, emailAddress, address);
					String customerId = c.getId().toString();
					return "redirect:/servlet/mc/customer?customerId="+customerId;					
				}
				else {
					return "redirect:/servlet/mc/createCustomer"
							+"?name="+name
							+"&phoneNumber="+phoneNumber
							+"&address="+address
							+"&emailAddress="+emailAddress
							;
				}
				
		}
		
		
		// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&customerEmailAddress=test@test.no
		@RequestMapping( 
				path="/customer", 
				params = { "customerId"}, 
				method = RequestMethod.GET
				)
		public ModelAndView customer( @RequestParam("customerId") String customerId) {
			Map<String, Object> modelData = new HashMap<String, Object>();
			// Validate input
			boolean valid = validateId(customerId);
			// add params
			modelData.put("customerId", customerId);
			// find customer by id
			if (valid) {
				Customer customer = booking.findCustomerById(UUID.fromString(customerId));
				modelData.put("customer", customer);
				// This customers orders
				List<Order> orders = booking.findOrdersByCustomerId(UUID.fromString(customerId));
				modelData.put("orders", orders);
				// Return Model and View
							} else {
				modelData.put("customer", null);
				modelData.put("error.customerId", "Invalid id format");
			}
			// Return Model and View
			return new ModelAndView("customer", modelData);
		}		
	   
		// Delete order and redirect to /customer
		// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&customerEmailAddress=test@test.no
		@RequestMapping( 
				path="/deleteOrder", 
				params = { "customerId", "orderId"}, 
				method = RequestMethod.GET
				)
		public String customer( @RequestParam("customerId") String customerId, @RequestParam("orderId") String orderId) {
			Map<String, Object> modelData = new HashMap<String, Object>();
			// Validate input
			boolean valid = validateId(customerId);
			// delete order by orderId
			if (valid) {
				booking.deleteOrderByOrderId(UUID.fromString(orderId));			
			}			
			// Return Model and View
			return "redirect:/servlet/mc/customer?customerId="+customerId;
		}		
		
			
	// /hotels/servlet/mc/hotels
	@RequestMapping(path="/hotels", method=RequestMethod.GET)
	public ModelAndView hotels( @RequestParam("customerId") String customerId) {
		Map<String, Object> modelData = new HashMap<String, Object>();
		modelData.put("customerId",  customerId);
		modelData.put("hotels", booking.hotelsList());
	return new ModelAndView("hotels", modelData);
	}
	
	
	// /hotels/servlet/mc/?action=hotelInfo&hotelId=1
	@RequestMapping(
		path="/hotel", 
		params = { "customerId","hotelId"},
		method = RequestMethod.GET
	)
	public ModelAndView hotelInfo(@RequestParam Map<String, String> params) {
		String customerId = params.get("customerId");
		Hotel hotel=booking.getHotelById(Integer.parseInt(params.get("hotelId")));
		Map<String, Object> modelData = new HashMap<String, Object>();
		modelData.put("customerId", customerId);
		modelData.put("hotel", hotel);
		
		return new ModelAndView("hotel", modelData);
	}
	
	
	// /hotels/servlet/mc/?action=availableRooms&hotelId=1
	@RequestMapping( 
			path="/rooms",
			params = {"customerId", "hotelId", "!fromdate", "!toDate"},
			method = RequestMethod.GET
	)
	public ModelAndView dates(@RequestParam Map<String, String> params) {
		String customerId = params.get("customerId");
		int hotelId=Integer.parseInt(params.get("hotelId"));
		Hotel hotel=booking.getHotelById(hotelId);
		Map<String, Object> modelData = new HashMap<String, Object>();
		modelData.put("customerId", customerId);
		modelData.put("hotelId",hotelId);
		modelData.put("hotelName",hotel.getName());
		return new ModelAndView("rooms", modelData);
	}	

	
	// /hotels/servlet/mc/rooms?action=availableRooms&hotelId=1&fromDate="2020-01-02"&todate="2020-01-03"
	@RequestMapping( 
			path="/rooms", 
			params = { "customerId", "hotelId","fromDate","toDate"}, 
			method = RequestMethod.GET
	)
	public ModelAndView datesAndRooms(@RequestParam Map<String, String> params) {
		String customerId = params.get("customerId");
		int hotelId=Integer.parseInt(params.get("hotelId"));
		Hotel hotel=booking.getHotelById(hotelId);
		String fromDate=params.get("fromDate");
		String toDate=params.get("toDate");
		Map<String, Object> modelData = new HashMap<String, Object>();
		// validate params
		LocalDate from=null;
		LocalDate to=null;
		try {from=LocalDate.parse(fromDate);}
		catch(DateTimeParseException e) {modelData.put("error.fromDate", "Enter date YYYY-MM-DD");}
		try {to=LocalDate.parse(toDate);}
		catch(DateTimeParseException e) {modelData.put("error.toDate", "Enter date YYYY-MM-DD");}
		if (from == null) {modelData.put("error.fromDate", "Enter date YYYY-MM-DD");}
		if (to == null) {modelData.put("error.toDate", "Enter date YYYY-MM-DD");}
		if(from != null && to != null) {
			if ( tools.ascendingDates(from, LocalDate.now() )) {
				modelData.put("error.fromDate", "from-date cant be in the past");
			}
			if ( ! tools.ascendingDates(from, to)){
				modelData.put("error.toDate","To-date must be after from-date");
			}
		}
		int errors=modelData.size();
		// add data
		modelData.putAll(params);
		modelData.put("hotelName",hotel.getName());
		// if errors
		if (errors > 0) {
			modelData.put("roomList", null);
			return new ModelAndView("rooms", modelData);
		}
		// if no errors
		List<Room> roomList = booking.findAvailableRooms(hotelId, LocalDate.parse(fromDate), LocalDate.parse(toDate));	
		System.out.println("errors:"+errors);
		System.out.println("rooms:"+roomList);
		modelData.put("roomList", roomList);
		return new ModelAndView("rooms", modelData);	
	}	


	// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&customerEmailAddress=test@test.no
	@RequestMapping( 
			path="/newOrder", 
			params = { "customerId", "hotelId","fromDate","toDate", "roomNumber"}, 
			method = RequestMethod.GET
			)
	public ModelAndView showOrder( @RequestParam Map<String, String> params) {
		int hotelId=Integer.parseInt(params.get("hotelId"));
		int roomNumber=Integer.parseInt(params.get("roomNumber"));
		UUID customerUUID = UUID.fromString(params.get("customerId"));
		String fromDate = params.get("fromDate");
		String toDate = params.get("toDate");
		String errorMsg = params.get("errorSave");
		// get room
		Room room = booking.getRoom(hotelId, roomNumber);		
		// Calculate total price
		BigDecimal totalPrice = tools.CalculateTotalPrice(LocalDate.parse(fromDate), LocalDate.parse(toDate), room.getRoomPrice());
		// Map data
		Map<String, Object> modelData = new HashMap<String, Object>();
		modelData.put("hotel", booking.getHotelById(hotelId));
		modelData.put("room", room);
		modelData.put("customer", booking.findCustomerById(customerUUID));
		modelData.put("fromDate", fromDate);
		modelData.put("toDate", toDate);
		modelData.put("totalPrice", totalPrice);
		modelData.put("errorSave", errorMsg);
		//Return Model and View
		return new ModelAndView("newOrder", modelData);
	}			
	
	
	// 
	@RequestMapping( 
			path="/saveOrder", 
			params = { "hotelId","fromDate","toDate", "roomNumber", "customerId", "totalPrice"}, 
			method = RequestMethod.GET
			)
	public String saveOrder( @RequestParam Map<String, String> params) {
		int hotelId=Integer.parseInt(params.get("hotelId"));
		String fromDate = params.get("fromDate");
		String toDate = params.get("toDate");
		LocalDate from = LocalDate.parse(fromDate);
		LocalDate to = LocalDate.parse(toDate);
		int roomNumber=Integer.parseInt(params.get("roomNumber"));
		String customerId = params.get("customerId");
		UUID customerUUID = UUID.fromString(customerId);
		// save order
		UUID orderId = booking.createOrder(customerUUID, hotelId, roomNumber, from, to);
		// if saved successfully
		if ( orderId != null) {
			 return "redirect:/servlet/mc/success?orderId="+orderId.toString();
		}
		else {
			String errorMsg = "An error occured while saving the order";
			return  "redirect:/servlet/mc/newOrder?"
						+"&hotelId="+hotelId
						+"&fromDate="+fromDate
						+"&toDate="+toDate
						+"&roomNumber="+roomNumber
						+"&customerId="+customerId
						+"&errorSave="+errorMsg;
		}
	}

	// /hotels/servlet/mc/orderWasSaved?orderId=someid
	@RequestMapping(
		path="/success", 
		params="orderId",
		method=RequestMethod.GET
	)
	public ModelAndView orderWasSaved( @RequestParam Map<String, String> params) {
		Map<String, String> modelData = new HashMap<String, String>();
		modelData.put("orderId", params.get("orderId"));
	return new ModelAndView("success", modelData);
	}
	
	
	private boolean validateId(String customerId) {
		try { UUID.fromString( customerId );}
		catch(IllegalArgumentException e) {return false;}
		return true;
	}
	
	/*
	 * The regex matches:
	 * 		^$ 			Empty string
	 * 		|			or
	 * 		^\\p{L}+	A string starting with one or more letters from any language.
	 * 		[   ]{0,}	zero or more of the following:
	 * 		\\p{L} 		Any letters from any language. 
	 *		\\p{Z} 		Any kind of whitespace. 
	 * 		\\p{P} 		Any kind of punctuation. 
	 */
	private boolean validateName(String name) {
	    String regex = "^$|^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
	    Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(name);
	    return matcher.find();
	}
}