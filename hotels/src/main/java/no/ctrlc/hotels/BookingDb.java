package no.ctrlc.hotels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository("bookingDb")
public class BookingDb {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
		
	public BookingDb() {}
	
	
	//--TODO not save UUID as String
	
	
	// hotel: find hotel by id
	public Hotel getHotelById(Integer hotelId){
		final String id=hotelId.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from hotels where hotelid = :id";
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new HotelMapper());
	}	
	
	// hotel: find all hotels
	List<Hotel> hotelsList(){
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
	
	
	// customer: find customer by id
	public Customer findCustomerById(UUID customerUUID){
		final String id=customerUUID.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from customers where customeruuid = :id";
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new CustomerMapper());
	}	
	
	
	// customer: find customers by none-unique name
	public List<Customer> findCustomerByName(String name){
		SqlParameterSource namedParameters = new MapSqlParameterSource("wildcardname", "%"+name+"%"); 
		final String sql = "select * from customers where name like :wildcardname order by name asc";
		List<Customer> customers = namedParameterJdbcTemplate.query( sql, namedParameters, new CustomerMapper() );
		return customers;
	}	
	
	// customer: find customer by phonenumber
	public List<Customer>  findCustomerByPhoneNumber(String customerPhoneNumber){
		SqlParameterSource namedParameters = new MapSqlParameterSource("phoneNumber", customerPhoneNumber); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from customers where phoneNumber = :phoneNumber";
		List<Customer> customers = namedParameterJdbcTemplate.query(sql, namedParameters, new CustomerMapper());
		return customers;
	}	
	
	// customer: find customer by email-address
	public List<Customer> findCustomerByEmailAddress(String emailAddress){
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
					ps.setString(3, order.reservingCustomer.getId().toString());
					ps.setInt	(4, order.reservedHotel.getId());
					ps.setInt   (5, order.reservedRoom.getRoomNumber());
					ps.setObject(6, order.getFromDate().toString());
					ps.setObject(7, order.getToDate().toString());
					ps.setBigDecimal(8, order.getTotalPrice());                
					return ps;
				}
			}// end new PreparedStatementCreator(); 
		);// end update();
		return res > 0 ? true : false;
	}
 	

 	// order: find orders by customerUUID
	List<Order> findOrdersByCustomerId(UUID customerUUID){
		final String id=customerUUID.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id); // samme som MapSqlParameterSource().add("id",id);
		final String sql = "select * from orders where customeruuid = :id";
		List<Order> orders = namedParameterJdbcTemplate.query( sql,namedParameters, new OrderMapper() );
		return orders;
	}
	
	
	// order: delete order by orderUUID
    public boolean deleteOrderByOrderId(UUID orderUUID) {  	
		final String id=orderUUID.toString();
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
		final String sql = "delete from orders where orderuuid = :id";
        int result=namedParameterJdbcTemplate.update(sql, namedParameters);
        return result > 0 ? true : false;
    }


    
	// find available rooms
	public List<Room> findAvailableRooms(Integer hotelId, LocalDate fromDate, LocalDate toDate) {
		// rooms have hotel+roomnumber as primary keys. We need to find the available roomnumbers;
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
	
	
	public Room getRoom(int hotelId, int roomNumber) {
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
	
	
	// find if a room exists in the first half of the query, and if it is available in the second half of the query.
	public Room getRoomIfAvailable(Integer hotelId, int roomNumber, LocalDate fromDate, LocalDate toDate) {
		// rooms have hotel+roomnumber as primary keys. We need to find the available roomnumbers;
		final String sql = "SELECT *"+
				" FROM rooms r"+
				" WHERE r.hotelid = '"+hotelId+"'"+
				" AND r.roomnumber ="+roomNumber+
				" AND NOT EXISTS"+
				" ( SELECT o.roomnumber"+
				" 	FROM orders o"+
				" 	WHERE o.hotelid = '"+hotelId+"'"+
				" 	AND o.roomnumber = "+roomNumber+
				" 	AND ( DATEDIFF('"+fromDate+"',o.todate) < 0 AND DATEDIFF('"+toDate+"',o.fromdate) > 0 )"+
				" )";
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
			Order o = new Order();
			o.setOrderId(UUID.fromString(rs.getString("orderuuid")));
			o.setOrderCreatedDateTime(rs.getObject("ordercreateddatetime", LocalDateTime.class));
			o.setReservingCustomer( findCustomerById( UUID.fromString( rs.getString("customeruuid" ))));
			o.setReservedHotel(getHotelById( rs.getInt("hotelid")));
			o.setReservedRoom(getRoom(o.reservedHotel.getId(), rs.getInt("roomnumber")));
			o.setFromDate(rs.getObject("fromdate",LocalDate.class));
			o.setToDate(rs.getObject("todate",LocalDate.class));
			o.setTotalPrice(rs.getBigDecimal("totalprice"));
			return o;
		}
	}	
}