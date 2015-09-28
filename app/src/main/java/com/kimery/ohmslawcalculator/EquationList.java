package com.kimery.ohmslawcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EquationList extends AppCompatActivity {

    ListView listView;
    int itemPosition;
    String itemValue;
    String[] equationList = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_list);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        Bundle equationListData = getIntent().getExtras();
        if(equationListData == null){
            return;
        }

        String equations = equationListData.getString("equationList");

        equationList = equations.split(",");

        // List Item adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_layout, R.id.text, equationList);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                itemPosition = position;

                // ListView Clicked item value
                itemValue = (String) listView.getItemAtPosition(position);

                selectEquation();
                  }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_volts_equation_list, menu);
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

    //Selected equation is put into and intent and opened in Solve Equation class
    public void selectEquation(){

        if (itemPosition == 0) {
            final String equation = "V = R x I";

            Intent i = new Intent(this, SolveEquation.class);
            i.putExtra("equation", equation);
            startActivity(i);
        }
    }
}
