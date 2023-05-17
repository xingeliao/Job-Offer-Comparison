package edu.gatech.seclass.jobcompare6300.module;

public class jobScore implements Comparable{
    private int jobID;
    private float jobScore;

    public jobScore(){}

    public jobScore(int id, float score) {
        super();
        this.jobID = id;
        this.jobScore = score;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public float getJobScore() {
        return jobScore;
    }

    public void setJobScore(float jobScore) {
        this.jobScore = jobScore;
    }

    @Override
    public int compareTo(Object o) {
        jobScore j = (jobScore) o;
        if(this.jobScore>j.jobScore){
            return -1;
        }
        else if(this.jobScore<j.jobScore){
            return 1;
        }
        return 0;
    }

}
