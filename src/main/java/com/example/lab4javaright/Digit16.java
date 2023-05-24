package com.example.lab4javaright;

import java.util.*;

public abstract class Digit16 {

    private static Map<Character, Integer> alphabet;
    private static boolean hasMinus;

    private static void constructAlphabet(){
        alphabet = new HashMap<>();
        for (int i = 0; i < 10; ++i){
            alphabet.put(Integer.toString(i).charAt(0), i);
        }
        alphabet.put('a', 10);
        alphabet.put('b', 11);
        alphabet.put('c', 12);
        alphabet.put('d', 13);
        alphabet.put('e', 14);
        alphabet.put('f', 15);
    }

    private static Character getKey(long value){
        List<Character> keys = new ArrayList<>(alphabet.keySet());
        for (Character key : keys){
            if (alphabet.get(key) == value){
                return key;
            }
        }
        return 0;
    }
    private static String countWholePartTo16(String fractionNum){
        String result = "";
        long fractionNumLong = Long.parseLong(fractionNum);

        do {
            result = getKey(fractionNumLong % 16) + result;
            fractionNumLong /= 16;
        }
        while (fractionNumLong != 0);
        /*if (result.charAt(0) == '0'){
            return result.substring(1);
        }*/
        return result;
    }


    private static String countFractionPartTo16(String fractionNum){
        String frNum = "0" + "." + fractionNum;
        int counter = 0;
        String result = "0.";
        double remainder = Double.parseDouble(frNum);
        while (counter < 8 && remainder != 0){
            long digit = (long)(remainder * 16);
            result = result + getKey(digit);
            remainder = (remainder * 16) % 1;
            counter++;
        }
        if (result.substring(1).equals(".")){
            return "";
        };
        return result.substring(1);
    }
    public static String Parse10NS(String number){
        constructAlphabet();
        double num10 = 0;
        int i = 0;
        if (number.contains("-")){
            number = number.substring(1);
            hasMinus = true;
        }
        else {
            hasMinus = false;
        }
        if (number.contains(".")){
            int wholeLength = number.substring(0, number.indexOf('.')).length();
            for (; i < wholeLength; ++i){
                num10 += alphabet.get(number.charAt(i)) * Math.pow(16, (wholeLength - i - 1));
            }
            for (int j = 1; j <= number.substring(number.indexOf('.') + 1).length(); ++j){
                num10 += alphabet.get(number.charAt(number.indexOf('.') + 1)) * Math.pow(16, -j);
            }
        }
        else{
            for (; i < number.length(); ++i){
                num10 += alphabet.get(number.charAt(i)) * Math.pow(16, (number.length() - i - 1));
            }
        }
        if (hasMinus){
            num10/=(-1);
        }
        return Double.toString(num10);

    }
    public static String Parse16NS(String number){
        if (number.contains("-")){
            number = number.substring(1);
            hasMinus = true;
        }
        else {
            hasMinus = false;
        }
        String wholeNum = "";
        String fractionNum = "";
        String num16NS = "";
        if (number.contains(".")){
            List <String> partsOfNum = new ArrayList<>();
            partsOfNum.add(number.substring(0, number.indexOf('.')));
            partsOfNum.add(number.substring(number.indexOf('.') + 1));
            wholeNum = countWholePartTo16(partsOfNum.get(0));
            fractionNum = countFractionPartTo16(partsOfNum.get(1));
            num16NS = wholeNum + fractionNum;
        }
        else{
            num16NS = countWholePartTo16(number);
        }
        if (hasMinus){
            num16NS = "-" + num16NS;
        }

        return num16NS;
    }
}