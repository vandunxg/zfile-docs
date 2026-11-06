#!/usr/bin/env python3
"""Xuất báo cáo doanh thu theo chi nhánh."""
import argparse
import os
import sys
from datetime import date

from sqlalchemy import bindparam, create_engine, text

MAX_RANGE_DAYS = 31


def parse_args():
    p = argparse.ArgumentParser(description="Export revenue report by branch")
    p.add_argument("--from", dest="date_from", required=True, type=date.fromisoformat,
                   help="Start date, YYYY-MM-DD")
    p.add_argument("--to", dest="date_to", required=True, type=date.fromisoformat,
                   help="End date (inclusive), YYYY-MM-DD")
    p.add_argument("--branch", action="append", default=[],
                   help="Branch code, repeatable. Omit to export all branches")
    p.add_argument("--format", choices=["csv", "xlsx"], default="csv")
    p.add_argument("--output", default="./reports", help="Output directory")
    return p.parse_args()


def main():
    args = parse_args()
    db_url = os.environ.get("REPORT_DB_URL")
    if not db_url:
        print("REPORT_DB_URL is not set", file=sys.stderr)
        return 1
    if args.date_to < args.date_from:
        print("--to must be on or after --from", file=sys.stderr)
        return 2
    if (args.date_to - args.date_from).days + 1 > MAX_RANGE_DAYS:
        print(f"Date range must not exceed {MAX_RANGE_DAYS} days", file=sys.stderr)
        return 2

    engine = create_engine(db_url)
    sql = ("SELECT branch_code, order_date, SUM(amount) AS revenue FROM orders "
           "WHERE order_date BETWEEN :f AND :t")
    params = {"f": args.date_from, "t": args.date_to}
    if args.branch:
        sql += " AND branch_code IN :branches"
        params["branches"] = args.branch
    sql += " GROUP BY branch_code, order_date ORDER BY branch_code, order_date"
    stmt = text(sql)
    if args.branch:
        stmt = stmt.bindparams(bindparam("branches", expanding=True))
    with engine.connect() as conn:
        rows = conn.execute(stmt, params).fetchall()

    os.makedirs(args.output, exist_ok=True)
    name = f"revenue_{args.date_from}_{args.date_to}.{args.format}"
    path = os.path.join(args.output, name)
    if args.format == "csv":
        import csv
        with open(path, "w", newline="", encoding="utf-8-sig") as f:
            w = csv.writer(f)
            w.writerow(["branch_code", "order_date", "revenue"])
            w.writerows(rows)
    else:
        from openpyxl import Workbook
        wb = Workbook()
        ws = wb.active
        ws.append(["branch_code", "order_date", "revenue"])
        for r in rows:
            ws.append(list(r))
        wb.save(path)
    print(path)
    return 0


if __name__ == "__main__":
    sys.exit(main())
