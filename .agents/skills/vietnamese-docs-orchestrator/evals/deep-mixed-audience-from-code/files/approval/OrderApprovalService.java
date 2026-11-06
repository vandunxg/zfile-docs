package vn.acme.order.approval;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApprovalService {

    /** Đơn có tổng tiền từ mức này trở lên phải do kế toán trưởng duyệt. Đơn vị: VND. */
    static final BigDecimal CHIEF_APPROVAL_THRESHOLD = new BigDecimal("50000000");

    private final OrderRepository orderRepository;
    private final PaymentRequestSender paymentRequestSender;
    private final NotificationService notificationService;

    @Transactional
    public Order approve(Long orderId, AppUser approver) {
        Order order = load(orderId);
        requirePending(order);
        if (order.getCreatedBy().equals(approver.getUsername())) {
            throw new SelfApprovalException(orderId);
        }
        if (order.getTotalAmount().compareTo(CHIEF_APPROVAL_THRESHOLD) >= 0
                && !approver.hasRole("CHIEF_ACCOUNTANT")) {
            throw new InsufficientApprovalLevelException(orderId, CHIEF_APPROVAL_THRESHOLD);
        }
        order.setStatus(OrderStatus.APPROVED);
        order.setApprovedBy(approver.getUsername());
        order.setApprovedAt(Instant.now());
        orderRepository.save(order);

        boolean sent = paymentRequestSender.send(order);
        if (!sent) {
            order.setStatus(OrderStatus.APPROVED_PAYMENT_PENDING);
            orderRepository.save(order);
        }
        notificationService.notifyCreator(order, "APPROVED");
        return order;
    }

    @Transactional
    public Order reject(Long orderId, String reason, AppUser approver) {
        Order order = load(orderId);
        requirePending(order);
        order.setStatus(OrderStatus.REJECTED);
        order.setRejectReason(reason.trim());
        order.setRejectedBy(approver.getUsername());
        orderRepository.save(order);
        notificationService.notifyCreator(order, "REJECTED");
        return order;
    }

    private Order load(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    private void requirePending(Order order) {
        if (order.getStatus() != OrderStatus.PENDING_APPROVAL) {
            throw new InvalidOrderStateException(order.getId(), order.getStatus());
        }
    }
}
