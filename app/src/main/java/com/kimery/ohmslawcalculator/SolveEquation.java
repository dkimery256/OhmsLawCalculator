package com.kimery.ohmslawcalculator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SolveEquation extends AppCompatActivity implements
        OnEditorActionListener, OnItemSelectedListener {

    private String equation;
    private TextView equationView;
    private Spinner spinnerValue1;
    private Spinner spinnerValue2;
    private EditText value1;
    private EditText value2;
    private Button veiwSavedEquation;
    private Button saveEquation;
    private boolean allSelected;
    SolvedEquationsDB dbHandler = new SolvedEquationsDB(this, null, null, 1);

    //Build the object that will be the notification
    NotificationCompat.Builder notify;
    private static final int uniqueID = 7676; //the notification has to assigned a unique ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_equation);

        //Set references to instance variables
        equationView = (TextView) findViewById(R.id.equationView);
        spinnerValue1 = (Spinner) findViewById(R.id.spinnerValue1);
        spinnerValue2 = (Spinner) findViewById(R.id.spinnerValue2);
        value1 = (EditText) findViewById(R.id.value1);
        value2 = (EditText) findViewById(R.id.value2);
        veiwSavedEquation = (Button) findViewById(R.id.viewSavedEquationButton);
        saveEquation = (Button) findViewById(R.id.saveEquationButton);

        //Set listeners for instance variables
        spinnerValue1.setOnItemSelectedListener(this);
        spinnerValue2.setOnItemSelectedListener(this);
        value1.setOnEditorActionListener(this);
        value1.setImeOptions(EditorInfo.IME_ACTION_DONE);
        value2.setOnEditorActionListener(this);
        value2.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //build new notification
        notify = new NotificationCompat.Builder(this);
        //remove notification once it has been visited
        notify.setAutoCancel(true);

        //Get data from equation list class
        Bundle equationData = getIntent().getExtras();
        if(equationData == null){
            return;
        }

        //Store and display data from equation list class
        equation = equationData.getString("equation");
        equationView.setText(equation);

        //Set values of spinners
        setValues(equation);
        calculateEquation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_ohms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //This method sets the values for operations with regular, millis, and micros
    public void setValues(String equation){
        ArrayAdapter<CharSequence> adapter1;
        ArrayAdapter<CharSequence> adapter2;
        switch (equation){
            case "V = R x I":
                 adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.ohm_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.current_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "V = P ÷ I":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.power_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.current_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "V = √P x R":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.power_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.ohm_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "R = V ÷ I":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.volt_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.current_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "R = V² ÷ P":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.volt_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.power_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "R = P ÷ I²":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.power_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.current_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "I = V ÷ R":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.volt_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.ohm_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "I = P ÷ V":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.power_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.volt_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
            case "I = √P ÷ R":
                adapter1 = ArrayAdapter.createFromResource(
                        this, R.array.power_value_array, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue1.setAdapter(adapter1);

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.ohm_value_array, android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerValue2.setAdapter(adapter2);
                break;
        }

    }
    public void calculateEquation(){

        //Start Thread to make sure values have been entered
        Runnable r = new Runnable() {
            @Override
            public void run() {
                allSelected = false;
                String firstValue;
                String secondValue;
                String update;
                String spinnerItem1;
                String spinnerItem2;
                Double firstValueNum = null;
                Double secondValueNum = null;
                int spinnerPosition1;
                int spinnerPosition2;
                DecimalFormat number = new DecimalFormat("###.###");

                switch (equation) {
                    case "V = R x I":
                       while(!allSelected) {
                           firstValue = value1.getText().toString();
                           secondValue = value2.getText().toString();
                           spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                           spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                           spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                           spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                           if (!firstValue.equals(""))
                               firstValueNum = Double.parseDouble(firstValue);
                           if (!secondValue.equals(""))
                               secondValueNum = Double.parseDouble(secondValue);
                           if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                               update = "V = " + firstValue + spinnerItem1 + " x I";
                               updateUI(update);
                           }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                               update = "V = R x " + secondValue + spinnerItem2;
                               updateUI(update);
                           }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                   && spinnerPosition2 > 0) {
                               String volts = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                               update = volts + " = " + firstValue + spinnerItem1 + " x " +
                                       secondValue + spinnerItem2;
                               updateUI(update);
                           }
                       }
                        break;
                    case "V = P ÷ I":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "V = " + firstValue + spinnerItem1 + " ÷ I";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "V = P ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String volts = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = volts + " = " + firstValue + spinnerItem1 + " ÷ " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "V = √P x R":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "V = " + firstValue + spinnerItem1 + " x R";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "V = √P x " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String volts = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = volts + " = " + firstValue + spinnerItem1 + " x " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "R = V ÷ I":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "R = " + firstValue + spinnerItem1 + " ÷ I";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "R = V ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String ohms = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = ohms + " = " + firstValue + spinnerItem1 + " x " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "R = V² ÷ P":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "R = " + firstValue + spinnerItem1 + " ÷ P";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "R = V² ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String ohms = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = ohms + " = " + firstValue + spinnerItem1 + " ÷ " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "R = P ÷ I²":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "R = " + firstValue + spinnerItem1 + " ÷ I²";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "R = P ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String ohms = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = ohms + " = " + firstValue + spinnerItem1 + " ÷ " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "I = V ÷ R":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "I = " + firstValue + spinnerItem1 + " ÷ R";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "I = V ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String ohms = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = ohms + " = " + firstValue + spinnerItem1 + " ÷ " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "I = P ÷ V":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "I = " + firstValue + spinnerItem1 + " ÷ V";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "I = R ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String ohms = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = ohms + " = " + firstValue + spinnerItem1 + " ÷ " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    case "I = √P ÷ R":
                        while(!allSelected) {
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if (!firstValue.equals(""))
                                firstValueNum = Double.parseDouble(firstValue);
                            if (!secondValue.equals(""))
                                secondValueNum = Double.parseDouble(secondValue);
                            if (firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "I = " + firstValue + spinnerItem1 + " ÷ R";
                                updateUI(update);
                            }if (firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "I = √P ÷ " + secondValue + spinnerItem2;
                                updateUI(update);
                            }if (firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                String ohms = solveEquation(spinnerPosition1, spinnerPosition2, firstValueNum, secondValueNum);
                                update = ohms + " = " + firstValue + spinnerItem1 + " ÷ " +
                                        secondValue + spinnerItem2;
                                updateUI(update);
                            }
                        }
                        break;
                    }
            }
        };
        new Thread(r).start();
    }

    public void updateUI(final String UPDATE){
        equationView.post(new Runnable() {
            @Override
            public void run() {
                equationView.setText(UPDATE);
            }
        });

    }
    //solve equation based on values entered
    public String solveEquation(int position1, int position2, double value1, double value2 ){
        double answer = 0;
        double milli = 0.001;
        double micro = 0.000001;
        String answerStr = "";
        String type;

        //choose correct notation
        switch (position1){
            case 2:
                value1 *= milli;
                break;
            case 3:
                value1 *= micro;
                break;
        }
        switch (position2){
            case 2:
                value2 *= milli;
                break;
            case 3:
                value2 *= micro;
                break;
        }
        //Start solving for answer
        switch (equation) {
            case "V = R x I":
                type = "V";
                answer = value1 * value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "V = P ÷ I":
                type = "V";
                answer = value1 / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "V = √P x R":
                type = "V";
                answer = Math.sqrt(value1) / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "R = V ÷ I":
                type = "R";
                answer = value1 / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "R = V² ÷ P":
                type = "R";
                answer = Math.pow(value1, 2) / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "R = P ÷ I²":
                type = "R";
                answer =  value1 / Math.pow(value2, 2);
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "I = V ÷ R":
                type = "I";
                answer =  value1 / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "I = P ÷ V":
                type = "I";
                answer =  value1 / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;
            case "I = √P ÷ R":
                type = "I";
                answer =  Math.sqrt(value1) / value2;
                //revert notation
                answerStr = revertNotation(answer, type);
                break;

        }
        return answerStr;
    }
    //Method revert the answer back to proper notation.
    public String revertNotation(double answer, String type) {
        int zeroCheck = 0;
        double milli = 0.001;
        double micro = 0.000001;
        char index = ' ';

        //Number format to get rid of scientific notation
        NumberFormat number = new DecimalFormat("###.############");
        NumberFormat answerNumber = new DecimalFormat("###.###");
        String answerStr = number.format(answer);

        //If answer is less than 1 start answer notation
        if (answer < 1){

            //Count index of decimal places to determine answer notation value
            for (int i = 0; i < answerStr.length(); i++) {
                index = answerStr.charAt(i);
                if(index == 48 || index == 46) {
                    zeroCheck++;
                }else{
                   if(zeroCheck == 1) {
                       break;
                   }else{
                       zeroCheck --;
                       break;
                   }
                }
            }
            //Add correct notation symbols
            if (zeroCheck >= 1 && zeroCheck <= 3){
                answer /= milli;
                answerStr = answerNumber.format(answer);
                answerStr = answerStr + "m";
            }
            if (zeroCheck > 3) {
                answer /= micro;
                answerStr = answerNumber.format(answer);
                answerStr = answerStr + "μ";
            }
        }else {
            answerStr = answerNumber.format(answer);
        }
        switch(type){
            case "V":
                answerStr = answerStr+"V";
                break;
            case "I":
                answerStr = answerStr+"A";
                break;
            case "R":
                answerStr = answerStr+"Ω";
        }

        return answerStr;
    }
    ///////////////////
    //Event Handlers//
    /////////////////

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            //Call thread method to check inputs for the equation
            //calculateEquation();
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        //Do nothing
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
    //Call the data base save equation method

    public void saveEquation(View view) {
        allSelected = true;
        String savedEquation = equationView.getText().toString();
        dbHandler.getEquations();
        dbHandler.saveEquation(savedEquation);
        Toast.makeText(getApplicationContext(), "Equation Saved", Toast.LENGTH_SHORT).show();
        notifyEquationSaved();
    }

    public void viewSavedEquations(View view) {
        allSelected = true;
        Intent i = new Intent(this, SolvedEquations.class);
        startActivity(i);
    }
    //Notification for a saved equation
    public void notifyEquationSaved(){
        notify.setSmallIcon(R.mipmap.ic_launcher);
        notify.setTicker("Equation Saved");
        notify.setWhen(System.currentTimeMillis());
        notify.setContentTitle("Ohms Law Calculator");
        notify.setContentText("An Equation has been saved to the Database");

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notify.setSound(alarmSound);

        //send the notify to the home screen
        Intent i = new Intent(this, SolvedEquations.class);
        //give the device access to perform this intent by calling pending intent
        PendingIntent pi = PendingIntent.getActivity(this, 0 , i , PendingIntent.FLAG_UPDATE_CURRENT);
        notify.setContentIntent(pi);

        //send out the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notify.build());
    }
    @Override
    public void onBackPressed(){
        allSelected = true;
        Intent i = new Intent(this, OhmsMain.class);
        startActivity(i);
    }
}
