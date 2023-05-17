package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import edu.gatech.seclass.jobcompare6300.module.dbManager;

public class CurrentJobActivity extends AppCompatActivity {

    private EditText currTitle;
    private EditText currCompany;
    private EditText currState;
    private EditText currCity;
    private EditText currAYS;
    private EditText currAYB;
    private EditText currLT;
    private EditText currCSO;
    private EditText currHBP;
    private EditText currWF;

    private TextView successSave;
    private Button buttonSave;
    private Button buttonCancel;

    dbManager dbManager;
    public static long currentJobID = 1;
    private String jobLocation;
    private List<String> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_current_job);

        dbManager = new dbManager(this);
        dbManager.open();

        //get value from UI
        currTitle = (EditText)findViewById(R.id.idCurrTitle);
        currCompany = (EditText)findViewById(R.id.idCurrCompany);

        currCity = (EditText)findViewById(R.id.idCurrCity);
        currState = (EditText)findViewById(R.id.idCurrState);

        currAYS = (EditText)findViewById(R.id.idCurrAYS);
        currAYB = (EditText)findViewById(R.id.idCurrAYB);
        currLT = (EditText)findViewById(R.id.idCurrLT);
        currCSO = (EditText)findViewById(R.id.idCurrCSO);
        currHBP = (EditText)findViewById(R.id.idCurrHBP);
        currWF = (EditText)findViewById(R.id.idCurrWF);

        successSave = (TextView)findViewById(R.id.saveSuccess);
        buttonSave = (Button) findViewById(R.id.idSaveButton);
        buttonCancel = (Button) findViewById(R.id.cancelCurrentJob);


        //get value from DB
        ArrayList<String> list = dbManager.getJobFromDB(currentJobID);

        //if there is a current job in DB, show current job in UI
        if(!list.get(2).isEmpty()){
            location = Arrays.asList(list.get(2).split(","));
            list.set(2, location.get(0));
            list.add(3, location.get(1));
            ArrayList<EditText> values = new ArrayList<EditText>(Arrays.asList(currTitle,currCompany,currCity,currState,currAYS,currAYB,currLT,currCSO,currHBP,currWF));
            for(int i = 0; i < values.size(); i++){
                values.get(i).setText(list.get(i));
            }
        }


    }


    public void handleClickSave(View view) {
        //when user click on save, update current job data
        boolean flag = true;
        if (currTitle.getText().toString().isEmpty()){
            flag = false;
            currTitle.setError("Invalid Empty Input");
        }
        if (currCompany.getText().toString().isEmpty()){
            flag = false;
            currCompany.setError("Invalid Empty Input");
        }
        if (currState.getText().toString().isEmpty()){
            flag = false;
            currState.setError("Invalid Empty Input");
        }
        if (currCity.getText().toString().isEmpty()){
            flag = false;
            currCity.setError("Invalid Empty Input");
        }

        if (currAYS.getText().toString().isEmpty()){
            flag = false;
            currAYS.setError("Invalid Empty Input");
        }
        if (currAYB.getText().toString().isEmpty()){
            flag = false;
            currAYB.setError("Invalid Empty Input");
        }
        if (currLT.getText().toString().isEmpty()){
            flag = false;
            currLT.setError("Invalid Empty Input");
        }
        if (currCSO.getText().toString().isEmpty() ){
            flag = false;
            currCSO.setError("Invalid Empty Input");
        }
        if (currHBP.getText().toString().isEmpty()){
            flag = false;
            currHBP.setError("Invalid Empty Input");
        }
        if (currWF.getText().toString().isEmpty()){
            flag = false;
            currWF.setError("Invalid Empty Input");
        }

        //Check invalid inputs
        if (!currLT.getText().toString().isEmpty() && !(Float.parseFloat(currLT.getText().toString()) <= 366)){
            flag = false;
            currLT.setError("Invalid Input");
        }
        if (!currAYS.getText().toString().isEmpty() && !currHBP.getText().toString().isEmpty() && !(Float.parseFloat(currHBP.getText().toString()) <= 0.15*Float.parseFloat(currAYS.getText().toString())) ){
            flag = false;
            currHBP.setError("Invalid Input");
        }
        if (!currWF.getText().toString().isEmpty() && Float.parseFloat(currWF.getText().toString()) > 5000){
            flag = false;
            currWF.setError("Invalid Input");
        }

        //Check if inputs are numeric
        if (!Pattern.matches("\\d+(\\.\\d+)?", currAYS.getText().toString())){
            flag = false;
            currAYS.setError("Invalid Input");
        }
        if (!Pattern.matches("\\d+(\\.\\d+)?", currAYB.getText().toString())){
            flag = false;
            currAYB.setError("Invalid Input");
        }
        if (!Pattern.matches("\\d+(\\.\\d+)?", currLT.getText().toString())){
            flag = false;
            currLT.setError("Invalid Input");
        }
        if (!Pattern.matches("\\d+(\\.\\d+)?", currCSO.getText().toString())){
            flag = false;
            currCSO.setError("Invalid Input");
        }
        if (!Pattern.matches("\\d+(\\.\\d+)?", currHBP.getText().toString())){
            flag = false;
            currHBP.setError("Invalid Input");
        }
        if (!Pattern.matches("\\d+(\\.\\d+)?", currWF.getText().toString())){
            flag = false;
            currWF.setError("Invalid Input");
        }


        if (flag){
            location = Arrays.asList(currCity.getText().toString(), currState.getText().toString());
            jobLocation = String.join(",", location);

            dbManager.updateJob(currentJobID, currTitle.getText().toString(), currCompany.getText().toString(), jobLocation, currAYS.getText().toString(), currAYB.getText().toString(), currLT.getText().toString(), currCSO.getText().toString(), currHBP.getText().toString(), currWF.getText().toString());

            //show success save message in UI
            successSave.setVisibility(View.VISIBLE);
            buttonSave.setEnabled(false);
            buttonCancel.setEnabled(false);

            //close and return to main page
            successSave.postDelayed(new Runnable() {
                public void run() {
                    Intent newPageIntent = new Intent(CurrentJobActivity.this, MainActivity.class);
                    startActivity(newPageIntent);
                }
            }, 1000);
        }

    }

    public void handleClickCancel(View view) {
        Intent newPageIntent = new Intent(CurrentJobActivity.this, MainActivity.class);
        startActivity(newPageIntent);
    }


}