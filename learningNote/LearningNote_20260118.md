# LearningNote 2026-01-18

## セッションログ（ユーザー入力＋回答）

### Randomクラスで範囲を指定したランダム整数を生成する方法

ユーザー: Javaで、0～N未満や1～N、あるいはmin～maxのような任意の範囲のランダムな整数を生成するにはどう書けばいいですか？練習問題のprayメソッドでは0～2を使いたいです。

アシスタント:

`Random` クラスの `nextInt(上限)` を使います。範囲ごとの書き方と公式は次のとおりです。

#### 基本的な方法：Randomクラスを使用

**方法1：0から指定した数値未満のランダムな整数**

`nextInt(上限)` は「0以上、上限未満」の整数を返します。

- `nextInt(10)` → 0～9
- `nextInt(5)` → 0～4
- `nextInt(3)` → 0～2（0, 1, 2）

**方法2：1から指定した数値まで**

`nextInt(上限) + 1` で「1以上、上限以下」にできます。

- `nextInt(10) + 1` → 1～10
- `nextInt(6) + 1` → 1～6（サイコロ）
- `nextInt(100) + 1` → 1～100

**方法3：min以上max以下（任意の範囲）**

公式は **`nextInt(max - min + 1) + min`** です。

例：5～10の整数

- `nextInt(10 - 5 + 1) + 5` = `nextInt(6) + 5` → 5, 6, 7, 8, 9, 10

#### 公式のまとめ

1. **0以上N未満**  
   `random.nextInt(N)`  
   例：`nextInt(10)` → 0～9

2. **1以上N以下**  
   `random.nextInt(N) + 1`  
   例：`nextInt(6) + 1` → 1～6

3. **min以上max以下**  
   `random.nextInt(max - min + 1) + min`  
   例：`nextInt(10 - 5 + 1) + 5` → 5～10

4. **毎回新しいRandomオブジェクトで**  
   `new java.util.Random().nextInt(N)`  
   例：`new java.util.Random().nextInt(3)` → 0～2

#### prayメソッド用の例（0～2）

0～2（0, 1, 2）なら `nextInt(3)` です。

```java
int randomValue = random.nextInt(3);  // 0, 1, 2のいずれか
```

祈った秒数に 0～2 の補正を加える例：

```java
int second = 3;  // 祈る秒数（例）
int randomBonus = new java.util.Random().nextInt(3);  // 0, 1, 2のいずれか
int recover = second + randomBonus;  // second=3のとき、recoverは3, 4, 5のいずれか
```

`RandomNumberExample.java` の全体は次のとおりです。

```java
package Chpter8;

/**
 * Javaで特定の範囲のランダムな数値を生成する方法の例
 */
public class RandomNumberExample {
    
    // ============================================
    // 基本的な方法：Randomクラスを使用
    // ============================================
    
    /**
     * 【方法1】0から指定した数値未満のランダムな整数を生成
     * 
     * RandomクラスのnextInt(上限)メソッドを使用
     * 
     * 例：nextInt(10) → 0以上10未満（0~9）のランダムな整数
     */
    public void example1() {
        java.util.Random random = new java.util.Random();
        
        int num1 = random.nextInt(10);  // 0~9のランダムな整数
        int num2 = random.nextInt(5);   // 0~4のランダムな整数
        int num3 = random.nextInt(3);   // 0~2のランダムな整数（0, 1, 2のいずれか）
        System.out.println("num1=" + num1 + ", num2=" + num2 + ", num3=" + num3);
    }
    
    
    /**
     * 【方法2】1から指定した数値までのランダムな整数を生成
     * 
     * nextInt(上限) + 1 を使用
     */
    public void example2() {
        java.util.Random random = new java.util.Random();
        
        int num1 = random.nextInt(10) + 1;  // 1~10のランダムな整数
        int num2 = random.nextInt(6) + 1;   // 1~6のランダムな整数（サイコロ）
        int num3 = random.nextInt(100) + 1; // 1~100のランダムな整数
        System.out.println("num1=" + num1 + ", num2=" + num2 + ", num3=" + num3);
    }
    
    
    /**
     * 【方法3】任意の範囲（min以上max以下）のランダムな整数を生成
     * 
     * nextInt(上限 - 下限 + 1) + 下限 の公式を使用
     * 
     * 例：5~10のランダムな整数
     *    nextInt(10 - 5 + 1) + 5
     *    = nextInt(6) + 5
     *    → 5, 6, 7, 8, 9, 10のいずれか
     */
    public void example3() {
        java.util.Random random = new java.util.Random();
        
        // 5~10のランダムな整数
        int num1 = random.nextInt(10 - 5 + 1) + 5;
        // または
        int num2 = random.nextInt(6) + 5;
        
        // 10~20のランダムな整数
        int num3 = random.nextInt(20 - 10 + 1) + 10;
        // または
        int num4 = random.nextInt(11) + 10;
        System.out.println("5~10: num1=" + num1 + ", num2=" + num2 + " / 10~20: num3=" + num3 + ", num4=" + num4);
    }
    
    
    // ============================================
    // よく使う具体例
    // ============================================
    
    /**
     * 【例1】0~2のランダムな整数（0, 1, 2のいずれか）
     * 
     * これは練習問題のprayメソッドで使う例です
     */
    public void exampleForPray() {
        java.util.Random random = new java.util.Random();
        
        int randomValue = random.nextInt(3);  // 0, 1, 2のいずれか
        System.out.println("randomValue=" + randomValue);
    }
    
    
    /**
     * 【例2】1~6のランダムな整数（サイコロ）
     */
    public void exampleDice() {
        java.util.Random random = new java.util.Random();
        
        int dice = random.nextInt(6) + 1;  // 1, 2, 3, 4, 5, 6のいずれか
        System.out.println("dice=" + dice);
    }
    
    
    /**
     * 【例3】毎回新しいRandomオブジェクトを作成する方法
     * 
     * メソッド内で毎回作成する場合（簡潔な書き方）
     */
    public void exampleSimple() {
        // new java.util.Random().nextInt(上限) で毎回作成
        int num1 = new java.util.Random().nextInt(10);  // 0~9
        int num2 = new java.util.Random().nextInt(3);   // 0~2
        System.out.println("num1=" + num1 + ", num2=" + num2);
    }
    
    
    // ============================================
    // 公式のまとめ
    // ============================================
    
    /*
     * 【公式まとめ】
     * 
     * 1. 0以上N未満のランダムな整数
     *    random.nextInt(N)
     *    例：nextInt(10) → 0~9
     * 
     * 2. 1以上N以下のランダムな整数
     *    random.nextInt(N) + 1
     *    例：nextInt(6) + 1 → 1~6
     * 
     * 3. min以上max以下のランダムな整数
     *    random.nextInt(max - min + 1) + min
     *    例：nextInt(10 - 5 + 1) + 5 → 5~10
     * 
     * 4. 毎回新しいRandomオブジェクトを作成
     *    new java.util.Random().nextInt(N)
     *    例：new java.util.Random().nextInt(3) → 0~2
     */
    
    
    // ============================================
    // 練習問題への応用例
    // ============================================
    
    /**
     * 【応用例】prayメソッドで使う場合
     * 
     * 祈った秒数 + ランダムで0~2ポイントの補正
     */
    public void exampleForPrayMethod() {
        int second = 3;  // 祈る秒数（例）
        
        // ランダムで0~2の補正値を生成
        int randomBonus = new java.util.Random().nextInt(3);  // 0, 1, 2のいずれか
        
        // 回復量 = 祈った秒数 + ランダム補正
        int recover = second + randomBonus;
        
        // 結果：second=3の場合、recoverは3, 4, 5のいずれかになる
        System.out.println("recover=" + recover);
    }
    
    
    // ============================================
    // 動作確認用のメインメソッド
    // ============================================
    
    public static void main(String[] args) {
        java.util.Random random = new java.util.Random();
        
        System.out.println("=== 0~2のランダムな整数を10回生成 ===");
        for (int i = 0; i < 10; i++) {
            int value = random.nextInt(3);  // 0~2
            System.out.println((i + 1) + "回目: " + value);
        }
        
        System.out.println("\n=== 1~6のランダムな整数（サイコロ）を5回 ===");
        for (int i = 0; i < 5; i++) {
            int dice = random.nextInt(6) + 1;  // 1~6
            System.out.println((i + 1) + "回目: " + dice);
        }
        
        System.out.println("\n=== 5~10のランダムな整数を5回 ===");
        for (int i = 0; i < 5; i++) {
            int value = random.nextInt(6) + 5;  // 5~10
            System.out.println((i + 1) + "回目: " + value);
        }
        
        System.out.println("\n=== 毎回新しいRandomオブジェクトを作成 ===");
        for (int i = 0; i < 5; i++) {
            int value = new java.util.Random().nextInt(3);  // 0~2
            System.out.println((i + 1) + "回目: " + value);
        }
    }
}
```

---


### 「This method must return a result of type int」エラーの原因と対処

ユーザー: 「This method must return a result of type int」というエラーが出ました。戻り値の型を指定しているのに return を書き忘れたようです。どう直せばいいですか？

アシスタント:

このエラーは、**戻り値の型を宣言しているのに、return で値を返していない**ときに出ます。  
「void 以外の型」を返すメソッドでは、**すべての実行経路で必ず return する**必要があります。

#### エラーになる例

戻り値の型を指定しているのに、return 文が無いとコンパイルエラーになります。

```java
/*
public int getNumber() {
    // return文がない → エラー！
}
*/
```

#### 正しい書き方の例

**例1：必ず return する**

```java
public int getNumber() {
    return 10;  // return文で値を返す
}
```

**例2：if / else の両方で return する**

```java
public int getNumberWithCondition(int value) {
    if (value > 0) {
        return value;  // この経路でreturn
    } else {
        return 0;      // この経路でもreturn（必須！）
    }
}
```

**例3：一度変数に入れてから return してもよい**

```java
public int calculate(int a, int b) {
    int result = a + b;  // 計算結果を変数に保存
    return result;       // 変数の値を返す
}
```

#### void との違い

- **void メソッド**  
  何も返さないので、return は省略可能。  
  `return;` だけ書く（値を返さない）こともできます。

- **戻り値があるメソッド（int など）**  
  必ず `return 値;` が必要。  
  どこかの経路で return が無いと「This method must return a result of type int」になります。

`ReturnErrorExplanation.java` の全体です。

```java
package Chpter8;

/**
 * エラー「This method must return a result of type int」の説明
 * 
 * このエラーは、戻り値があるメソッドでreturn文が欠けている場合に発生します。
 */
public class ReturnErrorExplanation {
    
    // ============================================
    // ❌ エラーが発生する例
    // ============================================
    
    /**
     * 【エラー例】戻り値の型を指定しているのに、return文がない
     * このメソッドはコンパイルエラーになります
     */
    /*
    public int getNumber() {
        // return文がない → エラー！
    }
    */
    
    
    // ============================================
    // ✅ 正しい書き方の例
    // ============================================
    
    /**
     * 【正しい例1】必ずreturn文で値を返す
     */
    public int getNumber() {
        return 10;  // return文で値を返す
    }
    
    /**
     * 【正しい例2】条件分岐がある場合も、全ての経路でreturnが必要
     */
    public int getNumberWithCondition(int value) {
        if (value > 0) {
            return value;  // この経路でreturn
        } else {
            return 0;      // この経路でもreturn（必須！）
        }
    }
    
    /**
     * 【正しい例3】変数に保存してからreturnしてもOK
     */
    public int calculate(int a, int b) {
        int result = a + b;  // 計算結果を変数に保存
        return result;       // 変数の値を返す
    }
    
    
    // ============================================
    // voidメソッドとの違い
    // ============================================
    
    /**
     * 【voidメソッド】戻り値がないので、return文は不要
     * これはエラーにならない
     */
    public void printMessage(String message) {
        System.out.println(message);
        // return文は不要（何も返さないから）
    }
    
    /**
     * 【戻り値があるメソッド】必ずreturn文が必要
     * return文がないとエラーになる
     */
    public int getValue() {
        // 何か処理...
        return 42;  // このreturn文がないとエラー！
    }
}
```

---


### 戻り値があるメソッドの書き方と int / String / boolean の例

ユーザー: 戻り値があるメソッドの基本的な書き方と、int・String・boolean を返す例、void との違いを知りたいです。

アシスタント:

#### 基本の形

```
アクセス修飾子 戻り値の型 メソッド名(引数) {
    処理;
    return 戻り値;
}
```

- `void` ではなく、`int`・`String`・`boolean` など具体的な型を書く  
- 必ず `return 戻り値;` で返す  
- `return` で返す値の型は、メソッドの「戻り値の型」と一致させる  

#### 例1：int を返す（数値計算）

```java
public int calculateSum(int a, int b) {
    int result = a + b;
    return result;
}

// 簡潔な書き方
public int calculateSumSimple(int a, int b) {
    return a + b;
}
```

#### 例2：String を返す

```java
public String createGreeting(String name) {
    String greeting = "こんにちは、" + name + "さん！";
    return greeting;
}

// 簡潔な書き方
public String createGreetingSimple(String name) {
    return "こんにちは、" + name + "さん！";
}
```

#### 例3：boolean を返す

```java
public boolean isPositive(int number) {
    if (number > 0) {
        return true;
    } else {
        return false;
    }
}

// 簡潔な書き方（条件式の結果をそのまま返す）
public boolean isPositiveSimple(int number) {
    return number > 0;
}
```

#### 例4：条件によって違う値を返す

```java
// 点数に応じて A, B, C
public String getGrade(int score) {
    if (score >= 80) {
        return "A";
    } else if (score >= 60) {
        return "B";
    } else {
        return "C";
    }
}

// 2つの数の最大値
public int getMax(int a, int b) {
    if (a > b) {
        return a;
    } else {
        return b;
    }
}
```

#### 例5：void との違い

- **void**：戻り値なし。return は不要。`return;` のみ可。  
- **戻り値あり**：すべての経路で `return 値;` が必要。

```java
// void：戻り値なし
public void printMessage(String message) {
    System.out.println(message);
}

// 戻り値あり：必ず return
public int calculate(int value) {
    if (value < 0) {
        return 0;
    }
    return value * 2;  // このreturnがないとコンパイルエラー
}
```

#### 例6：呼び出し方

```java
// 変数に代入
int sum = example.calculateSum(5, 3);

// そのまま利用
System.out.println(example.createGreeting("花子"));

// 条件分岐
if (example.isPositive(10)) { ... }

// 別メソッドの引数
int total = example.calculateSum(example.calculateSum(2, 3), example.calculateSum(4, 6));

// 計算に使用
int max = example.getMax(7, 12);
String grade = example.getGrade(85);
```

`ReturnValueExample.java` の全体です。

```java
package Chpter8;

/**
 * 戻り値を含むメソッドの基本的な書き方 - 具体例集
 */
public class ReturnValueExample {
    
    // ============================================
    // 【基本の基本】戻り値があるメソッドの構造
    // ============================================
    
    /*
     * 戻り値があるメソッドの書き方：
     * 
     * アクセス修飾子 戻り値の型 メソッド名(引数) {
     *     処理;
     *     return 戻り値;
     * }
     * 
     * 重要なポイント：
     * 1. voidではなく、intやStringなど具体的な型を指定
     * 2. 必ずreturn文で値を返す
     * 3. returnで返す値の型は、メソッドの戻り値の型と一致する必要がある
     */
    
    
    // ============================================
    // 例1: シンプルな数値計算メソッド（int型を返す）
    // ============================================
    
    /**
     * 2つの数の合計を計算して返す
     */
    public int calculateSum(int a, int b) {
        int result = a + b;  // 計算する
        return result;       // 計算結果を返す
    }
    
    /**
     * より簡潔な書き方（変数を使わず直接return）
     */
    public int calculateSumSimple(int a, int b) {
        return a + b;  // 計算結果を直接返す
    }
    
    
    // ============================================
    // 例2: 文字列を返すメソッド（String型を返す）
    // ============================================
    
    /**
     * 名前を受け取って、挨拶文を作成して返す
     */
    public String createGreeting(String name) {
        String greeting = "こんにちは、" + name + "さん！";
        return greeting;
    }
    
    /**
     * より簡潔な書き方
     */
    public String createGreetingSimple(String name) {
        return "こんにちは、" + name + "さん！";
    }
    
    
    // ============================================
    // 例3: 真偽値を返すメソッド（boolean型を返す）
    // ============================================
    
    /**
     * 数値が正の数かどうかを判定する
     */
    public boolean isPositive(int number) {
        if (number > 0) {
            return true;   // 正の数の場合、trueを返す
        } else {
            return false;  // 正の数でない場合、falseを返す
        }
    }
    
    /**
     * より簡潔な書き方（条件式の結果を直接返す）
     */
    public boolean isPositiveSimple(int number) {
        return number > 0;  // 条件式の結果（trueまたはfalse）を直接返す
    }
    
    
    // ============================================
    // 例4: 条件によって異なる値を返すメソッド
    // ============================================
    
    /**
     * 点数に応じて評価を返す（A, B, C）
     */
    public String getGrade(int score) {
        if (score >= 80) {
            return "A";      // 80点以上なら"A"を返す
        } else if (score >= 60) {
            return "B";      // 60点以上なら"B"を返す
        } else {
            return "C";      // それ以外なら"C"を返す
        }
    }
    
    /**
     * 数値の最大値を返す
     */
    public int getMax(int a, int b) {
        if (a > b) {
            return a;  // aの方が大きい場合、aを返す
        } else {
            return b;  // bの方が大きいか同じ場合、bを返す
        }
    }
    
    
    // ============================================
    // 例5: voidメソッドとの違い（比較用）
    // ============================================
    
    /**
     * voidメソッド：戻り値がない（何も返さない）
     * コンソールに出力するだけ
     */
    public void printMessage(String message) {
        System.out.println(message);
        // return文は不要（何も返さない）
        // もし書くなら return; のみ（値は返さない）
    }
    
    /**
     * 戻り値があるメソッド：必ず値を返す必要がある
     * エラーチェック：全ての経路でreturn文が必要
     */
    public int calculate(int value) {
        if (value < 0) {
            return 0;  // 負の数の場合は0を返す
        }
        // 正の数の場合は計算結果を返す
        return value * 2;  // このreturn文がないとコンパイルエラー
    }
    
    
    // ============================================
    // 例6: 実際の使い方の例（テスト用メインメソッド）
    // ============================================
    
    public static void main(String[] args) {
        ReturnValueExample example = new ReturnValueExample();
        
        // 【使い方1】戻り値を変数に保存して使用
        int sum = example.calculateSum(5, 3);
        System.out.println("合計: " + sum);  // 出力: 合計: 8
        
        // 【使い方2】戻り値を直接使用（変数に保存しない）
        System.out.println("挨拶: " + example.createGreeting("花子"));
        // 出力: 挨拶: こんにちは、花子さん！
        
        // 【使い方3】戻り値を条件分岐で使用
        if (example.isPositive(10)) {
            System.out.println("10は正の数です");
        }
        
        // 【使い方4】戻り値を別のメソッドの引数として使用
        int num1 = example.calculateSum(2, 3);  // 5
        int num2 = example.calculateSum(4, 6);  // 10
        int total = example.calculateSum(num1, num2);  // 15
        System.out.println("最終的な合計: " + total);
        
        // 【使い方5】戻り値を計算に使用
        int max = example.getMax(7, 12);
        System.out.println("最大値: " + max);  // 出力: 最大値: 12
        
        String grade = example.getGrade(85);
        System.out.println("評価: " + grade);  // 出力: 評価: A
    }
}
```
### RandomNumberExample.java の未使用ローカル変数警告の解消

#### 発生していた問題

`RandomNumberExample.java` で、以下のローカル変数について  
**「The value of the local variable ○○ is not used」** というリント警告（severity: 4）が出ていました。

| メソッド | 対象変数 | 行 |
|----------|----------|-----|
| example1() | num1, num2, num3 | 22–24 |
| example2() | num1, num2, num3 | 36–38 |
| example3() | num1, num2, num3, num4 | 56, 58, 61, 63 |
| exampleForPray() | randomValue | 79 |
| exampleDice() | dice | 89 |
| exampleSimple() | num1, num2 | 100–101 |
| exampleForPrayMethod() | recover | 146 |

いずれも「代入はしているが、その後どこでも使っていない」ために未使用と判断されていました。

---

#### 対応方針

変数は**削除せず残し**、それらを**使う処理**を追加して警告を解消しました。  
学習用のサンプルなので、生成した値を**`System.out.println` で表示する**形にしています。

---

#### 変更内容（追加した行）

**1. example1()**

```java
System.out.println("num1=" + num1 + ", num2=" + num2 + ", num3=" + num3);
```

**2. example2()**

```java
System.out.println("num1=" + num1 + ", num2=" + num2 + ", num3=" + num3);
```

**3. example3()**

```java
System.out.println("5~10: num1=" + num1 + ", num2=" + num2 + " / 10~20: num3=" + num3 + ", num4=" + num4);
```

**4. exampleForPray()**

```java
System.out.println("randomValue=" + randomValue);
```

**5. exampleDice()**

```java
System.out.println("dice=" + dice);
```

**6. exampleSimple()**

```java
System.out.println("num1=" + num1 + ", num2=" + num2);
```

**7. exampleForPrayMethod()**

```java
System.out.println("recover=" + recover);
```

---

#### 変更による効果

- 上記の**未使用ローカル変数に関するリント警告はすべて解消**されています。
- 各サンプルメソッドを実行すると、生成したランダム値や計算結果を**その場で確認できる**ようになり、学習用としても使いやすくなっています。

---

#### 参考：修正後の該当メソッドのイメージ（example1）

```java
public void example1() {
    java.util.Random random = new java.util.Random();
    
    int num1 = random.nextInt(10);  // 0~9のランダムな整数
    int num2 = random.nextInt(5);   // 0~4のランダムな整数
    int num3 = random.nextInt(3);   // 0~2のランダムな整数（0, 1, 2のいずれか）
    System.out.println("num1=" + num1 + ", num2=" + num2 + ", num3=" + num3);
}
```

他のメソッドも同様に、変数宣言の直後（または計算の直後）に  
対応する `System.out.println` を1行ずつ追加する形で統一しています。

