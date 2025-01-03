package com.utility;

import java.util.Scanner;

public class Utility {
    static Scanner scanner = new Scanner(System.in);

    /**
     *
     * @param length the length of string of input
     * @return the length-limited string
     */
    static public String GetString(int length) {
        String str = scanner.nextLine();
        return str.length() > length ? str.substring(0, length) : str;
    }
}
