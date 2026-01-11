/*-< 演習：Ex1_12_1 >---------------------------------
コマンドライン引数から数値を1つ受け取り、これをテストの点数とします。

(1)以下のプログラムを作成してください。
  - 点数が0～100以外の数字だった場合「不正な点数です！」と表示する

(2)以下の機能を(1)のプログラムに追加してください。
  - 点数が0～59の数字だった場合「赤点です！」と表示する
  - 点数が60～79の数字だった場合「普通です！」と表示する
  - 点数が80～100の数字だった場合「優秀です！」と表示する

(3)以下の機能を(1)(2)のプログラムに追加してください。
  - 点数の数字が100だった場合のみ「満点だったので宿題免除です！！」と最後に表示する
----------------------------------------------------*/
class Ex1_12_1 {
	public static void main (String[] args) {
		int receiveNumber = Integer.parseInt(args[0]);

    if (receiveNumber < 0 ) {
      System.out.println("不正な点数です！");
    }
		else if (receiveNumber > 100)  {
      System.out.println("不正な点数です！");
    }
		else if (receiveNumber <= 59) {
      System.out.println("赤点です！");
    }
		else if (receiveNumber <= 79) {
      System.out.println("普通です！");
    }	
		else {
      System.out.println("優秀です！");
    }	
		
    if (receiveNumber == 100) {
      System.out.println("満点だったので宿題免除です！！");
    }
	}
}
