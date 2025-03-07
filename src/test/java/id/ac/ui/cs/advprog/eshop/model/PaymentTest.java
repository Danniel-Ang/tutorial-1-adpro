package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products;
        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("bebekpeking");
        product1.setProductQuantity(3);
        product1.setProductName("Bebek Peking Goreng");
        Product product2 = new Product();
        product2.setProductId("ayojoincompetition");
        product2.setProductQuantity(2);
        product2.setProductName("Ayam Geprek Join Competition");
        products.add(product1);
        products.add(product2);

        this.orders = new ArrayList<>();

        Order order1 = new Order("danniel", products, 500L, "Danniel");
        Order order2 = new Order("bebekpeking", products, 250L, "BebekPeking");
        Order order3 = new Order("ayojoincompetition", products, 750L, "AyoJoinCompetition");
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @Test
    void testCreatePaymentSuccessfulVoucher() {
        Map<String, String> paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "ESHOP12345678JOI");
        Payment payment1 = new Payment("danniel", orders.get(1), "VOUCHER", paymentDataVoucher);
        assertSame(this.orders.get(1), payment1.getOrder());
        assertEquals(paymentDataVoucher, payment1.getPaymentData());
        assertEquals("danniel", payment1.getId());
        assertEquals("VOUCHER", payment1.getMethod());
    }

    @Test
    void testCreatePaymentInvalidVoucher() {
        Map<String, String> paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "INVALIDPEKING1234");
        assertThrows(IllegalArgumentException.class, () -> new Payment("bebekpeking", orders.get(1), "VOUCHER", paymentDataVoucher));
    }

    @Test
    void testCreatePaymentInvalidBankTransfer() {
        Map<String, String> paymentDataBank = new HashMap<>();
        paymentDataBank.put("bankName", "");
        paymentDataBank.put("referenceCode", "987654");
        assertThrows(IllegalArgumentException.class, () -> new Payment("ayojoincompetition", orders.get(1), "BANK_TRANSFER", paymentDataBank));
    }
    @Test
    void testSetPaymentStatusToSuccess() {
        Map<String, String> paymentDataBank = new HashMap<>();
        paymentDataBank.put("bankName", "Bank XYZ");
        paymentDataBank.put("referenceCode", "123456");
        Payment payment = new Payment("a0f9de46-90b1-437d-a0bf-d0821dde9096", orders.get(1), "BANK_TRANSFER", paymentDataBank);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetPaymentStatusToFailed() {
        Map<String, String> paymentDataBank = new HashMap<>();
        paymentDataBank.put("bankName", "Bank XYZ");
        paymentDataBank.put("referenceCode", "123456");
        Payment payment = new Payment("a0f9de46-90b1-437d-a0bf-d0821dde9096", orders.get(1), "BANK_TRANSFER", paymentDataBank);
        payment.setStatus("FAILED");
        assertEquals("FAILED", payment.getStatus());
    }
}
