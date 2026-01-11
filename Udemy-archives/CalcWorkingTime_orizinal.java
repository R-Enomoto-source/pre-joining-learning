import java.sql.Time;

public class CalcWorkingTime_orizinal {

/*
<仕様>
・時給は900円とし、給与は1分単位で支払われます。
・小数点以下の給与は切り捨てて算出されます。
・労働時間が6時間超~8時間以下の場合は45分の休憩を、8時間を超える場合は1時間の休憩をとるもの
とします。なお、休憩時間において給与は発生しません。
・休憩時間を差し引いた労働時間(実労働時間)が8時間を超える場合、
超過分に限り残業代として1.25倍の給与が支払われるものとします。
・BigDecimalによる誤差の考慮は不要とします。
・クラス名は自由に決めてください。
(必ずクラス名を見ただけで処理内容を想像できるものにすること)

<設計>
時給と分給を定数で定めておく
6時間超~8時間以下の場合は45分の休憩と、8時間を超える場合は1時間の休憩の2つを定数として定めておく
if文を使って、休憩時間を労働時間から引いた実労働時間を計算する
定数で、基本労働時間を8時間と定めておく。
1.25を残業代計算に使うための定数として定めておく。
さらにif文を使って、実労働時間が基本労働時間である8時間を超過した場合は、超過分の時給と分給を1.25倍する。
実労働時間が8時間以内の場合は8時間以内の労働時間で給与を計算する。
実労働時間が8時間を超える場合は8時間分を通常自給で計算して、超過した分は1.25倍された時給と分給で計算して、それぞれを足し合わせる。

*/




	public static void main(String[] args) {

		// 計算用の数値を定数で用意
		final long ONE_MIN_BY_MILLI_SEC  = 1000 * 60;      // 1分のミリ秒換算
		//通常分給と残業時分給
		final int hourlyWage = 900/60 ;                         
		final double overtimeWorkHourlyWage = hourlyWage * 1.25;
		//労働時間に関する定数
		final int provisionWorkingHours = 480;
		final int workingHoursNoRest = 360 ;
		//休憩時間に関する定数
		final int restTime_1 = 45;
		final int restTime_2 = 60;

		//給料に関する変数
		int salary = 0;
		
		// バイトの開始時間と終了時間をコマンドライン引数から受け取る
		Time startTime  = Time.valueOf(args[0]);
		Time finishTime = Time.valueOf(args[1]);

		// getTimeメソッドを使って労働時間をミリ秒（0.001秒単位）で取得する
		// ※getTime()メソッドの戻り値はlong型であることに注意
		long workingTime = finishTime.getTime() - startTime.getTime();

		// ミリ秒で取得した労働時間を○時間△分の形式に直す
		int actualWorkingHours  = (int)(workingTime / ONE_MIN_BY_MILLI_SEC ); // 分に換算

		// 出力
		
		if (actualWorkingHours <= workingHoursNoRest ) {
			salary = actualWorkingHours * hourlyWage;
		}else if (actualWorkingHours <= provisionWorkingHours) {
			actualWorkingHours = actualWorkingHours - restTime_1 ;
			salary = actualWorkingHours * hourlyWage;
		}else if (actualWorkingHours >= provisionWorkingHours) {
			actualWorkingHours = actualWorkingHours - restTime_2;
			if (actualWorkingHours > provisionWorkingHours) {
				salary = (provisionWorkingHours*hourlyWage) + (int)((actualWorkingHours - provisionWorkingHours)*overtimeWorkHourlyWage);
			}else{
				salary = actualWorkingHours * hourlyWage;
			}	
		}else{
			System.out.println("適正な値を入力して下さい");
		}

		System.out.println("本日のお給料は" + salary + "円です");
	}

	
}
