"""
Javaガイド第2章の画像を逐語起こし用Markdownに追記するスクリプト。

- 画像フォルダ（入力）
    C:\\Users\\20171\\Learning\\PDF_PICTURE\\Java_guide\\ch2_Java_guide_2_JavaSE17Silver_guide_Java_SE_17_images
    もしくは、リポジトリ内のコピー:
    image_cache/ch2_Java_guide_2_JavaSE17Silver_guide_Java_SE_17_images

- 書き起こしの保存先（出力）
    java_blackbook/local_problems/transcription/Chapter2_transcription.md

フォーマット:

    ファイル名

    ページ本文（生テキスト）
    ---

すでに手動で page_0001 / page_0002 を書き起こしている前提で、
それ以降のページだけを追記する。
"""

from pathlib import Path

from image_to_text import get_images_sorted, extract_text_easyocr


def main() -> None:
    # リポジトリルート（LearningTools/image-to-text-batch からの相対）
    repo_root = Path(__file__).resolve().parents[2]

    # 画像フォルダ（リポジトリ内のコピーを利用）
    image_folder = (
        repo_root
        / "image_cache"
        / "ch2_Java_guide_2_JavaSE17Silver_guide_Java_SE_17_images"
    )

    # 出力先ファイル
    output_path = (
        repo_root
        / "java_blackbook"
        / "local_problems"
        / "transcription"
        / "Chapter2_transcription.md"
    )

    if not image_folder.is_dir():
        raise SystemExit(f"画像フォルダが見つかりません: {image_folder}")

    images = get_images_sorted(image_folder)
    if not images:
        raise SystemExit(f"画像が見つかりません: {image_folder}")

    total = len(images)

    # 1枚目と2枚目はすでに手動で書き起こし済みとみなし、スキップする
    processed_manual = 2
    remaining_images = [p for p in images if not any(
        key in p.name
        for key in (
            "page_0001",
            "page_0002",
        )
    )]

    print(f"画像総数: {total} 枚")
    print(f"手動で書き起こし済み: {processed_manual} 枚")
    print(f"このスクリプトで処理する残り: {len(remaining_images)} 枚")
    print("-" * 40)

    # EasyOCR を使って 1枚ずつ順番に処理
    current_index = processed_manual
    for img in remaining_images:
        current_index += 1
        print(f"{img.name} ({current_index}/{total})")

        text = extract_text_easyocr(img)
        page_text = text.strip() if text else "(テキストなし)"

        # 追記
        lines: list[str] = [
            img.name,
            "",
            page_text,
            "---",
            "",
        ]

        # 既存内容は消さずに末尾に追記
        output_path.parent.mkdir(parents=True, exist_ok=True)
        with output_path.open("a", encoding="utf-8") as f:
            f.write("\n".join(lines))


if __name__ == "__main__":
    main()

