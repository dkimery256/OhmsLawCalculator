package com.kimery.ohmslawcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Spinner;
import android.widget.TextView;

public class SolveEquation extends AppCompatActivity implements
        OnEditorActionListener, OnItemSelectedListener {

    private String equation;
    private TextView equationView;
    private Spinner spinnerValue1;
    private Spinner spinnerValue2;
    private EditText value1;
    private EditText value2;

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

        //Set listeners for instance variables
        spinnerValue1.setOnItemSelectedListener(this);
        spinnerValue2.setOnItemSelectedListener(this);
        value1.setOnEditorActionListener(this);
        value1.setImeOptions(EditorInfo.IME_ACTION_DONE);
        value2.setOnEditorActionListener(this);
        value2.setImeOptions(EditorInfo.IME_ACTION_DONE);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solve_equation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            case "I = √P ÷ R\"":
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
                boolean selected = false;
                String firstValue;
                String secondValue;
                String update;
                String spinnerItem1;
                String spinnerItem2;
                Double firstValueNum = null;
                Double secondValueNum = null;
                int spinnerPosition1;
                int spinnerPosition2;

                while(!selected){
                     switch (equation) {
                        case "V = R x I":
                            firstValue = value1.getText().toString();
                            secondValue = value2.getText().toString();
                            spinnerPosition1 = spinnerValue1.getSelectedItemPosition();
                            spinnerPosition2 = spinnerValue2.getSelectedItemPosition();
                            spinnerItem1 = spinnerValue1.getSelectedItem().toString();
                            spinnerItem2 = spinnerValue2.getSelectedItem().toString();
                            if(!firstValue.equals(""))firstValueNum = Double.parseDouble(firstValue);
                            if(!secondValue.equals(""))secondValueNum = Double.parseDouble(secondValue);
                            if(firstValueNum != null && secondValueNum == null && spinnerPosition1 > 0) {
                                update = "V = " + firstValueNum + spinnerItem1 + " x I";
                                updateUI(update);
                                selected = true;
                            }
                            if(firstValueNum == null && secondValueNum != null && spinnerPosition2 > 0) {
                                update = "V = R x " + secondValueNum + spinnerItem2;
                                updateUI(update);
                                selected = true;
                            }
                            if(firstValueNum != null && secondValueNum != null && spinnerPosition1 > 0
                                    && spinnerPosition2 > 0) {
                                double volts = firstValueNum * secondValueNum;
                                update = volts + " = " + firstValueNum + spinnerItem1 + " x " +
                                        secondValueNum + spinnerItem2;
                                updateUI(update);
                                selected = true;
                            }
                            break;
                     }
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
    ///////////////////
    //Event Handlers//
    /////////////////

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            //Call thread method to check inputs for the equation
            calculateEquation();
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
}
