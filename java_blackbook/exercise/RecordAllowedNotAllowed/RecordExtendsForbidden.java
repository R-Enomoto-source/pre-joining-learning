/**
 * 許されていないパターン: レコードが extends を使う
 *
 * このファイルはコンパイルエラーになります。
 * 確認するには: javac RecordExtendsForbidden.java
 *
 * エラー例: レコードは別のクラスを継承できません
 */

// 親クラス（普通のクラス）
class Animal {
    private String kind;
    public Animal(String kind) {
        this.kind = kind;
    }
    public String getKind() {
        return kind;
    }
}

// レコードが extends しようとすると → コンパイルエラー
// public record Dog(String name, String kind) extends Animal {
// }
// エラー: レコードは、別のクラスを拡張できません (record cannot extend another class)
