package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {
    private PaymentService paymentService;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentServiceImpl(paymentRepository);
    }

    @Test
    void testAddPaymentSuccess() {
        Order order = new Order("order-001", List.of(new Product()), 1708560000L, "Alice Johnson");
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Alpha Bank");
        paymentData.put("referenceCode", "REF001");
        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);
        assertNotNull(result);
        assertEquals("BANK_TRANSFER", result.getMethod());
    }

    @Test
    void testAddPaymentFailure() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.addPayment(null, "BANK_TRANSFER", new HashMap<>()));
    }

    @Test
    void testSetStatusUpdatesOrderStatus() {
        Order order = new Order("order-002", List.of(new Product()), 1708560000L, "Bob Smith");

        Map<String, String> validPaymentData = new HashMap<>();
        validPaymentData.put("bankName", "Alpha Bank");
        validPaymentData.put("referenceCode", "REF001");

        Payment payment = new Payment(order, "BANK_TRANSFER", validPaymentData);

        paymentService.setStatus(payment, PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), order.getStatus());

        paymentService.setStatus(payment, PaymentStatus.REJECTED);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }


    @Test
    void testSetStatusFailure() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(null, PaymentStatus.SUCCESS));
    }

    @Test
    void testGetPaymentSuccess() {
        Payment payment = mock(Payment.class);
        when(paymentRepository.findById("payment-001")).thenReturn(payment);

        Payment result = paymentService.getPayment("payment-001");
        assertNotNull(result);
    }

    @Test
    void testGetPaymentFailure() {
        when(paymentRepository.findById("invalid-id")).thenReturn(null);
        assertNull(paymentService.getPayment("invalid-id"));
    }

    @Test
    void testGetAllPaymentsSuccess() {
        List<Payment> payments = List.of(mock(Payment.class), mock(Payment.class));
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllPaymentsEmpty() {
        when(paymentRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(paymentService.getAllPayments().isEmpty());
    }

    @Test
    void testDeletePaymentSuccess() {
        Payment payment = mock(Payment.class);
        when(paymentRepository.findById("payment-002")).thenReturn(payment);
        doNothing().when(paymentRepository).delete("payment-002");

        paymentService.deletePayment("payment-002");
        verify(paymentRepository).delete("payment-002");
    }

    @Test
    void testDeletePaymentFailure() {
        when(paymentRepository.findById("invalid-id")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> paymentService.deletePayment("invalid-id"));
    }
}