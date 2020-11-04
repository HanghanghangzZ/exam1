package com.check;

public class InputCheck {

    /**
     * 检查用户输入的字符串中是否并不全是数字
     *
     * @param num
     * @return
     */
    public static boolean checkNum(String num) {
        int ch;
        for (int i = 0; i < num.length(); i++) {
            ch = num.charAt(i);
            if (ch < 48 || ch > 57) return false;
        }
        return true;
    }

}
