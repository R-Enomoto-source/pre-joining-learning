### 1行目：問4・5・6・7・22  
**インタフェースのdefault/privateメソッドと `Object` 由来メソッド**

- **インタフェースの役割**  
  - インタフェース = 「クラスに、これらのメソッドを必ず持ってね」と約束だけを書く型。  
  - 例：`interface Printer { void print(); }` は「`print()` を必ず持て」という約束。

- **defaultメソッド**  
  - インタフェースの中で **「標準実装付き」のメソッド** を書ける。  
  - 宣言例：  
    ```java
    interface Greeter {
        default void greet() {
            System.out.println("Hello!");
        }
    }
    ```
  - このインタフェースを実装したクラスは、`greet()` を **オーバーライドしなくてもそのまま使える**。  
  - 「インタフェース側が“とりあえずこの動きにしておくね”」というイメージ。

- **privateメソッド（インタフェース内）**  
  - Java 9以降、インタフェースの中でも `private` メソッドを書ける。  
  - 目的：**複数のdefaultメソッドで共通の処理をまとめたいとき**。  
  - 例：  
    ```java
    interface Calculator {
        default int addAndDouble(int a, int b) {
            return doubleValue(add(a, b));
        }
        private int add(int a, int b) {
            return a + b;
        }
        private int doubleValue(int x) {
            return x * 2;
        }
    }
    ```
  - `private` なので **インタフェースの外からも、実装クラスからも呼べない**。  
    あくまで「インタフェースの中専用のヘルパー」。

- **`Object` 由来メソッド（`toString`, `equals`, `hashCode` など）**  
  - 全てのクラスの親は `Object`。  
  - そのため、**インタフェースに `toString()` などを書いても、「新しいメソッドを増やしている」わけではない**。  
    - どのクラスにも最初から `toString()` は存在するので、「`toString()` を“インタフェースとして約束し直している”だけ」という考え方。  
  - インタフェース側で `default` 実装を書くこともできるが、  
    実際の振る舞いは **クラスがオーバーライドした `toString()` などが使われる**。


---

### 2行目：問9  
**抽象クラス・抽象メソッドと、具象クラスでの実装・呼び出し関係**

- **抽象クラスとは**  
  - `abstract` が付いたクラス。**“完全な設計図ではなく、途中まで書いた設計図”** のイメージ。  
  - 直接 `new` できない（インスタンス化不可）。  
  - 例：  
    ```java
    abstract class Shape {
        abstract double area(); // 面積を求める方法は、具体的な図形に任せる
    }
    ```

- **抽象メソッドとは**  
  - 本体（処理）がなく、**「シグニチャだけ書いてあるメソッド」**。  
  - 例：`abstract double area();`  
  - 抽象メソッドを持つクラスは **必ず抽象クラス** になる。

- **具象クラスでの実装**  
  - 抽象クラスを継承した「普通のクラス（具象クラス）」は、  
    **抽象メソッドをすべてオーバーライドする義務がある**。  
  - 例：  
    ```java
    class Circle extends Shape {
        private double r;
        Circle(double r) { this.r = r; }

        @Override
        double area() {
            return Math.PI * r * r;
        }
    }
    ```

- **呼び出し関係（多態性）**  
  - 変数の型が抽象クラスでも、**中身が具象クラスなら、その具象クラスの実装が動く**。  
  - 例：  
    ```java
    Shape s = new Circle(2.0);
    System.out.println(s.area()); // Circleのarea()が呼ばれる
    ```
  - 「**親は“こんなメソッドがある”と約束だけして、具体的な動きは子が決める**」という関係。


---

### 3行目：問10・11  
**オーバーライドの条件（シグニチャ・戻り値型・アクセス修飾子）のおさらい**

- **オーバーライドの大前提**  
  - 親クラスにあるメソッドを、子クラスが **「同じ名前＋同じ引数リスト」で書き直すこと**。  
  - シグニチャ（名前＋引数の型・個数・順番）が **完全に同じ** でなければならない。  

- **戻り値型について**  
  - 原則：**親と同じ戻り値型**。  
  - ただし参照型の場合、**「親の戻り値型のサブクラス」に狭めること（共変戻り値）はOK**。  
    - 例：  
      ```java
      class Animal {}
      class Dog extends Animal {}

      class Parent {
          Animal create() { return new Animal(); }
      }
      class Child extends Parent {
          @Override
          Dog create() { return new Dog(); } // OK（Animalのサブクラス）
      }
      ```

- **アクセス修飾子のルール**  
  - **親より“狭く”してはいけない**。  
    - 親が `public` → 子も `public` のみ可（`protected` や `private` はNG）。  
    - 親が `protected` → 子は `protected` または `public` はOK、`private` はNG。  
  - 理由：親の型で見たときに、**見えていたメソッドが子で見えなくなると困るから**。

- **static / final との関係**  
  - `static` メソッドは **オーバーライドできない**（同名で“隠す”ことはできるが別物扱い）。  
  - `final` メソッドは **オーバーライド禁止**。


---

### 4行目：問12・19  
**親子で同名フィールドを持つときの参照ルール（フィールドは変数型で決まる）**

- **基本ルール**  
  - フィールド（インスタンス変数）は、**「変数の型」でどちらを見るかが決まる**。  
  - メソッドと違い、「中身の実際の型」では切り替わらない。

- **例**  
  ```java
  class Parent {
      String name = "parent";
  }
  class Child extends Parent {
      String name = "child";
  }

  Parent p = new Child();
  System.out.println(p.name); // "parent"
  ```
  - 変数 `p` の型は `Parent` なので、**Parentの`name`が参照される**。  
  - 実際の中身は `Child` でも、フィールドについては関係ない。

- **対比：メソッドは実オブジェクトの型で決まる**  
  ```java
  class Parent {
      String getName() { return "parent"; }
  }
  class Child extends Parent {
      @Override
      String getName() { return "child"; }
  }

  Parent p = new Child();
  System.out.println(p.getName()); // "child"
  ```
  - **メソッドは実オブジェクトの型（Child）に従う** が、  
    フィールドは **変数の型（Parent）に従う**。  
  - この違いをしっかり区別するのがポイント。


---

### 5行目：問13・14  
**ポリモーフィズムと「変数の型にないメソッドは呼べない」という制約**

- **ポリモーフィズム（多態性）**  
  - 「**親型の変数に、いろいろな子クラスのインスタンスを入れて使う**」こと。  
  - 例：  
    ```java
    Animal a = new Dog();
    a = new Cat();
    ```
  - これにより、「`Animal` として共通の操作（`move()`, `eat()` など）」を同じコードで扱える。

- **呼べるメソッドは“変数の型”で決まる**  
  - `Animal a = new Dog();` のとき、  
    - **`a` から呼べるのは「Animalに宣言されているメソッドだけ」**。  
    - `Dog` 固有メソッド `bark()` があっても、`a.bark()` はコンパイルエラー。  
  - なぜなら、コンパイラは **変数の型だけを見て「そんなメソッドは知らない」と判断する** から。

- **実際に動く中身は“実オブジェクトの型”**  
  - 例：  
    ```java
    Animal a = new Dog();
    a.move();  // Dog側でオーバーライドしていればDogのmove()が実行される
    ```
  - 「**呼べるかどうか**」は変数の型で決まり、  
    「**どの実装が動くか**」は実オブジェクトの型で決まる、という二段構え。


---

### 6行目：問15  
**インタフェースや無関係クラスを配列要素に入れるときの代入互換性**

- **代入互換性の基本**  
  - ある変数（または配列）に代入できるかどうかは、  
    **「その型に代入して安全か？」（型の親子関係・インタフェース実装関係）** で決まる。

- **インタフェース型の配列**  
  - 例：  
    ```java
    interface Drawable { void draw(); }

    class Circle implements Drawable { public void draw() {} }
    class Square implements Drawable { public void draw() {} }

    Drawable[] arr = new Drawable[2];
    arr[0] = new Circle(); // OK
    arr[1] = new Square(); // OK
    ```
  - `Drawable[]` の各要素には、**`Drawable` を実装しているクラスのインスタンスなら何でも入る**。

- **無関係クラスは入らない**  
  - 上の例で、`class Dog {}` のインスタンスは **`Drawable` を実装していなければ** `arr[0] = new Dog();` はコンパイルエラー。  
  - 「見た目が似ていても、**インタフェースの実装関係がなければ別物**」ということ。

- **クラスの継承関係の配列**  
  - `Parent[]` には `Parent` のサブクラス（`Child` など）は代入可能。  
  - ただし「配列の共変性（`Child[]` を `Parent[]` に代入できる）」には `ArrayStoreException` などの落とし穴があるが、  
    今はまず **“インタフェースを実装していればインタフェース型に入る”** を押さえればOK。


---

### 7行目：問16・17  
**アップキャスト／ダウンキャストと `ClassCastException` 発生パターン**

- **アップキャスト（上方向の型変換）**  
  - 子クラス → 親クラス への変換。**常に安全なので自動で行われる**。  
  - 例：  
    ```java
    Dog d = new Dog();
    Animal a = d; // アップキャスト（暗黙的）
    ```

- **ダウンキャスト（下方向の型変換）**  
  - 親クラス → 子クラス への変換。**危険があるので明示的キャスト必須**。  
  - 例：  
    ```java
    Animal a = new Dog();
    Dog d = (Dog) a; // ダウンキャスト
    ```
  - これは「`a` の中身が本当に `Dog` だから」安全。

- **`ClassCastException` が出るパターン**  
  - 実際の中身とキャスト先の型が合っていないときに実行時エラー。  
  - 例：  
    ```java
    Animal a = new Cat();
    Dog d = (Dog) a; // コンパイルは通るが、実行時にClassCastException
    ```
  - コンパイラは「`a` は `Animal` だから、`Dog` にキャストすること自体は文法上OK」と判断する。  
    しかし実行時に **中身が `Cat` だとわかって例外になる**。

- **防ぎ方：`instanceof`**  
  ```java
  if (a instanceof Dog) {
      Dog d = (Dog) a; // 安全
  }
  ```
  - キャスト前に **実際の型をチェック** するのが定番パターン。


---

### 8行目：問18  
**コンストラクタ引数とフィールド名が同じときの `this` の使い方**

- **よくあるパターン**  
  ```java
  class User {
      private String name;

      User(String name) {
          name = name; // ← これだと自分自身を代入しているだけ
      }
  }
  ```
  - 左側の `name`（フィールド）と右側の `name`（引数）が **同名** だと、  
    何も書かないと **「より近いスコープの変数（ここでは引数）」が優先される**。  
  - `name = name;` は「**引数nameに引数nameを代入**」しているだけで、フィールドが初期化されない。

- **`this` でフィールドを明示する**  
  ```java
  class User {
      private String name;

      User(String name) {
          this.name = name; // フィールドname = 引数name
      }
  }
  ```
  - `this.name` は **「このオブジェクトのフィールドname」** という意味。  
  - 引数と名前がかぶるときは、**フィールド側に `this.` を付ける習慣を付ける** とミスが減る。

- **`this` のイメージ**  
  - 「**自分自身のインスタンスを指す特別な変数**」。  
  - フィールドやメソッドを「このインスタンスのもの」と明示するときに使う。


---

### 9行目：問20・21  
**継承関係でのコンストラクタ呼び出し順序（親→子、`this` と `super` の流れ）**

- **呼び出し順序の大原則**  
  - インスタンス生成時、**必ず親クラスのコンストラクタが先に実行され、その後に子クラスのコンストラクタが実行される**。  
  - 親 → 子 の順番で、「上から土台を順に作っていく」イメージ。

- **`super(...)` の暗黙呼び出し**  
  - 子クラスのコンストラクタの **1行目で必ず親コンストラクタが呼ばれる**。  
  - 何も書かない場合、  
    ```java
    class Child extends Parent {
        Child() {  // ← コンパイラが自動的に super(); を挿入
        }
    }
    ```
  - 親に引数付きコンストラクタしかない場合は、**明示的に `super(引数...)` を書かないとコンパイルエラー** になる。

- **`this(...)` と `super(...)` のルール**  
  - どちらも **コンストラクタの先頭行でしか呼べない**。  
  - かつ **同時に両方は書けない**（1つのコンストラクタにつき、先頭で呼べるのは `this(...)` か `super(...)` どちらか1つ）。  
  - 例：  
    ```java
    class Child extends Parent {
        Child() {
            this(0); // OK
        }
        Child(int x) {
            super(x); // OK
        }
    }
    ```
  - この例だと実行順は  
    `new Child()` → `Child()` → `this(0)` → `Child(int)` → `super(x)` → `Parent(int)`  
    という流れになる。


---

### 10行目：問23  
**`final` を付けられる場所（クラス／メソッド／フィールド）の確認**

- **クラスに `final`**  
  - 例：`final class Utility { ... }`  
  - 意味：**そのクラスを継承できない**。  
  - 「これ以上派生クラスを作ってほしくない」「設計を固定したい」ときに使う。

- **メソッドに `final`**  
  - 例：  
    ```java
    class Parent {
        public final void log() { ... }
    }
    ```
  - 意味：**子クラスからオーバーライドできないメソッド**。  
  - 「この動きは絶対に変えてほしくない」メソッドに付ける。

- **フィールドに `final`**  
  - 例：`private final int maxSize;`  
  - 意味：**一度だけ値を代入できるフィールド**。  
    - 宣言と同時 or コンストラクタ内で代入して、それ以降は変更不可。  
  - 定数として使いたいときは、慣習的に  
    ```java
    public static final double PI = 3.14159;
    ```
    のように `static` と大文字名を組み合わせることが多い。

- **共通イメージ**  
  - `final` = 「**これ以上変えさせない・上書きさせない**」というブレーキ。


---

### 11行目：問24・25・26  
**sealedクラス／sealedインタフェースと `permits`、およびサブクラス側の `final` / `sealed` / `non-sealed` 必須ルール**

- **sealedクラス／sealedインタフェースとは**  
  - 「**継承できるクラス（実装できるクラス）を、あらかじめ限定しておく**」仕組み。  
  - 宣言例：  
    ```java
    public sealed class Shape
        permits Circle, Rectangle { }
    ```
  - `permits` で **「`Shape` を継承してよいクラス」を列挙** する。

- **`permits` のポイント**  
  - `permits` で列挙されたクラスは、  
    - 同じモジュール or 同じパッケージ内 など、一定の制約下にある必要がある。  
  - これにより、「**扱うパターンが限定された階層**」を作れる。  
    - 例：`Shape` が `Circle` と `Rectangle` しか持たないことがコンパイラレベルで保証されるので、  
      分岐処理で「その他」のケースを心配しなくて良くなる。

- **サブクラス側で必須の3択：`final` / `sealed` / `non-sealed`**  
  - `sealed` なクラスを継承したサブクラスは、**必ずいずれかを指定する**：  
    1. `final`  
       - そのクラス **以降の継承を禁止**。  
       - 例：`final class Circle extends Shape { ... }`  
    2. `sealed`  
       - 自分も sealed として、「さらにその先のサブクラスを限定」できる。  
       - 例：  
         ```java
         public sealed class Polygon extends Shape
             permits Triangle, Square { ... }
         ```
    3. `non-sealed`  
       - そこから先は **自由に継承して良い**。  
       - sealedな世界から「ここ以降は制限を外します」と宣言するイメージ。

- **sealedインタフェースも同様**  
  - クラスと同じ発想で、  
    ```java
    public sealed interface Command
        permits StartCommand, StopCommand { }
    ```
  - `StartCommand`, `StopCommand` 側も `final` / `sealed` / `non-sealed` のいずれかを付ける必要がある。

---

