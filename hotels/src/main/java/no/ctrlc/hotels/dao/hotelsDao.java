package no.ctrlc.hotels.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import no.ctrlc.hotels.model.Customer;
import no.ctrlc.hotels.model.Dates;
import no.ctrlc.hotels.model.Hotel;
import no.ctrlc.hotels.model.Order;
import no.ctrlc.hotels.model.Room;

@Repository("hotelsDao")
public class hotelsDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
		
	public hotelsDao() {}
	
	
	//--TODO not save UUID as String
	
	
	// hotel: get hotel by id
	public Hotel getHotelById(Integer hotelId){
		final String id=hotelId.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from hotels where hotelid = :id";
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new HotelMapper());
	}	
	
	// hotel: get all hotels
	public List<Hotel> hotelsList(){
		final String sql = "select * from hotels";
		List<Hotel> hotels = jdbcTemplate.query( sql, new HotelMapper() );
		return hotels;
	}	
	
	
	// customer: save customer
	public void saveCustomer(@Valid Customer customer) {
		
		final String sql = "insert  into customers(customeruuid, name, address, email, phonenumber) values(?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
					PreparedStatement ps=con.prepareStatement(sql, new String[] {"customeruuid"});
					ps.setString(1, customer.getId().toString());
					ps.setString(2, customer.getName());
					ps.setString(3, customer.getAddress());
					ps.setString(4, customer.getEmailAddress());
					ps.setString(5, customer.getPhoneNumber());
					return ps;
				}
			}// end new PreparedStatementCreator(); 
		);// end update();
	}
	
	
	// customer: get customer by id
	public Customer getCustomerById(UUID customerUUID){
		final String id=customerUUID.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from customers where customeruuid = :id";
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new CustomerMapper());
	}	
	
	
	// customer: get customers by none-unique name
	public List<Customer> getCustomerByName(String name){
		SqlParameterSource namedParameters = new MapSqlParameterSource("wildcardname", "%"+name+"%"); 
		final String sql = "select * from customers where name like :wildcardname order by name asc";
		List<Customer> customers = namedParameterJdbcTemplate.query( sql, namedParameters, new CustomerMapper() );
		return customers;
	}	
	
	// customer: get customer by phonenumber
	public List<Customer>  getCustomerByPhoneNumber(String customerPhoneNumber){
		SqlParameterSource namedParameters = new MapSqlParameterSource("phoneNumber", customerPhoneNumber); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from customers where phoneNumber = :phoneNumber";
		List<Customer> customers = namedParameterJdbcTemplate.query(sql, namedParameters, new CustomerMapper());
		return customers;
	}	
	
	// customer: get customer by email-address
	public List<Customer> getCustomerByEmailAddress(String emailAddress){
		SqlParameterSource namedParameters = new MapSqlParameterSource("emailAddress", emailAddress); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from customers where email = :emailAddress";
		List<Customer> customers = namedParameterJdbcTemplate.query(sql, namedParameters, new CustomerMapper());
		return customers;
	}	
	
    
 // order: save order
 	public boolean saveOrder(Order order) {
 	
		final String sql = "insert  into orders(orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) values(?, ?, ?, ?, ?, ? ,?, ?)";
				
		int res= jdbcTemplate.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
					PreparedStatement ps=con.prepareStatement(sql, new String[] {"orderuuid"});
					ps.setString(1, order.getOrderId().toString());
					ps.setObject(2, order.getOrderCreatedDateTime());
					ps.setString(3, order.customer.getId().toString());
					ps.setInt	(4, order.hotel.getId());
					ps.setInt   (5, order.room.getRoomNumber());
					ps.setObject(6, order.dates.getFromDate().toString());
					ps.setObject(7, order.dates.getToDate().toString());
					ps.setBigDecimal(8, order.getTotalPrice());                
					return ps;
				}
			}// end new PreparedStatementCreator(); 
		);// end update();
		return res > 0 ? true : false;
	}
 	

 	// order: get multiple orders by customerUUID
	public List<Order> getOrdersByCustomerId(UUID customerUUID){
		final String id=customerUUID.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from orders where customeruuid = :id";
		List<Order> orders = namedParameterJdbcTemplate.query( sql,namedParameters, new OrderMapper() );
		return orders;
	}
	

 	// order: get single order by order id
	public Order getOrderByOrderId(UUID orderId){
		final String id=orderId.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from orders where orderuuid = :id";
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new OrderMapper() );
	}	
	
	
	// order: delete order by orderUUID
    public boolean deleteOrderByOrderId(UUID orderUUID) {  	
		final String id=orderUUID.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
		final String sql = "delete from orders where orderuuid = :id";
        int result=namedParameterJdbcTemplate.update(sql, namedParameters);
        return result > 0 ? true : false;
    }


    
	// get available rooms
	public List<Room> getAvailableRooms(Integer hotelId, LocalDate fromDate, LocalDate toDate) {
		// rooms have hotel+roomnumber as primary keys. We need to get the available roomnumbers;
		final String sql = "SELECT *"+
				" FROM rooms r"+
				" WHERE r.hotelid = '"+hotelId+"'"+
				" AND r.roomnumber NOT IN"+
				" ( SELECT o.roomnumber"+
				" 	FROM orders o"+
				" 	WHERE o.hotelid = '"+hotelId+"'"+
				" 	AND ( DATEDIFF('"+fromDate+"',o.todate) < 0 AND DATEDIFF('"+toDate+"',o.fromdate) > 0 )"+
				" )";
		List<Room> availableRooms = jdbcTemplate.query( sql,   new RoomMapper() );
		return availableRooms;
	}
	
	
	private Room getRoom(int hotelId, int roomNumber) {
		final String sql = "SELECT *"+
				" FROM rooms r"+
				" WHERE r.hotelid = "+hotelId+
				" AND r.roomnumber = "+roomNumber;
		Room room=null;
		try {
			room= jdbcTemplate.queryForObject( sql, new RoomMapper() );
		}catch(EmptyResultDataAccessException e) {return null;} // return null if query did not give exactly 1 result 
		return room;
	}
	
	
	// inner class
	private class HotelMapper implements RowMapper<Hotel>{
		
		public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
			Hotel h = new Hotel();
			h.setId(rs.getInt("hotelid"));
			h.setName(rs.getString("hotelname"));
			h.setAddress(rs.getString("hoteladdress"));
			h.setEmailAddress(rs.getString("hotelemailaddress"));
			h.setPhoneNumber(rs.getString("hotelphoneNumber"));
			return h;
		}
	}
	
	// inner class
	private class CustomerMapper implements RowMapper<Customer>{
		
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer c = new Customer();
			c.setId(UUID.fromString(rs.getString("customeruuid")));
			c.setName(rs.getString("name"));
			c.setAddress(rs.getString("address"));
			c.setEmailAddress(rs.getString("email"));
			c.setPhoneNumber(rs.getString("phoneNumber"));
			return c;
		}
	}
	
	
	// inner class
	private class RoomMapper implements RowMapper<Room>{
		
		public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
			Room room=new Room();
			room.setHotelId(rs.getInt("hotelid"));
			room.setRoomNumber(rs.getInt("roomnumber"));
			room.setType(rs.getString("roomtype"));
			room.setRoomPrice(rs.getBigDecimal("roomprice"));
			return room;
		}
	}
		
	// inner class	
	private class OrderMapper implements RowMapper<Order> {
		
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setOrderId(UUID.fromString(rs.getString("orderuuid")));
			order.setOrderCreatedDateTime(rs.getObject("ordercreateddatetime", LocalDateTime.class));
			order.setTotalPrice(rs.getBigDecimal("totalprice"));
			// Customer
			Customer customer = getCustomerById( UUID.fromString( rs.getString("customeruuid" )));
			order.setCustomer( customer);
			// Hotel
			Hotel hotel = getHotelById( rs.getInt("hotelid"));
			order.setHotel(hotel);
			// Room
			Room room = getRoom(order.hotel.getId(), rs.getInt("roomnumber"));
			order.setRoom(room);
			// Dates
			Dates dates = new Dates();
			dates.setFromDate(rs.getObject("fromdate", LocalDate.class));
			dates.setToDate(rs.getObject("todate", LocalDate.class));
			order.setDates(dates);
			return order;
		}
	}	
}