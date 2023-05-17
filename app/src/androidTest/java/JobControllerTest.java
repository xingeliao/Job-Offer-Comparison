package edu.gatech.seclass.jobcompare6300;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import edu.gatech.seclass.jobcompare6300.module.dbManager;

@RunWith(AndroidJUnit4.class)
public class JobControllerTest {

    @Test
    public void calculateScoreTest1(){
        JobController jobController = new JobController();
        ArrayList<String> job = new ArrayList<String>(Arrays.asList("Baker", "GE Panaderia", "San Antonio,Texas", "1000", "2", "3", "15", "10", "0"));
        ArrayList<Float> weights = new ArrayList<Float>(Arrays.asList((float)1, (float)1, (float)1, (float)1, (float)1, (float)1));
        float res = jobController.calculateScore(job, weights);
        Assert.assertEquals((float)171.83975, res, 1e-15);
    }

    @Test
    public void calculateScoreTest2(){
        JobController jobController = new JobController();
        ArrayList<String> job = new ArrayList<String>(Arrays.asList("Accountant", "AAA Company", "Miami,Florida", "1200", "5", "3", "5", "1", "0"));
        ArrayList<Float> weights = new ArrayList<Float>(Arrays.asList((float)1, (float)1, (float)1, (float)1, (float)1, (float)1));
        float res = jobController.calculateScore(job, weights);
        Assert.assertEquals((float)203.724365234375, res, 1e-15);
    }
}
