package vn.acme.order.approval;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentRequestSender {

    private final PaymentGatewayClient gatewayClient;
    private final AlertService alertService;

    @Retryable(retryFor = PaymentGatewayTimeoutException.class,
               maxAttempts = 3,
               backoff = @Backoff(delay = 2000, multiplier = 2))
    public boolean send(Order order) {
        gatewayClient.createPaymentRequest(order.getId(), order.getTotalAmount());
        return true;
    }

    @Recover
    public boolean recover(PaymentGatewayTimeoutException ex, Order order) {
        log.warn("Payment request failed after retries for order {}", order.getId(), ex);
        alertService.raise("PAYMENT_REQUEST_FAILED", order.getId());
        return false;
    }
}
