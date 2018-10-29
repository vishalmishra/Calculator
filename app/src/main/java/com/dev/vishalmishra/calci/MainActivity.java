package com.dev.vishalmishra.calci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView tv,textview;
    String display = "";
    String currentOperator = "";
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view=findViewById(R.id.redis);
        tv = (TextView)view.findViewById(R.id.ent);
        tv.setText(display);


        ImageButton bback=findViewById(R.id.bback);
       textview=(TextView) view.findViewById(R.id.res);


    }

    private void updateScreen(){
        tv.setText(display);
    }

    public void onClickNumber(View v){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        if(op!='+'&&op!='-'&&op!='*'&&op!='/')return false;
        return true;
        
    }

    public void onClickOperator(View v){
        if(display == "") return;

        Button b = (Button)v;

        if(result != ""){
            String d = result;
            clear();
            display = d;
        }

        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
        textview.setText(result);
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }
    public void onClickBack(View v){
        try{
        StringBuilder stringBuilder=new StringBuilder(display);
        if(isOperator(stringBuilder.charAt(stringBuilder.length()-1)))
            currentOperator="";
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        display=stringBuilder.toString();
        tv.setText(display);
        if(textview.getText()!="")
            textview.setText("");
        }
        catch (Exception e){
            return;}
    }
    public void onClickSign(View v){
        if(currentOperator==""){
            double a=Double.parseDouble(display)*(-1);
            display=Double.toString(a);
            tv.setText(display);
        }


    }
    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getResult(){
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) return;
        tv.setText(display);
        textview.setText("="+String.valueOf(result));
    }
}
