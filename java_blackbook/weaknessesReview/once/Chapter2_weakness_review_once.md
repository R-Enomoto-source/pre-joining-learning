# Chapter2 弱点復習（1回目採点ベース）

本章は、採点結果の **「不正解から整理した弱点学習領域一覧」**（`local_problems/scoring_result/Chapter2_ScoringResult_1once.md`）と、公式の **解答・解説**（`local_problems/answers/Chapter2_answers.md`）に基づいて整理した弱点解説です。各項目は **不正解となった設問番号** を必ず紐づけています。

以下の **例となるコード** は理解用の断片です。複数の `Main` が出てくる場合は、ファイルを分けるか、`jshell` で断片だけ試すなどしてコンパイルしてください。

---

## 領域 1：整数リテラルと進数表記（該当：**問2**）

### 核心となるルール

整数リテラルは接頭辞で解釈が変わる。**`0` で始まる桁**は **8 進整数**として解釈される。8 進では各桁は **0〜7 のみ**であり、**8 と 9 は使えない**（これに違反するとコンパイルエラー）。2 進は `0b`、16 進は `0x` で始める（公式：0b、0、0x）。

### 公式解説ベースの要点

- `0827` は 8 進解釈となるが、8・9 を含むため **不正**。
- `0b100001011` は 2 進として有効。

### 例となるコード

```java
// 8 進：先頭 0。各桁は 0〜7 のみ（8, 9 はコンパイルエラー）
// int bad = 0827;

// これらは問題文の他の選択肢イメージとして有効な例
int dec = 267;           // 10 進（接頭辞なし）
int oct = 0413;          // 8 進
int hex = 0x10B;         // 16 進
int bin = 0b100001011;   // 2 進
```

### 陥りやすい罠

「接頭辞だけ覚えて、8 進の桁制約を忘れる」パターン。**E だけ**がエラー、と列挙問題で迷いやすい。

### 復習チェック

- [ ] `0` 始まり／`0b`／`0x` の見分けができる  
- [ ] 8 進で 8・9 が来るとエラー、と言える  

---

## 領域 2：数値リテラルのアンダースコア `_`（該当：**問3**）

### 核心となるルール

`_` は桁区切り。**先頭・末尾に置けない**。また **`.`、`L`、`F`、`0b`、`0x` などの「記号」の直前・直後にも置けない**。

### 公式解説ベースの要点

- 誤りの例：` _123 `（先頭）、`123_`（末尾）、`3_.1415F`（ドット前後）、`999_99_9999_L`（`L` の前後）、`0x_52`（`0x` と数字の間）などが **ルール 2** に抵触。

### 例となるコード

```java
// OK：先頭末尾・記号の前後に _ が来ていない
int a = 123_456_789;
int b = 5_______2;
byte g = 0b0_1;
int h = 0_52;

// NG 例（コンパイルエラー）
// int c = _123_456_789;   // 先頭
// int d = 123_456_789_;   // 末尾
// float e = 3_.1415F;     // '.' の前後
// long f = 999_99_9999_L; // 'L' の前後（仕様上 NG パターン）
// int i = 0x_52;           // 0x と数字の間
```

### 陥りやすい罠

「`_` は自由」ではない。**複数選択では正解集合の完全一致**が求められるため、1 つでも欠けると不正解。

### 復習チェック

- [ ] 「先頭末尾 NG」「記号前後 NG」を即答できる  
- [ ] 浮動小数・接尾辞 `L`/`F` でも同じことを確認する  

---

## 領域 3：識別子の命名規則（該当：**問4**）

### 核心となるルール

識別子に使える記号は原則 **`_` と `$` のみ**。**`.` は識別子の一部にならない**。`{}` を含む「疑似テンプレート」風の名前も **不可**。予約語不可。先頭は数字 NG。

### 公式解説ベースの要点

- `int ${d}` は `{` 等で **識別子として成立しない** → CE。  
- `int g.a` は `.` が **フィールド参照の演算子**であり、名前の途中に書けない → CE。

### 例となるコード

```java
// OK
int $a = 100;
int b_ = 200;
int _0 = 300;

// NG（識別子として解釈できない）
// int ${d} = 400;
// int g.a = 700;
```

### 陥りやすい罠

`b_` や `_0`（先頭 `_` は可）など **合法例を誤って「エラー」扱い**する、または **D を漏選**する。

### 復習チェック

- [ ] `$`, `_`, 英字で始められる／数字のみ先頭 NG  
- [ ] `.`, `{}` が混ざったら疑う  

---

## 領域 4：代入・型の整合（該当：**問5**）

### 核心となるルール

- **`byte` → `char` はキャスト無しでは不可**（文字コードの扱いはあるが、この代入は別問題として CE 側になりやすい）。  
- **`char` → `short` はキャストがあれば可**。  
- **浮動小数リテラルは既定で `double`**。`float` へは **`f` 付き**などで明示が必要。

### 公式解説ベースの要点

正解セット **A,B,D,F**。C は `char c2 = c1` が NG。E は `1.99` を `float` に。G は `char` と `String` の不一致。

### 例となるコード

```java
// 正しい例（設問の正解イメージ）
int[][] a = {{1, 1}, {2, 2}};
short b = (short) 'A';
boolean d = (10 == 10);
int f = 12_34;

// 誤りの例
// byte c1 = 10;
// char c2 = c1;          // byte → char は暗黙変換不可
// float e = 1.99;        // 1.99 は double リテラル
// String g = 'a';        // char を String に代入不可
```

### 陥りやすい罠

「キャストがあるから全部 OK」と捉え、`byte`/`char` 間を混同する。

### 復習チェック

- [ ] `double` リテラル → `float` の罠を言える  
- [ ] `{ }` で配列、`String` と `char` の区別を言える  

---

## 領域 5：`String` の不変性とメソッドの戻り値（該当：**問10**）

### 核心となるルール

`java.lang.String` は **immutable**。`replace` / `replaceAll` などは **新しい `String` を返す**だけで、**元のオブジェクトは変わらない**。返り値を変数へ代入しない限り、元の参照経由では内容はそのまま。

### 公式解説ベースの要点

設問では `hello` 内で `msg.replaceAll(...)` の戻り値を破棄しているため、`main` の `str` は **置換前のまま出力**される → 正解 A。

### 例となるコード

```java
public class Demo {
    public static void main(String[] args) {
        String str = "hoge, world.";
        hello(str);
        System.out.println(str); // hoge, world.（中身は変わらない）

        String s2 = "hoge, world.";
        s2 = s2.replaceAll("hoge", "hello"); // 戻り値を代入すれば別インスタンスを参照
        System.out.println(s2);               // hello, world.
    }

    static void hello(String msg) {
        msg.replaceAll("hoge", "hello"); // 戻り値を破棄している＝msg の参照先は不変
    }
}
```

### 陥りやすい罠

API は理解できていても **選択肢の記号を取り違える**（メタ領域 27）。

### 復習チェック

- [ ] 「戻り値を受け取らない `String` 操作は元が変わらない」と言える  
- [ ] `replace` と `replaceAll` の戻り値の扱いを追える  

---

## 領域 6：`String` / `StringBuilder` の `indexOf`（該当：**問12**, **問20**）

### 核心となるルール

- **部分文字列が存在しないときは `-1`**（開始位置が取れない）。  
- **`indexOf` が返すのは「開始インデックス」**（終了位置／長さ／「一番後ろ」などではない）。**インデックスは 0 始まり**。  
- `StringBuilder` でも同様に **開始位置**を返す（公式：**終了位置を返すものではない**）。

### 公式解説ベースの要点

- 「abcde」に対し「abcdef」を探す → **存在しない** → **-1**（問12 は E）。  
- 「abcde」で「bcd」の開始は **インデックス 1** を表示 → 選択肢 **A**（問20）。

### 例となるコード

```java
String str = "abcde";

// 問12：部分文字列 "abcdef" は存在しない → -1
System.out.println(str.indexOf("abcdef")); // -1

// 問20："bcd" の開始位置はインデックス 1（b の位置）。終了インデックスではない。
StringBuilder sb = new StringBuilder("abcde");
System.out.println(sb.indexOf("bcd")); // 1
```

### 陥りやすい罠

説明では `-1` や `1` と分かっていても **選択肢 D など別記号をマーク**。  
末尾インデックス（4 や 5）と **開始位置（1）** を混同。

### 復習チェック

- [ ] 欠パターンは常に `-1`  
- [ ] `bcd` が 0〜4 のどこで始まるか手で指で追える  

---

## 領域 7：`StringBuilder` の `capacity()`（該当：**問18**）

### 核心となるルール

- **引数なし**の `StringBuilder` は**概ねデフォルトで余分バッファ 16**（仕様・実装の説明は公式参照）。  
- **`new StringBuilder(String str)` は `str.length() + 16` の容量**から始まるイメージでよい（公式のコンストラクタ説明）。  
- `capacity()` は **文字列の長さそのものではなくバッファ容量**。

### 公式解説ベースの要点

`"abcde"`（長さ 5）で初期化 → **5 + 16 = 21** → 選択肢 **D**。

### 例となるコード

```java
// 設問：文字列で初期化した StringBuilder の capacity()
StringBuilder sb = new StringBuilder("abcde");
System.out.println(sb.length());    // 5（文字列の長さ）
System.out.println(sb.capacity()); // 21（概ね「長さ + 16」）

// 引数なしコンストラクタは別の初期容量（典型例として 16）
StringBuilder empty = new StringBuilder();
System.out.println(empty.length());    // 0
System.out.println(empty.capacity());  // 16（実装依存の点は仕様・JDK で確認）
```

### 陥りやすい罠

「5+16」と計算メモは正しいのに **選択肢 B（5）** など **表示値と記号の対応ミス**。

### 復習チェック

- [ ] `length()` と `capacity()` の違い  
- [ ] 計算後に「何が表示されるか」→ 選択肢の英字まで照合  

---

## 領域 8：テキストブロック（概念・誤説明の拾い）（該当：**問21**）

### 核心となるルール

テキストブロック導入により、たとえば次が整理できる（公式の列挙）：  
トリプルクォート、`+` 連結の削減、**`\"` が不要になりうる**、**改行を `\n` で無理に書かなくてよい** 等。

### 公式解説ベースの要点

選択肢 **D**（「改行には `\n` を使う」系の説明）は、テキストブロックの利点と食い違うため **誤った説明** として正解（＝この問は「誤りを選ぶ」タイプ）。

### 例となるコード

```java
// テキストブロックでは、ソース上の改行がそのまま文字列に含まれやすい（\n を必須ではない）
String block = """
    1行目
    2行目
    """;
System.out.println(block); // 先頭終端の扱いは仕様によるが「改行だけ \n と書け」だけでは説明できない

// 従来のリテラルでは改行や " のエスケープが多用されがち（対比用）
String classic = "1行目\n\"引用\"\n2行目\n";
```

### 陥りやすい罠

「正しそうな一般論」に見える **D を正と誤認**。

### 復習チェック

- [ ] テキストブロックの目的（可読性・エスケープ削減）を 1 分で説明できる  

---

## 領域 9：テキストブロック（文法：開始 `"""` の直後）（該当：**問22**）

### 核心となるルール

開始の **`"""` の直後は改行が必須**。直後に本文を続けると **コンパイルエラー**。終端側の `"""` 直前は一定の柔軟性あり（公式の良い例・悪い例を参照）。

### 公式解説ベースの要点

**D** は開始後に改行があり、合法。C/E 等は開始直後に本文で NG。

### 例となるコード

```java
// OK：開始 """ の直後に改行がある
String ok = """
    this is textblock sample.""";

// NG：開始 """ の直後にすぐ本文（コンパイルエラー）
// String ng = """this is textblock sample.""";
```

### 陥りやすい罠

ダブルクォートの文字列リテラル感覚で **`"""` 直後に詰める**。

### 復習チェック

- [ ] `"""` 直後に必ず改行、を暗唱できる  

---

## 領域 10：テキストブロック（インデントストリップ）（該当：**問23**）

### 核心となるルール

各行の左空白は、**ブロック内で最もインデントが浅い行（基準行）**に合わせて **共通部分が削除**される。設問では **終端の `"""` 行の位置**が基準になりやすい（公式の「設問のコード例」参照）。

### 公式解説ベースの要点

出力は **選択肢 A** のパターン（相対インデントが `_A` / `__B` / `___C` のような整い方—設問・公式図に従う）。

### 例となるコード

```java
// 共通の左インデント（最も浅い行）が取り除かれ、相対だけが残るイメージ
String str = """
        A
    B
C
""";
// 実際のスペース量はソースのインデントに依存。公式の問23と同様に「最も浅い行」を基準にストリップ
System.out.println(str);
```

### 陥りやすい罠

「ソース上の見た目の揃い」をそのまま出力に貼り付ける想像。

### 復習チェック

- [ ] 「最も浅い行」を基準に全行から同じ幅を削る、と言える  

---

## 領域 11：`intern()` とプール・`==`（該当：**問26**）

### 核心となるルール

**`intern()`** はプール（コンスタントプール）側の **`String` への参照**を返す用途。同じ文字列内容なら **同じ参照**に揃いうるので、**`==` が true** になりうる組み合わせが増える。

### 公式解説ベースの要点

設問の各 `if` は **すべて true** になり得て `count` は **3**（選択肢 **D**）。

### 例となるコード

```java
// 設問の骨格：intern() はプール上の同一表現に揃い、== が true になりやすい
String a = "abc";
String b = new String(a);
int count = 0;
if (a.intern() == "abc") {
    count++;
}
if (b.intern() == "abc") {
    count++;
}
if (a.intern() == b.intern()) {
    count++;
}
System.out.println(count); // 3
```

### 陥りやすい罠

`new String` とリテラル比較の感覚のまま **`intern()` 後を数え損ねる**。

### 復習チェック

- [ ] `a.intern() == "abc"` のニュアンスをケースごとに追える  

---

## 領域 12：配列の `println` と `Object#toString`（該当：**問27**）

### 核心となるルール

配列は **オブジェクト**。`System.out.println(array)` は **要素を列挙しない**。配列型は継承した **`toString` のデフォルト挙動**により、**型名＋`@`＋16 進ハッシュ**のような表示になりがち（公式コード参照）。**要素数 0 でも**文法上は有効。**この操作だけで実行時例外、とはならない**（設問の誤選択肢対策）。

### 公式解説ベースの要点

選択肢 **E**（ハッシュコード表示）。

### 例となるコード

```java
int[] array = new int[0]; // 要素 0 個でもインスタンスは作れる
System.out.println(array);                // [I@xxxx（環境により異なる／要素は並ばない）

// 中身を人が読める形で見たいとき
System.out.println(java.util.Arrays.toString(array)); // []
```

### 陥りやすい罠

「空だからエラー」「`{ }` と表示」の想像。`Arrays.toString` との混同。

### 復習チェック

- [ ] 内容表示には `Arrays.toString` を使う、と説明できる  

---

## 領域 13：配列型変数の宣言と要素数（該当：**問29**）

### 核心となるルール

**変数宣言時点では要素数は書けない**。`int[3] a` のような **`[定数長]` は宣言では NG**。長さは **`new int[3]`** 側で指定する。

### 公式解説ベースの要点

選択肢 **E**（**A〜D はすべて不正な宣言**）。

### 例となるコード

```java
// NG：宣言で要素数を書くことはできない
// int[3] a;
// int b[2];

// OK：次元は [] の並びだけ。長さは new の側
int[] c;
int d[];
int[][] e;
int[] f[] = new int[3][];
```

### 陥りやすい罠

C 言語などの **「スタック配列宣言」記法を Java に転写**してしまう。

### 復習チェック

- [ ] 「宣言」と「インスタンス生成」を言葉で分けられる  

---

## 領域 14：多次元配列の `new` と次元・型（該当：**問30**）

### 核心となるルール

- 要素数は **整数コンテキスト**（`double` は NG）。  
- **`new int[2][]` はあり**、`new int[][3]` のように **次元の省略順が崩れるものは NG**（公式：**1 次元目の要素数は省略できない**）。  
- **左辺の次元と右辺の次元は一致**（`int[]` 変数に `new int[2][3]` は NG）。

### 公式解説ベースの要点

正解は **A, B, F**。

### 例となるコード

```java
// NG（CE）：左辺 1 次元に 2 次元を代入
// int[] a = new int[2][3];

// NG（CE）：要素数に double
// int[] b = new int[2.3];

// OK
int[] c = new int[2 * 3];
int x = 2, y = 3;
int[] d = new int[x * y];
int[][] e = new int[2][];

// NG（CE）：1 次元目を空にして 2 次元目だけ指定
// int[][] f = new int[][3];
```

### 陥りやすい罠

**E（`new int[2][]`）を誤ってエラー扱い**。複数選択の取りこぼし。

### 復習チェック

- [ ] `int[][] x = new int[3][]` の絵を描ける  

---

## 領域 15：オブジェクト配列のデフォルトと NPE（該当：**問31**）

### 核心となるルール

`new Item[3]` は **長さ 3 の配列**まで作るだけ。**`Item` インスタンスは自動では作られない**。要素は **`null`** 初期化。そのまま **`items[i].price`** は **NPE**。

### 公式解説ベースの要点

選択肢 **E**。

### 例となるコード

```java
class Item {
    String name;
    int price = 100;
}

public class Main {
    public static void main(String[] args) {
        Item[] items = new Item[3]; // 要素は null のまま（Item は new していない）
        // int total = 0;
        // for (int i = 0; i < items.length; i++) {
        //     total += items[i].price; // NullPointerException
        // }

        // 正しくは要素にインスタンスを代入してからアクセス
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item();
        }
    }
}
```

### 陥りやすい罠

「配列を作った＝要素もそろっている」錯覚。

### 復習チェック

- [ ] 参照型配列のデフォルトは `null`  

---

## 領域 16：配列の初期化子と `new int[n]{ ... }` NG（該当：**問33**）

### 核心となるルール

**`new` と初期化子を併用する場合、`[]` の中に要素数を書いてはいけない**。要素数は `{ ... }` から算出。  
`**{ ... }**` だけの初期化は **宣言と同時のみ**、`e = {2,3}` のように後からだけは NG。  
`new int[][]{}` は **次元を明示できる**ので合法パターンとして覚える（公式例）。

### 公式解説ベースの要点

正解は **B, C, D**。

### 例となるコード

```java
// NG：要素数と初期化子の併記
// int[] a = new int[2]{ 2, 3 };

// OK：空配列
int[][] b = {};

// OK：次元を明示した空配列
int[][] c = new int[][]{};

// OK：宣言と分離するときは要素数を [] で明示（初期化子だけは不可）
int[] d;
d = new int[]{ 2, 3 };

// NG：初期化子だけを後から代入
// int[] e;
// e = { 2, 3 };
```

### 陥りやすい罠

**`new int[2]{2,3}` のような複合形**を見逃す。**E** が NG になる理由（初期化子は宣言と同時）を説明できない。

### 復習チェック

- [ ] 「要素数明示 + `{}`」の併記が NG と言える  

---

## 領域 17：ジグザグ配列／`null` 行と NPE（該当：**問34**）

### 核心となるルール

1 次元目の要素が **`null` のまま**のスロットがあり、拡張 `for` で **`tmp.length`** 等に触れると **NPE**。非対称多次元でも **null 行は普通に起こりうる**。

### 公式解説ベースの要点

選択肢 **E**（NPE）。長さの単純加算で済まない。

### 例となるコード

```java
// 1 行目配列の一部が null のまま length に触れると NPE
String[][] array = {
    { "A", "B" },
    null,
    { "C", "D", "E" },
};
// int total = 0;
// for (String[] tmp : array) {
//     total += tmp.length; // tmp が null のとき NullPointerException
// }
```

### 陥りやすい罠

「二重ループ＝全部非 null」前提。

### 復習チェック

- [ ] `String[][]` に `null` が混ざる典型を見たら NPE を疑う  

---

## 領域 18：配列の副次型と `Object[]` 代入（該当：**問35**）

### 核心となるルール

**実装クラス（`C`, `D`）はスーパータイプ（`A`）として扱える**。`A[]` に `C`/`D` を入れる初期化子は合法。**配列は `Object[]` に暗黙変換**しうる（公式例）。**未使用でキャストもなければ** CCE 等は起きない。

### 公式解説ベースの要点

選択肢 **D**（コンパイルも実行も可）。

### 例となるコード

```java
interface A {}

abstract class B implements A {}

class C extends B {}

class D extends C {}

public class Main {
    public static void main(String[] args) {
        A[] array = { new C(), null, new D() };
        Object[] objArray = array; // スーパータイプの配列への代入は暗黙変換で可
        // ここで読まなければ実行時例外は出ない典型的例
    }
}
```

### 陥りやすい罠

「配列の要素に `null` が混ざる」→ 即 NPE、と一般化してしまう（**使っていなければ発生しない**）。

### 復習チェック

- [ ] 継承関係を図にしてから配列代入を判断する習慣  

---

## 領域 19：`ArrayList` の性質（該当：**問37**）

### 核心となるルール

`ArrayList` は **動的配列**。**null 可**、**重複可**、**スレッドセーフではない**。**任意位置への add** も可能（別設問とリンク）。

### 公式解説ベースの要点

正解は **B, D, E**。**A（null 不可）・C（重複不可）は誤り**。

### 例となるコード

```java
import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);   // OK：null を格納できる
        list.add("A");
        list.add("A");    // OK：重複可
        list.add(1, "B"); // OK：位置指定の add も可能（別設問で境界に注意）
        // 単一スレッド前提の高速な動的配列／スレッドセーフではない、と整理
    }
}
```

### 陥りやすい罠

他コレクション（`Set` 等）の性質を **そのまま `ArrayList` に投影**。

### 復習チェック

- [ ] 「Vector はスレッドセーフの例」まで含めて一度読む（公式参考）  

---

## 領域 20：生の `ArrayList` とダイヤモンド推論（該当：**問38**）

### 核心となるルール

**左辺がジェネリクスなし `ArrayList list` のとき、要素型は `Object` 扱い**に近い理解でよい（公式のコンパイラ説明）。**`String` / ボクシングされた数値 / 文字**は **`add` 可能**。拡張 `for` で **`Object obj`** に取り出す分には **CE も実行時例外も出ない**典型。

### 公式解説ベースの要点

表示は **`A10B`**（`char` は `Character` として格納・`print` で文字に見える、等の流れ）→ **F**。

### 例となるコード

```java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList list = new ArrayList<>(); // 左辺が生型 → 実質 Object 相当の要素
        list.add("A");
        list.add(10);    // Integer にボクシング
        list.add('B');   // Character にボクシング
        for (Object obj : list) {
            System.out.print(obj); // A10B
        }
    }
}
```

### 陥りやすい罠

「混在＝コンパイルエラー」と決めつける。

### 復習チェック

- [ ] 生コレクションの危険性（実行時キャスト）は別設問の例も参照  

---

## 領域 21：`add(index, elem)` の有効インデックス（該当：**問39**）

### 核心となるルール

`add(int index, E e)` は **挿入位置**として **0〜size（両端の「間」まで）** を考える問題。**`size` より大きい隙間には挿入できず**実行時 **`IndexOutOfBoundsException`**（公式：`Index: 2, Size: 1`）。

### 公式解説ベースの要点

**E** が正解。

### 例となるコード

```java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A"); // size == 1。有効な挿入位置は 0 または 1 の「間」のみのイメージ
        // list.add(2, "B"); // IndexOutOfBoundsException（Index: 2, Size: 1）
        list.add(1, "B"); // OK：末尾に相当する隙間への挿入
    }
}
```

### 陥りやすい罠

「インデックス 2 は常に三番目」の **抽象的イメージ**だけで **`size`** を見ずに並べ替え結果を空想する。

### 復習チェック

- [ ] 「要素間に棒を引いて slot を数える」（公式イラストのやり方）  

---

## 領域 22：`remove(Object)` と `equals` と「最初の 1 件」（該当：**問41**）

### 核心となるルール

**オブジェクト渡しの `remove` は equals が true の要素を削除**。**複数一致しても先頭の 1 つだけ**。カスタム `equals` では **`price` が違っても `name` だけで true** など、**どのフィールドが効くか**をコードで読む。

### 公式解説ベースの要点

表示は **`B C A`**（先頭の `A` だけ除去）→ **B**。

### 例となるコード

```java
import java.util.ArrayList;

class Item {
    private final String name;
    private final int price;

    Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item other) {
            return name.equals(other.name); // price は比較しない
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item("A", 100));
        list.add(new Item("B", 200));
        list.add(new Item("C", 300));
        list.add(new Item("A", 100));
        list.remove(new Item("A", 500)); // equals は name のみ一致なので「A」だけ除去。先頭の 1 件のみ
        list.forEach(i -> System.out.println(i.getName())); // B, C, A（残りの順）
    }
}
```

### 陥りやすい罠

「同値が 2 個あるから両方消える」。

### 復習チェック

- [ ] `remove` のオーバーロード（index / Object）を区別  

---

## 領域 23：拡張 `for`＋`remove` と繰り上がり・カーソル（該当：**問42**）

### 核心となるルール

削除で後ろが **繰り上がる**。拡張 `for` の内部 **`next` が進むタイミング**と組み合わせると、**一部の要素を「飛ばす」ことがある**。設問では **`C` が処理されず終了しうる**（公式「カーソル」の図）。**単スレッドでも起こりうる**論理問題。

### 公式解説ベースの要点

表示は **`A` のみ** → **C**。

### 例となるコード

```java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        for (String str : list) {
            if ("B".equals(str)) {
                list.remove(str); // 削除で繰り上がり。次の next が「見落とし」になり得る
            } else {
                System.out.println(str);
            }
        }
        // 典型：A だけ表示され、C には到達しないパターン（公式解説のカーソル図と対応）
    }
}
```

### 陥りやすい罠

for-each を「書きぶんの for」と同一視して **頭の中でリストを固定**してしまう。

### 復習チェック

- [ ] ミュータブル操作は **インデックス走査/`Iterator`** で考え直せる  

---

## 領域 24：`ConcurrentModificationException`（該当：**問43**）

### 核心となるルール

`ArrayList` は **検知構造**。拡張 `for`（内部イテレータ）使用中に **`remove`** などでリストを構造変更し、さらに **読み続ける** と **`ConcurrentModificationException`**（公式スタックトレース参照）。  
**問42 と対比**：42 は「削除後に **まだ読みに行かず** に終わる」、43 は「**削除後もまだ next する**」。

### 公式解説ベースの要点

**E**。

### 例となるコード

```java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        for (String str : list) {
            if ("C".equals(str)) {
                list.remove(str); // 構造変更
            }
        }
        // この後も同じイテレーションで next が走ると ConcurrentModificationException になり得る
        for (String str : list) {
            System.out.println(str);
        }
    }
}
```

### 陥りやすい罠

「マルチスレッドだけ」と思う（公式：**シングルでも起きる**）。

### 復習チェック

- [ ] 安全パターン（コピーを回す、`Iterator.remove`）を 1 つ覚える  

---

## 領域 25：固定長リスト `List.of` / `Arrays.asList` と `new ArrayList`（該当：**問44**）

### 核心となるルール

- **`List.of(...)`** と **`Arrays.asList(...)`** で得られるリストは **サイズ変更不可（固定長）**の代表。`add/remove` で **unsupported** が起きうる（公式例）。  
- **`new ArrayList<>(3)` は初期容量のみ**で、リストは **`ArrayList`（可変）**。**不変リストではない**。

### 公式解説ベースの要点

正解は **A, C**。

### 例となるコード

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 固定長・サイズ変更不可に近い代表（add で UnsupportedOperationException になり得る）
        List<Integer> fixed1 = List.of(1, 2, 3);
        // fixed1.add(9);

        List<Integer> fixed2 = Arrays.asList(1, 2, 3);
        // fixed2.add(9);

        // 初期容量 3 だが、中身は普通の可変 ArrayList
        ArrayList<Integer> growable = new ArrayList<>(3);
        growable.add(1);
        growable.add(2);
        growable.add(3);
        growable.add(4); // OK
    }
}
```

### 陥りやすい罠

「サイズ関連のパラメータ＝固定長」と誤読。

### 復習チェック

- [ ] **不変**：`List.of` / 一部のコピー系／**固定長：`Arrays.asList` の結果**、`Collections.unmodifiableList`（別教材）との整理  

---

## 領域 26（メタ）：複数選択の完全一致（該当：**問3,4,5,30,33,37,44**）

### 核心となるルール

設問が **「n 個選択」**のとき、試験採点的には **選択肢集合が完全一致**しないと不正解。 **多すぎ／少なすぎ両方 NG**。

### 公式解説ベースの要点

各問の見出しの **「正解:」行** と自分の記号セットを **最後に並べて突合**。

### 例となるコード

```java
import java.util.Set;

// メモ：問3 の例。正解 { C,D,E,F,I } に対し自分 { B,C,D,F,I } は B が余計・E が欠けで不正解
public class AnswerSetCompare {
    public static void main(String[] args) {
        Set<String> official = Set.of("C", "D", "E", "F", "I");
        Set<String> mine = Set.of("B", "C", "D", "F", "I");
        System.out.println(official.equals(mine)); // false
    }
}
```

### 復習チェック

- [ ] チェックリスト：各肢について **○/×/迷い** を付け、そのうえで **カウントが n と一致するか**  

---

## 領域 27（メタ）：単一選択で「理解」と「マーク」の一致（該当：**問10,12,18,20**）

### 核心となるルール

メモ上の答え（数値・文字列）と **解答用紙の記号**は別レイヤー。**計算や API 理解の直後に**「どの選択肢がその値か」を **必ず照合**。

### 例となるコード

```java
// 問18イメージ：計算結果は 21 だが、選択肢ラベルを取り違えないこと
int len = "abcde".length();
int capacity = len + 16; // 21
// メモ: System.out.println(capacity); → 21
// 解答欄: 「21 が出る選択肢は D」と用紙の定義と必ず対応させる

// 問12イメージ
int idx = "abcde".indexOf("abcdef"); // -1
// メモ: -1 → 選択肢 E（問題冊子の並びでは E が -1 の場合）

// 自動化したいときは Map で対応を作る発想（試験中の手順の比喩）
// int value = -1;
// char choice = (value == -1) ? 'E' : '?';
```

### 復習チェック

- [ ] 試験本番：`計算結果 → 対応する選択肢英字`**を括弧で書いてから塗る**  

---

## 出典

- `java_blackbook/local_problems/scoring_result/Chapter2_ScoringResult_1once.md`（弱点領域一覧・採点）  
- `java_blackbook/local_problems/answers/Chapter2_answers.md`（問 2〜44 の公式解答・解説）

---

*本章は学習用の要約です。 JDK 実装細部や用語は公式ドキュメント・言語仕様と併読してください。*
