package com.example;

import java.io.*;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        String username = "chen";
        byte[] bytes = username.getBytes();
        System.out.println(Arrays.toString(bytes));

        System.out.println(Arrays.toString(getByte(username)));
        System.out.println(getString(getByte(username)));
    }
    public static byte[] getByte(String str) {
        byte[] arr = new byte[30];
        int len = str.length();
        for (int i = 0; i < len; i++) {
            arr[i] = (byte) str.charAt(i);
        }
        for (int i = len; i < arr.length; i++) {
            arr[i] =(byte) ' ';
        }
        return arr;
    }
    public static String getString(byte[] arr) {
        String str = new String(arr);
        return str.trim();
    }
}
