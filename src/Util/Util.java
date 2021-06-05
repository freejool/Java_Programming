package Util;

/**
 * a func to compare char []
 * param: char[] ch1, char[] ch2
 */
public class Util {

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
}