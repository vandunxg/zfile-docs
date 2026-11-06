package vn.acme.order.approval;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApprovalController {

    private final OrderApprovalService approvalService;

    @PostMapping("/{orderId}/approve")
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'CHIEF_ACCOUNTANT')")
    public OrderResponse approve(@PathVariable Long orderId,
                                 @AuthenticationPrincipal AppUser user) {
        return OrderResponse.from(approvalService.approve(orderId, user));
    }

    @PostMapping("/{orderId}/reject")
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'CHIEF_ACCOUNTANT')")
    public OrderResponse reject(@PathVariable Long orderId,
                                @Valid @RequestBody RejectRequest body,
                                @AuthenticationPrincipal AppUser user) {
        return OrderResponse.from(approvalService.reject(orderId, body.reason(), user));
    }
}
