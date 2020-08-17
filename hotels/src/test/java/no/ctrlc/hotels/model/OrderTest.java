package no.ctrlc.hotels.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class OrderTest {

    private Set<ConstraintViolation<Order>> constraintViolations;
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private Order order;

    @BeforeAll
    private static void setup() {
    }

    @BeforeEach
    private void init() {
        order = beforeEachCreateValidOrder();
    }

    @Test
    void testOrder() {
        constraintViolations = validator.validate(order, Order.Total.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    void testSetOrderId( UUID u) {
        order.setOrderId(u);
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Order id must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testGetOrderId() {
        UUID u = UUID.fromString("e5302e92-d16b-4a8d-91ff-317dcf25efa3");
        order.setOrderId(u);
        assertEquals(u, order.getOrderId());
    }

    @ParameterizedTest
    @NullSource
    void testSetOrderCreatedDateTime( LocalDateTime dt) {
        order.setOrderCreatedDateTime(dt);
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("OrderCreatedDateTime must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testGetOrderCreatedDateTime() {
        LocalDateTime dt = LocalDateTime.of(2020, 11, 20, 21, 30);
        order.setOrderCreatedDateTime(dt);
        assertEquals(dt, order.getOrderCreatedDateTime());
    }

    @Test
    void testCustomerNotNull() {
        order.setCustomer(null);
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("customer must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testHotelNotNull() {
        order.setHotel(null);
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("hotel must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testRoomNotNull() {
        order.setRoom(null);
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("room must not be null", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @NullSource
    void testSetTotalPriceNull( BigDecimal price) {
        order.setTotalPrice(price);
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Total price must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testSetTotalPriceNegative() {
        order.setTotalPrice(new BigDecimal(-1));
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Total price must be positive", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "0.01", "1", "9999999.99" })
    void testSetTotalPriceValids( String price) {
        order.setTotalPrice(new BigDecimal(price));
        constraintViolations = validator.validate(order, Order.Total.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "99999990", "0.990", "99999990.990" })
    void testSetTotalPriceInvalids( String price) {
        order.setTotalPrice(new BigDecimal(price));
        constraintViolations = validator.validate(order, Order.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Price has a limit of 7+2 digits", constraintViolations.iterator().next().getMessage());
    }

    private Order beforeEachCreateValidOrder() {
        order = new Order();
        order.setOrderId(UUID.fromString("7b575239-b6e9-4f23-929f-bf04f1378f2d"));
        order.setOrderCreatedDateTime(LocalDateTime.now());
        order.setCustomer(new Customer());
        order.setHotel(new Hotel());
        order.setDates(new Dates());
        order.setRoom(new Room());
        order.setTotalPrice(new BigDecimal(1000));
        return order;
    }
}
