package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Order mockOrder;
    private Map<String, String> emptyPaymentData;

    @BeforeEach
    void setUp() {
        mockOrder = new Order("order-789", null, 1234567890L, "TestUser");
        emptyPaymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentInstance() {
        Payment payment = new Payment(mockOrder, "CUSTOM_METHOD", emptyPaymentData) {
            @Override
            protected void setPaymentData(Map<String, String> paymentData) {
                this.paymentData = paymentData;
            }
        };
        assertEquals("CUSTOM_METHOD", payment.getMethod());
        assertEquals(PaymentStatus.WAITING_PAYMENT, payment.getStatus());
    }

    @Test
    void testSetStatusSuccessfully() {
        Payment payment = new Payment(mockOrder, "CUSTOM_METHOD", emptyPaymentData) {
            @Override
            protected void setPaymentData(Map<String, String> paymentData) {
                this.paymentData = paymentData;
            }
        };
        payment.setStatus(PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testSetInvalidStatusThrowsException() {
        Payment payment = new Payment(mockOrder, "CUSTOM_METHOD", emptyPaymentData) {
            @Override
            protected void setPaymentData(Map<String, String> paymentData) {
                this.paymentData = paymentData;
            }
        };
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus(null));
    }
}