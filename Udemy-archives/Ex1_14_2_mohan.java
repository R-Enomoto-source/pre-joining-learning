public class Ex1_14_2_mohan {

    // ▼▼▼ 定数をクラスのメンバとして定義（設定の一覧化） ▼▼▼
    // 上限・下限設定
    static final int THRESHOLD_LOWER_LIMIT = 1000;
    static final int THRESHOLD_UPPER_LIMIT = 5000; // 名前をUpper（上限）にすると対比がわかりやすいです
    static final int DISCOUNT_MAX_AMOUNT   = 5000;

    // 割引率計算用の除数（10%は1/10、20%は1/5）
    static final int DIVISOR_RATE_10_PERCENT = 10;
    static final int DIVISOR_RATE_20_PERCENT = 5;

    // 消費税率
    static final double TAX_RATE = 1.08;
    // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

    //！！！mainメソッドの処理は書き換えないでください！！！
    public static void main (String[] args) {
        int totalPrice = 0;
        for(int i = 0 ; i < args.length ; i++ ){
            totalPrice += Integer.parseInt( args[i] );
        }
        
        int discountedPrice = discount( totalPrice );
        int taxPayment = calcTaxPayment( discountedPrice );
        
        System.out.println("値引き後の支払金額：" + taxPayment + "円" );
    }
    
    
    /*
    ** 消費税計算メソッド
    */
    static int calcTaxPayment( int discountedPrice ){
        // 直接 1.08 と書かずに定数を使う
        int calcTaxTotalPrice = (int)(discountedPrice * TAX_RATE);
        return calcTaxTotalPrice;
    }
    
    
    /*
    ** 割引計算メソッド
    */
    static int discount( int totalAmount ){
        int discountedTotalAmount = 0;

        // メソッド内がスッキリし、計算ロジックだけに集中できます

        if(totalAmount >= 0 && totalAmount < THRESHOLD_LOWER_LIMIT){
            discountedTotalAmount = totalAmount;

        }else if(totalAmount >= THRESHOLD_LOWER_LIMIT && totalAmount <= THRESHOLD_UPPER_LIMIT){
            // 10を定数に置き換え
            int discountValue_1 = (totalAmount - THRESHOLD_LOWER_LIMIT) / DIVISOR_RATE_10_PERCENT;
            discountedTotalAmount = totalAmount - discountValue_1;

        }else if(totalAmount > THRESHOLD_UPPER_LIMIT){
            // 10, 5 を定数に置き換え
            // 計算式の中の定数も UpperLimit に統一
            int discountValue_2 = (THRESHOLD_UPPER_LIMIT - THRESHOLD_LOWER_LIMIT) / DIVISOR_RATE_10_PERCENT 
                                + (totalAmount - THRESHOLD_UPPER_LIMIT) / DIVISOR_RATE_20_PERCENT;
            
            if(discountValue_2 > DISCOUNT_MAX_AMOUNT ){
                discountValue_2 = DISCOUNT_MAX_AMOUNT;
            }
            discountedTotalAmount = totalAmount - discountValue_2;

        }else{
            System.out.println("不正な金額を入力しています。適正な金額を入力してください");
        }
        return discountedTotalAmount;
    }
}