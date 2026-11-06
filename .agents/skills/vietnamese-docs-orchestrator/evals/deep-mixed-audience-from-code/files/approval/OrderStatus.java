package vn.acme.order.approval;

public enum OrderStatus {
    DRAFT,
    PENDING_APPROVAL,
    APPROVED,
    APPROVED_PAYMENT_PENDING,
    REJECTED,
    CANCELLED
}
