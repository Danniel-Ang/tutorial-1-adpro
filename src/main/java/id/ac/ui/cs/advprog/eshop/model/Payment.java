package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class Payment {
    private String id;
    private String method;
    private Map<String, String> paymentData;
    private Order order;
    private String status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {

    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }

    public Order getOrder() {
        return order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}