package com.kimery.ohmslawcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class OhmsMain extends AppCompatActivity implements OnClickListener {

    TextView voltageLabel;
    TextView resistanceLabel;
    TextView currentLabel;
    private String equationList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohms_main);

        //set reference
        voltageLabel = (TextView) findViewById(R.id.voltageLabel);
        resistanceLabel = (TextView) findViewById(R.id.resistanceLabel);
        currentLabel = (TextView) findViewById(R.id.currentLabel);

        //set listener
        voltageLabel.setOnClickListener(this);
        resistanceLabel.setOnClickListener(this);
        currentLabel.setOnClickListener(this);
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
        @Override
        public void onClick(View v) {
            // Switch will handle which equation list class to access
                //Use comma delimited string for the formulas to split into arrays later
            switch(v.getId()){
                case R.id.voltageLabel:
                    equationList =  "V = R x I,V = P ÷ I,V = √P x R";
                    Intent volts = new Intent(this, EquationList.class);
                    volts.putExtra("equationList", equationList);
                    startActivity(volts);
                    break;
                case R.id.resistanceLabel:
                    equationList =  "R = V ÷ I,R = V² ÷ P,R = P ÷ I²";
                    Intent ohms  = new Intent(this, EquationList.class);
                    ohms.putExtra("equationList", equationList);
                    startActivity(ohms);
                    break;
                case R.id.currentLabel:
                    equationList = "I = V ÷ R,I = P ÷ V,I = √P ÷ R";
                    Intent current = new Intent(this, EquationList.class);
                    current.putExtra("equationList", equationList);
                    startActivity(current);
            }
        }
    //Exit app
    @Override
    public void onBackPressed(){
        this.finishAffinity();
    }
}
