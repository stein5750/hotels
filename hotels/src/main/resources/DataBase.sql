/* tabell generic jdbc + mariadb-java-client-2.5.3.jar*/

DROP TABLE IF EXISTS bookingsystem.orders; -- orders must be dropped before customers and hotels because of foreign key
DROP TABLE IF EXISTS bookingsystem.customers;
DROP TABLE IF EXISTS bookingsystem.rooms;
DROP TABLE IF EXISTS bookingsystem.hotels;

CREATE TABLE bookingsystem.hotels(
	hotelid INTEGER(2) NOT NULL,
	hotelname VARCHAR(50) NOT NULL, 
	hoteladdress VARCHAR(100) NOT NULL, 
	hotelemailaddress VARCHAR(50) NOT NULL, 
	hotelphonenumber VARCHAR(16) NOT NULL, 
	PRIMARY KEY (hotelid) 
);

CREATE TABLE bookingsystem.rooms(
	hotelid INTEGER(2) NOT NULL,
	roomnumber INTEGER(4) NOT NULL,	-- 0-999
	roomtype VARCHAR(6) NOT NULL, 	-- single, double, suite
	roomprice DECIMAL(9,2) NOT NULL, -- Total of 9 digits (7+2),max 9999999,99
	PRIMARY KEY (hotelid,roomnumber)
);

CREATE TABLE bookingsystem.customers(
	customeruuid CHAR(36) NOT NULL,
	name VARCHAR(100) NOT NULL, 
	address VARCHAR(100) NOT NULL, 
	email VARCHAR(50) NOT NULL,
	phonenumber VARCHAR(50),
	PRIMARY KEY (customeruuid)
);

CREATE TABLE bookingsystem.orders(
	orderuuid CHAR(36) NOT NULL,
	ordercreateddatetime DATETIME NOT NULL,
	customeruuid CHAR (36) NOT NULL,	
	hotelid INTEGER(2) NOT NULL,
	roomnumber INT(3) NOT NULL,	
	fromdate DATE NOT NULL,
	todate DATE NOT NULL,
	totalprice DECIMAL(11,2) NOT NULL, -- max 999.999.999,99 NOT linked directly to room prices, since they might vary.
	PRIMARY KEY (orderuuid),
	FOREIGN KEY (customeruuid) REFERENCES bookingsystem.customers(customeruuid), 
	FOREIGN KEY (hotelid) REFERENCES bookingsystem.hotels(hotelid), 
	FOREIGN KEY (hotelid, roomnumber) REFERENCES bookingsystem.rooms(hotelid, roomnumber) 
);