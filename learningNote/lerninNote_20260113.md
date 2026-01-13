# Java実行エラーと可変長引数について - 詳細まとめ

## 1. 発生したエラー

### エラーメッセージ
```
エラー: メイン・クラスMain.javaを検出およびロードできませんでした
原因: java.lang.ClassNotFoundException: Main.java
```

### エラーの原因

**誤った実行方法：**
```bash
java Main.java
```

このエラーは、Javaが`Main.java`という名前のクラスを探そうとしたことが原因です。

## 2. Javaプログラムの実行方法

### 2.1 従来の方法（すべてのJavaバージョンで利用可能）

**ステップ1：コンパイル**
```bash
javac Main.java
```
→ `Main.class`ファイルが生成される

**ステップ2：実行**
```bash
java Main
```
→ 拡張子（`.java`や`.class`）を付けず、クラス名のみを指定

**ポイント：**
- `javac` = コンパイラ（`.java` → `.class`に変換）
- `java` = 実行環境（`.class`ファイルを実行）
- `java`コマンドでは拡張子を付けない

### 2.2 単一ファイルソースプログラム（Java SE 11以降）

Java SE 11以降では、`javac`を使わずに直接実行できます。

**環境確認：**
```bash
java -version
```
- ユーザーの環境：OpenJDK 25.0.1（Java SE 11以降の機能を使用可能）

**実行方法：**
```bash
java Main.java
```
→ `.java`拡張子を付けてファイル名を指定

**重要な注意点：**
- 正しいディレクトリにいる必要がある
- ファイルが存在するディレクトリで実行する

**解決方法：**
```bash
# 方法1：該当ディレクトリに移動してから実行（推奨）
cd sukkiri-Java-reference-code\chap00\code00-01
java Main.java

# 方法2：ルートディレクトリから相対パスを指定
java sukkiri-Java-reference-code\chap00\code00-01\Main.java
```

## 3. `String[] args` と `String... args` の違い

### 3.1 基本的な違い

Javaの`main`メソッドでは、2つの書き方が可能です：

**配列形式：**
```java
public static void main(String[] args)
```

**可変長引数形式：**
```java
public static void main(String... args)
```

### 3.2 `main`メソッドでの動作

**重要なポイント：**
- `main`メソッドでは、どちらも**機能的に同等**に動作する
- Javaの仕様で、`main`メソッドの引数として両方が許可されている
- 内部的には配列として扱われる

### 3.3 構文の違い

| 項目 | `String[] args` | `String... args` |
|------|----------------|------------------|
| **構文** | 配列の角括弧 `[]` | 3つのドット `...` |
| **意味** | 文字列の配列 | 可変長引数（varargs） |
| **mainメソッド** | ✅ 使用可能 | ✅ 使用可能 |

### 3.4 可変長引数（`String...`）とは

**定義：**
- `String... args`は、0個以上の`String`引数を受け取れることを示す
- 内部的には`String[]`として扱われる
- "可変長引数"または"varargs"と呼ばれる

**実行例：**
```bash
java Sample.java a b c
```

この場合：
- `args`は`String[]`型
- `args[0] = "a"`
- `args[1] = "b"`
- `args[2] = "c"`

### 3.5 通常のメソッドでの違い

**配列形式 `String[]` の使い方：**
```java
public void method(String[] args) {
    // 呼び出し側で配列を作成する必要がある
}

// 呼び出し
method(new String[]{"a", "b", "c"});
method(new String[0]); // 空の配列
```

**可変長引数形式 `String...` の使い方：**
```java
public void method(String... args) {
    // 呼び出し側で複数の引数を直接渡せる
}

// 呼び出し
method("a", "b", "c");  // 複数の引数を直接渡せる
method("a");             // 1つだけでもOK
method();                // 引数なしでもOK（空の配列として扱われる）
method(new String[]{"a", "b"}); // 配列でも渡せる（互換性あり）
```

### 3.6 実例コード

**配列形式の例：**
```java
public class Test1 {
    public static void printItems(String[] items) {
        for (String item : items) {
            System.out.println(item);
        }
    }
    
    public static void main(String[] args) {
        String[] fruits = {"りんご", "バナナ", "みかん"};
        printItems(fruits);
        printItems(new String[]{"コーヒー", "紅茶"});
    }
}
```

**可変長引数形式の例：**
```java
public class Test2 {
    public static void printItems(String... items) {
        for (String item : items) {
            System.out.println(item);
        }
    }
    
    public static void main(String[] args) {
        printItems("りんご", "バナナ", "みかん");
        printItems("コーヒー", "紅茶");
        printItems("パン");  // 1つだけでもOK
        printItems();        // 何も渡さなくてもOK
    }
}
```

### 3.7 可変長引数の制約

**重要な制約：**
- 可変長引数は**最後のパラメータ**でしか使用できない

```java
// ✅ OK：最後のパラメータ
public void method(String name, int... numbers) { }

// ❌ エラー：最後ではない
public void method(int... numbers, String name) { }
```

## 4. 実際の使い分け

### 4.1 `main`メソッドでの使用状況

| 形式 | 使用頻度 | 理由 |
|------|---------|------|
| `String[] args` | **圧倒的に多い** | 標準的、慣習的、歴史的経緯 |
| `String... args` | 少ない | 機能的には同等だが、慣習に合わない |

**理由：**
- Java 1.0から`String[]`が標準
- 可変長引数はJava 5（2004年）で追加された機能
- 多くの教材やコード例が`String[]`を使用
- 標準ライブラリの`main`メソッドも`String[]`を使用

### 4.2 推奨される使い分け

**`main`メソッド：**
```java
// 慣習的に配列形式を使用
public static void main(String[] args) {
    // ...
}
```

**通常のメソッド：**
```java
// 可変長引数で柔軟性と可読性を向上
public static void processItems(String... items) {
    // 呼び出し側が便利
    processItems("A", "B", "C");
}
```

### 4.3 可変長引数を使うべき場合

1. **引数の数が可変で、呼び出し側で値を直接並べたい場合**
2. **メソッドの柔軟性を高めたい場合**
3. **APIの使いやすさを重視する場合**

**良い例：**
```java
// ログを出力するメソッド
public static void log(String... messages) {
    for (String msg : messages) {
        System.out.println(msg);
    }
}

// 使い方：とても読みやすい
log("エラーが発生しました");
log("ユーザー名:", userName);
log("処理開始", "データ読み込み中", "完了");
```

```java
// 数値の合計を計算するメソッド
public static int sum(int... numbers) {
    int total = 0;
    for (int n : numbers) {
        total += n;
    }
    return total;
}

// 使い方：簡潔で読みやすい
int result1 = sum(1, 2, 3, 4, 5);
int result2 = sum(10, 20);
```

### 4.4 配列を使うべき場合

1. **`main`メソッド**（慣習）
2. **既存の配列をそのまま渡す場合**
3. **型の明確性を重視する場合**
4. **後方互換性を保つ必要がある場合**

## 5. 実践的なアドバイス

### 学習・試験
- `main`メソッドは`String[] args`を使う（標準的）
- それ以外のメソッドで可変長が適切な場合は`String...`を検討

### 実務
- チームのコーディング規約に従う
- 既存コードとの一貫性を保つ
- 可変長引数が適切な場面では積極的に使う

### 学習の進め方
- まずは`String[] args`を基本として理解する
- 可変長引数は経験を積みながら理解を深める
- 「このメソッド、引数の数を変えたい」と思ったときに可変長引数を検討する

## 6. まとめ

### 重要なポイント

1. **Javaプログラムの実行方法**
   - 従来の方法：`javac Main.java` → `java Main`
   - Java SE 11以降：`java Main.java`（単一ファイルソースプログラム）
   - 正しいディレクトリで実行することが重要

2. **`String[] args` と `String... args`**
   - `main`メソッドでは、どちらも機能的に同等
   - `String[]`が標準的で慣習的
   - `String...`は可変長引数（varargs）

3. **使い分け**
   - `main`メソッド：`String[] args`を使用（慣習）
   - 通常のメソッド：適切な場合に`String...`を使用

4. **学習方針**
   - 基本は`String[]`を理解する
   - 可変長引数は経験を積みながら習得する

## 7. 参考情報

### 使用したコード例

**基本的なMain.java：**
```java
public class Main {
  public static void main(String[] args) {
    System.out.println("Hello World");
  }
}
```

**コマンドライン引数を処理するコード：**
```java
public class Sample {
    public static void main(String... args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
```

### 環境情報
- Javaバージョン：OpenJDK 25.0.1
- 単一ファイルソースプログラム機能：使用可能（Java SE 11以降）

---

このまとめは、Javaプログラムの実行エラーから始まり、`String[] args`と`String... args`の違い、そして実際の使い分けまでを網羅しています。