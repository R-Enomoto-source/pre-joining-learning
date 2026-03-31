# **JavaのRecord（レコード）基礎解説**

## **1\. Recordとは何か？**

Javaのrecord（レコード）は、主に\*\*「変更されないデータ（不変データ）を保持して運ぶこと」を目的とした特別なクラス\*\*です。Java 14でプレビュー機能として登場し、**Java 16で正式に導入**されました（JEP 395）。

従来のJavaでは、単にデータを保持するだけのクラス（データキャリアクラス、またはPOJO）を作成する場合でも、多くの定型コード（ボイラープレートコード）を記述する必要がありました。recordを使用することで、これらの記述を大幅に省略し、クラスの意図（「これは単なるデータの集まりである」ということ）を明確にすることができます。

## **2\. 従来のクラスとの比較**

名前と年齢を持つPersonというデータを表現する場合を比較します。

### **従来のクラス（POJO）での実装例**

コンストラクタ、ゲッター、equals、hashCode、toStringなどを全て明示的に書く（またはIDEに生成させる）必要があります。

public class Person {  
    private final String name;  
    private final int age;

    public Person(String name, int age) {  
        this.name \= name;  
        this.age \= age;  
    }

    public String getName() { return name; }  
    public int getAge() { return age; }

    @Override  
    public boolean equals(Object o) { /\* 長いので省略 \*/ }  
    @Override  
    public int hashCode() { /\* 長いので省略 \*/ }  
    @Override  
    public String toString() { /\* 長いので省略 \*/ }  
}

### **Recordでの実装例**

recordを使うと、上記とほぼ同等の機能を持つクラスを**たった1行**で定義できます。

public record Person(String name, int age) {}

括弧の中に記述された変数（コンポーネントと呼びます）を基に、Javaコンパイラが必要なメソッドを自動的に生成します。

## **3\. 自動生成される要素**

recordを宣言すると、コンパイラは以下の要素を自動的に生成します。

1. **フィールド**: 各コンポーネントに対応する private final なインスタンスフィールド。  
2. **コンストラクタ**: 全てのフィールドを初期化する正規コンストラクタ（Canonical Constructor）。  
3. **アクセサメソッド**: フィールドの値を取得するメソッド。※命名規則は getName() ではなく、コンポーネント名と同じ **name()** になります。  
4. **equals(Object)**: 全てのフィールドの値が等しい場合に true を返すメソッド。  
5. **hashCode()**: 全てのフィールドの値を基にハッシュ値を計算するメソッド。  
6. **toString()**: クラス名と全てのフィールドの値を出力するメソッド（例: Person\[name=Alice, age=30\]）。

**利用例:**

Person p1 \= new Person("Alice", 30);  
Person p2 \= new Person("Alice", 30);

System.out.println(p1.name());     // "Alice" (アクセサメソッド)  
System.out.println(p1.toString()); // "Person\[name=Alice, age=30\]"  
System.out.println(p1.equals(p2)); // true (値ベースの比較)

## **4\. Recordの重要な仕様と制限**

recordは通常のクラスと似ていますが、データキャリアとしての純粋性を保つために、いくつかの明確な制限が設けられています。

* **不変性（Immutable）**:  
  * 全てのインスタンスフィールドは暗黙的に final となります。生成後に値を変更すること（セッターの作成など）はできません。  
* **継承の制限**:  
  * recordクラス自体は暗黙的に final であり、**他のクラスに継承させることはできません**。  
  * すべてのrecordは暗黙的に java.lang.Record クラスを継承するため、**他のクラス（親クラス）を継承（extends）することはできません**。  
  * ※ インターフェースの実装（implements）は可能です。  
* **フィールドの追加制限**:  
  * レコードの宣言時（ヘッダの括弧内）に定義したコンポーネント以外の**インスタンスフィールドを新たに追加することはできません**。  
  * ※ static フィールド（クラス変数）を追加することは可能です。

## **5\. Recordのカスタマイズ**

自動生成される振る舞いに手を加えたい場合、カスタマイズが可能です。

### **5.1. コンパクト・コンストラクタ**

コンストラクタ内で値の検証（バリデーション）などを行いたい場合、「コンパクト・コンストラクタ」という簡略化された構文を使用できます。引数の宣言や、フィールドへの代入記述（this.name \= name; など）を省略できます。

public record Person(String name, int age) {  
    // コンパクト・コンストラクタ  
    public Person {  
        if (age \< 0\) {  
            throw new IllegalArgumentException("年齢は0以上である必要があります");  
        }  
        if (name \== null || name.isBlank()) {  
            throw new IllegalArgumentException("名前は必須です");  
        }  
        // this.name \= name; や this.age \= age; はコンパイラが自動で追加します  
    }  
}

### **5.2. メソッドの追加とオーバーライド**

通常のクラスと同様に、独自のインスタンスメソッドや静的メソッドを追加したり、自動生成されるメソッド（toStringなど）をオーバーライド（上書き）したりすることができます。

public record Person(String name, int age) {  
    // 独自のメソッドを追加  
    public boolean isAdult() {  
        return age \>= 18;  
    }

    // toString()をカスタマイズ  
    @Override  
    public String toString() {  
        return name \+ "さん (" \+ age \+ "歳)";  
    }  
}

## **6\. まとめと使い所**

**利用が推奨されるケース:**

* データベースからの取得結果（DTO: Data Transfer Object）  
* APIのレスポンスやリクエストデータ  
* メソッドから複数の値を返したい場合の戻り値用の型  
* 設定値の保持

**利用すべきではないケース:**

* 状態が変化する（ミュータブルな）オブジェクトを表現したい場合。  
* クラスの継承ツリー（階層構造）に組み込む必要がある場合。

recordを活用することで、Javaのコードはより簡潔になり、「データそのもの」に焦点を当てた可読性の高いプログラミングが可能になります。