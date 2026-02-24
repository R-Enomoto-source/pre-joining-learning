package exercise.Chapter4;
/**
 * static初期化子の動作確認用サンプル（学習用に作成）
 * クラスが初めて使われるときに static ブロックが1度だけ実行されることを示す。
 */
public class StaticInitializerDemo {
    static int value;

    static {
        value = 10;
        System.out.println("static初期化子: value = " + value);
    }

    public StaticInitializerDemo() {
        value = 100;
        System.out.println("コンストラクタ: value = " + value);
    }

    public static void main(String[] args) {
        System.out.println("--- main開始 ---");
        System.out.println("クラス参照時点の value = " + StaticInitializerDemo.value);
        System.out.println("--- 1つ目のインスタンス生成 ---");
        @SuppressWarnings("unused")
        StaticInitializerDemo a = new StaticInitializerDemo();
        System.out.println("--- 2つ目のインスタンス生成 ---");
        @SuppressWarnings("unused")
        StaticInitializerDemo b = new StaticInitializerDemo();
        System.out.println("最終的な value = " + StaticInitializerDemo.value);
        System.out.println("--- main終了 ---");
    }
}
