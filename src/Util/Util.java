package Util;

import java.lang.reflect.Array;

public class Util {
    public static String tostring(char[] chs){
        StringBuilder sb=new StringBuilder("");
        for (int i = 0; i < Array.getLength(chs); i++) {
            sb.append(chs[i]);
        }
        return sb.toString();

    }

    public static int chCmp(char[] ch1, char[] ch2) {
        int length1 = ch1.length;
        int length2 = ch2.length;
        if (length1 == length2) {
            //转换成字符串来比较
            String str1 = new String(ch1);
            String str2 = new String(ch2);
            if (str2.equals(str1)) {
                return 1;
            }
        }
        return 0;
    }



    public static void main(String[] args) {
        char [] a= {'a','b','c','d','e'};


        System.out.println( tostring(a));
    }
}
