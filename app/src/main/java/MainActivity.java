package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.module.dbManager;


public class MainActivity extends AppCompatActivity {
    edu.gatech.seclass.jobcompare6300.module.dbManager dbManager;
    private Button buttonCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new dbManager(this);
        dbManager.open();

        buttonCompare = (Button) findViewById(R.id.buttonCompare);

        //enable buttonCompare when (1) at least two job offers, in case there is no current job, or (2) at least one job offer, in case there is a current job.
        if(dbManager.getJobFromDB(1).get(0).equals("") && dbManager.getJobNumberFromDB() >= 3){
            buttonCompare.setEnabled(true);
        }
        else if(!(dbManager.getJobFromDB(1).get(0).equals("")) && dbManager.getJobNumberFromDB() >= 2){
            buttonCompare.setEnabled(true);
        }
        else{
            buttonCompare.setEnabled(false);
        }


    }


    public void handleClick(View view) {
        switch (view.getId()){
            case R.id.buttonEditCurrentJob:
                Intent currentJobIntent = new Intent(MainActivity.this, CurrentJobActivity.class);
                startActivity(currentJobIntent);
                break;
            case R.id.buttonEnterOffer:
                Intent enterJobIntent = new Intent(MainActivity.this, JobOfferActivity.class);
                startActivity(enterJobIntent);
                break;
            case R.id.buttonAdjustSettings:
                Intent adjustIntent = new Intent(MainActivity.this, ComparisonSettingsActivity.class);
                startActivity(adjustIntent);
                break;
            case R.id.buttonCompare:
                Intent compareIntent = new Intent(MainActivity.this, CompareActivity.class);
                startActivity(compareIntent);
                break;
        }
    }


}