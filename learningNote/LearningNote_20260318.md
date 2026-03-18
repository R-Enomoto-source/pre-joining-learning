# LearningNote 2026-03-18

## セッションログ（ユーザー入力＋回答）

### Exception系例外とRuntimeException系例外の整理
ユーザー:
この質問にだけ答えてください
試験に出るだろう、Exception系例外、RuntimeException系例外を初学者にも分かりやすく正確に教えてください

アシスタント:
### **結論から：Exception系とRuntimeException系の違い**

- **共通点**  
  - どちらも `Throwable` → `Exception` クラス階層に属する「例外オブジェクト」です。  
  - `try { ... } catch (...) { ... }` でキャッチできます。  

- **大きな違い（試験で超重要）**  
  - **Exception（のうち RuntimeException 以外）**  
    - **チェック例外（checked exception）**  
    - コンパイル時に**「ちゃんと対処しているか」チェックされる**  
      - `try-catch` で捕まえるか  
      - `throws` で呼び出し元に投げるか  
    - どちらもしないと**コンパイルエラー**になる  
  - **RuntimeException（とそのサブクラス）**  
    - **非チェック例外（unchecked exception）**  
    - **コンパイラは強制しない**  
      - `try-catch` で捕まえなくても  
      - `throws` を書かなくても  
      - **コンパイルは通る**（実行時に落ちる可能性があるだけ）

---

### **クラス階層（ざっくり図）**

テキストで階層を書くと：

```text
Throwable
 ├─ Error                 // JVMやシステムレベルの重大なエラー（普通はキャッチしない）
 └─ Exception             // アプリケーションレベルの例外
     ├─ RuntimeException  // 非チェック例外（unchecked）
     │    ├─ NullPointerException
     │    ├─ ArrayIndexOutOfBoundsException
     │    ├─ IllegalArgumentException
     │    ├─ ArithmeticException など
     │
     ├─ IOException           // ここから下は代表例（全部 checked）
     ├─ SQLException
     ├─ ClassNotFoundException
     └─ その他いろいろ
```

- **`RuntimeException` は `Exception` のサブクラス**
  - なので「**Exception系**の一部が **RuntimeException系**」という関係です。

---

### **"Exception系例外" と "RuntimeException系例外" の意味**

試験や教科書でよくある言い方：

- **Exception系例外**
  - 広い意味だと：`Exception` クラスとそのサブクラス**全部**  
  - 試験問題では多くの場合、  
    - **チェック例外（RuntimeException 以外の Exception）**を指して  
      「Exception系（チェック例外）」  
      と説明することが多いです。
- **RuntimeException系例外**
  - `RuntimeException` とそのサブクラス  
  - **非チェック例外（unchecked exception）** とほぼ同義で使われます。

※ 試験では「**checked / unchecked**」の用語と対応付けて覚えるのが大事です。

---

### **代表的な例外（試験に出やすい）**

- **RuntimeException系（非チェック例外）**
  - `NullPointerException`
  - `ArrayIndexOutOfBoundsException`
  - `ArithmeticException`（0 で割ったときなど）
  - `IllegalArgumentException`
  - `NumberFormatException` など  
  → **プログラマのミス（バグ）で起こることが多い**

- **Exception系（チェック例外、= RuntimeException 以外）**
  - `IOException`（ファイル読み書き、ネットワーク）
  - `SQLException`（データベース）
  - `ClassNotFoundException`
  - `FileNotFoundException` など  
  → **外部要因（ファイルがない、ネットワーク切断など）で起こりやすい**

---

### **キャッチのされ方（型と継承関係）**

- 例：`ArrayIndexOutOfBoundsException` は  
  `RuntimeException` → `Exception` → `Throwable` という継承関係。

```java
try {
    String[] array = {"A", "B", "C"};
    System.out.println(array[3]); // ← ここで ArrayIndexOutOfBoundsException
} catch (ArrayIndexOutOfBoundsException e) {
    // 一番ピンポイントで捕まえる
} catch (RuntimeException e) {
    // これでも捕まえられる（親クラスだから）
} catch (Exception e) {
    // これでも捕まえられる（さらに親クラス）
}
```

- **ポイント**
  - **子クラスの例外は、親クラスの catch でも捕まえられる**  
    - `ArrayIndexOutOfBoundsException` → `RuntimeException` → `Exception`  
      なので、この3種類どれの `catch` でも OK。
  - `RuntimeException` が `Exception` のサブクラスなので、  
    - `catch (Exception e)` は **チェック例外 + すべての RuntimeException** をまとめて捕まえます。

---

### **試験で狙われやすいポイントまとめ**

- **checked exception**
  - `RuntimeException` 以外の `Exception` サブクラス  
  - **try-catch か throws が必須**。しないとコンパイルエラー。
- **unchecked exception**
  - `RuntimeException` とそのサブクラス  
  - **try-catch も throws も必須ではない**。コンパイラは強制しない。
- **継承関係**
  - `RuntimeException` は `Exception` のサブクラス  
  - なので `catch (Exception e)` は **RuntimeException系も含めて全部キャッチ**できる。
- **キャッチの順番**
  - 子クラス → 親クラス の順に並べる  
  - 親クラスを先に書くと「到達不能コード」でコンパイルエラー。

---

もし「この例外は checked / unchecked のどっち？」みたいなリストで整理したい場合は、具体的な例外名を挙げてくれれば、一緒に整理表を作ります。

### finallyと参照型の図の意味
ユーザー:
これはどういうことでしょうか?
具体的なコードで初学者にも分かりやすく正確に教えてください

アシスタント:
### **図が言っていること（日本語でざっくり）**

- さっきのコードは `int` という**プリミティブ型**でした  
  → `return val;` の時点で「**数字のコピー**」が戻り値として確定します。  
  → その後 `finally` で `val += 10;` しても、**もう戻り値には影響しません**。

- 図はその対比として  
  **「メソッドの変数が参照型（オブジェクト）の場合は話が変わるよ」**  
  と言っています。

- **参照型（例：配列、`StringBuilder`、自作クラスなど）の場合**  
  - 「戻り値用の変数」も「メソッド内の変数」も  
    **同じインスタンス（同じオブジェクト）を指している**ので、  
  - `finally` でそのインスタンスの中身を変えると、  
    **呼び出し側から見える中身も変わってしまう**、という話です。

---

### **プリミティブ型の場合のイメージ（さっきの `int`）**

```java
public class Main {
    public static void main(String[] args) {
        int result = sample();
        System.out.println(result); // 10 が出る
    }

    private static int sample() {
        int val = 0;
        try {
            String[] array = {"A", "B", "C"};
            System.out.println(array[3]); // ここで例外
        } catch (RuntimeException e) {
            val = 10;
            return val;     // ← ここで「10」という数値がコピーされて戻り値にセット
        } finally {
            val += 10;      // ← ここで val は 20 になるが、「戻り値の10」は変わらない
        }
        return val;
    }
}
```

- 戻り値は「**int の値そのもの**」なので、  
  `return val;` の時点で **10 がコピーされて確定**します。  
- そのあと `finally` で変えても、**コピー先（戻り値）は変わりません**。

---

### **参照型の場合のコード例１：戻り値のオブジェクトを書き換える**

今度は「参照型（オブジェクト）」で同じようなことをします。

```java
class Box {
    int value;
}

public class Main {
    public static void main(String[] args) {
        Box box = new Box();
        box.value = 0;

        Box result = sample(box);

        // box と result は「同じインスタンス」を指している
        System.out.println("box.value   = " + box.value);
        System.out.println("result.value = " + result.value);
    }

    private static Box sample(Box val) {
        try {
            // ここで何かする…
            return val;          // ← val が指している「Boxインスタンス」を戻す
        } finally {
            val.value += 10;     // ← インスタンスの中身を変更している
        }
    }
}
```

- `val` は **Box 型の「参照（アドレス）」**を持っているだけです。
- `return val;` で戻り値にも **同じインスタンスへの参照がコピー**されます。
- `finally` でやっているのは  
  **`val` が指しているインスタンスの `value` フィールドを書き換えているだけ**なので、
  - 呼び出し側の `box` も  
  - 戻り値の `result` も  
  **同じインスタンスを指しており、書き換えの影響を受けます。**

実行結果イメージ：

```text
box.value   = 10
result.value = 10
```

---

### **参照型でも「参照そのもの」を変えたらどうなるか**

注意点として：

```java
private static Box sample(Box val) {
    try {
        return val;
    } finally {
        val = new Box();  // ← ここで「別のインスタンス」を指すようにしても…
        val.value = 999;
    }
}
```

- `return val;` の時点で「元のインスタンスへの参照」が戻り値にコピーされています。
- `finally` で `val = new Box();` としても、  
 それは「**ローカル変数 val がどのインスタンスを指すか**」を変えているだけで、  
  **戻り値としてすでに確定した参照は変わりません。**

つまり：

- **プリミティブ**  
  - 値がコピーされて戻り値に行く。  
  - `finally` で変えても、戻り値は変わらない。
- **参照型**  
  - 「どのインスタンスを指すか」という参照がコピーされて戻り値に行く。  
  - `finally` で **そのインスタンスの中身（フィールド）を変えると、呼び出し側もその変更を見る**。  
  - ただし「どのインスタンスを返すか（参照自体）」は、`return` の時点で確定していて、後からは変えられない。

---

### **図の文章を言い換えると**

> これはプリミティブ型の場合で、参照型の場合は戻り値のための変数もメソッド内の変数も同じインスタンスへの参照を持っているため、finally ブロックで戻すインスタンスの値を変更することは可能です。

→ 意味を噛み砕くと：

- さっき説明したコードは「int みたいなプリミティブ型」の話です。  
- でも「参照型（オブジェクト）の場合」は、
  - 戻り値用の変数も
  - メソッド内の変数も  
  **同じオブジェクトを指している**ので、  
- `finally` でそのオブジェクトの中身を変えると、  
  **「戻り値として返したオブジェクト」も中身が変わった状態で見える**、ということです。

---

「このパターンの Anki 用 Q&A を作りたい」「primitive と reference の違いを図でまとめたい」などあれば、その形でも整理できます。

### equalsメソッドとClassCastExceptionの発生理由
ユーザー:
@Chapter6_questions.md (401-442) 
このコードでコンパイルエラーにならないのは何故ですか？
(a.equals(b));で継承関係にない方の変数を引数に使ってメソッドを呼び出していますよね？

アシスタント:
### **結論**

- **コンパイル時にエラーにならない理由**  
  - `equals` メソッドの引数の型は `Object` なので、**どんな参照型のオブジェクトでも渡せる**。  
  - したがって `a.equals(b);` は「`Object` 型の引数を取るメソッドに `B` 型のオブジェクトを渡している」だけなので、**コンパイル上は正しい**。

- **実行時に例外になる理由**  
  - `equals` の中で `A a = (A) obj;` と **キャスト**している。  
  - 実行時には `obj` の中身は `B` インスタンスなので、  
    **「B を A にキャストしようとして失敗」→ `ClassCastException` がスローされる**。

---

### **コードを型の目線で分解**

```java
public class A {
    private int num;
    public A(int num) { this.num = num; }

    public boolean equals(Object obj) { // ← 引数は Object
        A a = (A) obj;                  // ← 実行時キャスト
        return this.num == a.num;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        A a = new A(10);
        B b = new B(10);
        System.out.println(a.equals(b));  // ← ここ
    }
}
```

1. **コンパイル時のチェック（a.equals(b) のところ）**
   - `a` の型は `A`
   - `A#equals` のシグネチャは `equals(Object obj)`
   - 引数 `b` の型は `B`
   - `B` は **`Object` のサブクラス**（すべてのクラスは暗黙に `Object` を継承）  
   → 「`Object` 型を受け取るメソッドに `B` 型を渡す」ことは**合法**なので、コンパイルOK。

2. **実行時の動き**
   - 実行するとき、`a.equals(b)` で `equals` が呼ばれ、`obj` には `B` インスタンスが入る。
   - `A a = (A) obj;` で  
     「中身が `B` なのに `A` として扱おうとする」キャストが行われる。
   - `A` と `B` は**継承関係がない**ので  
     **キャスト不可能 → 実行時に `ClassCastException` 発生**。

---

### **あなたの理解の確認**

> コンパイラがコンパイルするときにはエラーにならず、JVM が実行時に継承関係にないのでキャストできないクラスをキャストしようとして、エラーになるということでしょうか？

**その理解で正しいです。**

- **コンパイル時**  
  - コンパイラは  
    - 「`obj` は `Object` 型」  
    - 「`(A) obj` は、`obj` が実行時に `A` 型かそのサブクラスなら成功するキャスト」  
    とみなして、**文法的にはOK** と判断します。
- **実行時（JVM）**  
  - 実際の中身を見て「`B` を `A` にキャストしようとしている」ことがわかるので、  
    **継承関係がない → キャスト失敗 → `ClassCastException`** になります。

---

### **イメージでまとめ**

- **メソッド呼び出し時（a.equals(b)）**
  - 型チェック：  
    - `B` は `Object` の一種 → OK → **コンパイル通る**

- **メソッドの中（(A) obj）**
  - 実行時チェック：  
    - 「本当に `A` 系か？」を JVM が確認  
    - 実体は `B` → ダメ → **実行時例外**

この「**引数の宣言型は広く（Object）、中で狭くキャストしてミスると実行時例外**」というパターンは、試験でもよく出るポイントです。

### 実行時とは何か（誰が何を実行しているか）
ユーザー:
実行時というのは何が何を実行しているのでしょうか？

アシスタント:
### **ざっくり答え**

- **「実行時」＝ JVM が `.class` ファイルのバイトコードを、CPU に命令として実行させている時間帯のこと**  
- もう少しくだけて言うと  
  **「あなたが `java Main` を叩いてから、プログラムが終わるまでのあいだ全部」**が「実行時」です。

---

### **誰が何を実行しているのか**

1. **OS（Windows など）**  
   - あなたが `java Main` を実行すると、  
   - **`java.exe`（JVM のプロセス）を起動**します。

2. **JVM（Java Virtual Machine）** がやること  
   - `Main.class` などの **バイトコード（.class ファイル）を読み込む**
   - バイトコードを  
     - そのまま解釈したり（インタプリタ）  
     - 必要に応じてネイティブコードに変換したり（JITコンパイル）して  
   - **CPU に実行させる**

3. **CPU**  
   - JVM から渡された「機械語レベルの命令」を、  
   - 本当に **電気信号として動かしている本体**。

---

### **コンパイル時 vs 実行時**

- **コンパイル時（compile time）**
  - `javac Main.java` を実行しているとき。  
  - **コンパイラ（javac）がソースコードをチェック**して、
    - 文法エラー
    - 型の整合性（`int` に `String` を代入してないか など）
  - を検証し、OKなら **バイトコード（.class）を生成**する。

- **実行時（run time）**
  - `java Main` を実行しているとき。  
  - **JVM がバイトコードを読み込み、CPU に実行させている最中**。
  - この時点で初めて分かる問題（例）：
    - 0 で割る → `ArithmeticException`
    - `null` に対してメソッド呼び出し → `NullPointerException`
    - 継承関係のないクラスへのキャスト → `ClassCastException`
  - こういったものが「**実行時例外（RuntimeException）**」です。

---

### **あなたの前の質問とのつながり**

`ClassCastException` などは：

- **コンパイル時**  
  - 「`Object` を `A` にキャストするコード」としては文法的にOK → コンパイル通る
- **実行時**（= JVM がバイトコードを実行しているとき）  
  - 実際の中身を見て「B を A にキャストしようとしているじゃん、ムリ！」と気づき  
  → **JVM が `ClassCastException` をスロー**  

という流れです。

---

もっと踏み込んで「JVM の中で何が起きているか（クラスローダ、スタックフレームなど）」まで知りたければ、そこも図解しながら説明します。

### instanceofによる型チェックと強制キャスト
ユーザー:
実用上はinstanceofを使って型変換可能か確かめてから書くべきだったところを、強制的にキャストしているのでこうなってしまっていると考えてよいでしょうか？

アシスタント:
はい、その理解で大丈夫です。

### **ポイントだけまとめると**

- このコードは

```java
public boolean equals(Object obj) {
    A a = (A) obj;          // ← ここが「強制キャスト」
    return this.num == a.num;
}
```

- `obj` が本当に `A`（またはサブクラス）かどうか **確認せずに**  
 いきなり `(A)` でキャストしているので、

```java
a.equals(b); // b は B 型（A と無関係）
```

のときに、**実行時に `ClassCastException` が起きる**、という状況です。

### **実用上の正しい書き方のイメージ**

実務で安全にやるなら、たとえばこうします。

```java
public boolean equals(Object obj) {
    if (!(obj instanceof A)) {   // ← まず instanceof で型チェック
        return false;
    }
    A a = (A) obj;               // ← ここでキャストしても安全
    return this.num == a.num;
}
```

- **`instanceof` で「その型にキャスト可能か」を確かめてからキャストするべき**
- それをせずに **「どうせ A だろう」と決め打ちで強制キャスト**しているから、
  - `A` と継承関係のない `B` を渡したときに  
    **実行時例外になってしまう**、という整理でOKです。

### equalsの実装スタイルと可読性
ユーザー:
これでも良いですよね？
可読性の観点からこちらの方が良くないですか？
public boolean equals(Object obj) {
    if (obj instanceof A) {   // ← まず instanceof で型チェック
        A a = (A) obj;               // ← ここでキャストしても安全
    return this.num == a.num;
    }
    return false;
}

アシスタント:
はい、その書き方でも動作としては問題ありませんし、**読みやすさの面でも十分「アリ」なスタイル**です。

```java
public boolean equals(Object obj) {
    if (obj instanceof A) {
        A a = (A) obj;
        return this.num == a.num;
    }
    return false;
}
```

### **この書き方の良い点**

- **「A だったら比較する／違ったら false」**という流れが上から下へ素直に読める  
- `instanceof` のブロック内でしか `a` を使わないので、**スコープも小さくて読みやすい**
- `return` が2カ所だけで、どちらも分かりやすい

### **equals として最低限意識しておくと良いこと**

本格的には `equals` の「契約」（反射性・対称性・推移性など）を満たす必要がありますが、初学者の段階で最低限押さえるなら：

- `null` 対策を入れる（`null` なら `false` を返す）
- 同一インスタンスなら true（早期リターン）にすることが多い

を加えると、より「教科書的」になります。

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) {          // 同じインスタンスなら true
        return true;
    }
    if (!(obj instanceof A)) {  // null もここで弾ける
        return false;
    }
    A a = (A) obj;
    return this.num == a.num;
}
```

まとめると：  
**あなたが書いた if → return false パターンは可読性の面でも十分良い**ですし、`equals` として少しだけ条件を足していけば実務でもそのまま使える形になります。

### instanceofとnullチェックの実務的な書き方
ユーザー:
 null もここで弾けるためif (!(obj instanceof A))の書き方の方が実務的という風に捉えてよいですか？

アシスタント:
はい、その理解で大丈夫です。

### **なぜ実務的か**

- `if (!(obj instanceof A))` は  
  - `obj == null` のとき → `instanceof` は常に `false` → `!false` で `true`  
  - `obj` が `A` 以外の型のとき → これも `false` → `true`  
  → **「null または A 以外」を一気に弾ける** ので、実務でよく使われます。

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) {          // 同一インスタンスなら true
        return true;
    }
    if (!(obj instanceof A)) {  // null も別クラスもここでまとめて false
        return false;
    }
    A a = (A) obj;
    return this.num == a.num;
}
```

- 別々に

```java
if (obj == null) return false;
if (!(obj instanceof A)) return false;
```

と書くよりも  
**短く・漏れなく・よく見るパターン**なので、  
「`if (!(obj instanceof A))` の方が実務的な書き方」  
と捉えて問題ありません。

