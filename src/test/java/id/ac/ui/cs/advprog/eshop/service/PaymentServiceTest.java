package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
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

    // Happy cases

    @Test
    void testAddPaymentSuccess() {
        // Ensure order has at least one product
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-001");
        product.setProductName("Gadget Pro");
        product.setProductQuantity(3);
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
    void testSetStatusUpdatesOrderStatusSuccess() {
        // Prepare order with product
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-002");
        product.setProductName("Smart Watch");
        product.setProductQuantity(2);
        products.add(product);
        Order order = new Order("order-002", products, 1708560000L, "Bob Smith");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Alpha Bank");
        paymentData.put("referenceCode", "REF002");
        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        paymentService.setStatus(payment, PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), order.getStatus());

        paymentService.setStatus(payment, PaymentStatus.REJECTED);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testGetPaymentSuccess() {
        Payment payment = mock(Payment.class);
        when(paymentRepository.findById("payment-001")).thenReturn(payment);
        Payment result = paymentService.getPayment("payment-001");
        assertNotNull(result);
    }

    @Test
    void testGetAllPaymentsSuccess() {
        List<Payment> payments = List.of(mock(Payment.class), mock(Payment.class));
        when(paymentRepository.findAll()).thenReturn(payments);
        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }

    @Test
    void testDeletePaymentSuccess() {
        Payment payment = mock(Payment.class);
        when(paymentRepository.findById("payment-002")).thenReturn(payment);
        doNothing().when(paymentRepository).delete("payment-002");
        paymentService.deletePayment("payment-002");
        verify(paymentRepository).delete("payment-002");
    }

    // Unhappy cases

    @Test
    void testAddPaymentFailureInvalidParameters() {
        // Passing null order should throw an exception
        assertThrows(IllegalArgumentException.class, () -> paymentService.addPayment(null, "BANK_TRANSFER", new HashMap<>()));
        // Passing null method should throw an exception
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-003");
        product.setProductName("Tablet");
        product.setProductQuantity(1);
        products.add(product);
        Order order = new Order("order-003", products, 1708560000L, "Charlie");
        assertThrows(IllegalArgumentException.class, () -> paymentService.addPayment(order, null, new HashMap<>()));
        // Passing null paymentData should throw an exception
        assertThrows(IllegalArgumentException.class, () -> paymentService.addPayment(order, "BANK_TRANSFER", null));
    }

    @Test
    void testSetStatusFailureWithNullPayment() {
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(null, PaymentStatus.SUCCESS));
    }

    @Test
    void testDeletePaymentFailureWhenNotFound() {
        when(paymentRepository.findById("invalid-id")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> paymentService.deletePayment("invalid-id"));
    }
}
