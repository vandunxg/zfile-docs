---
title: Thiết kế Phase 4-8
---

# Thiết kế Phase 4-8

## Mục tiêu

Hoàn tất bộ tài liệu Business và Engineering cho các domain còn lại của ZFile, từ truy cập tệp bên ngoài đến onboarding contributor. Nội dung tiếng Việt bám source code và đánh dấu rõ phần không đủ bằng chứng.

## Cấu trúc

### Phase 4 - Sharing & Links

- `business/sharing-and-links.mdx`: phân biệt share link, direct link, short link và proxy download; mô tả actor, lifecycle, password, expiry, cấu hình bật/tắt và nhánh lỗi đã xác minh.
- Có sequence diagram cho share link và bảng so sánh các loại link.

### Phase 5 - Identity & Administration

- `business/identity-and-administration.mdx`: login/session, admin role, user-to-storage assignment, SSO và system configuration.
- Nêu rõ admin role gắn với user ID `1`, `/admin/**` yêu cầu login/admin role và SSO state validation.

### Phase 6 - Preview & Integrations

- `business/preview-and-integrations.mdx`: preview capability, OnlyOffice config/callback và ranh giới provider adapter.
- Có component diagram, điều kiện callback và bảng nhóm provider. Không khẳng định client renderer khi source backend không chứng minh.

### Phase 7 - Architecture & Code Navigation

- `engineering/architecture-overview.mdx`: bootstrap, module boundaries, storage abstraction và component diagram.
- `engineering/code-navigation.mdx`: bản đồ entry point và hai call chain mẫu.
- `engineering/data-and-configuration.mdx`: MyBatis mapping, datasource, Flyway và migration SQLite/MySQL.

### Phase 8 - Onboarding & Contribution

- `onboarding/start-here.mdx`: prerequisites, build, runtime configuration và local verification có bằng chứng.
- `onboarding/first-contribution.mdx`: chọn domain, tạo migration, debug/log và cách báo issue/security.

## Quy tắc nội dung

- Mỗi trang có mục đích rõ theo `Why -> What -> How -> Where in Code` khi phù hợp.
- Tên source entry point giữ nguyên trong backticks.
- Không tạo hướng dẫn source-run, Docker Compose hoặc contribution process nếu repository không có bằng chứng.
- Nêu explicit các điều kiện configuration/provider thay đổi hành vi.
- Mọi route runtime phụ thuộc `system_config` không được liệt kê như route cố định.

## Navigation

Sidebar giữ category `Business`, thêm ba trang Phase 4-6; thêm category `Engineering` và `Onboarding` cho các trang Phase 7-8. Roadmap được cập nhật để phản ánh các trang đã tạo.

## Xác minh

1. Tất cả trang Phase 4-8 có trong sidebar.
2. Markdown links, Mermaid và MDX render qua `yarn build`.
3. Mỗi phase có source reference và giới hạn/chưa xác minh khi cần.
4. Build không báo broken link hoặc lỗi MDX.
