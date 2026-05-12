# 第3章（Chapter3_sec）— 間違えないためのコツまとめ

`weaknessesReview/weaknessesReview.md` に整理した弱点（`exercise/Chapter3/Chapter3_sec.md` の不正解 23 問に対応）を、復習用に**題目／コツ形式**でまとめたものです。用語と設問対応の正確さのために、`local_problems/answers/Chapter3_answers.md`（第3章の公式解答・解説）の設問番号を併記しています。各コツの下に**最小のコード例**を付けています。断片のため **`package` や必要な `import` は試験・ローカル実行に合わせて補ってください**。

---

**項目**

数値・型 — リテラル既定とコンパイルエラーになる代入（`Chapter3_answers.md` 設問2）

**題目**

「エラーになる行」を選ぶ問題で、**本当にエラーになる組み合わせだけ**を選び切るため

**コツ**

整数リテラルは原則 **`int`**、浮動小数リテラルは原則 **`double`**。`byte` / `short` へは**値が範囲内**なら `int` リテラルでも暗黙変換が認められるが、**範囲外**は不可。`int` と **`long`（`L`）** の演算は **`long`** になり、結果を **`int` 変数**へ代入しようとするとエラー。**`double` → `float`** は暗黙変換できない。設問で「エラーにならない行」として **`int` 同士の演算が `short` に収まる`**パターンが出たら、それは**エラーではない**（弱点一覧でも触れている取りこぼしポイント）。

範囲

| 型 | サイズ | 最小値 | 最大値 | 備考 |
|----|--------|--------|--------|------|
| `byte` | 8 bit | -128 | 127 | バイナリデータ、小規模な数値 |
| `short` | 16 bit | -32,768 | 32,767 | メモリ制限がある場合の小規模数値 |

**例**

```java
// byte b = 0b1000_0000; // int リテラル 128 → byte へは暗黙変換 NG
byte b = (byte) 0b1000_0000; // 明示キャストなら可

// int x = 6L * 1; // long → int NG
long y = 6L * 1;

// float f = 1.0; // double リテラル → float NG
float g = 1.0f;

short s = 10 * 10 / 10; // int 演算だが値が short に収まれば代入可（「エラーでない」パターンの代表）
```

---

**項目**

演算子 — 優先順位と左からの評価（`Chapter3_answers.md` 設問6）

**題目**

`%` `*` `/` と `+` が混ざった式の順序を取り違えないため

**コツ**

カッコ・単項・インクリメントの次に、**乗除剰余**が **加減**より先。同じ優先度は**左から**評価。迷ったら一度 **`()` で意図どおりに分解**してから数値を代入する。

**例**

```java
int a = 100, b = 20, c = 30;
// (a % b * c) + (a / b) → (0 * 30) + 5 → 5
int r = (a % b * c) + (a / b);
System.out.println(r); // 5
```

---

**項目**

演算子 — カッコの後と **int 除算の切り捨て**（`Chapter3_answers.md` 設問7）

**題目**

`12 / 10` を「1.2」と読んでしまわないため

**コツ**

オペランドが **`int` のまま**なら除算結果も **`int`（小数切り捨て）**。**`()`** があればその中から先に評価する。

**例**

```java
int result = 30 - 12 / (2 * 5) + 1;
// (2*5)=10 → 12/10=1（int）→ 30-1+1=30
System.out.println(result); // 30
```

---

**項目**

演算子 — ビット演算子 `&` `|` `^`（`Chapter3_answers.md` 設問12）

**題目**

2 進ビット列を与えられたとき、結果がすべて 0 になる演算を選び間違えないため

**コツ**

桁ごとに表を書く。**AND** は両方 1 のところだけ 1。**OR** はどちらか 1 なら 1。**XOR** は片方だけ 1 なら 1。`1011` と `0100` で全体が 0 になるのは **AND のみ**（解答の典型）。

**例**

```java
//   1011
// & 0100
// = 0000

int x = 0b1011 & 0b0100;
System.out.println(x); // 0
```

---

**項目**

参照と比較 — **同一性** `==`（`Chapter3_answers.md` 設問8）

**題目**

`new` や代入のあとに **`==` が true か false か**を取り違えないため

**コツ**

参照型の **`==` は「同じインスタンスを指すか」**だけ。`s2 = s1` で共有しても、**片方だけ `new` し直す**と別インスタンスになり **`false`**。

**例**

```java
Object s1 = new Object();
Object s2 = s1;
System.out.println(s1 == s2); // true
s1 = new Object();
System.out.println(s1 == s2); // false
```

---

**項目**

`Object#equals` — **オーバーライド**と比較に使うフィールド（`Chapter3_answers.md` 設問9）

**題目**

「どのフィールドが一致すれば `equals` が true か」を読み落とさないため

**コツ**

`equals` はクラス設計者が定義した**同値条件**に従う。設問のように **num だけ見る**なら `name` が違っても **`true`** になり得る。`==` とは役割が違う。

**例**

```java
// 概念例: num のみで equals を定義したクラス
// a.equals(b) が true でも a == b は false（別インスタンス）になり得る
```

---

**項目**

`Object#equals` — **オーバーロード**とシグネチャ（`Chapter3_answers.md` 設問10）

**題目**

`equals(Sample obj)` を書いたつもりで **`equals(Object)` が呼ばれる**パターンを踏まないため

**コツ**

**オーバーライド**は `boolean equals(Object obj)` と**引数型まで一致**させる。引数だけ変えた同名メソッドは**オーバーロード**。`a.equals(b)` で実際に選ばれるのは **`Object` 版**になり、デフォルトは **`this == obj`** 相当 → 別インスタンスなら **`false`**。

**例**

```java
// class Bad {
//   public boolean equals(Bad other) { ... } // オーバーロード（Object 版はそのまま）
// }
// Bad a = new Bad(), b = new Bad();
// a.equals(b); // 実際は Object#equals が動き false になりがち
```

---

**項目**

`Object#equals` — **`null` 引数**（`Chapter3_answers.md` 設問11）

**題目**

`x.equals(null)` の戻り値を取り違えないため

**コツ**

**null でない参照 `x` について `x.equals(null)` は `false`**（契約）。`Object` の実装は `return (this == obj);` のため、`obj` が `null` なら `false`。

**例**

```java
String s = "a";
System.out.println(s.equals(null)); // false
// 実行時 NPE を避けるなら Objects.equals(a, b) や "literal".equals(var) を使う
```

---

**項目**

制御構文 — `if` / `else` の **中カッコ省略**（`Chapter3_answers.md` 設問14・15）

**題目**

「`if` にぶら下がるのは次の 1 文だけ」を見失わないため

**コツ**

`{}` を省略したら、**直後の 1 文だけ**が `if` または `else` の本体。**インデントは無意味**。試験では脳内で **`{}` を補完**してから出力順を追う。

**例**

```java
if (false)
    System.out.println("A");
System.out.println("B"); // 常に実行（if の外）
// 出力: B
```

---

**項目**

制御構文 — **`if` / `else` のあとに続く別の `if`**（`Chapter3_answers.md` 設問16）

**題目**

`else if` ではなく**独立した 2 つ目の `if`**だと表示がどうなるか取り違えないため

**コツ**

1 つ目の `if` / `else` が終わったあと、**次の `if` は別の文**。それぞれの条件を**別々に**評価する。

**例**

```java
int num = 10;
if (num < 10)
    System.out.println("A");
else
    System.out.println("B");
if (num == 10)
    System.out.println("C");
// 出力: B 改行 C
```

---

**項目**

`switch` — **条件式に使える型**（`Chapter3_answers.md` 設問17）

**題目**

「整数なら全部 `switch` できる」と **`long` や `boolean`** を含めてしまわないため

**コツ**

覚え方の一例: **`int` 以下の整数＋ラッパー**、**`String`**、**`enum`**。解答が明示するように **`long` / `boolean` / 浮動小数**は条件式に**使えない**。

**例**

```java
// long x = 1L;
// switch (x) { } // NG

int y = 1;
switch (y) { case 1: break; } // OK
```

---

**項目**

`switch` — **`case` ラベルは定数**（`Chapter3_answers.md` 設問18）

**題目**

変数や型不一致の `case` をコンパイルエラーと見抜けないため

**コツ**

`case` には**コンパイル時定数**（リテラル、`final` 定数、定数式など）。**普通の変数**は `case` に**書けない**。条件式の型と **`case` の型**も揃える。

**例**

```java
final int A = 1;
int x = 1;
switch (x) {
    case A: break;      // OK（final 定数）
    // case x: break; // NG（変数）
}
```

---

**項目**

`switch` **文** — **フォールスルー**（`Chapter3_answers.md` 設問19）

**題目**

`break` がないときに **下の `case` まで実行が流れる**のを忘れないため

**コツ**

**`switch` 文**では `break` が現れるまで**下へ落ちる**（フォールスルー）。表示順・実行順を**上から積み上げ**て追う。

**例**

```java
int num = 10;
switch (num) {
    case 10: System.out.print("A");
    case 11: System.out.print("B");
    default: System.out.print("C");
}
// break なし → ABC
```

---

**項目**

`switch` **文** — **`default` の位置**とフォールスルー（`Chapter3_answers.md` 設問20）

**題目**

`default` が **`case` より上**にあっても、**下の `case` に流れる**ことを見落とさないため

**コツ**

`default` は「どの `case` にも当てはまらないとき」に入るが、**そこに `break` がなければ**その下の `case` にも流れる。文法上 `default` は**最後でなくてよい**。

**例**

```java
int n = 0;
switch (n) {
    default:
        System.out.print("C");
        // break なし
    case 1:
        System.out.print("A");
        break;
}
// default → case 1 へフォールスルーし A まで → CA
```

---

**項目**

`switch` **式** — **網羅と `default`**（`Chapter3_answers.md` 設問22）

**題目**

**式**として値を返す `switch` で、**`default` 不足がコンパイルエラー**になる条件を取り違えないため

**コツ**

**`switch` 式**はすべての入力がどこかの arm に落ちる必要がある。**`int` の全域**を少数の `case` で埋められないときは **`default` が実質必須**になりやすい。従来の **`switch` 文**より**厳しい**、と対比して覚える。

**例**

```java
int x = 1;
// int v = switch (x) { case 1 -> 10; case 2 -> 20; }; // int の残りが無いとコンパイルエラー
int v = switch (x) {
    case 1 -> 10;
    case 2 -> 20;
    default -> 0;
};
```

---

**項目**

繰り返し — **`do`-`while` の形と最低 1 回実行**（`Chapter3_answers.md` 設問26）

**題目**

`do` の直後に `while (条件)` を書く誤りや、**セミコロン**を忘れないため

**コツ**

形は **`do { } while (条件);`**。`**`do` の直後に条件を書かない**。**`while` の後は `;` 必須**。**本体は先に実行**され、そのあと条件判定（`while` と逆）。

**例**

```java
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 3);
```

---

**項目**

繰り返し — **`do`-`while` と中カッコ省略**（`Chapter3_answers.md` 設問27）

**題目**

`do` と `while` の間に**文が 2 つ以上**あるのに `{}` がないコードをコンパイルエラーと見抜くため

**コツ**

`if` と同様、省略時は **`do` の直後の 1 文だけ**がループ本体。**2 文以上**なら **`{}` 必須**。

**例**

```java
// do
//     System.out.println("A");
//     System.out.println("B");
// while (false); // NG（中カッコなしで 2 文）

do {
    System.out.println("A");
    System.out.println("B");
} while (false); // OK
```

---

**項目**

繰り返し — **`for` の初期化文**（`Chapter3_answers.md` 設問28）

**題目**

初期化で **`int` と `long` をカンマ区切り**で同時宣言してしまわないため

**コツ**

初期化の **`int a = 0, b = 1`** のように、**同じ型**だけをカンマで並べられる。**型が混ざる**複数宣言は **NG**。

**例**

```java
// for (int i = 0, long j = 0; i < 1; i++) {} // NG
for (int i = 0, j = 0; i < 1; i++) {} // OK
```

---

**項目**

配列 — **二重ループと `array[i].length`**（`Chapter3_answers.md` 設問33）

**題目**

ジグザグ配列で **行ごとの長さ**を固定の数で代用してしまわないため

**コツ**

行 `i` の列数は **`array[i].length`**。内側は原則 **`j < array[i].length`**。固定の `j < 2` などは**合計がずれる**か **範囲外**になりやすい。

**例**

```java
int[][] a = { {1, 2}, {2, 3, 4} };
// 内側は必ず array[i].length 未満まで回す（行ごとの列数が違う）
for (int i = 0; i < a.length; i++) {
    for (int j = 0; j < a[i].length; j++) {
        // a[i][j] を処理
    }
}
```

---

**項目**

配列 — **`length` は 1 次元目か行か**（`Chapter3_answers.md` 設問34）

**題目**

**`matrix.length`** と **`matrix[i].length`** を混同しないため

**コツ**

**`matrix.length`** は **行数（1 次元目の要素数）**。**`matrix[i].length`** は **その行の列数**。二重ループの上限がどちらの `length` を見ているか、**変数名ごとに分離**して書き出す。

**例**

```java
String[][] m = new String[2][];
m[0] = new String[2];
m[1] = new String[2];
System.out.println(m.length);    // 2（行）
System.out.println(m[0].length); // 2（その行の列）
```

---

**項目**

繰り返し — **`break` と二重ループ**（`Chapter3_answers.md` 設問40）

**題目**

**内側だけ**抜けて外側は回る、という挙動で表示回数を取り違えないため

**コツ**

**`break` は最も内側の `switch` またはループだけ**を抜ける。外側まで一気に抜けたいときは**ラベル付き `break`** など別手段。

**例**

```java
outer:
for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 3; j++) {
        if (j == 1) break; // 内側だけ抜ける
        System.out.print(i + "" + j + " ");
    }
}
// 内側 j=0 のみ各 i で実行 → 00 10 のようなパターン（設問に合わせて表で追う）
```

---

**項目**

繰り返し — **ラベル付き `continue` / `break`**（`Chapter3_answers.md` 設問43）

**題目**

**どのループの次周回／どこへ抜けるか**を、`i` と `j` の表で追わずに誤らないため

**コツ**

**`continue ラベル`** はそのラベルの**外側ループの次反復**へ。**`break ラベル`** はそのラベルが付いた**文の直後**へ。二重以上は**表**（`i`×`j`）に「continue」「break」「加算」を書き込む。

**例**

```java
int total = 0;
outer:
for (int i = 0; i < 3; i++) {
    inner:
    for (int j = 0; j < 3; j++) {
        if (i == 0) continue outer; // 外側の次の i へ
        if (j == 2) break inner;    // inner ラベルの直後＝内側ループを抜ける
        total += i + j;
    }
}
// 実際の値は設問のコードに合わせて表で検算する（解答は total=12 系の典型）
```

---

**項目**

答案の作法 — **複数選択の完全一致**（設問2・17・18。採点は `Chapter3_sec_scoring_20260513.md` 参照）

**題目**

正解の**個数と集合**が公式と一致しているか、マークシートで取り違えないため

**コツ**

**1 つでも多い／少ない／違う肢があると不正解**になりやすい。問題文の **「N つ選択」**と、自分が選んだ**記号の個数**を最後に必ず突き合わせる（知識は合っていてもマークミスで落ちるのを防ぐ）。

**例**

```
正解が {A,C,D} のとき:
  {A,C}   → 不足で不正解
  {A,C,D,B} → 余分で不正解
  {A,C,D} → 順不同で可（採点ルールは採点結果ファイルの通り）
```

---

以上。
