package com.example.lab4javaright;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController{

    @FXML
    private Label text;
    @FXML
    private Label history;

    private final int maxSymbols = 11;
    private boolean start = true;
    private boolean operatorEntered;
    private Counter counter;
    private boolean commaEntered;
    private boolean modEntered;
    private String lastEnteredElemType = "";
    @FXML private Button buttonChange;
    @FXML
    protected void changeNS() throws IOException {
        if (Counter.isNs10()){
            Stage stage = (Stage)buttonChange.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view-16.fxml"));
            Counter.setNs10(false);
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setTitle("Мощный калькулятор       16NS");
            stage.setScene(scene);
            stage.show();
        }
        else{
            Stage stage = (Stage)buttonChange.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Counter.setNs10(true);
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setTitle("Мощный калькулятор       10NS");
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    protected void onDigitClick(ActionEvent event) {
        hasBadNotes();
        if(start){
            counter = new Counter();
            operatorEntered = false;
            commaEntered = false;
            modEntered = false;
            text.setText("");
            start = false;
        }
        if (text.getText().length() >= maxSymbols){
            return;
        }
        if (text.getText().equals("0") || lastEnteredElemType.equals("Operator")){
            text.setText("");
        }
        String value = ((Button)event.getSource()).getText();

        String toWrite = text.getText() + value;
        if (toWrite.length() > 11){
            text.setText(toWrite.substring(0, 12));
        }
        else {
            text.setText(toWrite);
        }


        lastEnteredElemType = "Digit";
        if(!operatorEntered){
            history.setText(text.getText());
        }
        else {
            history.setText(history.getText() + value);
        }
        historyHasScope();


    }

    @FXML
    protected void onOperatorClick(ActionEvent event){
        if (hasBadNotes() || lastEnteredElemType.equals("Operator") || start || lastEnteredElemType.equals("Comma")){
            return;
        }

        String value = ((Button)event.getSource()).getText();
        if (operatorEntered || modEntered){
            counter.setNum2(text.getText());
            counter.setNum1(counter.count());
            text.setText(value);
        }
        else {
            counter.setNum1(text.getText());
        }
        operatorEntered = true;

        lastEnteredElemType = "Operator";
        counter.setOperator(value);
        text.setText(value);
        commaEntered = false;
        if (value.equals("x^")){
            history.setText(counter.getNum1() + "^");

        }
        else {
            history.setText(counter.getNum1() + text.getText());

        }
        historyHasScope();
        modEntered = false;



    }
    @FXML
    protected void onPercentClick(){
        if (start || modEntered || hasBadNotes() || !operatorEntered || lastEnteredElemType.equals("Comma") ||
                lastEnteredElemType.equals("Operator")){
            return;
        }
        counter.percent(text);
        lastEnteredElemType = "Digit";
        operatorEntered = false;
        if (text.getText().contains(".")){
            checkExtraNulls();
        }
        commaEntered = hasComma();
        result();
        history.setText(text.getText());
        historyHasScope();
    }
    @FXML
    public void result (){
        if (start || hasBadNotes() || !operatorEntered || lastEnteredElemType.equals("Comma") ||
                lastEnteredElemType.equals("Operator")){
            return;
        }
        counter.setNum2(text.getText());
        String res = counter.count();
        if (res.length() > 11){
            if(res.contains("E")){
                String partE = res.substring(res.indexOf("E"));
                int partELength = partE.length();
                String resultString = res.substring(0, maxSymbols - partELength + 1) + partE;
                text.setText(resultString);
            }
            else {
                text.setText(res.substring(0, 12));
            }
            counter.setNum1(res);
        }
        else {
            text.setText(res);
        }

        lastEnteredElemType = "Digit";
        operatorEntered = false;
        counter.setNum1(res);
        counter.setNum2("");
        modEntered = false;
        commaEntered = hasComma();
        if (text.getText().contains(".")){
            checkExtraNulls();
        }
        history.setText(text.getText());

    }
    private void checkExtraNulls(){
        String afterComma = "";
        String allNulls = "";
        for (int i = text.getText().indexOf(".") + 1; i < text.getText().length(); ++i){
            afterComma = afterComma + text.getText().charAt(i);
            allNulls = allNulls + "0";
        }
        if (afterComma.equals(allNulls)){
            text.setText(text.getText().substring(0, text.getText().indexOf(".")));
        }
    }
    private boolean hasComma(){
        if (text.getText().contains(".")){
            return true;
        }
        return false;
    }
    @FXML
    protected void onOperatorUnoClick(ActionEvent event){
        if (start || hasBadNotes()){
            return;
        }
        if (lastEnteredElemType.equals("Digit")){
            String num2 = "";
            String num1 = "";


            if (operatorEntered || modEntered){
                counter.setNum2(text.getText());
                num2 = counter.getNum2();
            }
            else {
                counter.setNum1(text.getText());
                num1 = counter.getNum1();
            }
            String value = ((Button)event.getSource()).getText();
            if (value.equals("1/x") && text.getText().equals("0")){
                return;
            }
            counter.setOperatorUno(value);
            String res = counter.count();
            if (modEntered){
                if (Double.parseDouble(res) != (int) Double.parseDouble(res)){
                    return;
                }
            }

            if (res.length() > 11){
                if(res.contains("E")){
                    String partE = res.substring(res.indexOf("E"));
                    int partELength = partE.length();
                    String resultString = res.substring(0, maxSymbols - partELength + 1) + partE;
                    text.setText(resultString);
                }
                else {
                    text.setText(res.substring(0, 12));
                }
            }
            else {
                text.setText(res);
            }
            if (text.getText().contains(".")){
                checkExtraNulls();
            }
            commaEntered = hasComma();
            if (value.equals("1/x")){
                if (!operatorEntered){
                    history.setText("1/" + num1);
                }
                else {
                    history.setText(counter.getNum1() + counter.getOperator() + "1/" + num2);

                }
            }
            else{
                if (!operatorEntered){
                    history.setText("sqrt(" + num1 + ")");
                }
                else {
                    history.setText(counter.getNum1() + counter.getOperator() + "sqrt(" + num2 + ")");

                }
            }
            historyHasScope();
            //modEntered = false;



        }



    }
    @FXML
    protected void onDeleteAllClick(){
        if (start){
            return;
        }
        start = true;
        text.setText("0");
        counter.setNum1("");
        counter.setNum2("");
        operatorEntered = false;
        lastEnteredElemType = "";
        counter.setOperator("");
        counter.setOperatorUno("");
        modEntered = false;
        commaEntered = false;
        history.setText("0");








    }
    public boolean hasBadNotes(){
        if (text.getText().equals("Деление на0!") || text.getText().equals("NaN") || text.getText().equals("Infinity")){
            start = true;
            return true;
        }
        return false;
    }
    @FXML
    protected void onModClick(){
        if (start || modEntered || hasBadNotes()){
            return;
        }
        if (lastEnteredElemType.equals("Digit") && !text.getText().contains(".") && !operatorEntered && !modEntered){
            counter.setOperator("mod");
            counter.setNum1(text.getText());
            lastEnteredElemType = "Operator";
            modEntered = true;
            operatorEntered = true;
            text.setText("mod");
            history.setText(counter.getNum1() + "mod");
            historyHasScope();
        }
    }
    private String receiveLastType(){
        if(text.getText().length() > 0){
            String lastElem = text.getText().substring(text.getText().length() - 1);
            Pattern pattern = Pattern.compile("[0-9]");
            Matcher matcher = pattern.matcher(lastElem);
            boolean isDigit = matcher.matches();
            if (isDigit){
                return "Digit";
            }
            pattern = Pattern.compile(".");
            matcher = pattern.matcher(lastElem);
            boolean isComma = matcher.matches();
            if (isComma){
                return "Comma";
            }
        }
        if (operatorEntered){
            return "Operator";
        }
        return "";


    }

    @FXML
    protected void onDeleteLastClick(){
        if (hasBadNotes() || text.getText().contains("E") || start || lastEnteredElemType.equals("Operator") ||
                text.getText().length() == 0){
            return;
        }
        if (text.getText().length() == 1 && !operatorEntered){
            onDeleteAllClick();
            return;
        }
        if (text.getText().substring(text.getText().length() - 1).equals(".")){
            commaEntered = false;
        }
        text.setText(text.getText().substring(0, text.getText().length() - 1));
        lastEnteredElemType = receiveLastType();
        if (operatorEntered){
            history.setText(counter.getNum1() + counter.getOperator() + text.getText());
        }
        else {
            history.setText(text.getText());
        }
        historyHasScope();

    }
    @FXML
    protected void changePlusMinus(){

        if (hasBadNotes() || lastEnteredElemType.equals("Operator") || text.getText().length() >= maxSymbols &&
                !text.getText().substring(0,1).equals("-") || start || text.getText().equals("0") ||
                lastEnteredElemType.equals("Comma")){
            return;
        }

        if (text.getText().substring(0,1).equals("-")){
            text.setText(text.getText().substring(1));
        }
        else{
            text.setText("-" + text.getText());
        }
        if (!operatorEntered){
            history.setText(text.getText());
        }
        else{
            if (counter.getOperator().equals("x^")){
                history.setText(counter.getNum1() + "^" + "(" + text.getText() + ")");
            }
            else{
                history.setText(counter.getNum1() + counter.getOperator() + "(" + text.getText() + ")");
            }

        }
    }
    private void historyHasScope(){
        if (history.getText().contains(")") && !history.getText().contains("q")){
            String lastElem = history.getText().substring(history.getText().length()- 1);
            history.setText(history.getText().substring(0, history.getText().length() - 2) + lastElem + ")");
        }
    }
    @FXML
    protected void onCommaClick(ActionEvent event){
        if (start || modEntered || hasBadNotes() || text.getText().contains(".") ||
                text.getText().length() >= maxSymbols || !lastEnteredElemType.equals("Digit") && !start){
            return;
        }

        String value = ((Button)event.getSource()).getText();
        text.setText(text.getText() + value);

        lastEnteredElemType = "Comma";
        commaEntered = true;
        if (!operatorEntered){
            history.setText(text.getText());
        }
        else{
            history.setText(counter.getNum1() + counter.getOperator() + text.getText());
        }
        historyHasScope();
    }
}