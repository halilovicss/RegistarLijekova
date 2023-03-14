package com.halilovic.registarlijekova.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.halilovic.registarlijekova.Model.CategoryModel;
import com.halilovic.registarlijekova.Model.LijekoviModel;
import com.halilovic.registarlijekova.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "baza";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "lijekovi";
    private static final String TABLE_NAME_KATEGORIJE = "KATEGORIJE";

    //nazivi polja za tabelu lijekovi
    private static final String ID_COL = "id";
    private static final String ID_LIJEKA = "id_lijeka";
    private static final String NAME = "name";
    private static final String ATC = "atc";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORY_ID = "categoryId";
    private static final String ACTIVE_SUBSTANCE_VALUE = "activeSubstanceValue";
    private static final String MAXIMUM_DOSE = "maximumDailyDose";
    private static final String ACTIVE_SUBSTANCE_SELECT = "activeSubstanceSelectedQuantity";
    private static final String MINIMUM_DOSE = "minimumDailyDose";
    private static final String ACTIVE_SUBSTANCE_UNIT = "activeSubstanceMeasurementUnit";

    //nazivi polja za tabelu kategorija

    private static final String ID = "id";
    private static final String ID_KATEGORIJE = "id_kategorije";
    private static final String MARK = "mark";
    private static final String NAME_CAT = "name_cat";
    private static final String COLOR = "color";


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_LIJEKA + " INTEGER, "
                + NAME + " TEXT,"
                + ATC + " TEXT,"
                + SHORT_DESCRIPTION + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + CATEGORY_ID + " TEXT,"
                + ACTIVE_SUBSTANCE_VALUE + " TEXT,"
                + ACTIVE_SUBSTANCE_UNIT + " TEXT,"
                + ACTIVE_SUBSTANCE_SELECT + " TEXT,"
                + MINIMUM_DOSE + " TEXT,"
                + MAXIMUM_DOSE + " TEXT)";

        String queryCategory = "CREATE TABLE " + TABLE_NAME_KATEGORIJE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_KATEGORIJE + " INTEGER,"
                + MARK + " TEXT,"
                + NAME_CAT + " TEXT,"
                + COLOR + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
        db.execSQL(queryCategory);
    }

    public void addMedicaments(ArrayList<LijekoviModel> list){
       for (int i = 0;i<list.size();i++){
           int id = list.get(i).getId();
           String name = list.get(i).getName();
           String atc = list.get(i).getAtc();
           String description = list.get(i).getDescription();
           String shortDescription = list.get(i).getShortDescription();
           int categoryId = list.get(i).getCategoryId();
           int activeSubstanceValue = list.get(i).getActiveSubstanceValue();
           String activeSubstanceMeasurementUnit = list.get(i).getActiveSubstanceMeasurementUnit();
           int activeSubstanceSelectedQuantity = list.get(i).getActiveSubstanceSelectedQuantity();
           int minimumDailyDose = list.get(i).getMinimumDailyDose();

           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues values = new ContentValues();
           values.put("id_lijeka",id);
           values.put("name",name);
           values.put("atc",atc);
           values.put("shortDescription",shortDescription);
           values.put("description",description);
           values.put("categoryId",categoryId);
           values.put("activeSubstanceValue",activeSubstanceValue);
           values.put("activeSubstanceMeasurementUnit",activeSubstanceMeasurementUnit);
           values.put("activeSubstanceSelectedQuantity",activeSubstanceSelectedQuantity);
           values.put("minimumDailyDose",minimumDailyDose);

           db.insert(TABLE_NAME, null, values);
//           db.close();

       }




    }


    public void addCategory(ArrayList<CategoryModel> list){
        for (int i = 0;i<list.size();i++){
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            String mark = list.get(i).getMark();
            String color = list.get(i).getColor();


            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_kategorije",id);
            values.put("name_cat",name);
            values.put("mark",mark);
            values.put("color",color);

            db.insert(TABLE_NAME_KATEGORIJE, null, values);
//           db.close();

        }




    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // metoda za provjeru da li vec postoji baza
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

// provjeri da li je baza prazna ukoliko nema podataka ucitaj sa interneta
    public boolean isEmpty(String TableName){

        SQLiteDatabase database = this.getReadableDatabase();
        long NoOfRows = DatabaseUtils.queryNumEntries(database,TableName);

        if (NoOfRows == 0){
            return true;
        } else {
            return false;
        }
    }

    //ucitaj podatke u Model class
    public ArrayList<Model> readData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Model> courseModalArrayList;
        Cursor cursor = db.rawQuery("SELECT n.id_lijeka, n.name, l.color, l.name_cat, l.mark,  n.shortDescription, n.description, n.atc, n.activeSubstanceMeasurementUnit, n.activeSubstanceValue, n.activeSubstanceSelectedQuantity,n.minimumDailyDose,n.maximumDailyDose  FROM lijekovi n JOIN KATEGORIJE l ON n.categoryId = l.id_kategorije",null);
        courseModalArrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                courseModalArrayList.add(new Model(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_lijeka")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("color")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name_cat")),
                        cursor.getString(cursor.getColumnIndexOrThrow("atc")),
                        cursor.getString(cursor.getColumnIndexOrThrow("shortDescription")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("activeSubstanceValue")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("activeSubstanceSelectedQuantity")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("minimumDailyDose")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("maximumDailyDose"))





                        ));
            }while (cursor.moveToNext());
            cursor.close();
        }return courseModalArrayList;


    }

    // dobij podatke u listu, ucitaj one kategorije gdje se nalaze lijekovi
    public List<String> readCategory(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> courseModalArrayList;
        Cursor cursor = db.rawQuery("SELECT n.id_lijeka, n.name, l.name_cat FROM lijekovi n JOIN KATEGORIJE l ON n.categoryId = l.id_kategorije GROUP BY l.name_cat",null);
        courseModalArrayList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                courseModalArrayList.add(cursor.getString(2))

                ;
            }while (cursor.moveToNext());
            cursor.close();
        }return courseModalArrayList;


    }

}
