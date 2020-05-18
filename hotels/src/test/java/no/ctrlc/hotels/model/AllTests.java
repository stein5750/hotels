package no.ctrlc.hotels.model;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	CustomerTest.class, 
	HotelTest.class, 
	DatesTest.class, 
	RoomTest.class, 
	OrderTest.class 
})
public class AllTests {

    @BeforeClass
    public static void init() {}
	
}
