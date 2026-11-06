---
title: Thiết kế tuyến Business Core cho junior developer
---

# Thiết kế tuyến Business Core cho junior developer

## Mục tiêu

Tạo tuyến đọc ưu tiên cho developer junior để hiểu ZFile giải quyết vấn đề gì, đối tượng business nào tồn tại, actor nào có quyền gì và workflow nào tạo ra kết quả cho người dùng. Source mapping chỉ xuất hiện sau khi business flow đã rõ.

## Tài liệu mới

1. `business/core/system-purpose.mdx`
   - Vấn đề sản phẩm, giá trị của ZFile, ranh giới hệ thống và actors.
2. `business/core/business-model.mdx`
   - Quan hệ `Storage source -> User assignment -> Root path -> File/folder -> Share/link`, ownership và trạng thái.
3. `business/core/access-and-visibility.mdx`
   - Decision table cho enable, permission, root path, password, filter và download restriction.
4. `business/core/core-workflows.mdx`
   - Bốn luồng xuyên domain: chuẩn bị storage, duyệt/thao tác tệp, chia sẻ, khách truy cập/tải/preview.
5. `business/core/business-rules-and-scenarios.mdx`
   - Scenario thực tế, điều kiện, outcome và những business rule ảnh hưởng thao tác.

## Cách đọc

Sidebar đặt category `Business Core` trước các trang reference domain hiện có. Junior đọc năm trang theo thứ tự trên; sau mỗi workflow, tài liệu trỏ đến trang domain sâu hơn và entry point code đã xác minh.

## Quy tắc nội dung

- Mở đầu bằng outcome business, không mở đầu bằng class hoặc API.
- Mỗi workflow có trigger, precondition, happy path, exception/blocked path, outcome và code map.
- Chỉ khẳng định state/rule khi có nguồn README hoặc source; thiếu bằng chứng ghi **Chưa xác minh**.
- Reference domain hiện có không bị xóa; các trang core liên kết đến chúng.

## Xác minh

1. Năm trang nằm đầu sidebar `Business Core`.
2. Mô hình business, rule và workflow khớp source code đã khảo sát.
3. Internal link và Mermaid render qua `yarn build`.
