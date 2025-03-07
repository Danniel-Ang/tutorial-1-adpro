package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private final List<Payment> paymentStorage = new ArrayList<>();

    public Payment save(Payment payment) {
        if (findById(payment.getId()) != null) {
            throw new IllegalStateException("Payment with ID " + payment.getId() + " already exists.");
        }
        paymentStorage.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment p : paymentStorage) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentStorage);
    }
}
