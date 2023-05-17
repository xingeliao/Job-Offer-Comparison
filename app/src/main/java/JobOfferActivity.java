package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.module.dbManager;

public class JobOfferActivity extends AppCompatActivity {

    edu.gatech.seclass.jobcompare6300.module.dbManager dbManager;
    private EditText jobTitle;
    private EditText jobCompany;
    private EditText jobState;
    private EditText jobCity;
    private EditText jobAYS;
    private EditText jobAYB;
    private EditText jobLT;
    private EditText jobCSO;
    private EditText jobHBP;
    private EditText jobWF;

    private ArrayList<String> savedJob;
    private ArrayList<String> currentJob;

    private String jobLocation;
    private List<String> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_job_offers);

        dbManager = new dbManager(this);
        dbManager.open();

        currentJob = dbManager.getJobFromDB(CurrentJobActivity.currentJobID);
        //get value from UI
        jobTitle = (EditText)findViewById(R.id.idJobTitle);
        jobCompany = (EditText)findViewById(R.id.idJobCompany);
        jobCity = (EditText)findViewById(R.id.idJobCity);
        jobState = (EditText)findViewById(R.id.idJobState);
        jobAYS = (EditText)findViewById(R.id.idJobAYS);
        jobAYB = (EditText)findViewById(R.id.idJobAYB);
        jobLT = (EditText)findViewById(R.id.idJobLT);
        jobCSO = (EditText)findViewById(R.id.idJobCSO);
        jobHBP = (EditText)findViewById(R.id.idJobHBP);
        jobWF = (EditText)findViewById(R.id.idJobWF);

    }

    private boolean isCurrentJobExist() {
        if (currentJob.get(0).equals("")){
            return false;
        }
        else {
            return true;
        }
    }

    public void handleClickSave(View view) {
        //call save method
        location = Arrays.asList(jobCity.getText().toString(), jobState.getText().toString());
        jobLocation = String.join(",", location);
        savedJob = new ArrayList<String>(Arrays.asList(jobTitle.getText().toString(), jobCompany.getText().toString(), jobLocation, jobAYS.getText().toString(), jobAYB.getText().toString(), jobLT.getText().toString(), jobCSO.getText().toString(), jobHBP.getText().toString(), jobWF.getText().toString()));

        boolean flag = true;
        if (savedJob.get(0).isEmpty()){
            flag = false;
            jobTitle.setError("Invalid Empty Input");
        }
        if (savedJob.get(1).isEmpty()){
            flag = false;
            jobCompany.setError("Invalid Empty Input");
        }

        if (jobState.getText().toString().isEmpty()){
            flag = false;
            jobState.setError("Invalid Empty Input");
        }
        if (jobCity.getText().toString().isEmpty()){
            flag = false;
            jobCity.setError("Invalid Empty Input");
        }

        if (savedJob.get(3).isEmpty()){
            flag = false;
            jobAYS.setError("Invalid Empty Input");
        }
        if (savedJob.get(4).isEmpty()){
            flag = false;
            jobAYB.setError("Invalid Empty Input");
        }
        if (savedJob.get(5).isEmpty()){
            flag = false;
            jobLT.setError("Invalid Empty Input");
        }
        if (savedJob.get(6).isEmpty()){
            flag = false;
            jobCSO.setError("Invalid Empty Input");
        }
        if (savedJob.get(7).isEmpty()){
            flag = false;
            jobHBP.setError("Invalid Empty Input");
        }
        if (savedJob.get(8).isEmpty()){
            flag = false;
            jobWF.setError("Invalid Empty Input");
        }

        //Check invalid inputs
        if (!savedJob.get(5).isEmpty() && !(Float.parseFloat(savedJob.get(5)) <= 366)){
            flag = false;
            jobLT.setError("Invalid Input");
        }
        if (!savedJob.get(3).isEmpty() && !savedJob.get(7).isEmpty() && !(Float.parseFloat(savedJob.get(7)) <= 0.15*Float.parseFloat(savedJob.get(3)))){
            flag = false;
            jobHBP.setError("Invalid Input");
        }
        if (!savedJob.get(8).isEmpty() && Float.parseFloat(savedJob.get(8)) > 5000){
            flag = false;
            jobWF.setError("Invalid Input");
        }

        if (flag){
            dbManager.insertJob(savedJob.get(0), savedJob.get(1), savedJob.get(2), savedJob.get(3), savedJob.get(4), savedJob.get(5), savedJob.get(6), savedJob.get(7), savedJob.get(8));

            location = Arrays.asList(savedJob.get(2).split(","));

            if (isCurrentJobExist()) {
                setContentView(R.layout.enter_job_offers2);
                ((EditText)findViewById(R.id.idJobTitle2)).setText(savedJob.get(0));
                ((EditText)findViewById(R.id.idJobCompany2)).setText(savedJob.get(1));

                ((EditText)findViewById(R.id.idJobCity2)).setText(location.get(0));
                ((EditText)findViewById(R.id.idJobState2)).setText(location.get(1));

                ((EditText)findViewById(R.id.idJobAYS2)).setText(savedJob.get(3));
                ((EditText)findViewById(R.id.idJobAYB2)).setText(savedJob.get(4));
                ((EditText)findViewById(R.id.idJobLT2)).setText(savedJob.get(5));
                ((EditText)findViewById(R.id.idJobCSO2)).setText(savedJob.get(6));
                ((EditText)findViewById(R.id.idJobHBP2)).setText(savedJob.get(7));
                ((EditText)findViewById(R.id.idJobWF2)).setText(savedJob.get(8));
            }
            else {
                setContentView(R.layout.enter_job_offers3);
                ((EditText)findViewById(R.id.idJobTitle3)).setText(savedJob.get(0));
                ((EditText)findViewById(R.id.idJobCompany3)).setText(savedJob.get(1));

                ((EditText)findViewById(R.id.idJobCity3)).setText(location.get(0));
                ((EditText)findViewById(R.id.idJobState3)).setText(location.get(1));

                ((EditText)findViewById(R.id.idJobAYS3)).setText(savedJob.get(3));
                ((EditText)findViewById(R.id.idJobAYB3)).setText(savedJob.get(4));
                ((EditText)findViewById(R.id.idJobLT3)).setText(savedJob.get(5));
                ((EditText)findViewById(R.id.idJobCSO3)).setText(savedJob.get(6));
                ((EditText)findViewById(R.id.idJobHBP3)).setText(savedJob.get(7));
                ((EditText)findViewById(R.id.idJobWF3)).setText(savedJob.get(8));
            }
        }
    }


    public void clickAnotherJob(View view) {
        Intent newPageIntent = new Intent(JobOfferActivity.this, JobOfferActivity.class);
        startActivity(newPageIntent);
    }

    public void clickCompareWithCurrent(View view) {
        setContentView(R.layout.compare_two_jobs);
        //set saved job info
        ((EditText)findViewById(R.id.idJobTitleSaved)).setText(savedJob.get(0));
        ((EditText)findViewById(R.id.idJobCompanySaved)).setText(savedJob.get(1));
        ((EditText)findViewById(R.id.idJobLocationSaved)).setText(savedJob.get(2));
        ((EditText)findViewById(R.id.idJobAYSSaved)).setText(savedJob.get(3));
        ((EditText)findViewById(R.id.idJobAYBSaved)).setText(savedJob.get(4));
        ((EditText)findViewById(R.id.idJobLTSaved)).setText(savedJob.get(5));
        ((EditText)findViewById(R.id.idJobCSOSaved)).setText(savedJob.get(6));
        ((EditText)findViewById(R.id.idJobHBPSaved)).setText(savedJob.get(7));
        ((EditText)findViewById(R.id.idJobWFSaved)).setText(savedJob.get(8));
        //get current job from DB
        ((EditText)findViewById(R.id.idJobTitleCurrent)).setText(currentJob.get(0));
        ((EditText)findViewById(R.id.idJobCompanyCurrent)).setText(currentJob.get(1));
        ((EditText)findViewById(R.id.idJobLocationCurrent)).setText(currentJob.get(2));
        ((EditText)findViewById(R.id.idJobAYSCurrent)).setText(currentJob.get(3));
        ((EditText)findViewById(R.id.idJobAYBCurrent)).setText(currentJob.get(4));
        ((EditText)findViewById(R.id.idJobLTCurrent)).setText(currentJob.get(5));
        ((EditText)findViewById(R.id.idJobCSOCurrent)).setText(currentJob.get(6));
        ((EditText)findViewById(R.id.idJobHBPCurrent)).setText(currentJob.get(7));
        ((EditText)findViewById(R.id.idJobWFCurrent)).setText(currentJob.get(8));
    }

    public void clickBack(View view) {
        setContentView(R.layout.enter_job_offers2);
        ((EditText)findViewById(R.id.idJobTitle2)).setText(savedJob.get(0));
        ((EditText)findViewById(R.id.idJobCompany2)).setText(savedJob.get(1));
        ((EditText)findViewById(R.id.idJobCity2)).setText(location.get(0));
        ((EditText)findViewById(R.id.idJobState2)).setText(location.get(1));
        ((EditText)findViewById(R.id.idJobAYS2)).setText(savedJob.get(3));
        ((EditText)findViewById(R.id.idJobAYB2)).setText(savedJob.get(4));
        ((EditText)findViewById(R.id.idJobLT2)).setText(savedJob.get(5));
        ((EditText)findViewById(R.id.idJobCSO2)).setText(savedJob.get(6));
        ((EditText)findViewById(R.id.idJobHBP2)).setText(savedJob.get(7));
        ((EditText)findViewById(R.id.idJobWF2)).setText(savedJob.get(8));
    }

    public void handleClickCancel(View view) {
        Intent newPageIntent = new Intent(JobOfferActivity.this, MainActivity.class);
        startActivity(newPageIntent);
    }


}