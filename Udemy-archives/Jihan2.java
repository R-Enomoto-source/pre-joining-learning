public class Jihan2 {
    public static void main (String[] args){
        int sumCoins = 0 ;
        int totalAmount = 0 ;
        String changeAndThankYou = "円のお釣りです。ありがとうございました。" ;
        for(int i = 0; i < args.length; i++){
            int receveCoin = Integer.parseInt(args[i]) ;

            String warningStatement_1 = "警告：1円玉は使えません" ;
            String warningStatement_2 = "警告：5円玉は使えません" ;
            String warningStatement_3 = "警告：" + args[i] + "は硬貨として適切な値ではありません" ;
            
            if(i == args.length - 1){
                totalAmount = Integer.parseInt(args[i]) ;
                continue;
            }
            
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
        int change = sumCoins - totalAmount ;
        System.out.println(change + changeAndThankYou);
    }
}
