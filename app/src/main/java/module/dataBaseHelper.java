package edu.gatech.seclass.jobcompare6300.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataBaseHelper extends SQLiteOpenHelper {
    public static final String db_NAME = "compareJobDB";
    public static final int db_VERSION = 1;

    //create a table for Jobs
    public static final String job_TABLE_NAME = "jobDB";
    public static final String job_ID_COL = "id";

    public static final String job_title_COL = "title";
    public static final String job_company_COL = "company";
    public static final String job_location_COL = "location";
    public static final String job_yearlySalary_COL = "yearlySalary";
    public static final String job_yearlyBonus_COL = "yearlyBonus";
    public static final String job_leaveTime_COL = "leaveTime";
    public static final String job_numberOfStock_COL = "numberOfStock";
    public static final String job_homeBuyingProgramFund_COL = "homeBuyingProgramFund";
    public static final String job_wellnessFund_COL = "wellnessFund";

    private static final String create_job_table = "CREATE TABLE " + job_TABLE_NAME + " ("
            + job_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + job_title_COL + " TEXT,"
            + job_company_COL + " TEXT,"
            + job_location_COL + " TEXT,"
            + job_yearlySalary_COL + " TEXT,"
            + job_yearlyBonus_COL + " TEXT,"
            + job_leaveTime_COL + " TEXT,"
            + job_numberOfStock_COL + " TEXT,"
            + job_homeBuyingProgramFund_COL + " TEXT,"
            + job_wellnessFund_COL + " TEXT)";

    //create a table for Weights
    public static final String weight_TABLE_NAME = "weightDB";
    public static final String weight_ID_COL = "id";

    public static final String weight_yearlySalary_COL = "AYS";
    public static final String weight_yearlyBonus_COL = "AYB";
    public static final String weight_leaveTime_COL = "LT";
    public static final String weight_numberOfStock_COL = "CSO";
    public static final String weight_homeBuyingProgramFund_COL = "HBP";
    public static final String weight_wellnessFund_COL = "WF";

    private static final String create_weight_table = "CREATE TABLE " + weight_TABLE_NAME + " ("
            + weight_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + weight_yearlySalary_COL + " TEXT,"
            + weight_yearlyBonus_COL + " TEXT,"
            + weight_leaveTime_COL + " TEXT,"
            + weight_numberOfStock_COL + " TEXT,"
            + weight_homeBuyingProgramFund_COL + " TEXT,"
            + weight_wellnessFund_COL + " TEXT)";


    public dataBaseHelper(Context context) {
        super(context, db_NAME, null, db_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + job_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + weight_TABLE_NAME);
        onCreate(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table for all job
        db.execSQL(create_job_table);


        //add one empty line for current job
        ContentValues contentValue = new ContentValues();
        contentValue.put(dataBaseHelper.job_title_COL, "");
        contentValue.put(dataBaseHelper.job_company_COL, "");
        contentValue.put(dataBaseHelper.job_location_COL, "");
        contentValue.put(dataBaseHelper.job_yearlySalary_COL, "");
        contentValue.put(dataBaseHelper.job_yearlyBonus_COL, "");
        contentValue.put(dataBaseHelper.job_leaveTime_COL, "");
        contentValue.put(dataBaseHelper.job_numberOfStock_COL, "");
        contentValue.put(dataBaseHelper.job_homeBuyingProgramFund_COL, "");
        contentValue.put(dataBaseHelper.job_wellnessFund_COL, "");
        db.insert(dataBaseHelper.job_TABLE_NAME, null, contentValue);

        //create table for weight
        db.execSQL(create_weight_table);

        //add initiate value for Weights
        ContentValues contentValue2 = new ContentValues();
        contentValue2.put(dataBaseHelper.weight_yearlySalary_COL, "1");
        contentValue2.put(dataBaseHelper.weight_yearlyBonus_COL, "1");
        contentValue2.put(dataBaseHelper.weight_leaveTime_COL, "1");
        contentValue2.put(dataBaseHelper.weight_numberOfStock_COL, "1");
        contentValue2.put(dataBaseHelper.weight_homeBuyingProgramFund_COL, "1");
        contentValue2.put(dataBaseHelper.weight_wellnessFund_COL, "1");
        db.insert(dataBaseHelper.weight_TABLE_NAME, null, contentValue2);
    }

}
