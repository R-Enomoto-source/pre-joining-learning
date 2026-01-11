class Jihan{
/*
大まかな設計イメージ
入力をコマンドライン引数で配列を変数に入れる
これをfor文でループ文を作り、配列の数だけ、処理させる
if文とif else文で、条件を満たすものだけを計算するようにしておく
（警告は変数に入れておくとメンテナンス性や再利用性が上がるかも）
警告も違うものは出すように組む。
警告に当たるものは計算させないようにcontinueで処理する
最後に、合計金額を表示する
*/
/*
分からないこと
コマンドライン引数をこれで引き受けられるのか？
args[]
*/
    public static void main (String[] args){
        int sumCoins = 0 ;
        for(int i = 0; i < args.length; i++){
            int receveCoin = Integer.parseInt(args[i]) ;
            String warningStatement_1 = "警告：1円玉は使えません" ;
            String warningStatement_2 = "警告：5円玉は使えません" ;
            String warningStatement_3 = "警告：" + args[i] + "は硬貨として適切な値ではありません" ;
            if(receveCoin == 1){
                System.out.println(warningStatement_1);
                continue;
            }
            else if (receveCoin == 5){
                System.out.println(warningStatement_2);
                continue;
            }
            else if(receveCoin != 10 && receveCoin != 50 && receveCoin != 100 && receveCoin != 500 ){
                System.out.println(warningStatement_3);
                continue;
            }
            sumCoins += receveCoin ;
        }

        System.out.println("ただいまの投入金額は" + sumCoins + "円です");
        

    }
}