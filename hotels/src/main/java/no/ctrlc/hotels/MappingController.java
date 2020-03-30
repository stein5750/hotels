package no.ctrlc.hotels;

	import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;



// TODO @ModelAttribute(name = "name")


@Controller(value = "mappingController")
@RequestMapping("/mc")
public class MappingController {

	@Autowired
	BookingApp bookingApp;

	   @RequestMapping(value = "/redirect", method = RequestMethod.GET)
	   public String redirect() {
	      return "redirect:/servlet/mc/homepage";
	   }
	
			
	// /hotels/servlet/mc/homepage
	@RequestMapping(path="/homepage", method=RequestMethod.GET)
	public ModelAndView hotelsList() {
		Map<String, List<Hotel>> modelData = new HashMap<String, List<Hotel>>();
		modelData.put("list", bookingApp.hotelsList());
	return new ModelAndView("hotels", modelData);
	}
	
	
	// /hotels/servlet/mc/?action=hotelInfo&hotelId=1
	@RequestMapping(params = "action=hotelInfo", method = RequestMethod.GET)
	public ModelAndView hotelInfo(HttpServletRequest request) {
		Hotel hotel=bookingApp.getHotelById(Integer.parseInt(request.getParameter("hotelId")));
		Map<String, Hotel> modelData = new HashMap<String, Hotel>();
		modelData.put("hotel", hotel);
		return new ModelAndView("hotelInfo", modelData);
	}
	
	
	// /hotels/servlet/mc/?action=availableRooms&hotelId=1
	@RequestMapping( 
			path="/rooms",
			params = {"hotelId", "!fromdate", "!toDate"},
			method = RequestMethod.GET
	)
	public ModelAndView showForm(HttpServletRequest request) {
		Hotel hotel=bookingApp.getHotelById(Integer.parseInt(request.getParameter("hotelId")));
		Map<String, Data> modelData = new HashMap<String, Data>();
		Data data=new Data();
		data.setHotel(hotel);
		modelData.put("data", data);
		return new ModelAndView("rooms", modelData);
	}	
	

	// /hotels/servlet/mc/rooms?action=availableRooms&hotelId=1&fromDate="2020-01-02"&todate="2020-01-03"
	@RequestMapping( 
			path="/rooms", 
			params = { "hotelId","fromDate","toDate"}, 
			method = RequestMethod.GET
	)
	public ModelAndView showFormAndRooms(HttpServletRequest request) {
		
		Integer hotelId=Integer.parseInt(request.getParameter("hotelId"));
		String from=request.getParameter("fromDate");
		String to=request.getParameter("toDate");
		
		List<Room> roomList = bookingApp.findAvailableRooms(hotelId, LocalDate.parse(from), LocalDate.parse(to));			
		
		Data data = new Data();
		data.setHotel(bookingApp.getHotelById(hotelId));
		data.setFromDate(from);
		data.setToDate(to);
		data.setRooms(roomList);
		
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		return new ModelAndView("rooms", modelData);
	}		


	// /hotels/servlet/mc/rooms?action=availableRooms&hotelId=1&fromDate="2020-01-02"&todate="2020-01-03"
	@RequestMapping( 
			path="/findCustomer", 
			params = { "hotelId","fromDate","toDate", "roomNumber"}, 
			method = RequestMethod.GET
	)
	public ModelAndView findCustomer(HttpServletRequest request) {
		Data data = new Data();
		data.setHotel( bookingApp.getHotelById( Integer.parseInt(request.getParameter( "hotelId"))));
		data.setFromDate(request.getParameter("fromDate"));
		data.setToDate(request.getParameter("toDate"));
		data.setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")));
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		return new ModelAndView("findCustomer", modelData);
	}		
	// http://localhost:8080/Hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-05-01&toDate=2020-06-01&roomNumber=102&id=63875726-76d9-4a0a-bf0e-481b02fbc449
	@RequestMapping( 
			path="/findCustomer", 
			params = { "hotelId","fromDate","toDate", "roomNumber", "customerId"}, 
			method = RequestMethod.GET
	)
	public ModelAndView findCustomerById(HttpServletRequest request) {
		Data data = new Data();
		data.setHotel( bookingApp.getHotelById( Integer.parseInt(request.getParameter( "hotelId"))));
		data.setFromDate(request.getParameter("fromDate"));
		data.setToDate(request.getParameter("toDate"));
		data.setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")));
		data.setCustomerId(request.getParameter("customerId"));
		// find customer by id
		UUID customerId = UUID.fromString(request.getParameter("customerId"));
		Customer customer=bookingApp.findCustomerById(customerId);
		List<Customer> customers = new LinkedList<Customer>();
		System.out.println("Customer = "+customer);
		customers.add(customer);
		data.setCustomers(customers);
		// Map modelData
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		// Return Model and View
		return new ModelAndView("findCustomer", modelData);
	}	
	
	// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&name=Neil+Armstrong
	@RequestMapping( 
			path="/findCustomer", 
			params = { "hotelId","fromDate","toDate", "roomNumber", "name"}, 
			method = RequestMethod.GET
	)
	public ModelAndView findCustomerByName(HttpServletRequest request) {
		Data data = new Data();
		data.setHotel( bookingApp.getHotelById( Integer.parseInt(request.getParameter( "hotelId"))));
		data.setFromDate(request.getParameter("fromDate"));
		data.setToDate(request.getParameter("toDate"));
		data.setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")));
		// find customer by non unique name
		String name=request.getParameter("name");
		data.customer.setName(name);
		List<Customer> customers = bookingApp.findCustomerByName(name);
		System.out.println("name search = "+name);
		System.out.println("customers found = "+customers);
		data.setCustomers(customers);
		// Map modelData
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		// Return Model and View
		return new ModelAndView("findCustomer", modelData);
	}			
	
	
	// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&name=Neil+Armstrong
	@RequestMapping( 
			path="/findCustomer", 
			params = { "hotelId","fromDate","toDate", "roomNumber", "phoneNumber"}, 
			method = RequestMethod.GET
	)
	public ModelAndView findCustomerByPhoneNumber(HttpServletRequest request) {
		Data data = new Data();
		data.setHotel( bookingApp.getHotelById( Integer.parseInt(request.getParameter( "hotelId"))));
		data.setFromDate(request.getParameter("fromDate"));
		data.setToDate(request.getParameter("toDate"));
		data.setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")));
		// find customer by non unique phonenumber
		String phoneNumber=request.getParameter("phoneNumber");
		data.customer.setPhoneNumber(phoneNumber);
		List<Customer> customers = bookingApp.findCustomerByPhoneNumber(phoneNumber);
		data.setCustomers(customers);
		// Map modelData
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		// Return Model and View
		return new ModelAndView("findCustomer", modelData);
	}			
	
	
	// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&emailAddress=test@test.no
	@RequestMapping( 
			path="/findCustomer", 
			params = { "hotelId","fromDate","toDate", "roomNumber", "emailAddress"}, 
			method = RequestMethod.GET
			)
	public ModelAndView findCustomerByEmailAddress(HttpServletRequest request) {
		Data data = new Data();
		data.setHotel( bookingApp.getHotelById( Integer.parseInt(request.getParameter( "hotelId"))));
		data.setFromDate(request.getParameter("fromDate"));
		data.setToDate(request.getParameter("toDate"));
		data.setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")));
		// find customer by non unique email address
		String emailAddress = request.getParameter("emailAddress");
		data.customer.setEmailAddress(emailAddress);
		List<Customer> customers = bookingApp.findCustomerByEmailAddress(emailAddress);
		data.setCustomers(customers);
		// Map modelData
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		// Return Model and View
		return new ModelAndView("findCustomer", modelData);
	}			
	
	
	// /hotels/servlet/mc/findCustomer?hotelId=1&fromDate=2020-01-01&toDate=2020-02-01&roomNumber=201&emailAddress=test@test.no
	@RequestMapping( 
			path="/newOrder", 
			params = { "hotelId","fromDate","toDate", "roomNumber", "customerId"}, 
			method = RequestMethod.GET
			)
	public ModelAndView showOrder(HttpServletRequest request) {
		Data data = new Data();
		data.setHotel( bookingApp.getHotelById( Integer.parseInt(request.getParameter( "hotelId"))));
		data.setFromDate(request.getParameter("fromDate"));
		data.setToDate(request.getParameter("toDate"));
		data.setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")));
		// find customer by non unique email address
		String customerId = request.getParameter("customerId");
		data.setCustomer(bookingApp.findCustomerById(UUID.fromString(customerId)));
		// Map modelData
		Map<String, Data> modelData = new HashMap<String, Data>();
		modelData.put("data", data);
		// Return Model and View
		return new ModelAndView("newOrder", modelData);
	}					
	
	
	
	
	
	//TODO kutte fields og bruke objekter?
	public class Data{
		private Hotel hotel=null;
		private Integer hotelId=null;
		private List<Room> rooms=null;
		private String fromDate="yyyy-mm-dd";
		private String toDate="yyyy-mm-dd";
		private Integer roomNumber=null;
		private String name;
		private String customerId="";
		private String phoneNumber="";
		private String emailAddress="";
		private Customer customer = new Customer();
		private List<Customer> customers=null;
		
		public Hotel getHotel() {
			return hotel;
		}
		public void setHotel(Hotel hotel) {
			this.hotel = hotel;
		}
		public Integer getHotelId() {
			return hotelId;
		}
		public void setHotelId(Integer hotelId) {
			this.hotelId = hotelId;
		}
		public List<Room> getRooms() {
			return rooms;
		}
		public void setRooms(List<Room> list) {
			this.rooms = list;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		public Integer getRoomNumber() {
			return roomNumber;
		}
		public void setRoomNumber(Integer roomNumber) {
			this.roomNumber = roomNumber;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		public List<Customer> getCustomers() {
			return customers;
		}
		public void setCustomers(List<Customer> customers) {
			this.customers = customers;
		}			
	}
}