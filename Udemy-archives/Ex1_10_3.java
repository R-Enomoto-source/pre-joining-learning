/*-< 演習：Ex1_10_3 >---------------------------------
以下のように00～99までの数字を並べて表示するプログラムを作成してください。

00 01 02 03 04 05 06 07 08 09
10 11 12 13 14 15 16 17 18 19
20 21 22 23 24 25 26 27 28 29
30 31 32 33 34 35 36 37 38 39
40 41 42 43 44 45 46 47 48 49
50 51 52 53 54 55 56 57 58 59
60 61 62 63 64 65 66 67 68 69
70 71 72 73 74 75 76 77 78 79
80 81 82 83 84 85 86 87 88 89
90 91 92 93 94 95 96 97 98 99

----------------------------------------------------*/
class Ex1_10_3 {
	public static void main (String[] args) {
	String display_1 = "00" ;
	String display_2 = "10" ;
	String display_3 = "20" ;
	String display_4 = "30" ;
	String display_5 = "40" ;
	String display_6 = "50" ;
	String display_7 = "60" ;
	String display_8 = "70" ;
	String display_9 = "80" ;
	String display_10 = "90" ;
	for(int i = 1 ; i < 10 ; i++ ){
		display_1 += " 0" + String.valueOf(i) ;
	}
	for(int j = 11 ; j < 20 ; j++ ){
		display_2 += " " + String.valueOf(j) ;
	}
	for(int k = 21 ; k < 30 ; k++ ){
		display_3 += " " + String.valueOf(k) ;
	}
	for(int l = 31 ; l < 40 ; l++ ){
		display_4 += " " + String.valueOf(l) ;
	}
	for(int m = 41 ; m < 50 ; m++ ){
		display_5 += " " + String.valueOf(m) ;
	}
	for(int n = 51 ; n < 60 ; n++ ){
		display_6 += " " + String.valueOf(n) ;
	}
	for(int o = 61 ; o < 70 ; o++ ){
		display_7 += " " + String.valueOf(o) ;
	}
	for(int p = 71 ; p < 80 ; p++ ){
		display_8 += " " + String.valueOf(p) ;
	}
	for(int q = 81 ; q < 90 ; q++ ){
		display_9 += " " + String.valueOf(q) ;
	}
	for(int r = 91 ; r < 100 ; r++ ){
		display_10 += " " + String.valueOf(r) ;
	}
	
	System.out.println(display_1);
	System.out.println(display_2);
	System.out.println(display_3);	
	System.out.println(display_4);
	System.out.println(display_5);
	System.out.println(display_6);
	System.out.println(display_7);
	System.out.println(display_8);
	System.out.println(display_9);
	System.out.println(display_10);
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
