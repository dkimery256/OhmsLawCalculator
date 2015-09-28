package com.kimery.ohmslawcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SolveEquation extends AppCompatActivity {

    private static final String[] VOLT_VALUES = {"V, mV, \u03BC"+"V"};
    private static final String[] OHM_VALUES = {"Ohms, mOhms, \u03BC"+"Ohms"};
    private static final String[] CURRENT_VALUES = {"A, mA, \u03BC"+"A"};
    private TextView equationView;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_equation);

        equationView = (TextView) findViewById(R.id.equationView);

        Bundle equationData = getIntent().getExtras();
        if(equationData == null){
            return;
        }

        String equation = equationData.getString("equation");
        equationView.setText(equation);
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
}
