# LearningNote 2026-02-25

## セッションログ（ユーザー入力＋回答）

### Javaレコードの基本と目的
ユーザー: 
Javaのレコードという機能について、初学者にも分かりやすく正確に教えてください

アシスタント:
はい、その理解で合っています。

## Javaのレコード（record）とは

Java 16で正式に導入された**レコード**は、「データを保持するだけのクラス」を簡潔に書くための機能です。

---

### 1. なぜレコードがあるか

次のような「名前・年齢・メールアドレスを持つ」クラスを考えます。

従来のクラスだと、フィールド、コンストラクタ、getter、`equals`/`hashCode`/`toString` をたくさん書く必要があります。

```java
public class Person {
    private final String name;
    private final int age;
    private final String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String name() { return name; }
    public int age() { return age; }
    public String email() { return email; }

    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }
    @Override
    public String toString() { /* ... */ }
}
```

レコードを使うと、**同じ意味のクラスを1行で宣言**できます。

```java
public record Person(String name, int age, String email) {}
```

---

### 2. レコードで自動的に用意されるもの

`record Person(String name, int age, String email) {}` と書くだけで、コンパイラが以下を自動生成します。

| 内容 | 説明 |
|------|------|
| **private final フィールド** | `name`, `age`, `email` が自動で作られる |
| **正規コンストラクタ** | 全コンポーネントを受け取るコンストラクタ |
| **アクセサメソッド** | `name()`, `age()`, `email()`（getter だが名前は「フィールド名」そのまま） |
| **equals / hashCode** | 全コンポーネントを使って比較・ハッシュ |
| **toString()** | 例: `Person[name=太郎, age=20, email=...]` のような文字列 |

つまり「データの入れ物＋等価性・文字列化」がまとめて手に入ります。

---

### 3. 基本的な使い方

```java
// 宣言
public record Person(String name, int age, String email) {}

// インスタンス作成（new）
Person p = new Person("山田太郎", 25, "yamada@example.com");

// アクセサ（getter の代わり。メソッド名はフィールド名と同じ）
String n = p.name();   // "山田太郎"
int a = p.age();       // 25
String e = p.email();  // "yamada@example.com"

// equals / hashCode / toString も使える
Person p2 = new Person("山田太郎", 25, "yamada@example.com");
System.out.println(p.equals(p2));  // true
System.out.println(p);             // Person[name=山田太郎, age=25, email=yamada@example.com]
```

---

### 4. 重要な性質（「不変」と「データの入れ物」）

- **不変（immutable）**
  - 各コンポーネントは `final` なので、一度作ったら中身は変えられません。
  - マルチスレッドや共有データで扱いやすいです。

- **主目的は「データの保持」**
  - レコードは「何かのデータをまとめて持つ」用途向けです。
  - 継承はできません（`extends` 不可）。`implements` でインターフェースを実装するのは可能です。

---

### 5. カスタマイズの例

必要なら、コンストラクタやメソッドを追加できます。

```java
public record Person(String name, int age, String email) {
    // コンパクトコンストラクタ（全フィールドに共通のチェックなどを書く）
    public Person {
        if (age < 0) throw new IllegalArgumentException("年齢は0以上");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("不正なメールアドレス");
    }

    // 便利メソッドの追加
    public boolean isAdult() {
        return age >= 20;
    }
}
```

「コンパクトコンストラクタ」では、引数名の後ろに `{}` だけを書きます。引数の受け渡しはコンパイラが自動で行うので、`this.name = name;` のような代入は不要です。

---

### 6. いつレコードを使うか

- **DTO（データ転送オブジェクト）** … API のリクエスト/レスポンスなど
- **キーや値の組** … マップのキー、複数値をまとめる場合
- **設定やパラメータのまとまり** … オプションを1つの型で表現するとき

「状態を隠して振る舞いをたくさん持たせたい」クラスは通常のクラス、**「データをまとめて持ちたいだけ」**の型はレコード、と考えると分かりやすいです。

---

### 7. まとめ

- **レコード** = コンポーネント（`name`, `age`, `email` など）を宣言するだけで、フィールド・コンストラクタ・アクセサ・`equals`/`hashCode`/`toString` が自動でできる**不変のデータ用クラス**。
- 書く量が少なく、意図（「これはデータの入れ物である」）が明確になるので、初学者のうちから「データをまとめるときは record を検討する」と覚えておくとよいです。

### Javaの定数とレコードのフィールド名
ユーザー:
定数は大文字と_で書くのではないですか？

アシスタント:
はい、その理解で合っています。

## Javaの命名ルール

| 種類 | ルール | 例 |
|------|--------|-----|
| **定数**（`static final` で不変の値） | **大文字 + アンダースコア**（UPPER_SNAKE_CASE） | `MAX_SIZE`, `DEFAULT_TIMEOUT`, `PI` |
| 変数・フィールド・メソッド | キャメルケース（camelCase） | `name`, `age`, `getUserName()` |

定数は「大文字と `_`」で書く、という慣習で問題ありません。

---

## レコードの `name`, `age`, `email` について

先ほどのレコードの例では:

```java
public record Person(String name, int age, String email) {}
```

の **`name`, `age`, `email` は定数ではなく、「各インスタンスが持つデータ（フィールド）の名前」** なので、  
変数・フィールドと同じく **camelCase** で書くのが正しいです。

定数にするなら、例えば「最大年齢」のような**固定値**を表すときです:

```java
public record Person(String name, int age, String email) {
    public static final int MAX_AGE = 150;  // ← 定数なので大文字 + _
    
    public boolean isAdult() {
        return age >= 20;
    }
}
```

まとめると:

- **定数**（不変の固定値）→ **大文字 + アンダースコア**（`MAX_AGE`, `DEFAULT_NAME` など）
- **レコードのコンポーネントや普通のフィールド**（インスタンスごとに変わる値）→ **camelCase**（`name`, `age`, `email` など）

という使い分けになります。

