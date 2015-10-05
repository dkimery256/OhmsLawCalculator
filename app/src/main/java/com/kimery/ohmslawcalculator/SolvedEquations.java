package com.kimery.ohmslawcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private String[] equationList;
    private String itemValue;
    private int itemPosition;
    private int selected;

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
        equationList = equations.split(",");

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
                        selected = position + 1;
                    }else{
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.navy));
                    }

                }

            }

        });

    }

    public String toString() {

        String output = getId() + ". " + getEquation();

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

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public int getSelected(){
        return selected;
    }
    //Deleted selected equation
    public void deleteEquation(View view) {
        dbHandler.deleteEquation(selected);
        Intent i = new Intent(this, SolvedEquations.class);
        startActivity(i);
    }
}
