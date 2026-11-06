---
name: vietnamese-docs-orchestrator
description: Use when writing, editing, translating or reviewing Vietnamese software documentation or product text - README, hướng dẫn sử dụng, how-to, tài liệu API, tài liệu nghiệp vụ/BA/PRD, đặc tả, onboarding, runbook, thông báo lỗi, chuỗi giao diện - including docs written from source code or business flows, and requests to make Vietnamese text tự nhiên hơn, bớt văn AI or bớt văn dịch máy.
---

# Viết tài liệu kỹ thuật tiếng Việt

Tài liệu tốt là tài liệu người đọc Việt hiểu và làm đúng ngay lần đọc đầu, và mọi dữ kiện trong đó khớp với nguồn. Khi hai điều này xung đột, độ chính xác thắng văn phong.

## 1. Chọn quy mô

| Quy mô | Dấu hiệu | Các bước |
|---|---|---|
| **Ngắn** | Sửa câu, nhãn UI, thông báo lỗi, một đoạn | 4 → 5 |
| **Một trang** | README, how-to, giải thích API; nguồn đã đủ | 2 → 3 → 4 → 5 |
| **Nhiều nguồn** | Viết từ codebase, PRD nhiều luồng, nhiều nhóm đọc, hiểu sai sẽ gây thao tác sai | 2 → 3 → 4 → 5 → 6 |
| **Biên tập** | Viết lại một tài liệu có sẵn cho tự nhiên hơn | 5, rồi 4 cho những câu cần viết lại |

Chỉ đọc những file nguồn liên quan. Dừng khi mọi câu đều đúng nguồn, đọc trôi và đủ để người đọc làm việc; chỉ sửa tiếp khi chỉ ra được một lỗi cụ thể.

## 2. Bám nguồn sự thật

- Mỗi con số, giới hạn, quyền, trạng thái, tên nút, tên màn hình, kênh thông báo, thời hạn trong tài liệu phải có trong nguồn (code, contract, UI, tài liệu người dùng đưa). Thứ gì nguồn không có thì đưa vào mục **Chưa xác minh**, hoặc ghi ngay tại chỗ nếu nó ảnh hưởng thao tác; không viết như sự thật.
- Viết từ code: đi từ entrypoint (controller, CLI, handler) → business rule → trạng thái, lỗi, quyền → tích hợp ngoài (retry, queue, gateway). Ghi chính xác điều kiện và phạm vi: quy tắc áp dụng cho thao tác nào, và thao tác nào *không* bị áp dụng.
- Giữ đúng ngữ nghĩa của số đếm và biên: "tối đa 3 lần gọi" khác "thử lại 3 lần"; "từ 50 triệu trở lên" khác "trên 50 triệu"; khoảng ngày có tính cả hai đầu hay không.
- Chỉ hỏi lại người dùng khi thiếu dữ kiện làm sai nội dung cốt lõi. Còn lại, viết phần đã xác minh và liệt kê phần thiếu.

## 3. Chọn kiểu tài liệu và người đọc

Mỗi trang có một mục đích chính (Diataxis):

| Người đọc muốn | Kiểu | Khung tối thiểu |
|---|---|---|
| Học từ đầu | Tutorial | Sẽ làm được gì → Chuẩn bị → Các bước → Kiểm tra kết quả |
| Làm xong một việc | How-to | Khi nào dùng → Điều kiện (nếu có) → Các bước → Kết quả → Nếu gặp lỗi |
| Tra cứu | Reference | Bảng tên / giá trị / ràng buộc → Ví dụ |
| Hiểu vì sao | Explanation | Tình huống → Cách hoạt động → Hệ quả, đánh đổi |

README thường là How-to kèm một bảng Reference ngắn. Một trang không cần đủ cả bốn kiểu.

| Người đọc | Viết gì | Để ra ngoài |
|---|---|---|
| Người dùng cuối | Khi nào dùng → làm gì → sẽ thấy gì → gặp lỗi thì làm gì | Exception, DB, queue, API path, tên class |
| Developer | Luồng gọi, điều kiện, input/output, side effect, lỗi, edge case; tên method, field, mã lỗi giữ nguyên văn | Giải thích dài khái niệm phổ thông |
| Nghiệp vụ, BA | Tác nhân, trigger, trạng thái, business rule, ngoại lệ | Chi tiết triển khai |

Tài liệu cho nhiều nhóm là **một** file: phần nghiệp vụ cho người không biết code đứng trước; phần "Ghi chú kỹ thuật" cho dev đứng sau và trỏ lại quy tắc ở phần trước thay vì viết lại.

## 4. Viết tiếng Việt nguyên bản

| Loại tài liệu | Xưng hô | Thuật ngữ |
|---|---|---|
| Spec, design doc, postmortem | Không xưng hô; chủ ngữ là "hệ thống", "dịch vụ" | Giữ tên entity, API |
| Runbook | Câu mệnh lệnh: "Kiểm tra…", "Khởi động lại…" | Giữ `restart`, `rollback` |
| README, onboarding, tutorial | "bạn" khi cần, không rải mọi câu | Giải thích thuật ngữ ở lần đầu xuất hiện |
| PR, ghi chú cho dev | Tự nhiên, ngắn | Giữ `commit`, `merge`, `deploy`, `cache`, `retry` |
| UI, help center | "bạn" hoặc câu trực tiếp, một cách xưng hô cho cả sản phẩm | Từ phổ thông: "thử lại", "tải lên", "tài khoản", "mật khẩu" |
| Quy trình nghiệp vụ, PRD | Nêu vai trò cụ thể: "Kế toán duyệt đơn" | Thuật ngữ nghiệp vụ của đơn vị |

Dự án đã có house style hoặc bảng thuật ngữ thì theo dự án.

- Tên API, code, field, role, bảng, lệnh, enum giữ nguyên văn, đúng hoa thường, đặt trong backtick.
- Một khái niệm dùng một từ trong cả tài liệu (không đổi qua lại "đơn hàng" và "yêu cầu mua").
- Viết như người Việt nói, không dịch từng cụm tiếng Anh: động từ đứng gần chủ ngữ; câu nhiều điều kiện thì tách thành câu ngắn.
- Thông báo lỗi nói điều gì đã xảy ra và người đọc làm gì tiếp. Nguyên nhân và lời hứa ("hệ thống sẽ tự thử lại") chỉ xuất hiện khi nguồn nói vậy.

Ví dụ:

- `Việc triển khai cơ chế retry đóng vai trò vô cùng quan trọng trong việc nâng cao tính ổn định của hệ thống.` → `Retry giúp hệ thống gửi lại yêu cầu khi gặp lỗi tạm thời.`
- `Bạn nên tiến hành xác minh tình trạng hoạt động của service trước khi restart.` → `Kiểm tra trạng thái service trước khi restart.`
- `Đã có lỗi xảy ra trong quá trình xử lý request của bạn, vui lòng tiến hành thực hiện lại.` → `Không xử lý được yêu cầu. Vui lòng thử lại.`

## 5. Một lượt soát văn

Rà một lượt và sửa những câu có các dấu hiệu sau; câu đã rõ và đúng thì để nguyên.

- Mở đầu không mang thông tin ("Trong bối cảnh…", "Tài liệu này sẽ…", "Phần dưới đây hướng dẫn…"); kết luận nhắc lại ý đã nói ("Tóm lại…", "Chúc bạn…").
- "không chỉ… mà còn…", câu hỏi tu từ rồi tự trả lời, chuỗi ba tính từ trừu tượng.
- Từ khoa trương không định lượng: tối ưu, toàn diện, liền mạch, mạnh mẽ, vô cùng quan trọng, tuyệt đối.
- Cụm thừa: "tiến hành thực hiện", "nhằm mục đích", "cần phải", "một cách".
- Tiêu đề, chữ đậm, bullet, mục lục, đường kẻ ngang nhiều hơn mức nội dung cần.

Những thứ sau giữ nguyên, kể cả khi viết lại câu chứa chúng:

- Code block, inline code, JSON, SQL, YAML, lệnh CLI, API path, URL, tên biến, trích dẫn, nhãn giao diện.
- Số, ngày, đơn vị, placeholder (`{orderCode}`), điều kiện (nếu, trừ khi, chỉ khi), từ phủ định, chủ thể chịu trách nhiệm, status, mã lỗi.
- Mức độ chắc chắn: "thường", "có thể", "tối đa" vẫn là "thường", "có thể", "tối đa".

Nếu thay một từ làm lệch nghĩa, giữ từ gốc.

## 6. Thử với người đọc (quy mô Nhiều nguồn)

Đọc lại bản hoàn chỉnh với ba câu hỏi và sửa chỗ trượt:

1. Người mới có biết **khi nào** dùng và **bước tiếp theo** không?
2. Dev có xác định đúng **điều kiện, trạng thái, lỗi** chỉ từ tài liệu không?
3. Câu nào có thể khiến người đọc thao tác sai hoặc hiểu sai business rule?

## Kết quả trả về

- **Sửa câu, chuỗi ngắn:** các bản sửa theo đúng thứ tự gốc. Sau đó tối đa hai ghi chú, mỗi ghi chú một câu, chỉ khi người dùng cần quyết định điều gì (ví dụ chọn "tệp" hay "file").
- **Tài liệu, file:** câu đầu tài liệu nói tính năng hoặc công cụ làm gì cho người đọc; tài liệu cho nhiều nhóm thêm một dòng chỉ phần nào dành cho ai. Tin nhắn trả lời gồm một câu nói đã lưu ở đâu, rồi các điểm người dùng cần xác nhận (chưa xác minh, giả định, ví dụ minh họa cần thay), mỗi điểm một dòng.
- **Biên tập:** file đã sửa. Tin nhắn liệt kê ngắn các loại thay đổi và nêu rõ câu nào đã bỏ vì là tuyên bố không có cơ sở.
