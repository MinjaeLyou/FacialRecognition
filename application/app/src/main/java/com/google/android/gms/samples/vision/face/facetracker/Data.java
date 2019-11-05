package com.google.android.gms.samples.vision.face.facetracker;

public class Data {
    private final String userId;
    private final String password;
    private final String result;
    private final Boolean posOrNeg;
    //private final String createdAt;


    public Data(String userId, String password, String result, Boolean posOrNeg) {
        this.userId = userId;
        this.password = password;
        this.result = result;
        this.posOrNeg = posOrNeg;
    }

    //public AllResult(int posOrNeg, String result,)

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getResult() {
        return result;
    }

    public Boolean getPosOrNeg() {
        return posOrNeg;
    }

//    public int getCreatedAt() {
//        return createdAt;
//    }
}
