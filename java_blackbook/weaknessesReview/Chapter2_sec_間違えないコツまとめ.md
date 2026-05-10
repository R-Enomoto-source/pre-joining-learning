# 第2章（Chapter2_sec）— 間違えないためのコツまとめ



`exercise/Chapter2/Chapter2_sec.md` に書かれていた解説・原則を、復習用に題目／コツ形式で整理したものです。分野の切り分けと用語の正確さのために、`local_problems/answers/Chapter2_answers.md`（第2章の公式解答・解説）の設問番号を併記しています。各コツの下に**最小のコード例**を付けています。コード例は断片のため **`package` や `import`（例: `java.util.Arrays`、`java.util.ArrayList`、`java.util.List`）は必要に応じて補ってください**。



---



**項目**  

リテラル・基本構文 — 整数リテラルの進数表記（`Chapter2_answers.md` 設問2）



**題目**  

数値リテラルの進数表記を取り違えないため（2進・8進・16進の接頭辞の原則）

ツ**  

Java では数値を 10 進以外にも表せる。2 進は `0b`、8 進は先頭の `0`、16 進は `0x` で始める、と対応関係を押さえる。8 進リテラルでは各桁に 0〜7 しか使えない（`8` や `9` を含むとコンパイルエラー）。



**例**



```java

int dec = 267;       // 10進（接頭辞なし）

int oct = 0413;      // 8進（先頭0）

int hex = 0x10B;     // 16進

int bin = 0b100001011; // 2進

// int bad = 0827;  // 8進なのに 8,9 を含む → コンパイルエラー

```





---



**項目**  

リテラル・基本構文 — 数値リテラルのアンダースコア `_`（`Chapter2_answers.md` 設問3）



**題目**  

数値リテラル内のアンダースコア `_` を誤って置かないため



**コツ**  

リテラルの先頭・末尾には `_` を書けない。また、小数点、`L` / `F`、`0b`、`0x` などの**記号の直前直後**にも書けない、という制約を覚えておく。



**例**



```java

int ok = 1_000_000;

long ok2 = 0b1010_1100;

// int ng1 = _123;     // 先頭に _ → NG

// int ng2 = 123_;     // 末尾に _ → NG

// int ng3 = 0x_12;    // 0x の直後に _ → NG

// double ng4 = 1_.5;  // . の前後に _ → NG

```



---



**項目**  

識別子・キーワード — 識別子の命名規則（`Chapter2_answers.md` 設問4）



**題目**  

識別子（変数名・メソッド名など）をコンパイルエラーにしないため



**コツ**  

予約語は識別子に使えない。使える記号はアンダースコア `_` と `$` のみ（`-` や `.` は不可）。先頭を数字にしてはいけない（2 文字目以降なら数字可）。



**例**



```java

int _x = 1;

int $y = 2;

int x2 = 3;   // 先頭以外に数字は可

// int class = 1;   // 予約語 → NG

// int a-b = 1;     // - は不可 → NG

// int 2nd = 1;     // 先頭が数字 → NG

```



---



**項目**  

文字列（`String`）— インスタンスの生成方法（`Chapter2_answers.md` 設問9）



**題目**  

オブジェクトの生成と文字列リテラルを混同しないため



**コツ**  

`String` も他クラス同様 `new` でインスタンス化できるが、文字列だけはダブルクォート `"` で囲む**文字列リテラル**でもインスタンスを生成できる。文字は `'`、文字列は `"` と役割を分ける。



**例**



```java

String a = new String("hello");

String b = "hello";           // リテラルでも String ができる

char c = 'A';                 // 文字は '

// String d = 'AB';          // 文字リテラルを String に → NG

```



---



**項目**  

文字列（`String`）— 不変性（immutable）とメソッドの戻り値（`Chapter2_answers.md` 設問10）



**題目**  

`String` の不変性と、`replace` / `replaceAll` などの「戻り値」を取り違えないため



**コツ**  

`String` は不変なので、内容を変えるには新しいインスタンスが必要になる。`replaceAll` などは**新しい `String` の参照を返す**だけで、元のインスタンスの内容は変わらない。戻り値を変数に代入しないと、生成した文字列はどこからも参照されない。



**例**



```java

String s = "abc";

s.replace("a", "z");     // 戻り値を捨てている → s は "abc" のまま

String t = s.replace("a", "z"); // t が "zbc"

System.out.println(s);   // abc

System.out.println(t);   // zbc

```



---



**項目**  

文字列（`String`）— `indexOf` メソッド（`Chapter2_answers.md` 設問12）



**題目**  

`indexOf` の意味と戻り値（存在しないとき）を間違えないため



**コツ**  

引数で指定した文字または部分文字列が**始まる位置**（先頭から 0 始まり）を返す。部分文字列が存在しなければ **`-1`**。終了位置を返すメソッドではない。



**例**



```java

String s = "abcde";

System.out.println(s.indexOf('c'));    // 2

System.out.println(s.indexOf("cd"));   // 2（部分文字列の先頭位置）

System.out.println(s.indexOf("abcdef")); // -1（存在しない）

```



---



**項目**  

文字列（`String`）— `substring` メソッド（`Chapter2_answers.md` 設問13）



**題目**  

`substring` の役割を他の `String` メソッドと混同しないため



**コツ**  

`java.lang.String` の `substring` は、元の文字列から**部分文字列を抽出**するメソッド。範囲は「文字と文字の間」に線を引いて 0 から番号を振ると取り違えにくい（解答の図の考え方）。



**例**



```java

String s = "abcde";

// |a|b|c|d|e|  → 境界に 0,1,2,3,4,5

System.out.println(s.substring(2, 4)); // "cd"（2≦位置＜4）

System.out.println(s.substring(2));    // "cde"（2から末尾まで）

```



---



**項目**  

文字列（`String`）— `replace` メソッド（`Chapter2_answers.md` 設問14）



**題目**  

`replace` の「全部置き換え」と引数の型制約を間違えないため



**コツ**  

先頭から走査し、マッチする部分を**順にすべて**置換する。オーバーロードは **`char, char` と `CharSequence, CharSequence` のどちらか**で、`"aa"` と `'b'` のように型が混在する呼び出しはコンパイルエラーになる。



**例**



```java

String s = "aaaa";

System.out.println(s.replace("aa", "b")); // "bb"（連続してすべて置換）

System.out.println(s.replace('a', 'b'));  // char, char

System.out.println(s.replace("aa", "x")); // CharSequence, CharSequence

// s.replace("aa", 'b'); // String と char の混在 → コンパイルエラー

```



---



**項目**  

文字列（可変）— `StringBuilder` のバッファ容量（`Chapter2_answers.md` 設問18）



**題目**  

`StringBuilder` の内部バッファ容量を問題に落とさないため



**コツ**  

引数なしコンストラクタでは、内部バッファは**デフォルトで 16 文字分**（余裕領域）。`String` を渡すコンストラクタでは、**「文字列の長さ + 16」** を初期容量にする実装になっている、という前提で `capacity()` の問題を読む。



**例**



```java

StringBuilder a = new StringBuilder();

System.out.println(a.capacity()); // 16



StringBuilder b = new StringBuilder("hello"); // 長さ 5

System.out.println(b.capacity()); // 5 + 16 = 21

```



---



**項目**  

文字列（言語機能）— テキストブロックの利点（`Chapter2_answers.md` 設問21）



**題目**  

テキストブロックを従来の文字列リテラルと混同しないため



**コツ**  

`"""` で囲む。複数行の `+` 連結が不要になる。`\"` や `\n` などのエスケープが**必須でなくなる**場合がある（ブロック内の改行はそのまま保持される、など）。誤った説明の選択肢に注意する。



**例**



```java

// 従来: + 連結と \" や \n が必要になりがち

String old = "<p>\"Hello\"</p>\n";



// テキストブロック: 開始 """ の直後は改行必須（設問22参照）

String html = """

        <p>"Hello"</p>

        """;

```



---



**項目**  

文字列（言語機能）— テキストブロックの構文（開始・終了の改行）（`Chapter2_answers.md` 設問22）



**題目**  

テキストブロックの改行ルールを誤解しないため



**コツ**  

開始の `"""` の**直後は改行が必須**（直後に本文を続けるとコンパイルエラー）。終了の `"""` の**直前**は、コード例のとおり**改行なしで本文を閉じる**書き方もできる。



**例**



```java

// NG: 開始直後に本文

// String bad = """this is

// sample

// """;



// OK: 開始の直後で改行

String ok1 = """

        this is sample text

        """;



// OK: 終了の """ の直前は改行なしでもよい

String ok2 = """

        this is sample text""";

```



---



**項目**  

文字列（言語機能）— テキストブロックのインデント除去（`Chapter2_answers.md` 設問23）



**題目**  

テキストブロックのインデントがどう決まるかを取り違えないため



**コツ**  

共通の左空白の削除量は、ブロック内で**最もインデント量が少ない行**を基準に決まる。その行より深くインデントした部分だけが本文として残るイメージで覚える。



**例**



```java

String str = """

            A

          B

        C

        """;

// 最も左に近い行は "        C" → その左空白が基準。

// 出力では A, B 行の先頭の共通空白がそこまで削られるイメージ。

System.out.println(str);

```



---



**項目**  

文字列（`String`）— `intern` と参照の同一性（`Chapter2_answers.md` 設問26。設問24・25はコンスタントプール・`equals` / `==`）



**題目**  

`intern()` と `==` の関係を誤って解釈しないため



**コツ**  

`intern()` はプール上の同一内容の文字列への参照を返す。`new String(...)` で別インスタンスでも、`intern()` の戻り値同士や文字列リテラルとの `==` が **`true` になり得る**、というパターンを理解する（文字列の「同一」と「同値」は別物。同値は `equals`）。



**例**



```java

String a = new String("def");

String b = new String("def");

System.out.println(a == b);                    // false（別インスタンス）

System.out.println(a.intern() == b.intern());  // true（プール上の同一参照）



String x = "sample";

String y = "sample";

System.out.println(x == y);                    // true（リテラルの使い回し）

System.out.println(x.equals(y));             // true（同値）

```



---



**項目**  

配列 — 配列インスタンスと `Object#toString`（`Chapter2_answers.md` 設問27）



**題目**  

配列をそのまま `print` / 文字列連結したときの表示を取り違えないため



**コツ**  

配列は `Object` の `toString()` のままなので、**要素ではなく** `クラス名@ハッシュ` 形式になる。要素を見せたいときは **`Arrays.toString(配列)`**（多次元なら `deepToString` など）。



**例**



```java

int[] arr = {1, 2, 3};

System.out.println(arr);                       // 例: [I@15db9742

System.out.println(Arrays.toString(arr));    // [1, 2, 3]

int[][] m = {{1, 2}, {3}};

System.out.println(Arrays.deepToString(m));   // [[1, 2], [3]]

```



---



**項目**  

配列 — 配列型変数の宣言と `[]` の位置・次元（`Chapter2_answers.md` 設問28）



**題目**  

配列の次元数と `[]` の書き方を混乱しないため



**コツ**  

`[]` の個数が次元の数。`int[] a` も `int a[]` も宣言としては有効で、多次元では `[]` を型側と変数名側に分けて書くこともできる。読みやすさのためには**型の直後に `[]` をまとめる**書き方が推奨されやすい。



**例**



```java

int[] a;

int b[];

int[][] c;

int[] d[];     // 2次元: int[][] と同じ意味の宣言

int[][] e = new int[2][3];

```



---



**項目**  

配列 — 配列**型変数の宣言**と要素数（`Chapter2_answers.md` 設問29）



**題目**  

配列の「宣言」と「要素数（サイズ）」を同時に書いてコンパイルエラーにしないため



**コツ**  

**配列型変数を宣言するとき**は `int[5] a;` のように**角括弧内に要素数を書けない**。要素数は **`new` でインスタンスを作るとき**（または初期化子で暗黙的に決めるとき）に指定する。



**例**



```java

// int[5] a;        // NG（宣言に要素数）

int[] a;

a = new int[5];     // OK（生成時に要素数）

int[] b = new int[5];

int[] c = {1, 2, 3}; // OK（要素数は暗黙）

```



---



**項目**  

配列 — 配列インスタンスの生成（要素数・多次元）（`Chapter2_answers.md` 設問30）



**題目**  

配列インスタンス生成時の要素数・型・多次元のルールを取り違えないため



**コツ**  

`new` で配列を作るときは**扱える要素数を必ず**指定する。要素数は**整数**で、次元の長さに使うのは実質 **`int` 相当**（`long` リテラルは不可などに注意）。2 次元以上で `new int[][3]` のように**1 次元目を空けて 2 次元目だけ埋める**書き方は不可。**変数の次元数と `new` の次元数は一致**させる。2 次元目以降の長さは**揃えなくてよい**（非対称多次元）。



**例**



```java

int[] a = new int[3];

// int[] b = new int[];     // 要素数なし → NG

// int[] c = new int[2.3];  // 浮動小数 → NG

// int[][] d = new int[][3]; // 1次元目省略は NG

int[][] e = new int[3][];   // OK（2次元目は後から代入）

e[0] = new int[2];

e[1] = new int[5];           // ギザギザでよい

// int[] f = new int[3][];  // 変数は1次元なのに new が2次元 → NG

```



---



**項目**  

配列 — `new` と初期化子 `{}`、宣言との同行（`Chapter2_answers.md` 設問33）



**題目**  

`new` と `{}` を組み合わせた初期化で `[要素数]` を誤って書かないため



**コツ**  

`new int[] { ... }` のように **`new` と初期化子を併用するときは `[]` 内に要素数を書かない**（要素数は初期化子から決まる）。`{ ... }` だけの初期化子は**変数宣言と同じ文**でしか使えない（宣言と代入を `;` で分けるとエラー）。



**例**



```java

int[] a = {2, 3};

int[] b = new int[]{2, 3};

// int[] c = new int[2]{2, 3}; // 要素数の明示 + {} → NG

// int[] d;

// d = {2, 3};                 // {} だけは宣言と同行でないと NG

int[] e;

e = new int[]{2, 3};           // これなら OK

```



---



**項目**  

配列 — 多次元配列と `null`（`Chapter2_answers.md` 設問34）



**題目**  

多次元配列に `null` が混ざっているときの `NullPointerException` を見落とさないため



**コツ**  

1 次元目の要素が `null` のまま、その要素に対して **`.length` や `[ ]` でアクセス**すると **`NullPointerException`**。非対称多次元で「途中の行だけ参照が無い」図を想像してチェックする。



**例**



```java

int[][] a = new int[3][];

a[0] = new int[]{1, 2};

a[1] = null;

a[2] = new int[]{3};

// System.out.println(a[1].length); // NPE

// System.out.println(a[1][0]);      // NPE

```



---



**項目**  

配列・参照型 — 継承／実装関係と配列、実行時例外の有無（`Chapter2_answers.md` 設問35）



**題目**  

参照の型関係・キャスト・`null` 有無から実行時例外が起きるか判断するため



**コツ**  

複数クラス・インタフェースが絡むときは**クラス図で関係を確認**する。設問のコードのように、**危険な明示キャストがなく**、**`null` を経由した参照の読み書きが実行されない**なら、実行時例外は起きない、という整理で選ぶ。



**例**



```java

// スーパー型の配列変数に、サブクラス要素の配列を代入できる例（概念）

Object[] obj = {"A", "B", "C"}; // String は Object のサブタイプ

// 明示キャストや null 経由のフィールドアクセスが無ければ、

// 単純代入だけでは NPE にはならないパターンが多い

```



---



**項目**  

コレクション — `java.util.ArrayList` の性質（`Chapter2_answers.md` 設問37）



**題目**  

`ArrayList` の性質（許容する要素・順序・スレッド安全性）を他のコレクションと混同しないため



**コツ**  

（ジェネリクスなしでも）**オブジェクトなら型を問わず格納可能**（制限はジェネリクスで付ける）。容量は**必要に応じて自動拡張**。**挿入順**を保つ（リスト構造）。**`null` 可**。**重複可**。**スレッドセーフではない**（単一スレッド向けで高速、という位置づけ）。



**例**



```java

ArrayList<String> list = new ArrayList<>();

list.add("A");

list.add("A");   // 重複可

list.add(null);  // null 可

// マルチスレッドで共有するなら Vector や CopyOnWriteArrayList など別手段

```



---



**項目**  

ジェネリクス — 型引数を付けない `ArrayList`（生型）の解釈（`Chapter2_answers.md` 設問38）



**題目**  

ジェネリクスで型を指定しない（生の型）ときのコンパイル上の扱いを取り違えないため



**コツ**  

`List<String> list = new ArrayList<>();` のように型を制限できる。変数宣言で型引数を省略した **`ArrayList list = new ArrayList();`** のような形は、**要素の取り扱いが `Object` ベース**になる（旧 API 互換・型安全でない書き方。試験では推論や代入可否が問われやすい）。



**例**



```java

ArrayList raw = new ArrayList();

raw.add("test");

raw.add(10);

Object o = raw.get(0); // get の戻りは Object 扱いになりがち

// String s = raw.get(0); // そのままでは String に代入できないことが多い

```



---



**項目**  

コレクション（`List` / `ArrayList`）— `add` と `add(int, E)`（`Chapter2_answers.md` 設問39）



**題目**  

`List` の `add` と `add(index, 要素)` の違いと、`index` の許容範囲を間違えないため



**コツ**  

`add(要素)` は末尾（または一貫した「最後」）への追加。`add(index, 要素)` は**指定位置への挿入**。有効な `index` は **`0` 以上 `size` 以下**（「隙間」に番号を振るイメージ）。`size` を超えると **`IndexOutOfBoundsException`**。



**例**



```java

ArrayList<String> list = new ArrayList<>();

list.add("A");           // [A]

list.add(1, "B");        // [A, B]  挿入 index は 0〜size

// list.add(2, "C");    // 今 size=2 なら index 2 は可。size=1 のときに 2 は例外

```



---



**項目**  

コレクション（`List` / `ArrayList`）— `set` メソッド（`Chapter2_answers.md` 設問40）



**題目**  

`set` の意味を `add` と混同しないため



**コツ**  

`set` は**指定インデックスの要素を置き換え**る。リストの長さは基本的に変わらない（`add` のような挿入による伸長ではない）。



**例**



```java

ArrayList<String> list = new ArrayList<>();

list.add("A");

list.add("B");       // [A, B]

list.set(0, "X");    // [X, B]  要素数は 2 のまま

```



---



**項目**  

コレクション（`List` / `ArrayList`）— `remove(Object)` と `equals`（`Chapter2_answers.md` 設問41）



**題目**  

`remove` が複数一致するときに「何を消すか」を取り違えないため



**コツ**  

`remove(オブジェクト)` は **`equals` が `true` になる最初の 1 要素だけ**を削除する。`equals` の実装（どのフィールドを比較するか）まで読む設問がある。



**例**



```java

ArrayList<String> list = new ArrayList<>(List.of("A", "B", "A"));

list.remove("A");      // equals が true の先頭だけ削除 → [B, A]

```



---



**項目**  

コレクション（`List` / `ArrayList`）— `remove` 後の繰り上がりと反復（`Chapter2_answers.md` 設問42）



**題目**  

`remove` 後の詰め（繰り上がり）と `for` / 拡張 `for` の挙動で要素を飛ばさないため



**コツ**  

削除すると後ろの要素が**繰り上がる**。拡張 `for` やインデックス走査では、**内部カーソルや `i++` のタイミング**と組み合わさると、削除直後の要素が**スキップ**されたように見えることがある（解答の「カーソル」説明の通り）。拡張 `for` 中に `list.remove` すると **設問43** のように `ConcurrentModificationException` になり得るため、ここでは添字の `for` で「飛ばし」を示す。



**例**（削除で繰り上がり、`i++` だけだと次の要素を飛ばす典型）



```java

ArrayList<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

for (int i = 0; i < list.size(); i++) {

    System.out.print(list.get(i)); // i=1 で B を表示したあと remove すると C が index 1 に繰り上がる

    if ("B".equals(list.get(i))) {

        list.remove(i); // そのまま i++ すると i=2 となり、繰り上がった C（index 1）を飛ばす

    }

}

// 意図によっては remove 後に i-- する、Iterator#remove を使う、など

```



---



**項目**  

コレクション（`List` / `ArrayList`）— 反復中の構造変更（`Chapter2_answers.md` 設問43）



**題目**  

反復中のリスト操作で `ConcurrentModificationException` を踏まないため



**コツ**  

**反復中に `remove` などでリストを変える**と、次の読み出しで **`java.util.ConcurrentModificationException`** になり得る。マルチスレッドだけでなく**単一スレッド**でも起きる点が試験で問われやすい。



**例**



```java

ArrayList<String> list = new ArrayList<>(List.of("A", "B", "C"));

for (String s : list) {

    if ("B".equals(s)) {

        list.remove(s); // イテレータとリストの変更が競合 → 次の next で例外の典型

    }

}

// java.util.ConcurrentModificationException

```



---



**項目**  

コレクション（`List`）— 固定長リスト `Arrays.asList` と `List.of`（`Chapter2_answers.md` 設問44）



**題目**  

固定長に近いリストを `Arrays.asList` と `List.of` で取り違えないため



**コツ**  

**`Arrays.asList(...)`**  

配列や列挙からリストビューを得る。要素の**置き換え**はできる場合があるが、**サイズ変更（`add` / `remove`）は不可**で、試すと **`UnsupportedOperationException`** になり得る。  



**`List.of(...)`**  

**完全に変更不可**のリスト。`add` / `remove` / `set` で**例外**。**`null` 要素は禁止**（渡すと `NullPointerException`）。  



どちらも「可変の `ArrayList`」とは別物として整理する。



**例**



```java

List<Integer> a = Arrays.asList(1, 2, 3);

a.set(0, 9);        // 要素の上書きは可（配列由来のビューでは挙動に注意）

// a.add(4);       // UnsupportedOperationException



List<Integer> b = List.of(1, 2, 3);

// b.set(0, 9);    // UnsupportedOperationException

// b.add(4);       // UnsupportedOperationException

// List.of(1, null, 3); // NullPointerException

```



---



以上。


