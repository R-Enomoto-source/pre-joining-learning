# Chapter2 弱点復習（1回目採点ベース）

本章は、採点結果の **「不正解から整理した弱点学習領域一覧」**（`local_problems/scoring_result/Chapter2_ScoringResult_1once.md`）と、公式の **解答・解説**（`local_problems/answers/Chapter2_answers.md`）に基づいて整理した弱点解説です。各項目は **不正解となった設問番号** を必ず紐づけています。

以下の **例となるコード** は理解用の断片です。**本番問題の字面・選択肢と重ならないよう、別の単語や数値に置き換えています**。複数の `public class` が出てくる場合はファイルを分けるか、`jshell` で断片だけ試してください。

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
// int invalidOct = 0951; // 9 を含むので NG の例（値は架空）

// 接頭辞の見分け用（数値そのものはオリジナル）
int dec = 440;            // 10 進
int oct = 077;            // 8 進（0〜7 の桁だけ）
int hex = 0x2F4;          // 16 進
int bin = 0b101101;       // 2 進
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
int yearChunk = 20_26_05_07;
long big = 1_000_000L;
float piish = 3.14_15f;
int bin = 0b1010_1100;

// NG 例（コンパイルエラー）
// int badHead = _404;
// int badTail = 404_;
// float badDot = 2_.71f;
// long badL = 100_L;
// int badHex = 0x_FF; // x と桁の間（環境により NG パターンとして覚える）
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
// OK（記号は $ と _、英字開始などルール内）
int $session = 1;
int user_name = 2;
int _temp = 3;

// NG（識別子ルール違反）
// int 2nd = 4;        // 数字始まり
// int a.b = 5;        // '.' は識別子に含めない
// int n{1} = 6;       // '{}' は使えない
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
// 合法例（パターン理解用。数値・型は架空でよい）
int[][] grid = {{7, 7}, {8, 8}};
short code = (short) 'Z';
boolean flag = (-1 < 0);
int pretty = 99_88_77;

// 違法例
// byte lo = 20;
// char ch = lo;           // byte → char は暗黙不可
// float r = 3.14159;      // double リテラルを float に
// String label = 'Q';     // char を String に
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
        String line = "DRAFT: ready.";
        sanitize(line); // 呼び出し側の line は変わらない
        System.out.println(line);

        String copy = "DRAFT: ready.";
        copy = copy.replace("DRAFT", "FINAL"); // 戻り値を受け取った参照だけ更新
        System.out.println(copy);
    }

    static void sanitize(String text) {
        text.replace("DRAFT", "FINAL"); // 破棄＝内部の実体は immutable のまま
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
// 長さ 5 の英小文字だけの並び（中身は任意でよい）
String word = "klmno";

// 存在しないより長いパターン → -1
System.out.println(word.indexOf("klmnop")); // -1

// 中盤 3 文字の「開始インデックス」を返す（ここでは 1）
StringBuilder buf = new StringBuilder(word);
System.out.println(buf.indexOf("lmn")); // 1（終端 index ではない）
```

### 陥りやすい罠

説明では `-1` や `1` と分かっていても **選択肢 D など別記号をマーク**。  
末尾インデックス（4 や 5）と **開始位置（1）** を混同。

### 復習チェック

- [ ] 欠パターンは常に `-1`  
- [ ] 部分文字列の「先頭インデックス」を 0 から数えて指で追える  

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
// 長さ len の文字列で初期化したとき capacity ≒ len + 16（説明の定番式）
String seed = "chunk"; // len == 5 の例。語は任意でよい
StringBuilder sb = new StringBuilder(seed);
System.out.println(sb.length());     // 5
System.out.println(sb.capacity());     // 21 前後のイメージ（JDK 実装参照）

StringBuilder blank = new StringBuilder();
System.out.println(blank.length());    // 0
System.out.println(blank.capacity());   // 典型値 16（実装依存）
```

### 陥りやすい罠

計算結果（例：**21**）までは合っていても **解答記号だけ別の選択肢になる**対応ミス。

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
    line-one
    line-two""";

// NG：開始直後に本文を続ける（コンパイルエラー）
// String ng = """no newline here
// continues...""";
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
// 左端そろえ：最もインデントが浅い行を基準に、各行の先頭空白が削られる
String str = """
        East
    North
Pole
""";
System.out.println(str); // 実行して相対位置だけを観察するのが定着に効く
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
// intern() は同一内容ならプール側の参照へ寄せ、== が true になりやすい
String left = "ping";
String right = new String(left);
int hits = 0;
if (left.intern() == "ping") {
    hits++;
}
if (right.intern() == "ping") {
    hits++;
}
if (left.intern() == right.intern()) {
    hits++;
}
System.out.println(hits); // 3
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
// NG：次元の不一致
// int[] row = new int[3][2];

// NG：要素数に浮動小数
// int[] bad = new int[4.0];

// OK
int[] ok1 = new int[5 + 5];
int m = 3, n = 4;
int[] ok2 = new int[m * n];
int[][] ok3 = new int[3][];

// NG：2 次元目だけ先に長さ指定
// int[][] bad2 = new int[][4];
```

### 陥りやすい罠

**`new int[n][]` が合法**なのに、「欠けているのでエラー」と誤って除外する。複数選択の取りこぼし。

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
class Notebook {
    String title;
    int pages = 50;
}

class Shelf {
    public static void main(String[] args) {
        Notebook[] slots = new Notebook[2]; // スロットだけ。中身はまだ null
        // System.out.println(slots[0].pages); // NullPointerException

        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Notebook(); // 参照型配列は要素を自分で new する
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
// NG：サイズ指定と {} を同時に
// int[] bad = new int[3]{ 9, 8, 7 };

// OK：二次元の {}（空でも可）
int[][] grid = {};

// OK：new で次元だけ明示した空二次元
int[][] plane = new int[][]{};

// OK：宣言と代入を分けるときはこちらの形
int[] xs;
xs = new int[]{ 40, 41 };

// NG：単独の {} は宣言と一緒にしか書けない
// int[] ys;
// ys = { -1, -2 };
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
String[][] rows = {
    { "aa", "bb" },
    null,
    { "cc", "dd", "ee" },
};
// int sum = 0;
// for (String[] row : rows) {
//     sum += row.length; // row が null の行で NPE
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
interface Remote {}

abstract class Device implements Remote {}

class Phone extends Device {}

class Tablet extends Phone {}

public class Desk {
    public static void main(String[] args) {
        Remote[] dock = { new Phone(), null, new Tablet() };
        Object[] any = dock; // 配列の副次型 → Object[]
        // 未使用・未キャストならここでは静かに終わるだけ、という例もある
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
        ArrayList<String> bag = new ArrayList<>();
        bag.add(null);
        bag.add("alpha");
        bag.add("alpha");     // 重複しても可
        bag.add(1, "beta");   // 挿入位置の add も可能（境界は別の復習で）
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

public class MixBox {
    public static void main(String[] args) {
        ArrayList bag = new ArrayList<>(); // 生型：要素は Object 想定で受け止められる
        bag.add("X");
        bag.add(99);
        bag.add('Z');
        for (Object one : bag) {
            System.out.print(one); // X99Z（数と文字はボクシングされて格納）
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

public class SlotDemo {
    public static void main(String[] args) {
        ArrayList<String> queue = new ArrayList<>();
        queue.add("only"); // size == 1 のとき index 2 は「まだ存在しない隙間」
        // queue.add(2, "tooFar"); // IndexOutOfBoundsException
        queue.add(1, "tail"); // 0 と 1 の間への挿入なら OK
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

/** 金額ではなく「都市」だけで等価判定する例（試験問題の型の比喩） */
class Ticket {
    private final String city;
    private final int yen;

    Ticket(String city, int yen) {
        this.city = city;
        this.yen = yen;
    }

    String city() {
        return city;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ticket t) {
            return city.equals(t.city); // 金額は見ない
        }
        return false;
    }
}

class Ledger {
    public static void main(String[] args) {
        ArrayList<Ticket> pile = new ArrayList<>();
        pile.add(new Ticket("kyoto", 1200));
        pile.add(new Ticket("osaka", 900));
        pile.add(new Ticket("nara", 800));
        pile.add(new Ticket("kyoto", 1500)); // city だけ見る equals にはもう一通マッチする
        pile.remove(new Ticket("kyoto", 9999)); // 先頭の kyoto だけ消える（2 枚目キョウトは残る）
        pile.forEach(t -> System.out.println(t.city()));
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

public class SkipShow {
    public static void main(String[] args) {
        ArrayList<String> deck = new ArrayList<>();
        deck.add("uno");
        deck.add("dos");
        deck.add("tres");
        for (String face : deck) {
            if ("dos".equals(face)) {
                deck.remove(face); // 繰り上がり＋カーソルで次要素を読み損ねる典型
            } else {
                System.out.println(face);
            }
        }
        // よくある結果：uno だけ印字され tres は飛ぶ、など環境により挙動を打印で確認すること
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

public class CmeLikely {
    public static void main(String[] args) {
        ArrayList<String> lanes = new ArrayList<>();
        lanes.add("n");
        lanes.add("e");
        lanes.add("s");
        lanes.add("w");
        lanes.add("u");
        try {
            for (String compass : lanes) {
                if ("s".equals(compass)) {
                    lanes.remove(compass); // foreach 内の構造変更
                }
            }
            for (String compass : lanes) {
                System.out.println(compass);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getClass().getSimpleName()); // ConcurrentModificationException など
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

public class ListKinds {
    public static void main(String[] args) {
        List<Integer> sealed = List.of(11, 22, 33);
        // sealed.add(99); // 実行時 unsupported

        List<Integer> boxed = Arrays.asList(44, 55, 66);
        // boxed.add(77); // 同様に追加できないリストの代表例

        ArrayList<Integer> heap = new ArrayList<>(3); // ここでの 3 はあくまで初期バッファ
        heap.add(100);
        heap.add(200);
        heap.add(300);
        heap.add(400); // 増やしても大丈夫＝こちらは可変
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

/** 「複数正解」を集合として比べるだけの体感用（記号や個数は自由に変えよ） */
public class AnswerSetCompare {
    public static void main(String[] args) {
        Set<String> textbook = Set.of("M", "N", "O", "P", "Q");
        Set<String> student = Set.of("L", "N", "O", "P", "Q"); // L が余計 & M が欠ける例
        System.out.println(textbook.equals(student)); // false
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
// ① メモには「算出値」、マークには「選択肢記号」を二段で書く
String token = "vwxyz"; // len=5 の任意文字列でよい
int len = token.length(); // → 計算草稿
int guessedCapacity = len + 16;
System.out.println("draft=" + guessedCapacity); // ← これが「何番の肢か」を用紙と突き合わせる

// ② 「見つからない」型の問題は結果が負になりがちだが、記号との対応は毎回その場で確認
String hay = "qrstu";
System.out.println(hay.indexOf("qrstuv")); // -1 だとしても選択肢一覧のどこかは冊子依存

// ③ 自動採点はできなくてよいので、自分用ルールでも「草稿→選択肢」を必ず両方書く癖だけ作る
```

### 復習チェック

- [ ] 試験本番：`計算結果 → 対応する選択肢英字`**を括弧で書いてから塗る**  

---

## 出典

- `java_blackbook/local_problems/scoring_result/Chapter2_ScoringResult_1once.md`（弱点領域一覧・採点）  
- `java_blackbook/local_problems/answers/Chapter2_answers.md`（問 2〜44 の公式解答・解説）

---

*本章は学習用の要約です。 JDK 実装細部や用語は公式ドキュメント・言語仕様と併読してください。*
