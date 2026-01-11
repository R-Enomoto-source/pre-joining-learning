/*-< Ex1_04_1 >-------------------------------------------------------------------------
九九を表示するプログラムです。
数値を1つ入力して実行することで入力した数の段のみを表示することができます。
数値を入力せずに実行した場合は全ての段が表示されます。
--------------------------------------------------------------------------*/
class Ex1_04_1 {
	public static void main (String[] args) {
		
		for(int i = 1 ; i <= 9  ; i++){
			String disp3 = "";
			for(int j = 1 ; j <= 9  ; j++){
				disp3 = disp3 + i*j + " ";
			}
			if(args.length == 1){
				if(i == Integer.parseInt(args[0])){
					System.out.println(disp3);
				}
			}
			else{
				System.out.println(disp3);
			}
		}
	}
}
