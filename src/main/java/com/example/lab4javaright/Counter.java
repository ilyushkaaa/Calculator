package com.example.lab4javaright;

import javafx.scene.control.Label;



// сделать калькулятор чтобы можно ыло вводить числа в 16чной системе, 16 чное чмсло реализовать в виле класса.без корня , деление под вопросом
public class Counter {
    private static boolean ns10;

    public static boolean isNs10() {
        return ns10;
    }

    public static void setNs10(boolean ns10) {
        Counter.ns10 = ns10;
    }

    private String num1;
    private String num2;
    private String operator;
    private String operatorUno;

    public String getOperatorUno() {
        return operatorUno;
    }

    public void setOperatorUno(String operatorUno) {
        this.operatorUno = operatorUno;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }
    private String result(String res){
        if(!ns10){
            if (res.contains("E")){
                return "Infinity";
            }
            return Digit16.Parse16NS(res);
        }
        return res;
    }

    public Counter() {
        this.num1 = "";
        this.num2 = "";
        this.operator = "";
        this.operatorUno = "";

    }
    public String count(){
        if (!ns10){
            num1 = Digit16.Parse10NS(num1);
            num2 = Digit16.Parse10NS(num2);
        }
        if (!operatorUno.equals("")){

            return countUno();
        }
        return countNotUno();

    }
    private String countMod(){
        operator = "";
        if (num2.equals("0")){
            return "Деление на0!";
        }
        return result(Long.toString(Math.floorMod((long)Double.parseDouble(num1), (long)Double.parseDouble(num2))));
    }
    private String countNotUno(){
        switch (operator){
            case "+" : return addition();
            case "-" : return subtraction();
            case "*" : return multiplication();
            case "/" : return division();
            case "mod": return countMod();
            default: return power();




        }

    }
    private String countUno(){
        if(operatorUno.equals("sqrt")){
            return sqrt();
        }
        else {
            return inverse();
        }

    }
    private String inverse(){
        if (operator.equals("")){
            num1 = Double.toString(1/Double.parseDouble(num1));
            operatorUno = "";
            return num1;
        }
        else {
            num2 = Double.toString(1/Double.parseDouble(num2));
            operatorUno = "";

            return num2;
        }

    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    private String addition(){
        operator = "";

        return result(Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2)));



    }
    private String subtraction(){
        operator = "";

        return result(Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2)));


    }
    private String multiplication(){
        operator = "";

        return result(Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2)));


    }
    private String division(){
        operator = "";

        if (Double.parseDouble(num2) == 0){
            num1 = "";
            num2 = "";

            operator = "";
            operatorUno = "";
            return "Деление на0!";
        }
        return result(Double.toString(Double.parseDouble(num1)/ Double.parseDouble(num2)));


    }

    public void percent(Label text){
        String mainOperator = operator;
        operator = "*";
        num2 = text.getText();
        if (!ns10){
            num2 = Digit16.Parse10NS(num2);
        }

        double percentDouble = Double.parseDouble(num2) / 100;
        num2 = Double.toString(percentDouble);
        if (!ns10){
            num2 = Digit16.Parse16NS(num2);
        }


        num2 = count();
        operator = mainOperator;
        num2 =count();
        if (num2.length() > 11){
            text.setText(num2.substring(0, 12));
        }
        else {
            text.setText(num2);
        }

    }
    private String sqrt(){
        if (operator.equals("")){
            num1 = Double.toString(Math.sqrt(Double.parseDouble(num1)));
            operatorUno = "";

            return result(num1);
        }
        else {
            num2 = Double.toString(Math.sqrt(Double.parseDouble(num2)));
            operatorUno = "";

            return result(num2);
        }

    }
    private String power(){
        operator = "";

        return result(Double.toString(Math.pow(Double.parseDouble(num1), Double.parseDouble(num2))));

    }

}
