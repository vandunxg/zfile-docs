# Hướng dẫn tích hợp API hoàn tiền

Trong bối cảnh thương mại điện tử ngày càng phát triển mạnh mẽ, việc xử lý hoàn tiền một cách nhanh chóng, chính xác và liền mạch đóng vai trò vô cùng quan trọng trong việc nâng cao trải nghiệm khách hàng. Tài liệu này sẽ cung cấp cho bạn một cái nhìn toàn diện về cách tích hợp API hoàn tiền của AcmePay.

## Tổng quan

API hoàn tiền không chỉ giúp merchant hoàn tiền cho khách hàng mà còn đảm bảo tính minh bạch và an toàn tuyệt đối cho mọi giao dịch. Bạn có thể hoàn tiền toàn phần hoặc hoàn tiền một phần cho một giao dịch đã được capture thành công.

## Điều kiện hoàn tiền

Vậy khi nào bạn có thể thực hiện hoàn tiền? Câu trả lời rất đơn giản:

- Giao dịch phải ở trạng thái `CAPTURED`.
- Yêu cầu hoàn tiền phải được gửi trong vòng **30 ngày** kể từ thời điểm capture.
- Tổng số tiền hoàn không được vượt quá số tiền đã capture.
- Mỗi giao dịch chỉ được hoàn tiền một phần tối đa **3 lần**.

## Gửi yêu cầu hoàn tiền

Để tiến hành thực hiện việc gửi yêu cầu hoàn tiền, bạn cần phải gọi endpoint `POST /v2/refunds` kèm theo header `Idempotency-Key` nhằm mục đích đảm bảo rằng cùng một yêu cầu sẽ không bị xử lý hai lần.

```bash
curl -X POST https://api.acmepay.example/v2/refunds \
  -H "Authorization: Bearer $ACMEPAY_SECRET_KEY" \
  -H "Idempotency-Key: 7f3c2a9e-1b4d-4e8a-9c21-5d6f0a1b2c3d" \
  -H "Content-Type: application/json" \
  -d '{
    "transaction_id": "txn_01HF8Z2K",
    "amount": 150000,
    "reason": "customer_request"
  }'
```

Trường `amount` được tính bằng VND và là số nguyên. Nếu bỏ trống `amount`, hệ thống sẽ hoàn toàn bộ số tiền còn lại có thể hoàn.

## Mã phản hồi

| HTTP status | Ý nghĩa |
|---|---|
| `201` | Yêu cầu hoàn tiền đã được tạo, trạng thái `PENDING` |
| `409` | `Idempotency-Key` đã được dùng với nội dung request khác |
| `422` | Vi phạm điều kiện hoàn tiền (quá 30 ngày, vượt số tiền, quá số lần) |

## Theo dõi trạng thái

Sau khi tạo, hoàn tiền sẽ chuyển từ `PENDING` sang `SUCCEEDED` hoặc `FAILED`. AcmePay sẽ gửi webhook `refund.updated` đến URL mà bạn đã cấu hình. Thông thường, tiền sẽ về tài khoản khách hàng trong 5–7 ngày làm việc tùy ngân hàng.

## Kết luận

Tóm lại, với API hoàn tiền mạnh mẽ và linh hoạt của AcmePay, bạn hoàn toàn có thể xây dựng một quy trình hoàn tiền tối ưu, giúp khách hàng hài lòng và tin tưởng hơn vào doanh nghiệp của bạn.
