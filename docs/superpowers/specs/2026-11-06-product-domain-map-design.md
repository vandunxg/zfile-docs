---
title: Thiết kế Phase 2 - Product và Domain Map
---

# Thiết kế Phase 2 - Product và Domain Map

## Mục tiêu

Tạo ba trang tài liệu tiếng Việt giúp BA, PM, người vận hành và developer hiểu ZFile làm gì, ai tương tác với hệ thống, các domain chịu trách nhiệm gì và nơi bắt đầu đọc source code.

## Phạm vi

Phase 2 tạo các trang sau:

- `docs/business/overview.mdx`: tổng quan sản phẩm, actors và khả năng đã xác minh.
- `docs/business/domain-map.mdx`: bản đồ domain, quan hệ giữa domain và điểm vào source code.
- `docs/business/glossary.mdx`: thuật ngữ dùng thống nhất trong bộ tài liệu.
- `sidebars.js`: thêm nhóm `Business` chứa ba trang trên.

Phase này không mô tả đầy đủ thao tác tệp, vòng đời liên kết chia sẻ, cơ chế xác thực hoặc tích hợp preview. Các nội dung đó thuộc Phase 3 đến Phase 6.

## Cách tổ chức

Chọn cách viết business-first, tách ba trang theo nhu cầu đọc:

| Trang | Người đọc chính | Câu hỏi trả lời |
| --- | --- | --- |
| Business Overview | BA, PM, người vận hành, thành viên mới | ZFile dùng để làm gì, ai dùng và có những nhóm khả năng nào? |
| Domain Map | BA, PM, developer | Mỗi domain chịu trách nhiệm gì, liên hệ với nhau ra sao và đọc code từ đâu? |
| Glossary | Tất cả người đọc | Mỗi thuật ngữ trong tài liệu có nghĩa gì? |

## Nội dung và nguồn

`Business Overview` dùng `zfile/README.md` làm nguồn chính cho phạm vi sản phẩm: ZFile quản lý nhiều storage source, duyệt, xem trước và chia sẻ tệp cho cá nhân và nhóm nhỏ. Các actor được xác định từ các controller và service của module storage, user, share và link.

`Domain Map` dùng Mermaid để minh họa sáu domain: Storage source, File operations, Access control, Sharing & links, Identity & administration, Preview & integrations. Bảng domain nêu trách nhiệm, actor chính và entry point đã xác minh, bao gồm `StorageSourceService`, `StorageSourceContext`, `FileController`, `FileOperatorController`, `FileOperatorCheckAspect`, `CheckPasswordAspect`, `ShareLinkService`, `LinkDownloadService`, `UserController`, `SsoService` và module `onlyoffice`.

`Glossary` chỉ định nghĩa thuật ngữ có bằng chứng trong README hoặc source. Thuật ngữ chưa xác định được ngữ nghĩa đầy đủ sẽ mang nhãn **Chưa xác minh** thay vì suy diễn.

## Quy tắc biên soạn

- Mỗi trang dùng cấu trúc `Why -> What -> How -> Where in Code` khi phù hợp.
- Nhận định về sản phẩm, actor, trách nhiệm và technical entry point phải gắn source reference.
- Giữ nguyên tên class, method, module và thuật ngữ kỹ thuật trong backticks.
- Không biến claim trong README về tính năng có điều kiện license thành năng lực mặc định của mọi bản cài đặt.
- Bổ sung liên kết chéo giữa overview, domain map và glossary.

## Xác minh

`yarn build` phải thành công sau khi tạo tài liệu và sidebar. Rà nội dung theo các tiêu chí:

1. Ba trang đều nằm trong navigation `Business`.
2. Sáu domain trong Domain Map có trách nhiệm, actor và source entry point.
3. Glossary không chứa định nghĩa không có nguồn hoặc định nghĩa đó được đánh dấu **Chưa xác minh**.
4. Mermaid render trong production build.

## Giới hạn

- Route HTTP thực tế có thể phụ thuộc vào `system_config`, nên Phase 2 không liệt kê route runtime.
- README xác nhận ZFile 5.x đã phát hành, nhưng metadata Maven trong source là `4.5.0`; Phase 2 không dùng số version Maven như version phát hành.
