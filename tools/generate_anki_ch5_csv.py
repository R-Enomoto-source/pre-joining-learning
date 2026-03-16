import csv
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


def load_tsv(path: Path):
    rows = []
    if not path.exists():
        return rows
    with path.open("r", encoding="utf-8", newline="") as f:
        reader = csv.reader(f, delimiter="\t")
        for row in reader:
            if not row or all(not col.strip() for col in row):
                continue
            rows.append(row)
    return rows


def split_answer_and_explanation(back: str):
    """
    TSVの第2列は
    「短い答え。<br><br>長い解説…」
    という形式になっている前提で、
    最初の<br><br>で2つに分割する。
    """
    marker = "<br><br>"
    if marker in back:
        answer, explanation = back.split(marker, 1)
        return answer.strip(), explanation.lstrip()
    return back.strip(), ""


def generate_basic_csv(tsv_path: Path, out_path: Path):
    rows = load_tsv(tsv_path)
    out_path.parent.mkdir(parents=True, exist_ok=True)

    with out_path.open("w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter=",", quotechar='"', quoting=csv.QUOTE_ALL, lineterminator="\n")

        for row in rows:
            # 形式: Front, Back, Tags
            if len(row) < 3:
                continue
            front, back, tags = row[0].strip(), row[1].strip(), row[2].strip()
            if not front or not back:
                continue

            answer, explanation = split_answer_and_explanation(back)
            if explanation:
                back_field = f"{answer}<br><br>【解説】<br>{explanation}"
            else:
                back_field = f"{answer}<br><br>【解説】<br>"

            writer.writerow([front, back_field, tags])


def generate_cloze_csv(tsv_path: Path, out_path: Path):
    rows = load_tsv(tsv_path)
    out_path.parent.mkdir(parents=True, exist_ok=True)

    with out_path.open("w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter=",", quotechar='"', quoting=csv.QUOTE_ALL, lineterminator="\n")

        for row in rows:
            # 形式: Text, Extra, Tags
            if len(row) < 3:
                continue
            text, extra, tags = row[0].strip(), row[1].strip(), row[2].strip()
            if not text or not extra:
                continue

            back_field = f"【解説】<br>{extra}"
            writer.writerow([text, back_field, tags])


def main():
    basic_tsv = ROOT / "anki_basic.tsv"
    cloze_tsv = ROOT / "anki_cloze.tsv"

    basic_csv = ROOT / "docs" / "AnkiCard" / "anki_basic_reversed_Chapter5.csv"
    cloze_csv = ROOT / "docs" / "AnkiCard" / "anki_cloze_Chapter5.csv"

    generate_basic_csv(basic_tsv, basic_csv)
    generate_cloze_csv(cloze_tsv, cloze_csv)


if __name__ == "__main__":
    main()

