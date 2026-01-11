import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class CalcMonthPartTimeFee {
    public static void main(String[] args) {
        //  WorkingResult.csvのパス ※「C:\WorkSpace」直下に配置していない場合は適宜変更してください。
		final String WORKING_RESULT_FILE_PATH = "C:\\WorkSpace\\WorkingResult.csv";
		// コンマ
		final String COMMA = ","; 
		
        // --- 定数定義 ---
        final long ONE_MIN_BY_MILLI_SEC  = 1000 * 60;      // 1分のミリ秒換算
        
        // 給与関連
        final int HOURLY_WAGE_YEN = 900;
        final int MINUTE_WAGE_YEN = HOURLY_WAGE_YEN / 60; // 分給15円
        final double OVERTIME_RATE = 1.25;
        int salary = 0;

        // 時間規定（分単位）
        final int MINUTES_6_HOURS = 6 * 60; // 360分
        final int MINUTES_8_HOURS = 8 * 60; // 480分
        
        // 休憩規定（分単位）
        final int REST_TIME_45_MIN = 45;
        final int REST_TIME_60_MIN = 60;

		//ファイルから読み込んだデータの格納用
        List<String> workingResults = new ArrayList<String>();
		//  WorkingResult.csvを読み込む
		try {
			// WorkingResult.csvの読み込み準備
			File workingResultFile = new File(WORKING_RESULT_FILE_PATH);
			BufferedReader br = new BufferedReader(new FileReader(workingResultFile)); 
			
			// WorkingResult.csvを1行ずつ読み込んでArrayListに格納する
			String recode = br.readLine();
			while (recode != null) {
				workingResults.add(recode);
				recode = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}

		// ArrayListから1行ずつ取り出して日付/出勤時間/退勤時間に振り分け
		for (int i = 0; i < workingResults.size() ; i++) {
			
			String workingRecode    = workingResults.get(i);      // 1行ずつ文字列を取り出す
			String[] forSplitRecode = workingRecode.split(COMMA); // splitメソッドを用いてカンマ区切りで文字列を分解＆配列にそれぞれ格納
			
            // --- 入力処理 ---
            Time startTime  = Time.valueOf(forSplitRecode[1]);
            Time finishTime = Time.valueOf(forSplitRecode[2]);

            // --- 労働時間計算（分単位） ---
            long workingTimeMillis = finishTime.getTime() - startTime.getTime();
            int totalWorkingMinutes = (int)(workingTimeMillis / ONE_MIN_BY_MILLI_SEC);

            // --- 実労働時間（休憩を引いた時間）の算出 ---
            int actualWorkingMinutes;

			if (totalWorkingMinutes <= MINUTES_6_HOURS) {
            // 6時間以下：休憩なし
            actualWorkingMinutes = totalWorkingMinutes;
            
            } else if (totalWorkingMinutes <= MINUTES_8_HOURS) {
            // 6時間超〜8時間以下：45分休憩
            actualWorkingMinutes = totalWorkingMinutes - REST_TIME_45_MIN;
            
            } else {
            // 8時間超：60分休憩
            actualWorkingMinutes = totalWorkingMinutes - REST_TIME_60_MIN;
            }

            // --- 給与計算 ---

            // 実労働時間が8時間（480分）を超えているかどうかで分岐
            if (actualWorkingMinutes > MINUTES_8_HOURS) {
                // 残業あり
                int normalMinutes = MINUTES_8_HOURS;
                int overtimeMinutes = actualWorkingMinutes - MINUTES_8_HOURS;
            
                // 基本給 + 残業代
                salary += (normalMinutes * MINUTE_WAGE_YEN) 
                        + (int)(overtimeMinutes * MINUTE_WAGE_YEN * OVERTIME_RATE);
            } else {
                // 残業なし
                salary += actualWorkingMinutes * MINUTE_WAGE_YEN;
            }			
		}
        // --- 出力 ---
        System.out.println("今月のお給料は" + salary + "円です");
    }
    
}
