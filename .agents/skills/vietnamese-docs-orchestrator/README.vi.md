# Vietnamese Docs Orchestrator

[English](README.md) · **Tiếng Việt**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Skill cho Claude Code và các AI agent hỗ trợ Agent Skills, giúp viết và biên tập tài liệu phần mềm bằng tiếng Việt tự nhiên, đúng người đọc và khớp với nguồn sự thật.

## Vì sao cần skill này

Tài liệu tiếng Việt do AI viết thường mắc ba lỗi:

- **Đọc như văn dịch máy:** "tiến hành thực hiện", "đóng vai trò vô cùng quan trọng", mở bài "Trong bối cảnh…".
- **Bịa chi tiết:** tên nút, số lần retry, kênh thông báo không có trong code.
- **Phình to:** mục lục, tiêu đề, bullet nhiều hơn nội dung cần.

Skill này cho agent một quy trình gọn để tránh cả ba lỗi, theo quy mô công việc: sửa một câu thì không lập dàn ý, còn viết tài liệu từ codebase thì phải đọc code kỹ và đánh dấu phần chưa xác minh.

## Skill làm gì

| Quy mô | Ví dụ | Các bước |
|---|---|---|
| Ngắn | Sửa thông báo lỗi, nhãn UI | Viết lại → soát văn một lượt |
| Một trang | README, how-to, giải thích API | Bám nguồn → chọn kiểu tài liệu → viết → soát văn |
| Nhiều nguồn | Tài liệu từ codebase cho nhiều nhóm đọc | Như trên, thêm bước thử với người đọc |
| Biên tập | "Viết lại cho tự nhiên hơn" | Soát văn, chỉ viết lại câu có vấn đề |

Nội dung chính:

- **Bám nguồn sự thật:** mọi con số, quyền, trạng thái, tên nút phải có trong nguồn; phần thiếu ghi vào mục *Chưa xác minh*.
- **Diataxis và người đọc:** mỗi trang một mục đích (Tutorial, How-to, Reference, Explanation); cách viết riêng cho người dùng cuối, developer, nghiệp vụ.
- **Văn phong tiếng Việt:** xưng hô theo loại tài liệu, quy tắc giữ thuật ngữ tiếng Anh, danh sách dấu hiệu "văn AI" cần bỏ.
- **Danh sách giữ nguyên:** code, số, placeholder, điều kiện, từ phủ định, mức độ chắc chắn ("thường", "có thể").

Toàn bộ quy tắc nằm trong một file [SKILL.md](SKILL.md), khoảng 100 dòng.

## Ví dụ

| Trước | Sau |
|---|---|
| Đã có lỗi xảy ra trong quá trình xử lý request thanh toán của bạn, vui lòng tiến hành thực hiện lại sau. | Đã xảy ra lỗi khi xử lý thanh toán. Vui lòng thử lại sau. |
| Việc triển khai cơ chế retry đóng vai trò vô cùng quan trọng trong việc nâng cao tính ổn định của hệ thống. | Retry giúp hệ thống gửi lại yêu cầu khi gặp lỗi tạm thời. |
| Quý khách vui lòng click vào button **Quên mật khẩu** ở màn hình đăng nhập. | Ở màn hình đăng nhập, nhấn **Quên mật khẩu**. |

## Kết quả đo

Đo trên các tình huống trong [`evals/`](evals/) với Claude Opus 5.5, mỗi cấu hình chạy một lần, chấm bằng tiêu chí kiểm chứng được.

| So sánh | Tiêu chí đạt | Ghi chú |
|---|---|---|
| Bản đầu của skill so với không dùng skill (4 tình huống) | 27/27 so với 24/27 | Tài liệu ngắn hơn: README 927 so với 1264 từ, tài liệu viết từ code 1806 so với 3119 từ. Tốn thêm khoảng 25% token. |
| Bản hiện tại so với bản đầu (5 tình huống) | 33/34 so với 33/34 | Số lần gọi tool giảm 24%, token gần như không đổi. |

Mẫu nhỏ nên chênh lệch chỉ là xu hướng. Khi không dùng skill, các lỗi chủ yếu nằm ở văn phong (câu mở đầu thừa, giải thích từng dòng đã sửa), không phải ở độ chính xác.

## Cài đặt

### Claude Code

Cài cho mọi dự án của bạn:

```bash
git clone https://github.com/vandunxg/vietnamese-docs-orchestrator.git ~/.claude/skills/vietnamese-docs-orchestrator
```

Hoặc chỉ cho một dự án (commit cùng repo để cả team dùng):

```bash
git clone https://github.com/vandunxg/vietnamese-docs-orchestrator.git .claude/skills/vietnamese-docs-orchestrator
```

Khởi động lại phiên Claude Code để skill được nạp.

### Agent khác

Sao chép cả thư mục vào thư mục skills của agent hỗ trợ định dạng [Agent Skills](https://agentskills.io/specification). File `agents/openai.yaml` chứa tên hiển thị và prompt mặc định cho các agent đọc file này, ví dụ OpenAI Codex.

## Cách dùng

Không cần gọi tên skill. Agent tự nạp khi yêu cầu của bạn khớp, ví dụ:

```text
Sửa mấy thông báo lỗi này cho tự nhiên, dễ hiểu với người dùng cuối: ...
Viết README tiếng Việt cho script export_report.py để dev mới biết cách chạy.
Đọc module approval/ rồi viết tài liệu cho kế toán và dev mới, lưu vào docs/duyet-don-hang.md.
File huong-dan-api.md đọc như AI viết, sửa lại cho tự nhiên nhưng giữ nguyên nội dung kỹ thuật.
```

Muốn gọi trực tiếp trong Claude Code: `/vietnamese-docs-orchestrator <yêu cầu>`.

## Cấu trúc repo

```text
vietnamese-docs-orchestrator/
├── SKILL.md                 # Toàn bộ chỉ dẫn cho agent
├── agents/openai.yaml       # Metadata hiển thị cho agent tương thích OpenAI
├── evals/                   # Bộ test, mỗi thư mục là một tình huống
│   └── <tên-test>/
│       ├── prompt.md        # Yêu cầu của người dùng
│       ├── case.yaml        # Mô tả kết quả mong đợi
│       ├── graders/         # Mỗi file là một tiêu chí chấm
│       └── files/           # File đầu vào (nếu có)
├── scripts/verify_bundle.py # Kiểm tra frontmatter và link
├── README.md                # Bản tiếng Anh
└── README.vi.md             # Bản tiếng Việt
```

## Kiểm thử

Kiểm tra cấu trúc (frontmatter hợp lệ, không có link hỏng):

```bash
python3 scripts/verify_bundle.py
```

Chạy bộ test bằng model thật, mỗi test chạy cả có skill và không có skill (cần Claude Code v2.1.269 trở lên; lệnh này tốn phí gọi model):

```bash
claude plugin eval . --allow-tools Write,Edit,Bash --scaffold
```

Kết quả nằm trong `evals/results/` (đã được bỏ qua trong `.gitignore`).

## Đóng góp

Đóng góp có giá trị nhất là **một tình huống test mới** mà skill hiện tại xử lý chưa tốt:

1. Tạo `evals/<tên-test>/` theo cấu trúc ở trên. Tên test viết thường, nối bằng dấu gạch ngang, nói rõ thứ được kiểm tra.
2. Mỗi tiêu chí chấm là một file trong `graders/`, kiểm chứng được, ví dụ "Bản sửa giữ nguyên placeholder `{orderCode}`".
3. Chạy test trước và sau khi sửa `SKILL.md`, đính kèm kết quả vào pull request.

Khi sửa `SKILL.md`, ưu tiên mô tả hình dạng của kết quả đúng thay vì thêm lệnh cấm, và giữ file ngắn: mỗi dòng thêm vào được nạp ở mọi lần skill chạy.

## Ghi nhận

Nội dung skill được viết lại bằng tiếng Việt, dựa trên ý tưởng từ:

- [trussary/vietnamese-language-skill](https://github.com/trussary/vietnamese-language-skill): quy tắc viết tài liệu kỹ thuật tiếng Việt
- [tronghieu/agent-skills](https://github.com/tronghieu/agent-skills): áp dụng Diataxis khi viết tài liệu
- [blader/humanizer](https://github.com/blader/humanizer): nhận diện và bỏ dấu hiệu văn AI
- [anthropics/skills](https://github.com/anthropics/skills): quy trình đồng tác giả tài liệu và kiểm tra với người đọc
- [Diátaxis](https://diataxis.fr/): khung phân loại tài liệu của Daniele Procida

## Giấy phép

[MIT](LICENSE) © 2026 Nguyễn Văn Dũng
