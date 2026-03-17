### 配列・コレクションの範囲外アクセスと発生する例外
- **核心となるルール**: 配列は`length`個の要素を0番から順に持ち、アクセスできるインデックスは`0`以上`length - 1`以下の整数に限定されているとされています。この範囲外のインデックスで配列要素にアクセスしようとすると、実行時に`ArrayIndexOutOfBoundsException`がスローされます。また、`ArrayList`などの可変長リストでは、有効なインデックスは`0`以上`size() - 1`以下であり、この範囲外を`get`や`set`などで参照すると`IndexOutOfBoundsException`がスローされます。文字列`String`については、インデックス範囲は`0`以上`length() - 1`以下で、範囲外を`charAt`などで参照すると`StringIndexOutOfBoundsException`がスローされます。
- **なぜそうなるのか**: Javaの配列や`String`、`List`実装は、内部的には連続した要素領域に対して「先頭から何番目か」を整数インデックスで参照しています。インデックスが有効範囲かどうかの検査は、JVMやライブラリ実装が各アクセスごとに行っており、範囲外であれば不正なメモリアクセスを防ぐために、その場で対応する例外クラスのインスタンスを生成してスローする、という挙動が仕様として定められています。`ArrayIndexOutOfBoundsException`や`StringIndexOutOfBoundsException`は、より一般的な`IndexOutOfBoundsException`のサブクラスとして用意されており、「どの種類のシーケンスで起きた範囲外アクセスなのか」を型で区別できるようになっています。
- **直感的な例え・オリジナル例**: 本棚に10冊の本が左から順に1〜10番として並んでいる状況を考えます。現実世界では「11番目の本を取って」と言われても、そもそも11番目という位置が存在しないので、作業を続行できません。同様に、Javaで`int[] scores = new int[10];`と宣言した場合、取りうるインデックスは`0`〜`9`だけです。例えば次のようなコードは範囲外アクセスになります。

```java
int[] scores = new int[3];  // 有効なインデックスは 0, 1, 2
scores[3] = 100;            // 実行時に ArrayIndexOutOfBoundsException
```

`ArrayList`の場合も同様で、まだ要素を1つも追加していない`new ArrayList<String>()`に対して`list.get(0)`を呼べば、存在しない場所を要求したことになり`IndexOutOfBoundsException`がスローされます。`String name = "ABC";`に対して`name.charAt(3)`を呼ぶときも、実在しない4文字目を要求しているため、`StringIndexOutOfBoundsException`になります。
- **初学者の陥りやすい罠**: 
  - **インデックスの上限を`length`だと誤解する**: 「`length`個あるから最大インデックスも`length`」と考えてしまい、`length`そのものを使ってアクセスしてしまうと範囲外になります。常に「最大は`length - 1`／`size() - 1`」という関係を意識する必要があります。
  - **負のインデックスは絶対に使えないことを忘れる**: Pythonなど一部の言語では`-1`で末尾を指すことがありますが、Javaの配列や`List`では負のインデックスは一律で範囲外とみなされ、例外がスローされます。
  - **どの例外名がどの型に対応するか混同する**: 配列は`ArrayIndexOutOfBoundsException`、`String`は`StringIndexOutOfBoundsException`、`List`など一般的なコレクションは`IndexOutOfBoundsException`という対応関係を整理しておかないと、例外の原因を読み違えがちです。
  - **要素数0の配列や空のリストの扱い**: 「配列やリスト自体が存在している」ことと「中に1つでも要素が入っている」ことを混同し、長さ0（`length == 0`／`size() == 0`）にもかかわらずインデックス0を参照してしまい、範囲外アクセスになるケースがよく見られます。

