/**
 * レコードの「許されているパターン」と「許されていないパターン」の例
 *
 * 許されている: implements（インターフェース実装）、レコード内でのメソッド追加
 * 許されていない: extends（クラス継承）
 */

// ========== インターフェース（実装用） ==========
interface Named {
    String name();
}

interface Serializable {
    // マーカー的なインターフェース（メソッドなし）もOK
}

// ========== 許されているパターン ==========

// 1. implements でインターフェースを実装する → OK
//    レコードはすでに name() を持っているので、Named の契約を満たしている
public record Person(String name, int age) implements Named {
    // 2. レコード内でメソッドを追加する → OK
    public boolean isAdult() {
        return age >= 20;
    }
}

// 複数インターフェースの実装も OK
public record Point(int x, int y) implements Named, Serializable {
    @Override
    public String name() {
        return "Point(" + x + "," + y + ")";
    }
}

// ========== 許されていないパターン（このファイルではコメントで示す） ==========
/*
// extends でクラスを継承する → コンパイルエラー
public class Animal {
    private String kind;
    public Animal(String kind) { this.kind = kind; }
}
public record Dog(String name, String kind) extends Animal {  // エラー!
}
// エラー: レコードは別のクラスを継承できません
*/
