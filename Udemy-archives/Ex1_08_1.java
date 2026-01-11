/*-< 演習：Ex1_08_1 >---------------------------------
コメントの内容に従ってプログラムを作成してください。
----------------------------------------------------*/
class Ex1_08_1 {
	public static void main (String[] args) {
		
		/* (1) 以下の配列を準備してください。
		**     配列名    ：names
		**     保持する値：「taro」「takeshi」「hanako」「moco」「satoshi」
		*/
		String[] names = new String[] {"taro","takashi","hanako","moko","satoshi"} ;

		/*
		String [] names = {"taro","takashi","hanako","moko","satoshi"} ;
		*/
		/*
		names[0] = "taro" ;
		names[1] = "takashi" ;
		names[2] = "hanako" ;
		names[3] = "moko" ;
		names[4] = "satoshi" ;
		*/
		

		
		/* (2) 以下のアンダーバーを埋め、
		**     names配列から「moco」の値を表示するプログラムに修正してください。
		*/
		String printName = names[3] ;         //(21行目)アンダーバーに適切な処理を埋めてください
		System.out.println( printName );    //(22行目)変更しないでください
		
	}
}
