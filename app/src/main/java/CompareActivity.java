package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.module.dbManager;
import edu.gatech.seclass.jobcompare6300.module.jobScore;

public class CompareActivity extends AppCompatActivity {
    edu.gatech.seclass.jobcompare6300.module.dbManager dbManager;

    private LinearLayout scrollLayout;
    private ArrayList<CardView> cardList = new ArrayList<CardView>();
    private ArrayList<Float> weights = new ArrayList<Float>();
    private ArrayList<ArrayList<String>> jobList = new ArrayList<ArrayList<String>>();
    private ArrayList<jobScore> rankedList = new ArrayList<jobScore>();

    JobController jobController = new JobController();

    public edu.gatech.seclass.jobcompare6300.module.dbManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(edu.gatech.seclass.jobcompare6300.module.dbManager dbManager) {
        this.dbManager = dbManager;
    }

    public LinearLayout getScrollLayout() {
        return scrollLayout;
    }

    public void setScrollLayout(LinearLayout scrollLayout) {
        this.scrollLayout = scrollLayout;
    }

    public ArrayList<CardView> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<CardView> cardList) {
        this.cardList = cardList;
    }

    public ArrayList<Float> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Float> weights) {
        this.weights = weights;
    }

    public ArrayList<ArrayList<String>> getJobList() {
        return jobList;
    }

    public void setJobList(ArrayList<ArrayList<String>> jobList) {
        this.jobList = jobList;
    }

    public ArrayList<jobScore> getRankedList() {
        return rankedList;
    }

    public void setRankedList(ArrayList<jobScore> rankedList) {
        this.rankedList = rankedList;
    }

    public JobController getJobController() {
        return jobController;
    }

    public void setJobController(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_activity);

        dbManager = new dbManager(this);
        dbManager.open();

        //get the weights of comparison setting
        weights = dbManager.getWeightFromDB(ComparisonSettingsActivity.weightID);
        scrollLayout = (LinearLayout) findViewById(R.id.scrollLayout);

        //get job data from db and push them to rank list and job list

        //System.out.println(dbManager.getJobNumberFromDB());
        //int n = dbManager.getJobNumberFromDB();
        if (dbManager.getJobNumberFromDB() == 1 && dbManager.getJobFromDB(1).get(0).equals("")) {
            //int n = -1;
            CardView cardJobView = (CardView) View.inflate(this, R.layout.card_layout, null);
            TextView jobTitle = (TextView) cardJobView.findViewById(R.id.cardJobTitleView);
            TextView jobScore = (TextView) cardJobView.findViewById(R.id.cardJobScoreView);
            jobTitle.setText("no Job, add Job first");
            cardList.add(cardJobView);
        } else {
            rankedList = jobController.rank(dbManager, weights, rankedList, jobList);
//            for (int i = 1; i <= dbManager.getJobNumberFromDB(); i++) {
//                ArrayList<String> tempJob = dbManager.getJobFromDB(i);
//                System.out.println(tempJob.get(0));
//                float tempScore = jobController.calculateScore(tempJob, weights);
//                if (tempScore < 0) {
//                    break;
//                }
//                rankedList.add(new jobScore(i, tempScore));
//                jobList.add(tempJob);
//            }
//
//
//            Collections.sort(rankedList);


            //iterate rank list
            NumberFormat formatter = new DecimalFormat("0.00");
            for (int i = 0; i < rankedList.size(); i++) {
                int tempID = rankedList.get(i).getJobID();
                float tempScore = rankedList.get(i).getJobScore();

                //If the score is -1 (job is empty), will not show to user
                if(tempScore != -1) {
                    CardView cardJobView = (CardView) View.inflate(this, R.layout.card_layout, null);
                    TextView jobTitle = (TextView) cardJobView.findViewById(R.id.cardJobTitleView);
                    TextView jobScore = (TextView) cardJobView.findViewById(R.id.cardJobScoreView);

                    if (tempID == CurrentJobActivity.currentJobID) {
                        String tempText = jobList.get(tempID - 1).get(0) + "(Current Job)";
                        jobTitle.setText(tempText);
                    } else {
                        jobTitle.setText(jobList.get(tempID - 1).get(0));
                    }

                    String tempScoreStr = formatter.format(tempScore);
                    jobScore.setText(String.valueOf(tempScoreStr));
                    cardList.add(cardJobView);
                }
            }

        }
        for (int i = 0; i < cardList.size(); i++) {
            scrollLayout.addView(cardList.get(i));
        }
    }


    public void handleClickCompare(View view) {
        ArrayList<Integer> checkedJob = new ArrayList<Integer>();
        for (int i = 0; i < cardList.size(); i++) {
            CheckBox cardCheckBox = (CheckBox) cardList.get(i).findViewById(R.id.cardCheckBox);
            if (cardCheckBox.isChecked()) {
                checkedJob.add(i);
            }
        }
        if (checkedJob.size() == 2) {
            setContentView(R.layout.compare_two_jobs2);
            int jobAID = rankedList.get(checkedJob.get(0)).getJobID();
            int jobBID = rankedList.get(checkedJob.get(1)).getJobID();
            //set job A info
            ((EditText) findViewById(R.id.idJobTitleA)).setText(jobList.get(jobAID - 1).get(0));
            ((EditText) findViewById(R.id.idJobCompanyA)).setText(jobList.get(jobAID - 1).get(1));
            ((EditText) findViewById(R.id.idJobLocationA)).setText(jobList.get(jobAID - 1).get(2));
            ((EditText) findViewById(R.id.idJobAYSA)).setText(jobList.get(jobAID - 1).get(3));
            ((EditText) findViewById(R.id.idJobAYBA)).setText(jobList.get(jobAID - 1).get(4));
            ((EditText) findViewById(R.id.idJobLTA)).setText(jobList.get(jobAID - 1).get(5));
            ((EditText) findViewById(R.id.idJobCSOA)).setText(jobList.get(jobAID - 1).get(6));
            ((EditText) findViewById(R.id.idJobHBPA)).setText(jobList.get(jobAID - 1).get(7));
            ((EditText) findViewById(R.id.idJobWFA)).setText(jobList.get(jobAID - 1).get(8));
            //set job B info
            ((EditText) findViewById(R.id.idJobTitleB)).setText(jobList.get(jobBID - 1).get(0));
            ((EditText) findViewById(R.id.idJobCompanyB)).setText(jobList.get(jobBID - 1).get(1));
            ((EditText) findViewById(R.id.idJobLocationB)).setText(jobList.get(jobBID - 1).get(2));
            ((EditText) findViewById(R.id.idJobAYSB)).setText(jobList.get(jobBID - 1).get(3));
            ((EditText) findViewById(R.id.idJobAYBB)).setText(jobList.get(jobBID - 1).get(4));
            ((EditText) findViewById(R.id.idJobLTB)).setText(jobList.get(jobBID - 1).get(5));
            ((EditText) findViewById(R.id.idJobCSOB)).setText(jobList.get(jobBID - 1).get(6));
            ((EditText) findViewById(R.id.idJobHBPB)).setText(jobList.get(jobBID - 1).get(7));
            ((EditText) findViewById(R.id.idJobWFB)).setText(jobList.get(jobBID - 1).get(8));
        }
    }

    public void handleClickBack(View view) {
        Intent newPageIntent = new Intent(CompareActivity.this, CompareActivity.class);
        startActivity(newPageIntent);
    }

    public void handleClickReturn(View view) {
        Intent newPageIntent = new Intent(CompareActivity.this, MainActivity.class);
        startActivity(newPageIntent);
    }
}