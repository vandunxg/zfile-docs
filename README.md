# Tài liệu ZFile

Repository này chứa site Docusaurus cho tài liệu business và engineering của ZFile.

Trang tài liệu: <https://vandunxg.github.io/zfile-docs/>

## Nội dung

- **Business Core**: mục đích, mô hình, quyền truy cập, workflow và quy tắc cốt lõi.
- **Business**: tính năng, storage source, thao tác tệp, phân quyền, chia sẻ, cấu hình và các kịch bản nghiệm thu.
- **Engineering**: kiến trúc, điều hướng mã nguồn, dữ liệu, cấu hình và quy ước biên soạn tài liệu.
- **Onboarding**: bắt đầu đọc mã nguồn và thực hiện đóng góp đầu tiên.
- **Tài liệu cho team**: roadmap và các tài liệu định hướng.

Bắt đầu từ [Tài liệu ZFile](./docs/intro.mdx) hoặc mở [site đã xuất bản](https://vandunxg.github.io/zfile-docs/).

## Yêu cầu

- Node.js 22.12.0 trở lên, theo yêu cầu của Mermaid.
- Yarn 1.22.22. Phiên bản này được khai báo trong `package.json`.
- Corepack để kích hoạt Yarn nếu máy chưa có lệnh `yarn`.

## Cài đặt

```bash
corepack enable
yarn install --frozen-lockfile
```

## Phát triển cục bộ

Khởi động máy chủ Docusaurus:

```bash
yarn start
```

Site chạy tại `http://localhost:3002`.

## Kiểm tra và build

Chạy test của repository:

```bash
yarn test
```

Build site:

```bash
yarn build
```

Sau khi build, có thể phục vụ thư mục `build` bằng:

```bash
yarn serve
```

## Cấu trúc repository

```text
docs/                 Nội dung tài liệu Markdown/MDX
src/                  CSS và client module của Docusaurus
tests/                Test cấu hình và pipeline tài liệu
docusaurus.config.js  Cấu hình site, URL và route
sidebars.js           Cấu trúc menu tài liệu
build/                Output sau khi build
```

Không sửa trực tiếp các tệp trong `.docusaurus/`; đây là thư mục sinh tự động trong quá trình build.

## Xuất bản

Workflow `.github/workflows/deploy.yml` chạy khi có push lên `main` hoặc được kích hoạt thủ công. Workflow thực hiện các bước:

1. Cài dependency bằng `yarn install --frozen-lockfile`.
2. Chạy `yarn test`.
3. Build bằng `yarn build`.
4. Upload thư mục `build` và deploy lên GitHub Pages.

Nếu test hoặc build lỗi, workflow không deploy phiên bản mới.

## Đóng góp tài liệu

1. Chọn miền cần cập nhật trong `docs/`.
2. Đối chiếu nội dung với nguồn và giữ nguyên tên API, field, enum, class và lệnh trong backtick.
3. Kiểm tra liên kết nội bộ, Mermaid nếu có, rồi chạy `yarn test` và `yarn build`.
4. Gửi thay đổi theo quy trình của repository.

Xem [Quy ước biên soạn](./docs/engineering/documentation-standards.mdx) để biết cách viết và kiểm tra tài liệu.
