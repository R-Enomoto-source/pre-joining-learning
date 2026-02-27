# LearningNote 2026-02-27

## セッションログ（ユーザー入力＋回答）

### インタフェースのdefaultメソッドと型安全の基本
ユーザー: @Chapter5_questions.md (44-68) 

選択肢Bは 、Aイ ンタフ正一スとBク ラスのコンパイルは成功 しますが、Main
クラスのmainメ ソッ ドでコンパイルエラーとな ります。∧インタフエースの
sampleメ ソッドを削除 し、3ク ラスにsampleメ ソッ ドを追加 しても、Bク ラス
のインスタンスヘの参照をA型の変数で扱 つているため、sampleメ ソッ ドが
ないと判断されるからです。よつて、選択肢Bは誤 りです。
こうなるのは何故ですか？
初学者にも分かりやすく正確に教えてください
ポリモーフィズムでA型の変数に入っていても、実際に使われるのは、クラスBのインスタンスなので、不都合はなさそうですが。

アシスタント:
コードを確認し、初学者向けに説明します。

アシスタント:
Javaの型システムの仕組みを、初学者向けに説明します。

要点: コンパイル時は変数の宣言型でメソッドの存在をチェックします。実行時に実際のオブジェクト型で呼び出されるのは、そのメソッドが宣言型に存在する場合のみです。

具体例で説明します：

アシスタント:
## 核心的なポイント

**Javaでは、コンパイル時に変数の宣言型でメソッドの存在をチェックします。**

```java
A a = new B();  // 変数aの「型」はA
a.sample();     // コンパイラは「A型にsample()があるか？」をチェック
```

### なぜこれが必要か？

1. 型安全性のため
   - 後で`A a = new C();`に変更しても、`C`に`sample()`がなくてもコンパイル時に検出できる

2. インターフェース契約のため
   - `A`型の変数なら、`A`が定義するメソッドだけを使えることを保証

### ポリモーフィズムとの関係

ポリモーフィズムは「実行時に実際のオブジェクトのメソッドが呼ばれる」仕組みですが、そのメソッドは変数の宣言型に存在している必要があります。

```
コンパイル時：A型にsample()があるか？ → なければエラー
     ↓（コンパイル成功）
実行時：Bのsample()が呼ばれる（ポリモーフィズム）
```

**つまり、実行時にBのメソッドが呼ばれるのは、コンパイル時にAにそのメソッドが存在することが確認できた場合だけです。**

詳細な解説をファイルに保存しました。ご確認ください。追加の質問があればどうぞ。

ユーザー:
詳細な説明をここに出力してください

アシスタント:
解説ファイルの内容を表示します。

アシスタント:
# Chapter5 問題4の解説：なぜ選択肢Bはコンパイルエラーになるのか？

## 問題の状況

```java
// 選択肢Bの修正案
public interface A {
    // sample()メソッドを削除
}

public class B implements A {
    public void sample() {  // Bクラスにsample()メソッドを追加
        System.out.println("sample");
    }
}

public class Main {
    public static void main(String[] args) {
        A a = new B();  // A型の変数にBのインスタンスを代入
        a.sample();     // ← ここでコンパイルエラー！
    }
}
```

## なぜコンパイルエラーになるのか？

### 1. Javaの2段階のメソッド解決プロセス

Javaでは、メソッドの呼び出しが**2つの段階**で処理されます：

#### ステップ1：コンパイル時（静的チェック）
- **変数の宣言型**（この場合は`A`型）に基づいてメソッドの存在をチェック
- `A a = new B();` の場合、変数`a`の**型は`A`**
- `a.sample();` を呼び出すとき、コンパイラは「**A型にsample()メソッドが定義されているか？**」をチェック
- **Aインターフェースにsample()メソッドがなければ、コンパイルエラー**

#### ステップ2：実行時（動的ディスパッチ）
- コンパイルが成功した場合のみ、実行時に実際のオブジェクト型（この場合は`B`）のメソッドが呼ばれる
- これが**ポリモーフィズム**の仕組み

### 2. 具体例で理解する

```java
A a = new B();
a.sample();
```

このコードをコンパイラが読むとき：

1. **変数`a`の型は何か？** → `A`型
2. **`A`型に`sample()`メソッドは定義されているか？** → 選択肢Bの修正案では**NO**
3. **コンパイルエラー！** → 実行時まで到達しない

### 3. なぜこの仕組みが必要なのか？

#### 理由1：型安全性の保証
```java
A a = new B();
// もし後で別の実装クラスに変更されたら？
A a = new C();  // Cクラスにはsample()がないかもしれない

// コンパイル時にチェックすることで、実行時エラーを防ぐ
a.sample();  // コンパイル時に「A型にsample()があるか」を確認
```

#### 理由2：インターフェース契約の保証
- インターフェースは「この型を使うなら、このメソッドが使える」という**契約**を定義
- 変数の型が`A`なら、`A`が提供するメソッドだけが使えることを保証

### 4. ポリモーフィズムとの関係

ポリモーフィズムは「実行時に実際のオブジェクトのメソッドが呼ばれる」仕組みですが、**そのメソッドは必ず変数の宣言型に存在していなければなりません**。

```java
A a = new B();
a.sample();  // 実行時にはBのsample()が呼ばれる（ポリモーフィズム）
             // でも、コンパイル時にはAにsample()があることを確認する必要がある
```

### 5. 正しい修正方法

選択肢Aが正解です：

```java
public interface A {
    default void sample() {  // defaultメソッドとして定義
        System.out.println("sample");
    }
}

public class B implements A {
    // sample()をオーバーライドしなくてもOK
    // 必要ならオーバーライドも可能
}

public class Main {
    public static void main(String[] args) {
        A a = new B();
        a.sample();  // コンパイルOK！実行時はAのdefaultメソッドが呼ばれる
    }
}
```

## まとめ

| 項目 | 説明 |
|------|------|
| **コンパイル時** | 変数の**宣言型**（`A`型）にメソッドが存在するかチェック |
| **実行時** | 実際のオブジェクト型（`B`型）のメソッドが呼ばれる（ポリモーフィズム） |
| **重要なポイント** | ポリモーフィズムが働くためには、**宣言型にメソッドが存在している必要がある** |

### 覚えておくべきこと

> **「変数の型でコンパイル時にチェック、実際のオブジェクトで実行時に呼び出し」**

- `A a = new B();` の場合
  - コンパイル時：`A`型に`sample()`があるか？ → **なければエラー**
  - 実行時：`B`の`sample()`が呼ばれる → **でもコンパイルが通らないと実行できない**

## 補足：もしB型の変数を使ったら？

```java
B b = new B();
b.sample();  // これはOK！B型にsample()があるから
```

この場合は、変数の型が`B`なので、`B`に`sample()`があればコンパイルOKです。

ユーザー:
そのような仕組みになっていることは分かりました。
何故そうなっているのでしょうか？
私の考えとしては、以下のとおりです。
型安全となり、これがあることで、そのメソッドがあることが確約されるというのは分かりました。型安全とは、型にメソッドが存在するかどうかをチェックすることで、メソッドが存在することが確約されていることをいうのでしょうか？
ただ、それだとそのメソッドの中身が書かれているかは分かりませんよね？しかし、これはまた別の管轄のコンパイルエラーだということは分かっています。

説明はここに出力してください

アシスタント:
なぜその仕組みになっているのか、理由を説明します。

## なぜそのような仕組みになっているのか？

### 1. 型安全（Type Safety）とは何か？

型安全とは、型にメソッドが存在するかをコンパイル時にチェックし、そのメソッドが存在することを確約することです。

重要な点：
- メソッドの存在は確約される
- メソッドの中身（実装）は別のチェック対象

```java
// 例：メソッドの存在チェック（型安全）
A a = new B();
a.sample();  // ← A型にsample()があるか？をチェック

// 例：メソッドの中身チェック（別のコンパイルエラー）
public void sample() {
    // 中身が空でも、return文がないでも、別のエラーとして扱われる
}
```

### 2. なぜこの2段階チェックが必要なのか？

#### 理由1：抽象化と実装の分離

```java
// インターフェースA：契約を定義
public interface A {
    void sample();  // 「sample()というメソッドがある」という契約
}

// クラスB：契約を実装
public class B implements A {
    public void sample() {
        // 実装の詳細
    }
}
```

- インターフェースは「何ができるか」を定義（契約）
- クラスは「どう実現するか」を定義（実装）
- コンパイル時は契約の存在を確認し、実装の詳細は実行時に解決

#### 理由2：実行時エラーを防ぐため

もしコンパイル時にチェックしなかったら：

```java
// 悪い例：コンパイル時にチェックしない場合
A a = new B();
a.sample();  // 実行時に「sample()がない！」というエラーが発生
```

問題点：
- 実行時までエラーに気づけない
- テストや実行で初めて発覚する
- デバッグが困難

現在の仕組み：
```java
A a = new B();
a.sample();  // コンパイル時に「Aにsample()がない」と即座に分かる
```

#### 理由3：コードの可読性と保守性

```java
// 良い例：型で何が使えるかが明確
A a = someMethod();
a.sample();  // A型を見れば、sample()が使えることが分かる

// 悪い例：実際のオブジェクトを見ないと分からない
A a = someMethod();
a.sample();  // 実際にBなのかCなのか、コードを見ないと分からない
```

- 変数の型を見れば、使えるメソッドが分かる
- コードを読むだけで理解できる
- リファクタリングが安全

#### 理由4：ポリモーフィズムの実現

```java
// 同じコードで、異なる実装を使い分けられる
A a1 = new B();  // Bの実装を使う
A a2 = new C();  // Cの実装を使う

a1.sample();  // Bのsample()が呼ばれる
a2.sample();  // Cのsample()が呼ばれる
```

- インターフェースで統一された操作を定義
- 実装クラスごとに異なる動作を実現
- コンパイル時は契約（インターフェース）をチェックし、実行時は実装を選択

### 3. メソッドの存在チェックと実装チェックは別物

```java
// チェック1：メソッドの存在（型安全）
public interface A {
    void sample();  // 存在する
}

A a = new B();
a.sample();  // ← A型にsample()があるか？をチェック

// チェック2：メソッドの実装（別のコンパイルエラー）
public class B implements A {
    public void sample() {
        // 実装が不完全でも、存在チェックは通る
        // でも、実装のエラーは別途チェックされる
    }
}
```

- 存在チェック：型にメソッドが定義されているか
- 実装チェック：メソッド本体が正しく書かれているか（return文、例外処理など）

### 4. 設計思想：契約プログラミング（Design by Contract）

```java
// 契約：A型を使うなら、sample()が使える
public interface A {
    void sample();
}

// 実装：契約を守る
public class B implements A {
    public void sample() {
        // 実装の詳細
    }
}

// 使用：契約に基づいてコードを書く
A a = new B();
a.sample();  // 契約により、sample()が使えることが保証されている
```

- インターフェースが契約を定義
- 実装クラスが契約を満たす
- 使用側は契約に基づいてコードを書ける

### 5. 実際の例：なぜこれが重要か

#### 例1：ライブラリの使用

```java
// ライブラリのインターフェース
public interface DatabaseConnection {
    void connect();
    void disconnect();
    void query(String sql);
}

// あなたのコード
DatabaseConnection db = DatabaseFactory.create();
db.connect();     // コンパイル時に「connect()がある」ことが保証される
db.query("...");  // コンパイル時に「query()がある」ことが保証される
```

- ライブラリの実装が変わっても、インターフェースが同じならコンパイルエラーにならない
- インターフェースを見れば、使えるメソッドが分かる

#### 例2：チーム開発

```java
// 開発者A：インターフェースを定義
public interface PaymentProcessor {
    void processPayment(double amount);
}

// 開発者B：実装を書く（まだ未完成）
public class CreditCardProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        // TODO: 実装中...
    }
}

// 開発者C：使用するコードを書く
PaymentProcessor processor = new CreditCardProcessor();
processor.processPayment(100.0);  // コンパイルOK！インターフェースがあるから
```

- インターフェースがあれば、実装が未完成でもコンパイルできる
- 並行開発が可能

### 6. まとめ：なぜこの仕組みなのか

| 理由 | 説明 |
|------|------|
| **型安全性** | コンパイル時にメソッドの存在を確約し、実行時エラーを防ぐ |
| **抽象化** | インターフェース（契約）と実装を分離し、柔軟性を確保 |
| **可読性** | 変数の型を見れば、使えるメソッドが分かる |
| **保守性** | 実装を変更しても、インターフェースが同じなら影響が少ない |
| **ポリモーフィズム** | 同じコードで異なる実装を使い分けられる |
| **契約プログラミング** | インターフェースが契約を定義し、実装がそれを満たす |

### 7. あなたの理解について

> 型安全とは、型にメソッドが存在するかどうかをチェックすることで、メソッドが存在することが確約されていることをいうのでしょうか？

はい、その理解で合っています。

- 型安全 = コンパイル時に型にメソッドが存在することを確約
- メソッドの中身（実装）のチェックは別のコンパイルエラーとして扱われる

この2段階のチェックにより：
1. メソッドの存在は型システムで保証される
2. 実装の正しさは別のコンパイラチェックで保証される

これにより、型安全性と実装の柔軟性を両立できます。

---

結論：この仕組みは、型安全性、抽象化、可読性、保守性、ポリモーフィズムを実現するための設計です。コンパイル時にメソッドの存在をチェックすることで、実行時エラーを防ぎ、コードの品質を高めます。

ユーザー:
メソッドの戻り値がaに代入されているのでしょうか？
理由3：コードの可読性と保守性
// 良い例：型で何が使えるかが明確A a = someMethod();a.sample();  // A型を見れば、sample()が使えることが分かる// 悪い例：実際のオブジェクトを見ないと分からないA a = someMethod();a.sample();  // 実際にBなのかCなのか、コードを見ないと分からない

アシスタント:
はい、その通りです。`someMethod()`の戻り値が変数`a`に代入されています。説明を明確にします。

## コード例の説明

```java
A a = someMethod();
a.sample();
```

### 何が起きているか？

1. `someMethod()`を呼び出す
   - このメソッドは`A`型（または`A`を実装したクラス）のオブジェクトを返す
   - 例：`return new B();` や `return new C();`

2. 戻り値を変数`a`に代入
   - `a`の型は`A`
   - 実際のオブジェクトは`B`や`C`の可能性がある

3. `a.sample()`を呼び出す
   - コンパイル時は`A`型に`sample()`があるかをチェック
   - 実行時は実際のオブジェクト（`B`や`C`）の`sample()`が呼ばれる

### 具体例で理解する

```java
// someMethod()の実装例
public A someMethod() {
    // 条件によって異なる実装を返す
    if (condition1) {
        return new B();  // BはAを実装
    } else {
        return new C();  // CはAを実装
    }
}

// 使用する側のコード
public void useA() {
    A a = someMethod();  // someMethod()の戻り値（BまたはC）をaに代入
    
    // A型として使えるメソッド
    a.sample();          // OK！A型にsample()がある
    a.anotherMethod();   // OK！A型にanotherMethod()がある
}
```

### なぜ「型で何が使えるかが明確」なのか？

```java
// 良い例：型で何が使えるかが明確
A a = someMethod();
a.sample();  // A型を見れば、sample()が使えることが分かる
```

- 変数`a`の型は`A`
- `A`インターフェースを見れば、使えるメソッドが分かる
- `someMethod()`が`B`を返そうが`C`を返そうが、`A`型として扱える

### もし型チェックがなかったら？

```java
// 悪い例：実際のオブジェクトを見ないと分からない
A a = someMethod();
a.sample();  // 実際にBなのかCなのか、コードを見ないと分からない
```

- `someMethod()`の実装を確認しないと、返されるオブジェクトの型が分からない
- 返されるオブジェクトに`sample()`があるか、実行時まで分からない
- コードを読むだけでは、使えるメソッドが分からない

### より詳しい例

```java
// インターフェース定義
public interface A {
    void sample();
    void anotherMethod();
}

// 実装クラス1
public class B implements A {
    public void sample() { System.out.println("B's sample"); }
    public void anotherMethod() { System.out.println("B's another"); }
    public void bSpecificMethod() { }  // B独自のメソッド
}

// 実装クラス2
public class C implements A {
    public void sample() { System.out.println("C's sample"); }
    public void anotherMethod() { System.out.println("C's another"); }
    public void cSpecificMethod() { }  // C独自のメソッド
}

// someMethod()の実装
public A someMethod() {
    if (Math.random() > 0.5) {
        return new B();  // Bのインスタンスを返す
    } else {
        return new C();  // Cのインスタンスを返す
    }
}

// 使用する側
public void useA() {
    A a = someMethod();  // someMethod()の戻り値（BまたはC）をaに代入
    
    // A型として使えるメソッド
    a.sample();          // OK！A型にsample()がある
    a.anotherMethod();   // OK！A型にanotherMethod()がある
    
    // BやC独自のメソッドは使えない
    // a.bSpecificMethod();  // コンパイルエラー！A型にbSpecificMethod()がない
    // a.cSpecificMethod();  // コンパイルエラー！A型にcSpecificMethod()がない
}
```

### まとめ

| 項目 | 説明 |
|------|------|
| **`someMethod()`** | `A`型（または`A`を実装したクラス）のオブジェクトを返すメソッド |
| **`A a = someMethod();`** | `someMethod()`の戻り値を変数`a`に代入 |
| **`a.sample();`** | `A`型に`sample()`があるかをコンパイル時にチェック |
| **実行時** | 実際のオブジェクト（`B`や`C`）の`sample()`が呼ばれる |

**重要なポイント：**
- `someMethod()`の戻り値が`a`に代入される
- `a`の型は`A`なので、`A`インターフェースで定義されたメソッドだけが使える
- 実際のオブジェクトが`B`でも`C`でも、`A`型として扱える
- これにより、コードの可読性と保守性が向上する

この仕組みにより、`someMethod()`の実装が変わっても、`A`インターフェースが同じなら、使用する側のコードを変更する必要がありません。

ユーザー:
@code block (22-28) はどこのクラスに実体がありますか？

アシスタント:
### 回答

**その`someMethod()`は、私が説明用に書いた「架空の例」であって、あなたのプロジェクト内のどのクラスにも実体はありません。**

- 実際のファイルには存在しない、概念説明のためのコードです。
- 実際に書くなら、例えばこんな形でどこかのクラスのメソッドとして定義します：

```java
public class SampleFactory {

    public A someMethod() {
        if (Math.random() > 0.5) {
            return new B();  // BはAをimplements
        } else {
            return new C();  // CもAをimplements
        }
    }
}
```

「`A a = someMethod();` の `someMethod()` は、`A`型を返すメソッドであれば、どのクラスのインスタンスメソッド／staticメソッドでもよい」ということを示すための例でした。

ユーザー:
違いがないように思います
初学者にも分かりやすく正確に教えてください
理由3：コードの可読性と保守性
// 良い例：型で何が使えるかが明確A a = someMethod();a.sample();  // A型を見れば、sample()が使えることが分かる// 悪い例：実際のオブジェクトを見ないと分からないA a = someMethod();a.sample();  // 実際にBなのかCなのか、コードを見ないと分からない
変数の型を見れば、使えるメソッドが分かる
コードを読むだけで理解できる
リファクタリングが安全

アシスタント:
### まず：「違いがないように思う」のは正しいです

あなたが感じている通り、  
私が書いた

```java
A a = someMethod();
a.sample();
```

「良い例」「悪い例」として並べた書き方だけを見ると、**コード自体は同じに見える**ので、「何が違うの？」となるのは当然です。

**違いは「周りの設計・前提」にあります。**  
行そのものではなく、

- `A` がどういう型か
- `someMethod()` が何を返す約束になっているか

ここが違います。

---

### 良い例の世界観

良い例では、こんな前提です：

```java
interface A {
    void sample();  // インタフェースが「sampleがある」と約束している
}
```

```java
A a = someMethod();  // 戻り値の型も A
a.sample();          // A に sample があることが、型だけで分かる
```

ここでのポイント：

- 変数 `a` の**型は `A`**  
- `A` インタフェースを開けば  
  → 「`sample()` というメソッドがある」と一目で分かる  
- `someMethod()` が内部で `new B()` を返そうが `new C()` を返そうが、**呼び出し側は気にしなくてよい**

→ 「`a` は `A` 型だから、`A` が約束しているメソッドは全部安全に呼べる」  
　という状態になっています。

---

### 悪い例の世界観（本当に言いたかったケース）

悪い例として言いたかったのは、例えばこういう状況です：

#### パターン1：戻り値の型があいまい（`Object` など）

```java
Object a = someMethod();  // 戻り値の型が Object
((B) a).sample();         // 「きっと B だろう」と思ってキャストして呼ぶ
```

- 変数 `a` の型は `Object`
- `Object` クラスを見ても `sample()` なんてメソッドはない
- 「本当は中身は B なんだよ」という**知識**に依存している
- someMethod() の実装が将来 `new C()` に変わったら？
  - コンパイルは通るが、実行時に `ClassCastException`

#### パターン2：`A` が何も約束していない

```java
interface A {
    // sample() を定義していない
}

class B implements A {
    void sample() { ... }  // B 独自のメソッド
}

A a = new B();
// a.sample();  // コンパイルエラー（A には sample がない）
```

- 変数 `a` の型は `A`
- `A` を見ても `sample()` は宣言されていない
- 「中身は B だから sample 呼べるはず」という**実装依存の思い込み**になる

---

### 「変数の型を見れば、使えるメソッドが分かる」とは？

良い設計：

```java
interface A {
    void sample();
    void foo();
}

A a = someMethod();
a.sample(); // A にある
a.foo();    // A にある
```

- 変数 `a` の**型 `A` だけ見れば**、「`sample()` と `foo()` が使える」ことが分かる
- `someMethod()` の中で B を返そうが C を返そうが関係ない
- 将来、`someMethod()` の実装を変えても、`A` が同じなら呼び出し側はそのままでよい

悪い設計のイメージ：

```java
A a = someMethod();  // でも A には sample が定義されていない
a.sample();          // 実は「中身がたまたま B だから呼べる」と思っているだけ
```

- コードを読むだけでは「本当に sample を呼べるのか？」が分からない
- someMethod() の実装や、B クラスの中身まで追わないと安心して呼べない
- 将来 B→C に変えたら壊れるかもしれない

---

### リファクタリングが安全、というのは何がうれしいのか

良い設計（インタフェースで約束）：

```java
interface A {
    void sample();
}

class B implements A { ... }
class C implements A { ... }

A a = someMethod();  // どの実装でもいい
a.sample();          // A が sample を約束している
```

- 将来 B をやめて D を作っても：

```java
class D implements A { ... }

// someMethod の中を変えるだけ
return new D();
```

- 呼び出し側の `A a = someMethod(); a.sample();` は**一切変更不要**
- コンパイラも「A に sample がある」ことを確認してくれる

悪い設計（実装クラスに依存）：

```java
B a = (B) someMethod();  // B に依存
a.sample();
```

- 将来 B→D に変えたくなったら、**呼び出し側すべて**を直さないといけない

---

### まとめ（初学者向けに一文で）

- **良い状態**  
  「この変数は `A` 型です」と宣言しておけば、**`A` が約束しているメソッドだけを呼ぶ**ようにしておく。  
  → 変数の型を見るだけで「何ができるか」が分かる。実装が変わっても壊れにくい。

- **悪い状態**  
  「中身はたぶん B だから、このメソッド呼べるよね」と、**実装クラスに直接依存して呼ぶ**。  
  → コード全体を読まないと安全か分からない。実装変更に弱い。

もし「この部分の具体例を、自分のコードでこう書き換えるならどうなるか」などあれば、そのコードを貼ってくれれば、それを題材にして説明を続けます。

ユーザー:
someMethod() の実装が将来 new C() に変わったら？とは？
someMethod() とはどのメソッドで中身にどんなコードが書かれていますか？

ユーザー:
iava」 ang.Obiectク ラスに定義されているメソッ ド、つまりdefaultメソッドでオーバーライドできないメソッドでSE17のJavasilverの試験に出そうなメソッドはどんなメソッドがありますか？
toString,hashcode,equalsは知っています

ユーザー:
@Chapter5_ScoringResult_1once.md (59-60) 
AのdefaultメソッドをBは継承しているので、super.sample();でBのsampleメソッドが呼び出されると思ったのですが違うのでしょうか？

アシスタント:
問題のコードを確認し、`A.super.sample()`の動作を説明します。

アシスタント:
`インタフェース名.super.メソッド()`の呼び出しルールを説明します。

アシスタント:
選択肢を確認中です。

アシスタント:
`インタフェース名.super.メソッド()`の呼び出しルールを説明します。

## 問題の構造

```java
public interface A {
    default void sample() {
        System.out.println("Hello");
    }
}

public interface B extends A {
    // 空、Aのsample()を継承
}

public class C implements B {
    @Override
    public void sample() {
        __________  // ここでAのsample()を呼びたい
        System.out.println("Java");
    }
}
```

## 重要なルール：`インタフェース名.super.メソッド()`は「直接実装している型」からしか呼べない

### あなたの理解について

> AのdefaultメソッドをBは継承しているので、super.sample();でBのsampleメソッドが呼び出されると思った

この理解は部分的に正しいですが、重要な点が抜けています。

### 正しい理解

#### 1. `super.sample()`と`B.super.sample()`の違い

```java
public class C implements B {
    public void sample() {
        super.sample();  // ← これはクラスの親を探す（Cはクラスなので、Objectクラスを探す）
        // Objectにはsample()がないので、これはコンパイルエラー
    }
}
```

- `super.sample()`はクラスの親クラス（`Object`）を探す
- `C`は`Object`を継承しているが、`Object`には`sample()`がない
- したがって、`super.sample()`はコンパイルエラー

#### 2. `インタフェース名.super.メソッド()`のルール

```java
public class C implements B {
    public void sample() {
        A.super.sample();  // ← これは「Aを直接実装している型」からしか呼べない
        // CはAを直接実装していない（Bを実装している）ので、コンパイルエラー
        
        B.super.sample();  // ← これはOK！CはBを直接実装している
        // B.super.sample()を呼ぶと、Bにはsample()の実装がないので、
        // 継承チェーンをたどってAのsample()が呼ばれる
        System.out.println("Java");
    }
}
```

重要なルール：
- `インタフェース名.super.メソッド()`は、そのインタフェースを直接実装している型からしか呼べない
- `C`は`B`を直接実装しているが、`A`は直接実装していない
- したがって、`C`から`A.super.sample()`は呼べない

#### 3. 正しい呼び出し方

```java
public class C implements B {
    public void sample() {
        B.super.sample();  // ← これはOK！CはBを直接実装している
        // B.super.sample()を呼ぶと、Bにはsample()の実装がないので、
        // 継承チェーンをたどってAのsample()が呼ばれる
        System.out.println("Java");
    }
}
```

- `C`は`B`を直接実装しているので、`B.super.sample()`は呼べる
- `B`には`sample()`の実装がないので、継承チェーンをたどって`A`の`sample()`が呼ばれる
- 結果：「Hello」が表示され、その後「Java」が表示される

### なぜこのルールがあるのか？

#### 理由1：曖昧さを避けるため

```java
public interface A {
    default void sample() { System.out.println("A"); }
}

public interface B extends A {
    default void sample() { System.out.println("B"); }
}

public interface C extends A {
    default void sample() { System.out.println("C"); }
}

public class D implements B, C {
    public void sample() {
        A.super.sample();  // ← どちらの経路でAにたどり着く？B経由？C経通？
        // 曖昧なので、直接実装している型からしか呼べない
    }
}
```

#### 理由2：継承チェーンを明確にするため

```
A (sample()を定義)
 ↑
B extends A (sample()を継承)
 ↑
C implements B (Bを直接実装)
```

- `C`から`A.super.sample()`を呼ぶと、`A`への経路が曖昧になる可能性がある
- `B.super.sample()`なら、`B`経由で`A`にたどり着くことが明確

### この問題の正解

選択肢を見ると：
- A. `super.sample();` → クラスの親（`Object`）を探すのでエラー
- B. `A.super.sample();` → `C`は`A`を直接実装していないのでエラー
- C. `public.sample();` → 無効な構文
- D. `new.sample();` → 無効な構文
- E. 上記のいずれも誤りである → 正解

ただし、実際には`B.super.sample();`が正解です（選択肢にないためEが正解）。

### まとめ

| 呼び出し方 | 説明 | この問題での結果 |
|-----------|------|----------------|
| `super.sample()` | クラスの親クラス（`Object`）を探す | コンパイルエラー（`Object`に`sample()`がない） |
| `A.super.sample()` | `A`を直接実装している型からしか呼べない | コンパイルエラー（`C`は`A`を直接実装していない） |
| `B.super.sample()` | `B`を直接実装している型から呼べる | OK（`C`は`B`を直接実装している） |

重要なポイント：
- `インタフェース名.super.メソッド()`は、そのインタフェースを直接実装している型からしか呼べない
- `C`は`B`を直接実装しているので、`B.super.sample()`は呼べる
- `C`は`A`を直接実装していないので、`A.super.sample()`は呼べない
- `B`には`sample()`の実装がないので、`B.super.sample()`を呼ぶと継承チェーンをたどって`A`の`sample()`が呼ばれる

このルールにより、どのインタフェースのメソッドを呼ぶかが明確になります。

ユーザー:
そもそもsuper.sample();で呼べるものが違うということですね。
クラス名.super.メソッド名とsuper.メソッド名の違いを初学者にも分かりやすく正確に教えてください

アシスタント:
`super.メソッド名()`と`インタフェース名.super.メソッド名()`の違いを説明します。

アシスタント:
## `super.メソッド名()`と`インタフェース名.super.メソッド名()`の違い

### 基本的な違い

| 構文 | 探す場所 | 使える場所 |
|------|---------|-----------|
| `super.メソッド名()` | **クラスの親クラス**（継承階層） | クラス内のみ |
| `インタフェース名.super.メソッド名()` | **インタフェースのdefaultメソッド** | そのインタフェースを直接実装しているクラス内のみ |

---

## 1. `super.メソッド名()`：クラスの継承階層をたどる

### 基本ルール

```java
class Parent {
    void method() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    void method() {
        super.method();  // ← Parentクラスのmethod()を呼ぶ
        System.out.println("Child");
    }
}
```

**動作：**
- `super.method()`は、**クラスの親クラス**（`Parent`）の`method()`を探す
- クラスの継承階層を上にたどる

### 具体例

```java
class GrandParent {
    void method() {
        System.out.println("GrandParent");
    }
}

class Parent extends GrandParent {
    void method() {
        super.method();  // GrandParentのmethod()を呼ぶ
        System.out.println("Parent");
    }
}

class Child extends Parent {
    void method() {
        super.method();  // Parentのmethod()を呼ぶ
        System.out.println("Child");
    }
}
```

**実行結果：**
```
GrandParent
Parent
Child
```

### インタフェースには使えない

```java
interface A {
    default void method() {
        System.out.println("A");
    }
}

class B implements A {
    void method() {
        super.method();  // ← コンパイルエラー！
        // superはクラスの親を探すので、Objectクラスを探す
        // Objectにはmethod()がないのでエラー
    }
}
```

**なぜエラー？**
- `super`は**クラスの親クラス**を探す
- `B`の親クラスは`Object`（明示的に継承していない場合）
- `Object`には`method()`がないので、コンパイルエラー

---

## 2. `インタフェース名.super.メソッド名()`：インタフェースのdefaultメソッドを呼ぶ

### 基本ルール

```java
interface A {
    default void method() {
        System.out.println("A");
    }
}

class B implements A {
    void method() {
        A.super.method();  // ← Aインタフェースのdefaultメソッドを呼ぶ
        System.out.println("B");
    }
}
```

**動作：**
- `A.super.method()`は、**Aインタフェースのdefaultメソッド**を呼ぶ
- インタフェースの継承階層をたどる

### 重要なルール：直接実装している型からしか呼べない

```java
interface A {
    default void method() {
        System.out.println("A");
    }
}

interface B extends A {
    // Aのmethod()を継承
}

class C implements B {
    void method() {
        A.super.method();  // ← コンパイルエラー！
        // CはAを直接実装していない（Bを実装している）のでエラー
        
        B.super.method();  // ← OK！
        // CはBを直接実装しているので、B.super.method()は呼べる
        // Bにはmethod()の実装がないので、継承チェーンをたどってAのmethod()が呼ばれる
    }
}
```

**なぜ`A.super.method()`がエラー？**
- `インタフェース名.super.メソッド名()`は、**そのインタフェースを直接実装している型**からしか呼べない
- `C`は`B`を直接実装しているが、`A`は直接実装していない
- したがって、`C`から`A.super.method()`は呼べない

**なぜ`B.super.method()`はOK？**
- `C`は`B`を直接実装しているので、`B.super.method()`は呼べる
- `B`には`method()`の実装がないので、継承チェーンをたどって`A`の`method()`が呼ばれる

---

## 3. 両方を使う例：クラス継承とインタフェース実装の組み合わせ

```java
class Parent {
    void method() {
        System.out.println("Parent class");
    }
}

interface A {
    default void method() {
        System.out.println("A interface");
    }
}

class Child extends Parent implements A {
    void method() {
        super.method();        // ← Parentクラスのmethod()を呼ぶ
        A.super.method();      // ← Aインタフェースのdefaultメソッドを呼ぶ
        System.out.println("Child");
    }
}
```

**実行結果：**
```
Parent class
A interface
Child
```

**ポイント：**
- `super.method()`はクラスの親（`Parent`）を探す
- `A.super.method()`はインタフェース（`A`）のdefaultメソッドを呼ぶ
- 両方とも使える（ただし、メソッド名が同じ場合は注意が必要）

---

## 4. 問題のコードでの動作

```java
public interface A {
    default void sample() {
        System.out.println("Hello");
    }
}

public interface B extends A {
    // 空、Aのsample()を継承
}

public class C implements B {
    @Override
    public void sample() {
        super.sample();        // ← エラー！Objectクラスを探すが、sample()がない
        A.super.sample();      // ← エラー！CはAを直接実装していない
        B.super.sample();      // ← OK！CはBを直接実装している
        System.out.println("Java");
    }
}
```

### 各構文の動作

| 構文 | 探す場所 | 結果 |
|------|---------|------|
| `super.sample()` | `Object`クラス（`C`の親クラス） | エラー（`Object`に`sample()`がない） |
| `A.super.sample()` | `A`インタフェース | エラー（`C`は`A`を直接実装していない） |
| `B.super.sample()` | `B`インタフェース | OK（`C`は`B`を直接実装している） |

### `B.super.sample()`の動作

1. `B.super.sample()`を呼ぶ
2. `B`には`sample()`の実装がない
3. `B`は`A`を継承しているので、継承チェーンをたどる
4. `A`の`sample()`が呼ばれる
5. 「Hello」が表示される

---

## 5. まとめ表

| 項目 | `super.メソッド名()` | `インタフェース名.super.メソッド名()` |
|------|---------------------|-------------------------------------|
| **探す場所** | クラスの親クラス（継承階層） | インタフェースのdefaultメソッド |
| **使える場所** | クラス内のみ | そのインタフェースを直接実装しているクラス内のみ |
| **継承階層** | クラスの継承階層を上にたどる | インタフェースの継承階層をたどる |
| **例** | `super.method()` | `A.super.method()` |

---

## 6. 覚えておくべきポイント

### ポイント1：`super`はクラスの親を探す

```java
class Child extends Parent {
    void method() {
        super.method();  // Parentクラスを探す
    }
}
```

### ポイント2：`インタフェース名.super`はインタフェースのdefaultメソッドを呼ぶ

```java
interface A {
    default void method() { }
}

class B implements A {
    void method() {
        A.super.method();  // Aインタフェースのdefaultメソッドを呼ぶ
    }
}
```

### ポイント3：直接実装している型からしか呼べない

```java
interface A { }
interface B extends A { }
class C implements B {
    void method() {
        A.super.method();  // エラー！CはAを直接実装していない
        B.super.method();  // OK！CはBを直接実装している
    }
}
```

### ポイント4：クラスとインタフェースは別物

```java
class Parent { }
interface A { }
class Child extends Parent implements A {
    void method() {
        super.method();    // Parentクラスを探す
        A.super.method();  // Aインタフェースを探す
        // 両方とも使える（別々の階層）
    }
}
```

---

## 7. よくある間違い

### 間違い1：インタフェースで`super`を使う

```java
interface A {
    default void method() {
        super.method();  // ← エラー！インタフェースには親クラスがない
    }
}
```

**正解：** インタフェース内では`super`は使えない

### 間違い2：間接的に実装しているインタフェースの`super`を呼ぶ

```java
interface A { }
interface B extends A { }
class C implements B {
    void method() {
        A.super.method();  // ← エラー！CはAを直接実装していない
    }
}
```

**正解：** 直接実装しているインタフェース（`B`）の`super`を使う

```java
B.super.method();  // OK！
```

### 間違い3：クラスでインタフェースの`super`を使わずに`super`を使う

```java
interface A {
    default void method() { }
}

class B implements A {
    void method() {
        super.method();  // ← エラー！Objectクラスを探すが、method()がない
    }
}
```

**正解：** インタフェースの`super`を使う

```java
A.super.method();  // OK！
```

---

**核心的な違い：**
- `super.メソッド名()`はクラスの親クラスを探す
- `インタフェース名.super.メソッド名()`はインタフェースのdefaultメソッドを呼ぶ
- これらは別の階層を探すため、両方とも使える場合がある

ユーザー:
B.super.sample()は、Bの親クラスのsampleメソッドを呼び出しているんですね
A.super.sample()は、Aの親クラスのsamleメソッドは存在しないし、2つ内側にあるものにはそもそもアクセスできない、ということですか？

アシスタント:
いいところに気づいていますが、用語と考え方を少しだけ修正した方がよいです。

### 1. B.super.sample() は「Bの“親クラス”」ではない

この例ではすべて**インタフェース**です。

```java
public interface A {
    default void sample() { System.out.println("Hello"); }
}

public interface B extends A { }

public class C implements B {
    @Override
    public void sample() {
        B.super.sample();      // ここ
        System.out.println("Java");
    }
}
```

- `B.super.sample()` が探すのは  
  **「B が継承しているインタフェース（= super インタフェース）にある default メソッド」** です。
- `B` 自身には `sample()` の実装がないので、`B` の super インタフェースである **`A` の default `sample()`** が呼ばれます。

つまり：

> **B.super.sample() = 「B 経由で A の default sample() を呼ぶ」**

であって、クラスの「親クラス」を呼んでいるわけではありません。

### 2. A.super.sample() がダメな理由

```java
public class C implements B {
    @Override
    public void sample() {
        A.super.sample();  // コンパイルエラー
    }
}
```

- ルール：`X.super.m()` は、「**X を “直接” implements/extends している型**」からしか呼べません。
- `C` は `implements B` なので、**直接関係があるのは B だけ**。
- `A` とは「B を挟んだ間接的な関係」なので、`C` から `A.super.sample()` は呼べません。

あなたの言う

> 「2つ内側にあるものにはそもそもアクセスできない」

という感覚はほぼ合っていますが、より正確には：

- **「直接 implements/extends していないインタフェースの `X.super` は使えない」**
- だから `C` から使えるのは `B.super.sample()` だけ  
  → その結果として `A` の default が見つかる、という流れです。

ユーザー:
ClassCは継承しているのがObjectだから、継承先としてそれを読んでいると判断されるんですね。
implementsは内部的に継承とは別扱いということでしょうか？

ユーザー:
これが良く分かりません。
抽象クラスを実装している具象クラスをインスタンス化した場合、抽象クラスのメンバと具象クラスで新規作成やオーバーライドされたメンバが含まれていると私は理解しています。
具象クラスをインスタンス化した具象インスタンスが抽象インスタンスをそのまま持っているというのがイメージできません。
画像の説明は私の言っていることと同じことを言っているのでしょうか？

ユーザー:
以下の条件で、チャットのやり取りをMarkdownファイルに書き起こしてください。
【出力先】
pre-joining-learning/learningNote/LearningNote_20260227.md
（YYYYMMDDは当日の日付）
【形式（見出し階層を厳守）】
1) ファイル先頭の見出し
# LearningNote YYYY-MM-DD
2) セッションログの見出し
## セッションログ（ユーザー入力＋回答）
3) 各やりとりのタイトル
### タイトル（内容を読み込んで相応しいタイトルをつける）
4) タイトル内の小見出しは #### 以降を使用（# / ## / ### を使わない）
【記載ルール】
「ユーザー:」「アシスタント:」を交互に並べる
チャットで出力された内容は省略せず、そのまま貼り付ける
コードブロック、箇条書き、改行などもそのまま保持する
途中で「要約」「簡略化」「言い換え」をしない
各やりとりの前に、内容にふさわしいタイトルを付ける
すでに中身がある場合はその最後に追加する形に
【タイトル付けの例】
配列の再代入と final の挙動
equals と == の違い（配列・List）
JVMメモリ（スタック/ヒープ/GC）の理解
拡張for文（for-each）の基本と注意点
コンストラクタとメソッドの違い
2次元配列の走査と出力
【出力例】
# LearningNote 2026-01-16

## セッションログ（ユーザー入力＋回答）

### タイトル（内容を読み込んで相応しいタイトルをつける）
ユーザー: ...
アシスタント:
...（ここにチャットの本文をそのまま）
