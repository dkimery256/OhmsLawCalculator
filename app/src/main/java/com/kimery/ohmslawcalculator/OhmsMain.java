package com.kimery.ohmslawcalculator;

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
    private String equationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohms_main);

        //set reference
        voltageLabel = (TextView) findViewById(R.id.voltageLabel);

        //set listener
        voltageLabel.setOnClickListener(this);
       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ohms_main, menu);
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
        @Override
        public void onClick(View v) {
            // Switch will handle which equation list class to access
            switch(v.getId()){
                case R.id.voltageLabel:

                    equationList =  "V = R x I,V = P \u00F7 I,V = P \u221A R";
                    Intent volts = new Intent(this, EquationList.class);
                    volts.putExtra("equationList", equationList);
                    startActivity(volts);
                    break;
                case R.id.resistanceLabel:
                    equationList =  "V = R x I,V = P \u00F7 I,V = P \u221A R";

                    Intent ohms  = new Intent(this, EquationList.class);
                    ohms.putExtra("equationList", equationList);
                    startActivity(ohms);
                    break;
            }
        }


}
