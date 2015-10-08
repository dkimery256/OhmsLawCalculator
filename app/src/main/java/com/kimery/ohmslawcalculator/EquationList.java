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
        //Set correct title
        switch (equations){
            case "V = R x I,V = P ÷ I,V = √P x R":
                setTitle("Voltage Equations");
                break;
            case "R = V ÷ I,R = V² ÷ P,R = P ÷ I²":
                setTitle("Resistance Equations");
                break;
            case "I = V ÷ R,I = P ÷ V,I = √P ÷ R":
                setTitle("Current Equations");
                break;
        }
        //Split used to spilt comma delimited string into string array
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

                for(int i=0; i<parent.getChildCount(); i++)
                {
                    if(i == position){
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lime));

                    }else{
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.navy));
                    }

                }

                selectEquation();
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

    //Selected equation is put into and intent and opened in Solve Equation class
    public void selectEquation(){
        //Assign each string equation element to its own variable
        String equation1 = equationList[0];
        String equation2 = equationList[1];
        String equation3 = equationList[2];
        Intent i = new Intent(this, SolveEquation.class);
        if (itemPosition == 0) {
            i.putExtra("equation", equation1);
            startActivity(i);
        }else if(itemPosition == 1){
            i.putExtra("equation", equation2);
            startActivity(i);
        }else if(itemPosition == 2){
            i.putExtra("equation", equation3);
            startActivity(i);
        }
    }
}
