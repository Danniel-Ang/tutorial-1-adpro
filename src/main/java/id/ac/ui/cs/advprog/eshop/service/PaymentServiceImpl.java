package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        validateAddPaymentParameters(order, method, paymentData);
        Payment payment = new Payment(order, method, paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, PaymentStatus status) {
        validatePaymentNotNull(payment);
        payment.setStatus(status);
        updateOrderStatus(payment.getOrder(), status);
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void deletePayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found");
        }
        paymentRepository.delete(paymentId);
    }

    // Helper methods for validation and update

    private void validateAddPaymentParameters(Order order, String method, Map<String, String> paymentData) {
        if (order == null || method == null || paymentData == null) {
            throw new IllegalArgumentException("Invalid payment details");
        }
    }

    private void validatePaymentNotNull(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found");
        }
    }

    private void updateOrderStatus(Order order, PaymentStatus status) {
        if (order == null) return;
        if (status == PaymentStatus.SUCCESS) {
            order.setStatus(PaymentStatus.SUCCESS.getValue());
        } else if (status == PaymentStatus.REJECTED) {
            order.setStatus("FAILED");
        }
    }
}
