package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private String id;
    private String method;
    private Map<String, String> paymentData;
    private Order order;
    private PaymentStatus status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this(order, method, paymentData);
        this.id = id;
    }

    public Payment(Order order, String method, Map<String, String> paymentData) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.method = method;
        this.status = PaymentStatus.WAITING_PAYMENT;
        this.setPaymentData(paymentData);
    }

    public void setStatus(PaymentStatus status) {
        if (status != null) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid payment status");
        }
    }

    protected void setPaymentData(Map<String, String> paymentData) {
        if (this.method.equals("BANK_TRANSFER")) {
            if (!paymentData.containsKey("bankName") || paymentData.get("bankName").isEmpty() ||
                    !paymentData.containsKey("referenceCode") || paymentData.get("referenceCode").isEmpty()) {
                throw new IllegalArgumentException("Bank payment data is invalid");
            }
            this.paymentData = paymentData;
        } else if (this.method.equals("VOUCHER")) {
            if (!paymentData.containsKey("voucherCode") || !isValidVoucher(paymentData.get("voucherCode"))) {
                throw new IllegalArgumentException("Invalid voucher code");
            }
            this.paymentData = paymentData;
        } else {
            throw new IllegalArgumentException("Invalid payment method");
        }
    }

    private boolean isValidVoucher(String voucherCode) {
        return voucherCode.length() == 16 && voucherCode.startsWith("ESHOP") && voucherCode.replaceAll("[^0-9]", "").length() == 8;
    }
}