package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.module.dbManager;


public class ComparisonSettingsActivity extends AppCompatActivity {

    edu.gatech.seclass.jobcompare6300.module.dbManager dbManager;

    private EditText weightAYS;
    private EditText weightAYB;
    private EditText weightLT;
    private EditText weightCSO;
    private EditText weightHBP;
    private EditText weightWF;

    public static long weightID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_comparison_settings);

        dbManager = new dbManager(this);
        dbManager.open();

        weightAYS = (EditText)findViewById(R.id.weightAYS);
        weightAYB = (EditText)findViewById(R.id.weightAYB);
        weightLT = (EditText)findViewById(R.id.weightLT);
        weightCSO = (EditText)findViewById(R.id.weightCSO);
        weightHBP = (EditText)findViewById(R.id.weightHBP);
        weightWF = (EditText)findViewById(R.id.weightWF);

        ArrayList<Float> weight = dbManager.getWeightFromDB(weightID);

        weightAYS.setText(String.valueOf(weight.get(0)));
        weightAYB.setText(String.valueOf(weight.get(1)));
        weightLT.setText(String.valueOf(weight.get(2)));
        weightCSO.setText(String.valueOf(weight.get(3)));
        weightHBP.setText(String.valueOf(weight.get(4)));
        weightWF.setText(String.valueOf(weight.get(5)));
    }

    public void handleClickSave(View view) {
        boolean flag = true;
        if (weightAYS.getText().toString().isEmpty()){
            flag = false;
            weightAYS.setError("Invalid Empty Input");
        }
        if (weightAYB.getText().toString().isEmpty()){
            flag = false;
            weightAYB.setError("Invalid Empty Input");
        }
        if (weightLT.getText().toString().isEmpty()){
            flag = false;
            weightLT.setError("Invalid Empty Input");
        }
        if (weightCSO.getText().toString().isEmpty()){
            flag = false;
            weightCSO.setError("Invalid Empty Input");
        }
        if (weightHBP.getText().toString().isEmpty()){
            flag = false;
            weightHBP.setError("Invalid Empty Input");
        }
        if (weightWF.getText().toString().isEmpty()){
            flag = false;
            weightWF.setError("Invalid Empty Input");
        }
        if (flag){
            dbManager.updateWeight(weightID, weightAYS.getText().toString(), weightAYB.getText().toString(), weightLT.getText().toString(), weightCSO.getText().toString(), weightHBP.getText().toString(), weightWF.getText().toString());
            Intent newPageIntent = new Intent(ComparisonSettingsActivity.this, MainActivity.class);
            startActivity(newPageIntent);
        }
    }

    public void handleClickCancel(View view) {
        Intent newPageIntent = new Intent(ComparisonSettingsActivity.this, MainActivity.class);
        startActivity(newPageIntent);
    }


}