package edu.gatech.seclass.jobcompare6300.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class dbManager {
    private dataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private Context context;

    public dbManager(Context c) {
        context = c;
    }

    //open the database
    public dbManager open() throws SQLException {
        dataBaseHelper = new dataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }


    //get a job from job data base
    public ArrayList<String> getJobFromDB(long id) {
        String[] columns = new String[] { dataBaseHelper.job_ID_COL, dataBaseHelper.job_title_COL, dataBaseHelper.job_company_COL, dataBaseHelper.job_location_COL, dataBaseHelper.job_yearlySalary_COL, dataBaseHelper.job_yearlyBonus_COL, dataBaseHelper.job_leaveTime_COL, dataBaseHelper.job_numberOfStock_COL, dataBaseHelper.job_homeBuyingProgramFund_COL, dataBaseHelper.job_wellnessFund_COL };
        Cursor cursor = database.query(dataBaseHelper.job_TABLE_NAME, columns, null, null, null, null, null);

        ArrayList<String> items = new ArrayList<String>();

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToPosition((int) id-1);
            for(int i = 1; i < cursor.getColumnNames().length; i++){
                items.add(cursor.getString(i));
            }
        }
        cursor.close();

        return items;
    }

    //get job number
    public int getJobNumberFromDB() {
        String[] columns = new String[] { dataBaseHelper.job_ID_COL, dataBaseHelper.job_title_COL, dataBaseHelper.job_company_COL, dataBaseHelper.job_location_COL, dataBaseHelper.job_yearlySalary_COL, dataBaseHelper.job_yearlyBonus_COL, dataBaseHelper.job_leaveTime_COL, dataBaseHelper.job_numberOfStock_COL, dataBaseHelper.job_homeBuyingProgramFund_COL, dataBaseHelper.job_wellnessFund_COL };
        Cursor cursor = database.query(dataBaseHelper.job_TABLE_NAME, columns, null, null, null, null, null);
        return cursor.getCount();
    }

    //insert a new job in job DB
    public void insertJob(String name, String company, String location, String yearlySalary, String yearlyBonus, String leaveTime, String numberOfStock, String homeBuyingProgramFund, String wellnessFund) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(dataBaseHelper.job_title_COL, name);
        contentValue.put(dataBaseHelper.job_company_COL, company);
        contentValue.put(dataBaseHelper.job_location_COL, location);
        contentValue.put(dataBaseHelper.job_yearlySalary_COL, yearlySalary);
        contentValue.put(dataBaseHelper.job_yearlyBonus_COL, yearlyBonus);
        contentValue.put(dataBaseHelper.job_leaveTime_COL, leaveTime);
        contentValue.put(dataBaseHelper.job_numberOfStock_COL, numberOfStock);
        contentValue.put(dataBaseHelper.job_homeBuyingProgramFund_COL, homeBuyingProgramFund);
        contentValue.put(dataBaseHelper.job_wellnessFund_COL, wellnessFund);

        database.insert(dataBaseHelper.job_TABLE_NAME, null, contentValue);

    }

    //update a job in DB, base on the job id
    public void updateJob(long id, String name, String company, String location, String yearlySalary, String yearlyBonus, String leaveTime, String numberOfStock, String homeBuyingProgramFund, String wellnessFund) {
        ContentValues currentJobValues = new ContentValues();

        currentJobValues.put(dataBaseHelper.job_title_COL, name);
        currentJobValues.put(dataBaseHelper.job_company_COL, company);
        currentJobValues.put(dataBaseHelper.job_location_COL, location);
        currentJobValues.put(dataBaseHelper.job_yearlySalary_COL, yearlySalary);
        currentJobValues.put(dataBaseHelper.job_yearlyBonus_COL, yearlyBonus);
        currentJobValues.put(dataBaseHelper.job_leaveTime_COL, leaveTime);
        currentJobValues.put(dataBaseHelper.job_numberOfStock_COL, numberOfStock);
        currentJobValues.put(dataBaseHelper.job_homeBuyingProgramFund_COL, homeBuyingProgramFund);
        currentJobValues.put(dataBaseHelper.job_wellnessFund_COL, wellnessFund);

        database.update(dataBaseHelper.job_TABLE_NAME, currentJobValues, dataBaseHelper.job_ID_COL + " = " + id, null);

    }


    //get weights from DB
    public ArrayList<Float> getWeightFromDB(long id) {
        String[] columns = new String[] { dataBaseHelper.weight_ID_COL, dataBaseHelper.weight_yearlySalary_COL, dataBaseHelper.weight_yearlyBonus_COL, dataBaseHelper.weight_leaveTime_COL, dataBaseHelper.weight_numberOfStock_COL, dataBaseHelper.weight_homeBuyingProgramFund_COL, dataBaseHelper.weight_wellnessFund_COL };
        Cursor cursor = database.query(dataBaseHelper.weight_TABLE_NAME, columns, null, null, null, null, null);

        ArrayList<Float> weights = new ArrayList<Float>();

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToPosition((int) id-1);
            for(int i = 1; i < cursor.getColumnNames().length; i++){
                weights.add(Float.parseFloat(cursor.getString(i)));
            }
        }
        cursor.close();

        return weights;
    }

    //update weights in DB, base on the id
    public void updateWeight(long id, String AYS, String AYB, String LT, String CSO, String HBP, String WF) {
        ContentValues weightValues = new ContentValues();

        weightValues.put(dataBaseHelper.weight_yearlySalary_COL, AYS);
        weightValues.put(dataBaseHelper.weight_yearlyBonus_COL, AYB);
        weightValues.put(dataBaseHelper.weight_leaveTime_COL, LT);
        weightValues.put(dataBaseHelper.weight_numberOfStock_COL, CSO);
        weightValues.put(dataBaseHelper.weight_homeBuyingProgramFund_COL, HBP);
        weightValues.put(dataBaseHelper.weight_wellnessFund_COL, WF);

        database.update(dataBaseHelper.weight_TABLE_NAME, weightValues, dataBaseHelper.weight_ID_COL + " = " + id, null);
    }


}
