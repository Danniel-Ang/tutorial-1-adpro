
package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private List<Payment> payments;
    private Order sampleOrder;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();
        products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("prod-001");
        product1.setProductName("Gadget Pro");
        product1.setProductQuantity(3);
        products.add(product1);

        Product product2 = new Product();
        product2.setProductId("prod-002");
        product2.setProductName("Smart Watch");
        product2.setProductQuantity(2);
        products.add(product2);

        sampleOrder = new Order("order-XYZ", products, 1800000000L, "Alice Wonderland");

        Map<String, String> bankPaymentData = new HashMap<>();
        bankPaymentData.put("bankName", "Virtual Bank");
        bankPaymentData.put("referenceCode", "987654");
        Payment bankPayment = new Payment("payment-001", sampleOrder, "BANK_TRANSFER", bankPaymentData);

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP87654321WXY");
        Payment voucherPayment = new Payment("payment-002", sampleOrder, "VOUCHER", voucherPaymentData);

        payments.add(bankPayment);
        payments.add(voucherPayment);
    }

    @Test
    void testSaveAndRetrievePayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        assertEquals(2, paymentRepository.findAll().size());
    }

    @Test
    void testFindPaymentById() {
        paymentRepository.save(payments.get(0));
        Payment retrieved = paymentRepository.findById(payments.get(0).getId());
        assertNotNull(retrieved);
        assertEquals(payments.get(0).getId(), retrieved.getId());
    }

    @Test
    void testFindPaymentByUnknownId() {
        assertNull(paymentRepository.findById("unknown-payment-id"));
    }

    @Test
    void testPreventDuplicatePayments() {
        paymentRepository.save(payments.get(0));
        Payment duplicate = new Payment(payments.get(0).getId(), sampleOrder, "BANK_TRANSFER", payments.get(0).getPaymentData());
        assertThrows(IllegalStateException.class, () -> paymentRepository.save(duplicate));
    }

    @Test
    void testDeletePayment() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        paymentRepository.delete(payment.getId());

        assertNull(paymentRepository.findById(payment.getId()), "Payment should be removed from repository.");
    }

}