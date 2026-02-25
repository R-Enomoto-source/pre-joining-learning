/**
 * レコードの「許されているパターン」と「許されていないパターン」の例
 *
 * このファイルには「許されている」例のみ記載。
 * 許されていない例は PersonRecordInvalid.java を参照（コンパイルエラーになるため別ファイル）。
 */

// ========== 許されている: implements でインターフェースを実装 ==========

interface Named {
interface Serializable {
    // マーカーインターフェース（メソッドなし）のイメージ
}

    String name();
}

// OK: レコードは implements 可能。メソッドは追加してよい（インターフェースの契約を満たすため）
public record PersonRecord(String name, int age) implements Named {
    // インターフェース Named の name() は、レコードが自動生成する name() で満たされる
    // 必要なら追加のメソッドも書ける
    public boolean isAdult() {
        return age >= 20;
    }
}
