---
title: Đặc tả business documentation theo feature
---

# Đặc tả business documentation theo feature

## Mục tiêu

Viết lại sáu trang feature để developer junior hiểu business của từng tính năng trước khi đọc code: nhu cầu, actor, ownership, lifecycle, rule, scenario và ảnh hưởng khi thay đổi.

## Phạm vi

Các trang được viết lại:

1. `business/storage-sources.mdx`
2. `business/file-operations.mdx`
3. `business/access-control.mdx`
4. `business/sharing-and-links.mdx`
5. `business/identity-and-administration.mdx`
6. `business/preview-and-integrations.mdx`

Các trang business hỗ trợ mới:

1. `business/feature-catalog.mdx`: feature, actor, outcome, dependency và trang chi tiết.
2. `business/permission-matrix.mdx`: user/assignment/operation/link permission và ranh giới admin.
3. `business/provider-capability-matrix.mdx`: capability đã xác minh theo nhóm provider; phần không có evidence ghi **Chưa xác minh**.
4. `business/business-rules-catalog.mdx`: rule xuyên feature về enable, assignment, path, link, expiry, provider capability.
5. `business/acceptance-scenarios.mdx`: Given/When/Then cho happy path và blocked path quan trọng.

## Cấu trúc bắt buộc của mỗi feature

1. **Mục tiêu business**: feature giải quyết nhu cầu nào.
2. **Actor và ownership**: ai tạo, dùng, quản lý hoặc nhận tác động.
3. **Business objects và lifecycle**: dữ liệu/trạng thái nào thay đổi.
4. **Workflow**: trigger, precondition, happy path, outcome.
5. **Rules và exception**: decision table, blocked path, side effect.
6. **Scenario**: tình huống thực tế để developer dự đoán behavior.
7. **Code map**: controller/service/aspect/entry point chỉ để lần source sau khi hiểu business.

## Ranh giới theo feature

| Feature | Câu hỏi business phải trả lời |
| --- | --- |
| Storage source | Làm sao backend lưu trữ trở thành workspace khả dụng cho user? Xóa/copy/disable ảnh hưởng gì? |
| File operations | User làm việc với tệp trong phạm vi nào? Batch result và provider limitation ảnh hưởng outcome ra sao? |
| Access control | Điều gì quyết định thấy, đọc, thay đổi hoặc tải một path? Các rule xếp thứ tự thế nào? |
| Sharing & links | Loại link nào phục vụ ai? Ownership, password, expiry và policy thay đổi trải nghiệm khách như thế nào? |
| Identity & administration | Admin chuẩn bị hệ thống ra sao? User nhận workspace như thế nào? SSO đưa user vào đâu? |
| Preview & integrations | Khi nào preview chỉ đọc/chỉnh sửa được? Provider và configuration làm thay đổi behavior ra sao? |

## Quy tắc chất lượng

- Business flow đứng trước source mapping.
- Không khẳng định capability provider nếu chưa có ma trận source-backed.
- Rule có số lượng, thời hạn, permission hoặc trạng thái phải trỏ source cụ thể.
- Dùng **Chưa xác minh** thay vì suy diễn từ tên class/configuration.
- Những trang matrix/catalog liên kết đến feature page và Business Core, không sao chép toàn bộ nội dung.

## Navigation

`Business` giữ thứ tự: Feature Catalog, sáu feature page, matrices/catalog, sau đó Domain Map và Glossary. `Business Core` vẫn là tuyến đọc đầu tiên.

## Xác minh

1. Mỗi feature page có đủ bảy phần bắt buộc.
2. Feature catalog, matrix và acceptance scenarios liên kết được đến feature page liên quan.
3. Không có internal link/MDX/Mermaid lỗi khi chạy `yarn build`.
4. Các assertion về rule và capability có source reference hoặc nhãn **Chưa xác minh**.
