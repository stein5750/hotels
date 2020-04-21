package no.ctrlc.hotels;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CustomerTest.class, HotelTest.class, OrderTest.class, RoomTest.class })

public class AllTests {

    @BeforeClass
    public static void init() {}
	
}
