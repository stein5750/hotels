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
	phonenumber VARCHAR(50) NOT NULL,
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

-- Hotels
INSERT INTO bookingsystem.hotels (hotelid, hotelname, hoteladdress, hotelemailaddress, hotelphonenumber) VALUES (1, "Artic Hotel", "Ishavsvegen 18", "artic.hotel@test.test","55512345");
INSERT INTO bookingsystem.hotels (hotelid, hotelname, hoteladdress, hotelemailaddress, hotelphonenumber) VALUES (2, "Polar Hotel", "Polarvegen 24", "polar.hotel@test.test","55523456");
INSERT INTO bookingsystem.hotels (hotelid, hotelname, hoteladdress, hotelemailaddress, hotelphonenumber) VALUES (3, "Frost Hotel", "Drivisen 13", "Frost.hotel@test.test","55534567");
-- Artic hotel
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 101, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 102, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 103, "suite",  2000.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 201, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 202, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 203, "suite",  2000.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 301, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 302, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (1, 303, "suite",  2000.00);
-- Polar hotel
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 101, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 102, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 103, "suite",  2000.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 201, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 202, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 203, "suite",  2000.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 301, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 302, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (2, 303, "suite",  2000.00);
-- Frost hotel
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 101, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 102, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 103, "suite",  2000.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 201, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 202, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 203, "suite",  2000.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 301, "single",  850.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 302, "double", 1300.00);
INSERT INTO bookingsystem.rooms (hotelid, roomnumber, roomtype, roomprice) VALUES (3, 303, "suite",  2000.00);
-- customers
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("63875726-76d9-4a0a-bf0e-481b02fbc449", "Neil Armstrong", 	"NASA Headquarters Washington DC 20546-0001 A", "test1@test.test", "55511111");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("05c8acba-c875-4cd8-a108-e389330188fd", "Edwin Aldrin", 	"NASA Headquarters Washington DC 20546-0001 B", "test2@test.test", "55522222");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("a680e0f4-4563-4888-b582-f0344234e7d3", "Charles Conrad", 	"NASA Headquarters Washington DC 20546-0001 C", "test3@test.test", "55533333");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("c63d718f-944b-4556-9221-63f0a5f7bd80", "Alan Shepard Jr", 	"NASA Headquarters Washington DC 20546-0001 D", "test4@test.test", "55544444");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("a8d98a61-2c02-4da6-8d0f-b926cccd1b61", "David Scott", 		"NASA Headquarters Washington DC 20546-0001 E", "test5@test.test", "55555555");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("13fcd783-a688-4c08-8f78-1f5ef2ee0c74", "James Irwin", 		"NASA Headquarters Washington DC 20546-0001 F", "test6@test.test", "55566666");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("8395b645-ab9d-4351-89b0-b5daf1b737e8", "John Young", 		"NASA Headquarters Washington DC 20546-0001 G", "test7@test.test", "55566666");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("b01d9543-70d9-4b98-ba66-c4ff11b037c3", "Charles Duke", 	"NASA Headquarters Washington DC 20546-0001 H", "test8@test.test", "55566666");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("8169c480-f421-4cab-967f-0a782a2a362f", "Eugene Cernan", 	"NASA Headquarters Washington DC 20546-0001 I", "test9@test.test", "55566666");
-- test customers with similar names as Neil Armstrong and same email and phonenumber
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("63875726-76d9-4a0a-bf0e-481b02fbc448", "Neil Armstrong", 	"NASA Headquarters Washington DC 20546-0001 A", "test1@test.test", "55511111");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("6bb88110-20c6-4567-88e8-a44fed2cc0e5", "Neil Young", 		"Toronto Ontario Canada", "test1@test.test", "55511111");
INSERT INTO bookingsystem.customers (customeruuid, name, address, email, phonenumber) VALUES ("72b3f901-b075-4e5a-aeef-85733bb9dce1", "Peggy Armstrong", 	"North Riding United Kingdom","test1@test.test", "55511111");
-- Orders
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("48863637-d0f0-478f-bb98-c4e33680d7fc", "2020-01-01 12:44:45.257549", "63875726-76d9-4a0a-bf0e-481b02fbc449", 1, 101, "2020-05-17", "2020-05-18",  850);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("48863637-d0f0-478f-bb98-c4e33680d714", "2020-01-02 14:14:45.257549", "63875726-76d9-4a0a-bf0e-481b02fbc449", 1, 202, "2020-05-10", "2020-05-12", 2600);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("b7a07551-00b6-417a-a370-ad97236ce481", "2020-01-03 16:24:45.257549", "63875726-76d9-4a0a-bf0e-481b02fbc449", 1, 303, "2020-05-17", "2020-05-19", 4000);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("f45c7bf7-396e-4bb8-b5be-26d40b3e7794", "2020-01-04 17:32:45.257549", "05c8acba-c875-4cd8-a108-e389330188fd", 1, 101, "2020-05-17", "2020-05-18",  850);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("5e6525dd-14fb-4662-affd-28ad67e0a669", "2020-01-05 18:12:45.257549", "c63d718f-944b-4556-9221-63f0a5f7bd80", 2, 301, "2020-05-10", "2020-05-12", 1700);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("54d9d5b1-8edd-4bc1-9b2c-ecf39d394744", "2020-01-06 20:23:45.257549", "a8d98a61-2c02-4da6-8d0f-b926cccd1b61", 2, 203, "2020-05-05", "2020-05-08", 6000);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("b984a05b-fa98-4b76-ae1e-689064cf0614", "2020-01-07 22:34:45.257549", "a8d98a61-2c02-4da6-8d0f-b926cccd1b61", 3, 101, "2020-05-09", "2020-05-10",  850);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("86ecad49-6000-47d2-bcbe-ba36e3eda383", "2019-06-01 04:45:45.257549", "13fcd783-a688-4c08-8f78-1f5ef2ee0c74", 3, 302, "2019-06-01", "2019-06-11", 13000);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("86ecad49-6000-47d2-bcbe-ba36e3eda384", "2020-01-08 04:45:45.257549", "13fcd783-a688-4c08-8f78-1f5ef2ee0c74", 3, 302, "2020-05-01", "2020-05-11", 13000);
INSERT INTO bookingsystem.orders (orderuuid, ordercreateddatetime, customeruuid, hotelid, roomnumber, fromdate, todate, totalprice) VALUES ("86ecad49-6000-47d2-bcbe-ba36e3eda385", "2020-01-01 04:45:45.257549", "13fcd783-a688-4c08-8f78-1f5ef2ee0c74", 3, 302, "2020-12-01", "2020-12-24", 29900);
