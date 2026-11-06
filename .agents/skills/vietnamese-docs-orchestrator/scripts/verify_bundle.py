#!/usr/bin/env python3
"""Kiểm tra cấu trúc skill: frontmatter hợp lệ, đúng một SKILL.md, mọi link nội bộ trỏ tới file có thật."""
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
NAME_RE = re.compile(r"^[a-z0-9]+(-[a-z0-9]+)*$")


def verify() -> list[str]:
    errors: list[str] = []
    skill_files = sorted(p for p in ROOT.rglob("SKILL.md") if ".git" not in p.parts)
    if skill_files != [ROOT / "SKILL.md"]:
        errors.append(f"Cần đúng một SKILL.md ở thư mục gốc, tìm thấy: {skill_files}")
        return errors

    text = (ROOT / "SKILL.md").read_text(encoding="utf-8")
    match = re.match(r"^---\n(.*?)\n---\n", text, re.S)
    if not match:
        return ["SKILL.md thiếu frontmatter YAML"]
    fields = dict(line.split(":", 1) for line in match.group(1).splitlines() if ":" in line)
    name = fields.get("name", "").strip()
    description = fields.get("description", "").strip()
    if not NAME_RE.match(name):
        errors.append(f"name không hợp lệ: {name!r}")
    if not description:
        errors.append("Thiếu description")
    elif len(description) > 1024:
        errors.append(f"description dài {len(description)} ký tự, tối đa 1024")

    for doc in [ROOT / "SKILL.md", ROOT / "README.md"]:
        if not doc.exists():
            continue
        for target in re.findall(r"\]\((?!https?://|#|mailto:)([^)#\s]+)", doc.read_text(encoding="utf-8")):
            if not (doc.parent / target).exists():
                errors.append(f"{doc.name}: link hỏng tới {target}")
    return errors


def main() -> int:
    problems = verify()
    for problem in problems:
        print("FAIL:", problem)
    if not problems:
        print("PASS: frontmatter hợp lệ, một SKILL.md, không có link hỏng")
    return 1 if problems else 0


if __name__ == "__main__":
    sys.exit(main())
