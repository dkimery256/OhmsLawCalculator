package com.kimery.ohmslawcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SolvedEquations extends AppCompatActivity {

    private int id;
    private String equation;
    private ListView listView;
    private SolvedEquationsDB dbHandler;
    private String equations;
    private String[] list;
    private String itemValue;
    private int itemPosition;
    private int count = 0;

    public SolvedEquations(){

    }

    public SolvedEquations(int newId, String newEquation){
        id = newId;
        equation = newEquation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_equations);

        listView = (ListView) findViewById(R.id.list);
        dbHandler = new SolvedEquationsDB(this, null, null, 1);
        equations = dbHandler.getArray(dbHandler.getEquations());
        list = equations.split(",");

        //Get rid of id from database query
        int size = (list.length / 2);
        String[] equationList = new String[size];
        for(int i = 0; i < list.length; i++){
            if(i != 0 && i % 2 != 0){
                String addElement = list[i];
                equationList[count] = addElement;
                count++;
            }
        }

        // List Item adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_layout, R.id.text, equationList);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                itemPosition = position;
                // ListView Clicked item value
                itemValue = (String) listView.getItemAtPosition(position);

                for(int i=0; i<parent.getChildCount(); i++)
                {
                    if(i == position){
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lime));

                    }else{
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.navy));
                    }
                }
            }
        });
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

    public String toString() {

        String output = getId() + "," + getEquation();

        return output;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    //Deleted selected equation
    public void deleteEquation(View view) {
        dbHandler.deleteEquation(itemValue);
        Intent i = new Intent(this, SolvedEquations.class);
        startActivity(i);
    }
    //go back to main activity
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, OhmsMain.class);
        startActivity(i);
    }
}
