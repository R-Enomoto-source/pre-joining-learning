## 第5章 弱点復習ノート（1回目）

このノートは `Chapter5_ScoringResult_1once.md` の  
「第5章 復習チェックリスト（誤答・誤解ベース）」と  
「第5章 復習すべき箇所まとめ（箇条書き）」を  
**初学者向けにかみ砕いて解説したもの**です。

---

## 1. 抽象クラスと抽象メソッド（問9）

### 1-1. 抽象クラスとは

- **抽象クラス**: `abstract` が付いたクラス。  
  - 「そのクラス単体では不完全。必ずサブクラスを作って使う前提」のクラス。
  - `new` で直接インスタンス化できない。

```java
abstract class AbstractSample {
    // 具象メソッド（中身あり）
    public void sample() {
        System.out.println("A");
        test();              // 抽象メソッドを呼び出している
        System.out.println("C");
    }

    // 抽象メソッド（中身なし）
    protected abstract void test();
}
```

### 1-2. 抽象メソッドとは

- **抽象メソッド**: 本体（`{}` の中身）がないメソッド。
  - 「ここにこういう名前・引数・戻り値のメソッドがあるべきだ」という“約束”だけ書いてある。
  - 実際の処理は **サブクラスが必ず実装する必要がある**。

### 1-3. 実際の呼び出しの流れ（問9のパターン）

```java
class ConcreteSample extends AbstractSample {
    @Override
    protected void test() {
        System.out.println("B");
    }
}

public class Main {
    public static void main(String[] args) {
        AbstractSample s = new ConcreteSample();
        s.sample();
    }
}
```

- `s.sample()` の中で `test()` が呼ばれるが、
  - **実行されるのは `ConcreteSample` の `test()`**。
- つまり表示順は  
  1. `sample()` の中の「A」  
  2. `ConcreteSample.test()` の「B」  
  3. `sample()` に戻ってきて「C」  
  → 結果: **A B C** の順に出力される。

ポイント:

- 「抽象メソッドだから動かない」ではなく、  
  **「使う前にサブクラスが必ず実装するので、実際にはサブクラスのメソッドが呼ばれる」** と理解する。

---

## 2. ポリモーフィズムと変数の型（問12・14・19）

### 2-1. 2つの“型”を区別する

オブジェクト指向では、次の 2つを分けて考える必要があります。

- **変数の型**（宣言時に書くもの）
- **実際のインスタンスの型**（`new` したクラス）

```java
A a = new B();   // 変数の型: A, 実際のインスタンス: B
```

### 2-2. 何がどちらで決まるか

- **メソッド呼び出し**  
  → 実際のインスタンスの型（オーバーライドした方）が使われる。

- **フィールド参照**  
  → 変数の型のフィールドが使われる（動的ディスパッチされない）。

### 2-3. フィールドが親子で同じ名前のとき（問12・19）

```java
class A {
    String val = "A";
    void print() {
        System.out.print(val);
    }
}

class B extends A {
    String val = "B";
}

public class Main {
    public static void main(String[] args) {
        A a = new A();
        A b = new B();
        System.out.print(a.val);  // A型のval
        System.out.print(b.val);  // A型のval（Bのvalは見ない）
        a.print();                // A.print() → Aのvalを参照
        b.print();                // A.print() → Aのvalを参照
    }
}
```

結果はすべて **"A"** になる → `"AAAA"`。

理由:

- `b` は `new B()` だが、**変数の型が `A`** なので、
  - `b.val` は A の `val` を参照。
  - `b.print()` も `A` クラスの `print()` が動き、その中で A の `val` を参照。

### 2-4. 親子で `name` フィールドを両方持つケース（問19）

```java
class Parent {
    String name;
    String getName() {
        return this.name;  // Parent側のname
    }
}

class Child extends Parent {
    String name;          // Child側のname
}

public class Main {
    public static void main(String[] args) {
        Child child = new Child();
        child.name = "sample";          // Child側のnameに代入
        System.out.println(child.getName()); // Parent側のnameを参照
    }
}
```

- `child.name = "sample";` → **Childの `name`** に値が入る。
- `getName()` で参照している `this.name` は **Parentの `name`**。
  - Parent 側の `name` は代入されていないので `null`。

---

## 3. インタフェースの default / private メソッド（問4・5・6・7・22）

### 3-1. インタフェースの基本

- インタフェースのメソッド宣言は、何も書かなくても **自動的に `public abstract`**。
- インタフェースには
  - 抽象メソッド
  - `default` メソッド（中身あり）
  - `static` メソッド（中身あり）
  を書ける。

### 3-2. default メソッドとは

- インタフェースに「**標準的な実装**」を持たせたいときに使う。

```java
public interface A {
    default void sample() {
        System.out.println("sample");
    }
}
```

- このように書くことで、A を実装したクラスが  
  `sample()` をオーバーライドしなくても、そのまま使える。

### 3-3. `Object` 由来メソッドを default で書けない（問5）

```java
public interface A {
    @Override
    default String toString() {  // これはコンパイルエラー
        return "A";
    }
}
```

- `toString` は `java.lang.Object` に定義されているメソッド。
- **インタフェースで `Object` のメソッドを default メソッドとしてオーバーライドすることはできない**。
  - そのため、このコードは **インタフェース側でコンパイルエラー** になる。

### 3-4. `インタフェース名.super.メソッド()` 構文（問6・7）

```java
public interface A {
    default void sample() {
        System.out.println("Hello");
    }
}

public interface B extends A { }

public class C implements B {
    @Override
    public void sample() {
        // A.super.sample(); ← ここは本来「Aを直接継承／実現している型」からしか使えない
        System.out.println("Java");
    }
}
```

- `A.super.sample()` は
  - A を **直接** 継承しているインタフェース、
  - または A を **直接** implements しているクラス
  からだけ使える。
- 継承関係が 2 段以上になると（A → B → C など）、
  - その一番外側のクラスから **A.super.sample() を直接呼ぶことはできない**。

### 3-5. インタフェースの private メソッド（問22）

- 目的: **default メソッドや static メソッドの共通処理をまとめる**ため。
- ルール:
  - `private` メソッドには **必ず本体 `{}` が必要**（普通のメソッドと同じ）。
  - 抽象メソッドを `private` にはできない。

```java
public interface Sample {
    private void helper() {
        // 共通処理
    }

    default void a() {
        helper();
    }
}
```

---

## 4. インタフェース／配列と代入互換性（問15）

### 4-1. インタフェースは `new` できない

```java
public interface A { }

public class B implements A { }

public class C extends B { }

public class D { }
```

```java
A[] array = {
    new B(),   // OK: B は A を実装
    new C(),   // OK: C は B を継承 → 結果的に A を実装
    new A(),   // NG: インタフェースは new できない
    new D()    // NG: D は A とまったく無関係
};
```

- `new A()` は「インタフェースのインスタンス化」なのでコンパイルエラー。
- `new D()` は「A と無関係な型を A 配列に入れようとしている」のでコンパイルエラー。

ポイント:

- **配列の要素型と、実際に入れるインスタンスの型の間に「代入可能な関係」が必要**。

---

## 5. キャストと `ClassCastException`（問16・17）

### 5-1. アップキャストとダウンキャスト

```java
class A { }

class B extends A {
    void hello() {
        System.out.println("hello");
    }
}
```

- **アップキャスト（子 → 親）**

```java
A a = new B();  // 自動でOK
```

- **ダウンキャスト（親 → 子）**

```java
A a = new B();
B b = (B) a;    // キャストが必要
b.hello();      // OK
```

### 5-2. コンパイルは通るが、実行時に落ちるパターン（問17）

```java
A a = new A();
B b = (B) a;    // コンパイルは通る
b.hello();      // 実行時に ClassCastException
```

- コンパイラは「`A` と `B` が継承関係にある」ので、キャスト式があれば一応OKとみなす。
- しかし実際には `a` の中身は `A` インスタンスなので、`B` にすることはできない。
  → 実行時に `ClassCastException` が発生。

まとめ:

- **キャスト式を書くと「コンパイル時の型チェック」は通るが、  
  実際のインスタンスが互換性を持たないと実行時に例外になる**。

---

## 6. sealed クラス／sealed インタフェースと修飾子ルール（問24・25・26）

### 6-1. sealed クラスとは

```java
public sealed class Sample permits A, B {
    // ...
}
```

- `Sample` を継承できるクラスを **A と B に限定**する宣言。
- それ以外のクラスが `extends Sample` するとコンパイルエラー。

### 6-2. sealed クラスのサブクラス側ルール

`Sample` を継承するクラス `A` / `B` は、必ず次のどれかで修飾する必要がある。

- `final`   : これ以上継承させない。
- `sealed`  : さらに `permits` で継承先を限定する。
- `non-sealed` : そこから先は自由に継承してよい。

```java
public sealed class Sample permits A { }

public final class A extends Sample { }        // OK
// public class A extends Sample { }          // NG: final/sealed/non-sealed いずれか必須
```

### 6-3. sealed インタフェース（問26）

```java
public sealed interface Test permits A {
    void sample();
}

public abstract class A implements Test {
    // ...
}
```

- `Test` を実装できるクラスは A だけ、と限定している。
- ここでも **A 側に `final` / `sealed` / `non-sealed` のどれかが必須**。
  - 何も付けないとコンパイルエラー。

ポイント:

- sealed は「**誰が継承（実装）してよいかを親側で限定する仕組み**」。
- その代わり、**親から直接継承・実装するクラス／インタフェースには、継承の方針を示す修飾子（final/sealed/non-sealed）が必須**になる。

---

## 7. その他のポイント（問10・11・18・20・21・23）

### 7-1. オーバーライドの条件（問10・11）

- **シグニチャが同じ**（メソッド名・引数の型・数・順番）。
- **戻り値型は元と同じか、そのサブクラス型**（共変戻り値）。
- **アクセス修飾子は同じか、より“緩い”ものだけに変更可**。  
  - 例: `protected` → `public` はOK。  
        `protected` → デフォルト / `private` はNG。

### 7-2. コンストラクタと `this` / `super`（問18・20・21）

- **親クラスのコンストラクタが先に動き、子クラスのコンストラクタが後で動く**。
- 子クラスのコンストラクタの先頭には、暗黙に `super();` が挿入される。
- 同じクラス内の別コンストラクタを呼びたいときは `this(...)` を使う。

```java
class A {
    int num;
    A(int num) {
        this.num = num;   // フィールドと引数が同名なら this.num で区別
    }
}
```

---

このノートは、第5章でつまずいたポイントを **何度でも見返せる「自分用の教科書」** として使ってください。  
特に、コードを実際に書いて動かしながら読むと、理解が一気に深まりやすくなります。  
