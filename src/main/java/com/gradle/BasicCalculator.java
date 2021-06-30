package com.gradle;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Basic Calculator
 * @author Alexa Lewis
 *
 * All of the functions of a basic calculator in a Java program
 */

public class BasicCalculator extends JFrame implements ActionListener{
    //attributes - javax.swing
    private JTextField jtDisplay;
    private JButton [][] btns;

    private ArrayList<Object> alNumbers;
    private ArrayList<String> alOpers;
    private String expression, input;
    private int idx, entry, memoryInt;
    private double dentry, memoryDoub;
    private Object memory;

    public static void main(String[] args){
        new BasicCalculator();
    }//main

    private BasicCalculator(){
        //instantiation
        expression = "";
        input = "";
        idx = 0;
        entry = 0;
        dentry = 0.0;
        memoryInt = 0;
        memoryDoub = 0.0;
        memory = new Object();
        alNumbers = new ArrayList<>();
        alOpers = new ArrayList<>();

        //display panel
        JPanel jpDisplay = new JPanel();
        jtDisplay = new JTextField("0",16);
        jtDisplay.setEditable(false);
        jtDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        jpDisplay.add(jtDisplay);

        //buttons panel
        btns = new JButton[8][8];
        JPanel jpButtons = new JPanel(new GridLayout(6,4));
        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 4; col++){
                JButton jb = new JButton();
                btns[row][col] = jb;
                btns[row][col].addActionListener(this);
                jpButtons.add(btns[row][col]);

            }//col
        }//row

        setButtons();

        //add panels to JFrame
        add(jpDisplay, BorderLayout.NORTH);
        add(jpButtons);

        //window settings
        setTitle("Basic Calculator");
        setPreferredSize(new Dimension(550,600));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }//constructor

    private void setButtons(){

        //first row
        //memory operations
        btns[0][0].setText("MC"); //mclear

        btns[0][1].setText("MR"); //mrecall

        btns[0][2].setText("M+"); //mplus

        btns[0][3].setText("M-"); //msubtract

        //second row - operations
        btns[1][0].setText("CE"); //clear entry

        btns[1][1].setText("C"); //clear

        btns[1][2].setText("(+-)"); //positive-negative

        btns[1][3].setText("%"); //percent

        //last column - operations
        btns[2][3].setText("/"); //divide

        btns[3][3].setText("*"); //multiply

        btns[4][3].setText("-"); //subtract

        btns[5][3].setText("+"); //plus

        //setting numbers 0-9
        btns[5][0].setText("0");

        int row = 4;
        int col = 0;
        int n = 1;
        String num;

        while(row > 1){
            while(col < 3){
                num = Integer.toString(n);
                btns[row][col].setText(num);
                n = Integer.parseInt(num);
                n++;
                col++;
            }
            col = 0;
            row--;
        }

        btns[5][1].setText("."); //decimal

        btns[5][2].setText("="); //equals

    }//setButtons

    private void doCalculation(){
        //printArrayList(alNumbers);
        operations();
        //if user wants to do a further calculation
        input = jtDisplay.getText();
        alNumbers.remove(0);
    }//doCalculation

    private void operations(){

        int curr = 0;
        int res;
        double dcurr = 0.0;
        double dres;
        Object objResult = new Object();
        boolean bothInt = false;
        boolean bothDoub = false;
        boolean IntDoub = false;
        boolean DoubInt = false;

        /*printArrayList(alNumbers);
        printArrayList(alOpers);*/
        if(alNumbers.size() > 1 && !expression.endsWith("=")){
            if(alNumbers.get(0).getClass() == Integer.class){
                entry = Integer.parseInt(alNumbers.get(0).toString());

                if(alNumbers.get(1).getClass() == Integer.class){
                    curr = Integer.parseInt(alNumbers.get(1).toString());
                    bothInt = true;
                }
                else{
                    dcurr = Double.parseDouble(alNumbers.get(1).toString());
                    IntDoub = true;
                }
            }
            else if(alNumbers.get(0).getClass() == Double.class){
                dentry = Double.parseDouble(alNumbers.get(0).toString());

                if(alNumbers.get(1).getClass() == Double.class){
                    dcurr = Double.parseDouble(alNumbers.get(1).toString());
                    bothDoub = true;
                }
                else{
                    curr = Integer.parseInt(alNumbers.get(1).toString());
                    DoubInt = true;
                }
            }

            //operations
            switch(alOpers.get(idx)){
                case "/": //division
                    if(bothDoub && dcurr != 0){
                        dres = dentry / dcurr;
                        objResult = dres;
                    }
                    else if(bothInt && curr != 0){
                        if(entry % curr == 0){
                            res = entry / curr;
                            System.out.println("RESULT = " + res);
                            objResult = res;
                        }
                        else{ //just in case the divisor is not a factor of the dividend
                            dres = (double)entry / curr;
                            System.out.println("RESULT = " + dres);
                            objResult = dres;
                        }
                    }
                    else if(DoubInt && curr != 0){
                        dres = dentry / curr;
                        objResult = dres;
                    }
                    else if(IntDoub && dcurr != 0){
                        dres = entry / dcurr;
                        objResult = dres;
                    }
                    else{
                        objResult = "Cannot divide by zero";
                        break;
                    }
                    break;
                case "*": //multiplication
                    if(bothDoub){
                        dres = dentry * dcurr;
                        objResult = dres;
                    }
                    else if(bothInt){
                        res = entry * curr;
                        System.out.println("RESULT = " + res);
                        objResult = res;
                    }
                    else if(DoubInt){
                        dres = dentry * curr;
                        objResult = dres;
                    }
                    else if(IntDoub){
                        dres = entry * dcurr;
                        objResult = dres;
                    }
                    break;
                case "-": //subtraction
                    if(bothDoub){
                        dres = dentry - dcurr;
                        objResult = dres;
                    }
                    else if(bothInt){
                        res = entry - curr;
                        System.out.println("RESULT = " + res);
                        objResult = res;
                    }
                    else if(DoubInt){
                        dres = dentry - curr;
                        objResult = dres;
                    }
                    else if(IntDoub){
                        dres = entry - dcurr;
                        objResult = dres;
                    }
                    break;
                case "+": //addition
                    if(bothDoub){
                        dres = dentry + dcurr;
                        objResult = dres;
                    }
                    else if(bothInt){
                        res = entry + curr;
                        System.out.println("RESULT = " + res);
                        objResult = res;
                    }
                    else if(DoubInt){
                        dres = dentry + curr;
                        objResult = dres;
                    }
                    else if(IntDoub){
                        dres = entry + dcurr;
                        objResult = dres;
                    }
                    break;
                default:
                    System.out.println("--------------");
            }//switch

            idx++;
            System.out.println(objResult);
            alNumbers.set(0, objResult);
            alNumbers.remove(alNumbers.get(alNumbers.size() - 1)); //remove last number
            jtDisplay.setText(objResult.toString());
        }
        JButton jb = btns[5][1];
        if(!jb.isEnabled()){
            jb.setEnabled(true);
        }
    }//operations

    private void memoryOperations(String mOper){
        switch(mOper){
            case "MC": //memory clear
                memoryInt = 0;
                memoryDoub = 0;
                memory = 0;
                break;
            case "MR": //memory recall
                input = memory.toString();
                jtDisplay.setText(input);
                break;
            case "M+": //memory plus
                if(input.contains(".")){
                    dentry = Double.parseDouble(input);
                    memoryDoub += dentry;
                    System.out.println("Memory(double): " + memoryDoub);
                    memory = memoryDoub;
                }
                else{
                    entry = Integer.parseInt(input);
                    memoryInt += entry;
                    System.out.println("Memory(integer): " + memoryInt);
                    memory = memoryInt;
                }
                break;
            case "M-": //memory subtract
                if(input.contains(".")){
                    dentry = Double.parseDouble(input);
                    memoryDoub -= dentry;
                    System.out.println("Memory(double): " + memoryDoub);
                    memory = memoryDoub;
                }
                else{
                    entry = Integer.parseInt(input);
                    memoryInt -= entry;
                    System.out.println("Memory(integer): " + memoryInt);
                    memory = memoryInt;
                }
                break;
            default:
                System.out.println("--------------");
                break;
        }//switch
    }//memoryOperation

    private void inputSetUp(String set){
        //decimal button
        JButton jb = btns[5][1];

        switch(set){
            case "(+-)":
                //number is already negative - making it positive again
                if(input.startsWith("-")){
                    input = input.substring(1);
                }
                //making number negative
                else{
                    input = "-".concat(input);
                }
                jtDisplay.setText(input);
                break;
            case "%": //percent
                if(input.contains(".")){
                    dentry = Double.parseDouble(input) / 100;
                }
                else{
                    dentry = Double.parseDouble(input) / 100;
                }
                jtDisplay.setText(Double.toString(dentry));
                break;
            case "C": //clear all
                input = "";
                expression = "";
                idx = 0;
                entry = 0;
                dentry = 0;
                alNumbers.clear();
                alOpers.clear();
                jtDisplay.setText("0");

                if(!jb.isEnabled()){
                    jb.setEnabled(true);
                }
                break;
            case "CE": //clear entry
                entry = 0;
                dentry = 0;
                input = "";

                //clear last number
                if(alNumbers.isEmpty()){
                    System.out.println("No numbers stored yet");
                }
                else{
                    alNumbers.remove(alNumbers.size() - 1);
                    //System.out.println(alNumbers);
                }
                jtDisplay.setText("0");

                if(!jb.isEnabled()){
                    jb.setEnabled(true);
                }
                break;
            default:
                System.out.println("--------------");
                break;
        }//switch

    }//inputSetUp

    /*private void printArrayList(ArrayList al){
        System.out.println("ArrayList:");
        for(Object obj : al){
            System.out.println(obj);
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent ae){
        JButton jb = (JButton)ae.getSource();

        String btnText = jb.getText();

        //makes sure the numbers are clicked first
        try {
            //input has to not be empty and have a decimal pt
            if(!input.isEmpty() && input.contains(".")){
                dentry = Double.parseDouble(btnText);
            }
            else{
                entry = Integer.parseInt(btnText);
            }

            input += btnText;

            //add to jTextfield
            jtDisplay.setText(input);

        } catch (NumberFormatException nfe) {
            if(btnText.equals(".")){ //changing the number to a decimal -- cannot be undone have to clear input
                jb.setEnabled(false);
                input += btnText;
                jtDisplay.setText(input);
            }

            if(btnText.startsWith("C") || btnText.startsWith("(") || btnText.equals("%")){
                //input set up: percent, clear, clear entry, positive/negative

                inputSetUp(btnText);
            }
            else if(btnText.equals("/") || btnText.equals("*") || btnText.equals("-") || btnText.equals("+")){
                System.out.println("Equals = " + input);
                if(input.contains(".")){
                    dentry = Double.parseDouble(input);
                    System.out.println("DInput: " + dentry);
                    alNumbers.add(dentry);

                }
                else{
                    entry = Integer.parseInt(input);
                    System.out.println("Input: " + entry);
                    alNumbers.add(entry);
                }

                //the button clicked was one of the operators
                alOpers.add(btnText);
                operations();
                input = ""; //reset the input to avoid unnecessary concatenation
            }
            else if(btnText.equals("=")){
                if(input.contains(".")){
                    dentry = Double.parseDouble(input);
                    System.out.println("DInput: " + dentry);
                    alNumbers.add(dentry);
                }
                else{
                    entry = Integer.parseInt(input);
                    System.out.println("Input: " + entry);
                    alNumbers.add(entry);
                }

                //alOpers.add(btnText);
                doCalculation();
            }
            else if(btnText.startsWith("M")){
                memoryOperations(btnText);
            }

        }//try-catch

        expression += btnText;
        System.out.println("EXP > " + expression);

    }//actionPerformed
}//end class
