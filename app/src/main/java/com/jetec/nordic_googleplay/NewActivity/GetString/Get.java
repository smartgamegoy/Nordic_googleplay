package com.jetec.nordic_googleplay.NewActivity.GetString;

public class Get {

    public Get(){
        super();
    }

    public String todo(String a, byte p, int num){
        String str;
        int point = p * -1;
        double value = num * Math.pow(10 , point);
        if (value >= 0) {
            str = a + "+" + value;
        }else {
            str = a + value;
        }
        /*if (value == 0.0) {
            str = a + "+" + "0000.0";
        }else {
            if (value < 0) {
                gets = String.valueOf(value);
                int i = gets.indexOf(".");
                String num1 = gets.substring(1, gets.indexOf("."));
                String num2 = gets.substring(gets.indexOf("."), gets.indexOf(".") + 2);
                StringBuilder set = new StringBuilder("0");
                if (i != 4) {
                    for (int j = 0; j < (4 - i); j++)
                        set.append("0");
                    str = a + "-" + set + num1 + num2;
                } else {
                    str = a + "-" + "0" + num1 + num2;
                }
            } else {
                gets = String.valueOf(value);
                int i = gets.indexOf(".");
                String num1 = gets.substring(0, gets.indexOf("."));
                String num2 = gets.substring(gets.indexOf("."), gets.indexOf(".") + 2);
                StringBuilder set = new StringBuilder("0");
                for (int j = 1; j < (4 - i); j++)
                    set.append("0");
                str = a + "+" + set + num1 + num2;
            }
        }*/

        return str;
    }
}
