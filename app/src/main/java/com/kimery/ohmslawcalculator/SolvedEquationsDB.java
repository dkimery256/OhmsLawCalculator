package com.kimery.ohmslawcalculator;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import java.util.ArrayList;

public class SolvedEquationsDB extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "savedEquations.db";
    private static final String TABLE_SAVED_EQUATIONS = "savedEquations";
    private static final String COLUMN_ID = "_id";
    private static final int COLUMN_ID_COL = 0;
    private static final String COLUMN_EQUATION = "equation";
    private static final int COLUMN_EQUATION_COL = 1;
    private int entries = 0;
    SolvedEquations newEquation = new SolvedEquations();
    private ArrayList<SolvedEquations> equationEntries = new ArrayList<SolvedEquations>();

    public SolvedEquationsDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static String getTableSavedEquations() {
        return TABLE_SAVED_EQUATIONS;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE =
                    "CREATE TABLE "    + getTableSavedEquations() + "(" +
                            getColumnId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            getColumnEquation() + " CHAR);";

            db.execSQL(CREATE_TABLE);
        String insert = "INSERT INTO " + getTableSavedEquations() + " VALUES (1, '20V = 2Ω * 2A')";
        db.execSQL(insert);
        insert = "INSERT INTO " + getTableSavedEquations() + " VALUES (2, '120V = 30Ω * 4A')";
        db.execSQL(insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + getTableSavedEquations());
        onCreate(db);

    }
    public ArrayList<SolvedEquations> getEquations() {
        if (equationEntries.size() < 1){

            String query = "SELECT * FROM " + getTableSavedEquations() + ";";

            SQLiteDatabase db = getReadableDatabase();
           // cursor point to a location in your results
            Cursor c = db.rawQuery(query, null);
            // move to the first row in your results
            c.moveToFirst();
            //Add tip objects to the tips array list
            while (!c.isAfterLast()) {

                int id = c.getInt(getColumnIdCol());
                String equation = c.getString(getColumnEquationCol());

                newEquation = new SolvedEquations(id, equation);

                equationEntries.add(newEquation);
                //increment entry number for later inserts
                entries++;
                c.moveToNext();
            }
            //close database
            db.close();
        }else{

        }
        return getEquationEntries();
    }
    //Turn array list elements into string
    public String getArray(ArrayList <SolvedEquations> t){
        String output = "";
        String comma = ",";
        //loop to separate the array elements
        for ( int i = 0; i < t.size(); i ++ ){
            String data = t.get(i).toString();
            output = output + data;
            if(i != t.size() - 1)
            output = output + comma;
        }
        return output;
    }
    //save the equation to the database
    public void saveEquation(String equation) {
       //create new object to add to the array list
        int entry = entries + 1;
        SolvedEquations saveEquation = new SolvedEquations(entry, equation);
        SQLiteDatabase db = getWritableDatabase();
        //Insert equation data

        String insert = "INSERT INTO " + TABLE_SAVED_EQUATIONS + " VALUES (" + entry + ", '" + equation + "');";
        db.execSQL(insert);
        //increment entry number for later inserts
        entries++;
        //Add object to array list for later use of the getTips method
        equationEntries.add(saveEquation);
        db.close();
    }
    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static int getColumnIdCol() {
        return COLUMN_ID_COL;
    }

    public static String getColumnEquation() {
        return COLUMN_EQUATION;
    }

    public static int getColumnEquationCol() {
        return COLUMN_EQUATION_COL;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public ArrayList<SolvedEquations> getEquationEntries() {
        return equationEntries;
    }

    public void setEquationEntries(ArrayList<SolvedEquations> equationEntries) {
        this.equationEntries = equationEntries;
    }
    public void deleteEquation(int selected){
        SQLiteDatabase db = getWritableDatabase();
        SolvedEquations deleteEquation = new SolvedEquations();
        String delete = "DELETE FROM " + TABLE_SAVED_EQUATIONS + " WHERE " + COLUMN_ID +
                "=" + selected + ";";
        db.execSQL(delete);
        equationEntries.trimToSize();
    }
}
