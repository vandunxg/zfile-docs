package vn.acme.order.approval;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RejectRequest(
        @NotBlank
        @Size(min = 10, max = 500, message = "Lý do từ chối phải từ 10 đến 500 ký tự")
        String reason) {
}
