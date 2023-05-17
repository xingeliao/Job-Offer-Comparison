package edu.gatech.seclass.jobcompare6300;

import java.util.ArrayList;
import java.util.Collections;

import edu.gatech.seclass.jobcompare6300.module.dbManager;
import edu.gatech.seclass.jobcompare6300.module.jobScore;

public class JobController {

    public float calculateScore(ArrayList<String> job, ArrayList<Float> weights) {
        if (job.size() == 0 || job.get(3).isEmpty()) {
            return -1;
        }
        float jobAYS = Float.parseFloat(job.get(3));
        float jobAYB = Float.parseFloat(job.get(4));
        float jobLT = Float.parseFloat(job.get(5));
        float jobCSO = Float.parseFloat(job.get(6));
        float jobHBP = Float.parseFloat(job.get(7));
        float jobWF = Float.parseFloat(job.get(8));
        float weightSum = 0;
        for (int i = 0; i < weights.size(); i++) {
            weightSum += weights.get(i);
        }
        float weightAYS = weights.get(0) / weightSum;
        float weightAYB = weights.get(1) / weightSum;
        float weightLT = weights.get(2) / weightSum;
        float weightCSO = weights.get(3) / weightSum;
        float weightHBP = weights.get(4) / weightSum;
        float weightWF = weights.get(5) / weightSum;
        return weightAYS * jobAYS + weightAYB * jobAYB + weightLT * (jobLT * jobAYS / 260) + weightCSO * (jobCSO / 2) + weightHBP * jobHBP + weightWF * jobWF;
    }

    public ArrayList<jobScore> rank(dbManager dbManager,  ArrayList<Float> weights, ArrayList<jobScore> rankedList, ArrayList<ArrayList<String>> jobList) {


        for (int i = 1; i <= dbManager.getJobNumberFromDB(); i++) {
            ArrayList<String> tempJob = dbManager.getJobFromDB(i);
            //System.out.println(tempJob.get(0));
            float tempScore = calculateScore(tempJob, weights);

/*            if (tempScore < 0) {
                break;
            }*/
            rankedList.add(new jobScore(i, tempScore));
            jobList.add(tempJob);
        }

        Collections.sort(rankedList);

        return rankedList;
    }
}
