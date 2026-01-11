public class Jihan_mohan {
    // 【定数定義】
    // 変更されない固定のメッセージは、クラスの先頭に「static final」で定義します。
    // これにより、メモリの無駄遣いを防ぎ、文章の修正も一箇所で済みます。
    private static final String WARN_1YEN = "警告：1円玉は使えません";
    private static final String WARN_5YEN = "警告：5円玉は使えません";

    public static void main (String[] args){
        // 【ガード節】引数が空の場合のクラッシュを防止
        if (args.length < 1) {
            return; 
        }


        int sumCoins = 0;
        
        // 【効率化】最後の引数は「商品の値段」なので、ループの外で先に取得します
        // これにより、ループの中で毎回「これは最後の引数か？」を判定する必要がなくなります
        int totalAmount = Integer.parseInt(args[args.length - 1]); 
        
        String changeAndThankYou = "円のお釣りです。ありがとうございました。";

        // 【ループ範囲の最適化】条件を「i < args.length - 1」とし、硬貨の部分だけを回します
        for(int i = 0; i < args.length - 1; i++){
            int receiveCoin = Integer.parseInt(args[i]);

            if(receiveCoin == 1){
                // 定数を使用（高速）
                System.out.println(WARN_1YEN);
            }
            else if (receiveCoin == 5){
                // 定数を使用（高速）
                System.out.println(WARN_5YEN);
            }
            // 使用可能な硬貨かチェック
            else if(receiveCoin == 10 || receiveCoin == 50 || receiveCoin == 100 || receiveCoin == 500){
                sumCoins += receiveCoin;
            }
            else {
                // 【動的生成】入力値を含める必要がある警告文だけ、その場で文字列結合を行います
                System.out.println("警告：" + args[i] + "は硬貨として適切な値ではありません");
            }
        }

        // お釣りの計算
        int change = sumCoins - totalAmount;
        
        // 結果の表示
        System.out.println(change + changeAndThankYou);
    }
}
