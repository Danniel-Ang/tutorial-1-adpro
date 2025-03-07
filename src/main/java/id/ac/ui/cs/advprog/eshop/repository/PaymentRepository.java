package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private final List<Payment> paymentStorage = new ArrayList<>();

    public Payment save(Payment payment) {
        if (existsById(payment.getId())) {
            throw new IllegalStateException("Payment with ID " + payment.getId() + " already exists.");
        }
        paymentStorage.add(payment);
        return payment;
    }

    public boolean existsById(String id) {
        return findById(id) != null;
    }
    public Payment findById(String id) {
        return paymentStorage.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentStorage);
    }

    public void delete(String id) {
        paymentStorage.removeIf(payment -> payment.getId().equals(id));
    }
}
