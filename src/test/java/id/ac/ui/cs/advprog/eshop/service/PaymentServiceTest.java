
package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;

import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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
    void testAddPayment() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-001");
        product.setProductName("Gadget");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("order-001", products, 1708560000L, "Alice Johnson");

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
    void testGetPayment() {
        Payment payment = mock(Payment.class);
        when(paymentRepository.findById("payment-001")).thenReturn(payment);

        Payment result = paymentService.getPayment("payment-001");
        assertNotNull(result);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = List.of(mock(Payment.class), mock(Payment.class));
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }
}
