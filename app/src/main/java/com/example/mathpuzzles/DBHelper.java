package com.example.mathpuzzles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private final String TABLE_NAME = "PuzzleRecords";
    Context context;

    public DBHelper(Context context) {
        super(context, "MathPuzzles.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create the table structure to store data
        String query = "Create table "+TABLE_NAME +"(" +
                "level_id Integer primary key autoincrement," +
                "answer int,"+
                "hint text,"+
                "level_progress boolean"+
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String query = "Drop table if exists "+TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void insertData(){
        SQLiteDatabase db = this.getWritableDatabase();

        //create an array which have all answer and insert them one by one into database
        int[] answerArray = {72,40,2,12,15,8,27,140,90,20,25,11,8,32,21,33,4,6,4,3,60,1,17,2,32,81,42,3,1241,125,9,1,35,111,98,129,44,3,128,1440,
        50,109,91,25};
        String[] hintArray = {"Multiply","Observe picture","11","Use Math rule","Observe","Follow Sequence","Observe picture","Multiply","Multiply","Observe picture",
        "Average","Observe picture","Square","x2","Observe picture","(AA)+(2C)+B","11","(AA)-(BC)","Close circle","Observe",
        "Multiply","Multiply","+","Start from end","Table of 2","Algebrically","3","Cross","Cube","Square",
        "Observe carefully","Square","Observe carefully","Sequence","Square","Follow Sequence","Observe carefully","+","Cube","Multiply First No. at tenth place",
        "Increasing Order","x^3 - y^2","/2","x"};

        for(int i=0; i < answerArray.length; i++){
            ContentValues values = new ContentValues();
            values.put("answer",answerArray[i]);
            values.put("hint",hintArray[i]);
            values.put("level_progress",false);
            db.insert(TABLE_NAME,null,values);
        }
        db.close();
    }

    public int getAnswer(int level_id){
        SQLiteDatabase db = this.getReadableDatabase();

        //get the location at the position
        Cursor cursor = db.rawQuery("Select answer from "+TABLE_NAME+" where level_id="+level_id,null);
        int answer = 0;
        if(cursor.moveToFirst()){
            answer = cursor.getInt(0);
        }
         return answer;  //It can return the int answer
    }

    public String getHint(int level_id){
        SQLiteDatabase db = this.getReadableDatabase();

        //get the location at the position
        Cursor cursor = db.rawQuery("Select hint from "+TABLE_NAME+" where level_id="+level_id,null);
        String hint = "";
        if(cursor.moveToFirst()){
            hint = cursor.getString(0);
        }
        return hint;  //It can return the string hint
    }


    public void updateProgress(int level_id, Boolean levelProgress){
        SQLiteDatabase db = this.getWritableDatabase();

        //update the progress as true or false
        ContentValues values = new ContentValues();
        values.put("level_progress",levelProgress);
        db.update(TABLE_NAME,values,"level_id="+level_id,null);
    }

    public int getLevelProgress(int level_id){
        SQLiteDatabase db = this.getReadableDatabase();

        //get the location at the position
        Cursor cursor = db.rawQuery("Select level_progress from "+TABLE_NAME+" where level_id="+level_id,null);
        int progress = 0;
        if(cursor.moveToFirst()){
            progress = cursor.getInt(0);   //it can fetch data in the form of int rather than boolean
        }
        return progress;  //It can return the int progress 1 is true and 0 is false
    }

    public void deleteLevelProgress(){

        SQLiteDatabase db = this.getWritableDatabase();

        //delete all progress and set the false at all positions
        ContentValues values = new ContentValues();
        values.put("level_progress",false);
        db.update(TABLE_NAME,values,null,null);
    }
}
