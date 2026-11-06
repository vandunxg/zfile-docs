# Vietnamese Docs Orchestrator

**English** · [Tiếng Việt](README.vi.md)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

An [Agent Skill](https://agentskills.io/specification) for Claude Code and other compatible AI agents that writes and edits software documentation in natural Vietnamese: matched to the reader and faithful to the source.

## Why

Vietnamese documentation written by AI tends to fail in three ways:

- **It reads like machine translation:** "tiến hành thực hiện", "đóng vai trò vô cùng quan trọng", openings such as "Trong bối cảnh…".
- **It invents details:** button names, retry counts or notification channels that are not in the code.
- **It bloats:** more tables of contents, headings and bullets than the content needs.

This skill gives the agent a short procedure that avoids all three and scales with the job. Fixing one sentence needs no outline. Documenting a codebase means reading the code carefully and marking what could not be verified.

## What the skill does

| Scale | Example | Steps |
|---|---|---|
| Short | Error messages, UI labels | Rewrite → one style pass |
| One page | README, how-to, API explanation | Check sources → pick doc type → write → style pass |
| Multi-source | Docs from a codebase for several audiences | Same, plus a reader test |
| Edit | "Make this sound more natural" | Style pass; rewrite only sentences with a problem |

Core rules:

- **Source of truth:** every number, permission, state and button name must come from the source; anything missing goes under *Chưa xác minh* (unverified).
- **Diataxis and audience:** one purpose per page (Tutorial, How-to, Reference, Explanation); different writing for end users, developers and business readers.
- **Vietnamese style:** form of address per document type, when to keep English terms, a list of "AI prose" tells to remove.
- **Never-change list:** code, numbers, placeholders, conditions, negations and hedges ("thường", "có thể") stay exactly as they are.

All instructions live in one [SKILL.md](SKILL.md) file of about 100 lines.

## Examples

| Before | After |
|---|---|
| Đã có lỗi xảy ra trong quá trình xử lý request thanh toán của bạn, vui lòng tiến hành thực hiện lại sau. | Đã xảy ra lỗi khi xử lý thanh toán. Vui lòng thử lại sau. |
| Việc triển khai cơ chế retry đóng vai trò vô cùng quan trọng trong việc nâng cao tính ổn định của hệ thống. | Retry giúp hệ thống gửi lại yêu cầu khi gặp lỗi tạm thời. |
| Quý khách vui lòng click vào button **Quên mật khẩu** ở màn hình đăng nhập. | Ở màn hình đăng nhập, nhấn **Quên mật khẩu**. |

## Results

Measured on the cases in [`evals/`](evals/) with Claude Opus 5.5, one run per configuration, graded against checkable criteria.

| Comparison | Criteria passed | Notes |
|---|---|---|
| First version of the skill vs. no skill (4 cases) | 27/27 vs. 24/27 | Shorter docs: README 927 vs. 1264 words, docs written from code 1806 vs. 3119 words. About 25% more tokens. |
| Current version vs. first version (5 cases) | 33/34 vs. 33/34 | 24% fewer tool calls, about the same tokens. |

The sample is small, so treat the differences as a trend. Without the skill, the misses were about style (filler openings, line-by-line change commentary), not accuracy.

## Installation

### Claude Code

For all your projects:

```bash
git clone https://github.com/vandunxg/vietnamese-docs-orchestrator.git ~/.claude/skills/vietnamese-docs-orchestrator
```

For a single project (commit it with the repo so the whole team gets it):

```bash
git clone https://github.com/vandunxg/vietnamese-docs-orchestrator.git .claude/skills/vietnamese-docs-orchestrator
```

Restart the Claude Code session so the skill is loaded.

### Other agents

Copy the folder into the skills directory of any agent that supports the [Agent Skills](https://agentskills.io/specification) format. `agents/openai.yaml` holds the display name and default prompt for agents that read it, such as OpenAI Codex.

## Usage

You don't need to name the skill. The agent loads it when your request matches, for example:

```text
Sửa mấy thông báo lỗi này cho tự nhiên, dễ hiểu với người dùng cuối: ...
Viết README tiếng Việt cho script export_report.py để dev mới biết cách chạy.
Đọc module approval/ rồi viết tài liệu cho kế toán và dev mới, lưu vào docs/duyet-don-hang.md.
File huong-dan-api.md đọc như AI viết, sửa lại cho tự nhiên nhưng giữ nguyên nội dung kỹ thuật.
```

To call it explicitly in Claude Code: `/vietnamese-docs-orchestrator <request>`.

## Repository layout

```text
vietnamese-docs-orchestrator/
├── SKILL.md                 # All instructions for the agent
├── agents/openai.yaml       # Display metadata for OpenAI-compatible agents
├── evals/                   # Test cases, one folder per scenario
│   └── <case-name>/
│       ├── prompt.md        # The user's request
│       ├── case.yaml        # Description of the expected result
│       ├── graders/         # One file per grading criterion
│       └── files/           # Input files, if any
├── scripts/verify_bundle.py # Checks frontmatter and links
├── README.md                # English
└── README.vi.md             # Vietnamese
```

## Testing

Check the structure (valid frontmatter, no broken links):

```bash
python3 scripts/verify_bundle.py
```

Run the test cases against a real model, each with and without the skill (requires Claude Code v2.1.269 or later; these are billed model calls):

```bash
claude plugin eval . --allow-tools Write,Edit,Bash --scaffold
```

Results go to `evals/results/`, which is git-ignored.

## Contributing

The most useful contribution is **a new test case** that the current skill handles badly:

1. Create `evals/<case-name>/` with the layout above. Use a lowercase, hyphenated name that says what is being tested.
2. Put each grading criterion in its own file under `graders/`, and make it checkable, e.g. "The rewrite keeps the placeholder `{orderCode}`".
3. Run the tests before and after your change to `SKILL.md` and attach the results to the pull request.

When editing `SKILL.md`, describe what a good result looks like rather than adding prohibitions, and keep the file short: every added line is loaded on every run.

## Acknowledgements

The skill is an original Vietnamese rewrite based on ideas from:

- [trussary/vietnamese-language-skill](https://github.com/trussary/vietnamese-language-skill): Vietnamese technical writing rules
- [tronghieu/agent-skills](https://github.com/tronghieu/agent-skills): applying Diataxis to documentation
- [blader/humanizer](https://github.com/blader/humanizer): spotting and removing AI prose patterns
- [anthropics/skills](https://github.com/anthropics/skills): document co-authoring and reader testing
- [Diátaxis](https://diataxis.fr/): the documentation framework by Daniele Procida

## License

[MIT](LICENSE) © 2026 Nguyễn Văn Dũng
