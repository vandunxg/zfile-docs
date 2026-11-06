---
title: Thiết kế Phase 3 - Storage và File Operations
---

# Thiết kế Phase 3 - Storage và File Operations

Phase 3 tách tài liệu theo ba ranh giới: vòng đời `storage source`, thao tác tệp và kiểm soát truy cập. Mỗi trang có workflow, source entry point và các nhánh từ chối đã xác minh.

## Deliverables

- `business/storage-sources.mdx`: tạo, khởi tạo, kiểm tra kết nối, enable/disable, copy và delete storage source.
- `business/file-operations.mdx`: duyệt tệp, tạo thư mục, upload, rename, delete, move và copy.
- `business/access-control.mdx`: permission, password và filter theo storage source.

## Giới hạn

- Không khẳng định mọi provider hỗ trợ mọi thao tác.
- Không mô tả cấu hình UI, HTTP contract đầy đủ hoặc proxy transfer; các nội dung này thuộc phase sau khi có bằng chứng tương ứng.
- Filter được mô tả theo các method đã đọc trong `FilterConfigService`.

## Xác minh

Ba trang phải có trong sidebar `Business`; mỗi workflow và rule phải trỏ source class/method; Mermaid và internal link phải qua `yarn build`.
