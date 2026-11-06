---
title: Thiết kế hướng dẫn đóng góp mã nguồn
---

# Thiết kế hướng dẫn đóng góp mã nguồn

## Mục tiêu

Bổ sung tài liệu cho backend contributor của ZFile để chọn đúng điểm sửa, giữ đúng ranh giới các tầng, lần theo luồng xử lý và tái sử dụng abstraction hoặc tiện ích đang có thay vì thêm logic trùng lặp.

## Phạm vi

Thay đổi tạo bốn trang tài liệu và cập nhật các điểm điều hướng hiện có:

- `docs/onboarding/code-contribution-guide.mdx`: hướng dẫn tổng hợp trước, trong và sau khi sửa mã nguồn.
- `docs/engineering/code-conventions.mdx`: quy tắc kiến trúc và convention đã được xác minh từ source.
- `docs/engineering/code-flows.mdx`: các chuỗi gọi cần biết khi thay đổi request, storage, file listing và lỗi HTTP.
- `docs/engineering/reusable-components.mdx`: thư viện/framework và abstraction, util có thể tái sử dụng.
- `sidebars.js`, `README.md`, `docs/onboarding/start-here.mdx` và `docs/onboarding/first-contribution.mdx`: thêm trang mới vào navigation và liên kết chéo.

Không tạo `CONTRIBUTING`, quy trình pull request, lệnh chạy local hay quy ước Git khi các nội dung này chưa có nguồn xác minh. Tài liệu cũng không thay thế hướng dẫn Docusaurus cho repository tài liệu hiện tại.

## Cách tổ chức

Chọn trang tổng hợp ngắn cho người mới, kết hợp ba trang tra cứu độc lập cho lúc đang sửa code:

| Trang | Người đọc chính | Câu hỏi trả lời |
| --- | --- | --- |
| Hướng dẫn đóng góp mã nguồn | Backend contributor mới | Bắt đầu từ đâu, kiểm tra gì trước và sau thay đổi? |
| Convention mã nguồn | Developer đang thiết kế thay đổi | Logic thuộc controller, service, mapper, chain hay storage implementation? |
| Luồng mã nguồn | Developer đang lần lỗi hoặc tính năng | Một request/storage/file/error đi qua những thành phần nào? |
| Thành phần tái sử dụng | Developer đang chuẩn bị thêm code | Có framework, abstraction hoặc util nào phù hợp để dùng lại? |

Trang hướng dẫn tổng hợp chỉ trỏ đến các trang chuyên sâu, không sao chép bảng convention, sơ đồ flow hoặc danh mục util.

## Nội dung từng trang

### Hướng dẫn đóng góp mã nguồn

Trang mở đầu bằng khả năng người đọc có được: phân loại thay đổi, lần từ entry point, chọn đúng extension point và ghi rõ phạm vi đã kiểm tra. Nội dung gồm:

1. Chuẩn bị: Java 21, Maven, lệnh `mvn clean package`, giới hạn `skipTests=true` và yêu cầu không đưa secret vào issue hoặc log.
2. Quy trình theo loại thay đổi: HTTP/domain, persistence/schema, storage provider, cross-cutting rule và lỗi HTTP.
3. Checklist trước khi viết: xác định route hoặc hành vi, service/chain/aspect liên quan, datasource, migration và khả năng dùng lại abstraction/util.
4. Checklist sau khi viết: đồng bộ mapper Java/XML/model, migration cho datasource hỗ trợ, khởi tạo storage qua context và ghi lại những phần chưa kiểm tra.
5. Liên kết đến kiến trúc, dữ liệu/cấu hình, convention, flow và reusable components.

### Convention mã nguồn

Trang mô tả quy tắc có bằng chứng trong code, bao gồm:

- `controller` là điểm nhận HTTP; business logic được điều phối qua service, aspect hoặc chain.
- Persistence thay đổi đồng bộ `*Mapper.java`, XML dưới `resources/mapper/`, entity và request/result bị ảnh hưởng.
- Thay đổi schema dùng Flyway `V...__...sql`, không sửa migration đã áp dụng và áp dụng cho SQLite/MySQL khi schema hỗ trợ cả hai.
- Storage tra qua `StorageSourceContext`, implementation kế thừa `AbstractBaseFileService`, tham số triển khai `IStorageParam`; controller không gọi trực tiếp provider cụ thể.
- Cross-cutting rule kiểm tra annotation, aspect và chain hiện có trước khi thêm điều kiện vào controller.
- Lỗi HTTP lần từ `GlobalExceptionHandler` và exception nghiệp vụ thay vì tự định nghĩa response lỗi không nhất quán.

Mỗi quy tắc ghi phạm vi áp dụng, ví dụ entry point và source reference. Những naming/style convention không có căn cứ sẽ đặt trong **Chưa xác minh**, không biến thành quy định dự án.

### Luồng mã nguồn

Trang dùng sơ đồ Mermaid và phần đọc tiếp cho bốn flow:

| Flow | Chuỗi chính |
| --- | --- |
| Request nghiệp vụ | route -> controller -> service/aspect/chain -> mapper XML hoặc context -> response |
| Lưu storage source | `StorageSourceController.saveStorageItem()` -> `StorageSourceService.saveStorageSource()` -> mapper/config service -> `StorageSourceContext.init()` |
| Liệt kê tệp | `FileController.list()` -> storage source lookup -> file service -> `FileChain` -> `AjaxJson<FileInfoResult>` |
| Xử lý lỗi HTTP | controller/service -> business exception -> `GlobalExceptionHandler` -> HTTP response |

Với từng flow, tài liệu chỉ rõ điểm đặt thay đổi và thành phần cần kiểm tra trước khi sửa. Sơ đồ không thêm DTO, cache hoặc helper nếu chúng không làm thay đổi quyết định của contributor.

### Thành phần tái sử dụng

Trang có hai bảng tách biệt:

1. **Framework và thư viện đã dùng**: Spring Boot, MyBatis-Plus, Flyway, HikariCP, AOP và cache, kèm vai trò được xác minh cùng cấu hình hoặc lớp dùng chúng.
2. **Abstraction và util**: `StorageSourceContext`, `AbstractBaseFileService`, `IStorageParam`, `FileChain`, các annotation/aspect cross-cutting và các util dưới `core/util` chỉ khi xác định được mục đích, consumer và cách dùng từ source.

Mỗi mục trả lời: dùng để làm gì, dùng từ đâu và khi nào nên tái sử dụng. Không lập danh sách toàn bộ lớp có hậu tố `Utils`; util không xác định được consumer hoặc ý nghĩa sẽ mang nhãn **Chưa xác minh** hoặc không đưa vào trang.

## Nguồn và độ chính xác

Nguồn ưu tiên là repository mã nguồn ZFile: `pom.xml`, controller, service, mapper/XML, migration, `StorageSourceContext`, `AbstractBaseFileService`, `FileChain`, aspect, exception handler, cấu hình và util được nêu trong tài liệu. Các trang hiện có trong repository tài liệu là nguồn dẫn đường, không thay thế việc đối chiếu mã nguồn khi bổ sung quy tắc, flow hay util mới.

Giữ nguyên tên package, class, method, annotation, field, enum, route và lệnh trong backtick. Với thông tin không được source chứng minh, dùng nhãn **Chưa xác minh**; không suy luận quy ước từ tên thư mục hoặc thông lệ Spring.

## Liên kết và navigation

Nhóm `Onboarding` đặt `code-contribution-guide` sau `first-contribution`. Nhóm `Engineering` đặt convention, flow và reusable components sau `code-navigation`, trước hoặc cạnh `data-and-configuration` theo trình tự đọc: kiến trúc -> điều hướng -> convention -> flow -> reusable components -> dữ liệu/cấu hình.

`start-here` trỏ đến trang tổng hợp sau phần kiến trúc và điều hướng. `first-contribution` trỏ đến convention, flow và reusable components tại các checklist tương ứng. README thay phần hướng dẫn đóng góp ngắn bằng liên kết đến trang tổng hợp, vẫn giữ quy trình kiểm tra cho repository tài liệu.

## Xác minh

Sau khi triển khai:

1. Chạy `yarn test` và `yarn build` trong repository tài liệu.
2. Xác minh bốn trang có trong sidebar, liên kết nội bộ không hỏng và Mermaid render trong production build.
3. Rà từng quy tắc, flow, thư viện, abstraction và util với source reference cụ thể.
4. Kiểm tra contributor có thể xác định đúng điểm bắt đầu cho thay đổi HTTP, persistence, storage và error handling chỉ từ hướng dẫn tổng hợp.
5. Kiểm tra tài liệu không khẳng định test backend, local run, PR workflow, naming/style rule hoặc util khi chưa có nguồn.

## Giới hạn

Repository hiện tại là site tài liệu, không chứa toàn bộ source ZFile. Việc triển khai chỉ được mở rộng danh mục thư viện và util sau khi có thể truy cập và đối chiếu repository mã nguồn ZFile tương ứng. Nếu source đó không có trong workspace, trang sẽ nêu rõ phạm vi đã xác minh thay vì suy đoán.
