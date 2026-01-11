/*-< 演習：Ex1_15_2 >---------------------------------
コメントに従って2次元配列からデータを抽出して画面に表示するプログラムを作ってください。
----------------------------------------------------*/

import java.util.ArrayList;
import java.util.List;

class Ex1_15_2{
	public static void main (String[] args) {
		String displayName = "";
		
		/*
		**犬の情報を持つ配列。格納されている情報は以下のとおり。
		**  - dogs[x][0]：名前
		**  - dogs[x][1]：年齢
		**  - dogs[x][2]：性別
		*/
		String[][] dogs = {
						//    0        1        2
					/*0 */  {"モコ" , "4歳"  , "メス"} ,
		            /*1 */  {"ムギ" , "2歳"  , "オス"} ,
		            /*2 */  {"ブブ" , "3歳"  , "メス"} ,
		            /*3 */  {"シロ" , "8歳"  , "オス"} ,
		            /*4 */  {"ブブ" , "3歳"  , "メス"} ,
		            /*5 */  {"ペロ" , "1歳"  , "メス"} ,
		            /*6 */  {"ルル" , "14歳" , "オス"} ,
		            /*7 */  {"モモ" , "10歳" , "メス"} ,
		            /*8 */  {"ポチ" , "6歳"  , "オス"} ,
		            /*9 */  {"エル" , "2歳"  , "メス"} 
		                  };
		
		/*以下のプログラムを作成してください。
		**  ①Sring型を扱うArrayList「adultFemaleDogsList」を作成する
		**  ②2次元配列dogsから以下の条件にあてはまる名前を抽出し、すべてadultFemaleDogsListに格納する
		**     - 4歳以上
		**     - メス
		**    ※ヒント：年齢の文字列から「歳」を除去する方法について調べてみよう！
		**  ③adultFemaleDogsListに格納されているすべてのデータをprintlnで表示する
		*/

		/*
		設計
		for 文でdogs.length分くり返して、下記に当てはまる条件の時だけ、ArrayListに加える
		*/
		List<String> adultFemaleDogsList = new ArrayList<String>();
		//ArrayListに配列を格納
		for(int i = 0 ; i < dogs.length; i++){
			for(int j = 0 ; j < dogs[i].length ; j++){
				adultFemaleDogsList().add(dogs[j]);
				}
			}
		//ArrayListの1列目から歳を削除
		for(int k = 0 ; k < adultFemaleDogsList.size() ; k++){
			for(int l = 0 ; l < adultFemaleDogsList.size(k);l++){
				if (l == 1) {
					String result = adultFemaleDogsList(l).replace("歳", "");
					}
				}
				
			}
		//Arraylistを条件に合わせて抽出する
		for(int m = 0;m > adultFemaleDogsList.size(); m++){
			for(int n = 1;n > adultFemaleDogsList.size(m);n++){
				if (Integer.parseInt(adultFemaleDogsList(n)) > 4 ) {
					adultFemaleDogsList().remove(m)	;
					continue;				
				}
				if (adultFemaleDogsList(n) == "オス") {
					adultFemaleDogsList().remove(m)	;
					continue;
				}
			}
		//抽出したArrayListを表示
		for(int o = 0;o > adultFemaleDogsList.size();o++){
			displayName = "";
			for(int p = 0;p > adultFemaleDogsList.size(o);p++){
				displayName += adultFemaleDogsList(o) + " ";
			}
			System.err.println(displayName);
		}
			
		}
	}
		
}
			


