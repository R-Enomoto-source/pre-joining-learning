# 弱点一覧（試験領域の特定）

## スコープと根拠

| 項目 | 内容 |
|------|------|
| 根拠とした答案 | `exercise/Chapter3/Chapter3_sec.md`（採点済み） |
| 問題・解説の照合 | `local_problems/answers/Chapter3_answers.md`（各問の「## N.」見出しと **正解**・解説本文） |
| 採点結果 | `local_problems/scoring_result/Chapter3_sec_scoring_20260513.md` |
| 対象とした不正解 | **問 2, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 22, 26, 27, 28, 33, 34, 40, 43**（計 **23** 問） |

試験の「大項目」は、Oracle が公開している **Java SE 17 Developer（1Z0-829）** の Exam Topics に載っている英語カテゴリ名に合わせて分類しています。各問がどの公式解説に対応するかは `Chapter3_answers.md` の節タイトルと照合済みです。

---

## 不正解 1 問ごとの対応（正確性のためのトレーサビリティ）

| 問 | `Chapter3_answers.md` で扱っている主題（要約） | 試験トピック（1Z0-829） |
|----|-----------------------------------------------|-------------------------|
| 2 | 型変換・コンパイルエラーになる代入／演算（リテラル既定型、`long` 混在、`float` 等） | **Handling date, time, text, numeric and boolean values**（プリミティブ・数値リテラル・キャストの可否） |
| 6 | 演算子の優先順位（`%` `*` `/` と加算、左からの評価） | **Handling date, time, text, numeric and boolean values**（数値式の評価） |
| 7 | 算術演算子の優先順位と **整数除算の切り捨て** | **Handling date, time, text, numeric and boolean values** |
| 8 | 参照の **同一性**（`==` と `new` による参照の変化） | **Utilizing Java Object-Oriented Approach**（オブジェクト参照の理解） |
| 9 | **equals のオーバーライド**と比較に使うフィールド | **Utilizing Java Object-Oriented Approach** |
| 10 | `equals` の **オーバーロード**と実際に束縛されるメソッド | **Utilizing Java Object-Oriented Approach**（*Implement overloading…* に相当） |
| 11 | `equals` と **null**（契約上の戻り値） | **Utilizing Java Object-Oriented Approach** |
| 12 | **ビット演算子**（`&` / `|` / `^` とビットパターン） | **Handling date, time, text, numeric and boolean values**（整数をビット列として扱う） |
| 14 | `if` と **中カッコ省略**（次の 1 文だけが分岐本体） | **Controlling Program Flow** |
| 15 | `if` / `else` と中カッコ省略 | **Controlling Program Flow** |
| 16 | `if` / `else` のあと **独立した `if`** と表示パターン | **Controlling Program Flow** |
| 17 | **`switch` 条件式に使える型**（`long` / `boolean` 不可など） | **Controlling Program Flow** |
| 18 | **`switch` の case 値**（定数性・型の整合） | **Controlling Program Flow** |
| 19 | **`switch` 文のフォールスルー**（`break` なし） | **Controlling Program Flow** |
| 20 | **`default` の位置**とフォールスルー | **Controlling Program Flow** |
| 22 | **`switch` 式**の網羅性と `default` 不足によるコンパイルエラー | **Controlling Program Flow**（*switch … expressions*） |
| 26 | **`do`-`while`** の構文・条件・最低 1 回実行 | **Controlling Program Flow** |
| 27 | **`do`-`while`** と中カッコ省略（本体は 1 文のみ） | **Controlling Program Flow** |
| 28 | **`for`** の初期化文（同じ型のみ同時宣言） | **Controlling Program Flow** |
| 33 | **二重ループ**とジグザグ配列（内側の終端 `array[i].length`） | **Working with Arrays and Collections**（配列の作成・走査） |
| 34 | **多次元配列の `length`**（1 次元目）と走査結果 | **Working with Arrays and Collections** |
| 40 | **`break` と二重ループ**（内側ループのみ脱出） | **Controlling Program Flow**（*break … statements*） |
| 43 | **ラベル付き `continue` / `break`** と累積変数 | **Controlling Program Flow** |

---

## 試験領域別の弱点一覧（集約）

以下は上表を **試験トピック単位** にまとめたものです。いずれも **上記 23 問の不正解**から導出しています。

### 1. Handling date, time, text, numeric and boolean values

- **含まれる誤答**: 2, 6, 7, 12  
- **弱点の中身**: 数値リテラルの既定型、代入時のコンパイル可否、演算子優先順位、**int 除算の切り捨て**、ビット単位の `&` 等。  
- **復習の芯**: `Chapter3_answers.md` の問 2・6・7・12 の解説（型変換、優先順位、ビット演算子表）。

### 2. Utilizing Java Object-Oriented Approach

- **含まれる誤答**: 8, 9, 10, 11  
- **弱点の中身**: 参照の同一性、`equals` のオーバーライド内容、**`equals(Object)` でないシグネチャはオーバーロード**、`equals(null)`。  
- **復習の芯**: 問 8〜11 の解説（同一性・同値性・オーバーロード・null）。

### 3. Controlling Program Flow

- **含まれる誤答**: 14, 15, 16, 17, 18, 19, 20, 22, 26, 27, 28, 40, 43  
- **弱点の中身**: `if` / `else` の中カッコ省略、`switch` 文の型・case 定数・フォールスルー・`default` の位置、`switch` 式の網羅、`while` / `do`-`while` / `for` の構文、`break` / `continue` / **ラベル**と二重ループ。  
- **復習の芯**: 問 14〜22、26〜28、40、43 の解説。

### 4. Working with Arrays and Collections（配列の作成・走査）

- **含まれる誤答**: 33, 34  
- **弱点の中身**: 二重ループ、**`array.length` と `array[i].length` の違い**、ジグザグ配列の走査。  
- **復習の芯**: 問 33・34 の解説。  
- **注**: Oracle の公開トピックでは配列とコレクションが同じ大見出しにまとまっている資料が多いです。第 3 章の誤答は **配列**に限定されます。

---

## メタ（答案の取り方）

次の不正解は、上記の言語仕様に加えて **「複数選択の完全一致」** の取りこぼしが要因です。

- **問 2, 17, 18**: 正解集合から 1 つでもずれると不正解（採点基準は `Chapter3_sec_scoring_20260513.md` 冒頭と同じ）。

試験本体の知識領域とは別層ですが、**1Z0-829 は複数選択が含まれる**ため、答案の突合せも弱点対策に含めるのが安全です。

---

## カバレッジ確認（漏れなし）

次の集合は、上記セクション「不正解 1 問ごとの対応」の **23 問**と一致します。

`{2,6,7,8,9,10,11,12,14,15,16,17,18,19,20,22,26,27,28,33,34,40,43}`

各問は **いずれか 1 つの試験トピック行**にのみ重複なく列挙されているわけではなく（例: 問 6 は数値式と制御構文の前提の両方に触れる）、**集約セクションでは 4 トピックに分割**して重複を避けています。

---

*試験トピック名は Oracle が公開する 1Z0-829 の表現に合わせています。試験ガイドの改訂で用語が変わった場合は、最新の Exam Topics に置き換えてください。*
