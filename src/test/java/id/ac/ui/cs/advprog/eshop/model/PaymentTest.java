package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank A");
        paymentData.put("referenceCode", "123456");
    }

    @Test
    void testCreatePaymentSuccess() {
        payment = new Payment("1", "BANK_TRANSFER", "PENDING", paymentData);
        assertNotNull(payment);
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyBankName() {
        paymentData.put("bankName", "");
        assertThrows(IllegalArgumentException.class, () -> new Payment("1", "BANK_TRANSFER", "PENDING", paymentData));
    }

    @Test
    void testCreatePaymentWithEmptyReferenceCode() {
        paymentData.put("referenceCode", "");
        assertThrows(IllegalArgumentException.class, () -> new Payment("1", "BANK_TRANSFER", "PENDING", paymentData));
    }

    @Test
    void testSetPaymentStatusToSuccess() {
        payment = new Payment("1", "BANK_TRANSFER", "PENDING", paymentData);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetPaymentStatusToRejected() {
        payment = new Payment("1", "BANK_TRANSFER", "PENDING", paymentData);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidPaymentMethod() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("1", "INVALID_METHOD", "PENDING", paymentData));
    }
}
