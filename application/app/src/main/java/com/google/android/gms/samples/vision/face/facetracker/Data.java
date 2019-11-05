package com.google.android.gms.samples.vision.face.facetracker;

public class Data {
    private final String userId;
    private final String password;
    private final float result;
    private final int posOrNeg;


    public Data(String userId, String password, float result, int posOrNeg) {
        this.userId = userId;
        this.password = password;
        this.result = result;
        this.posOrNeg = posOrNeg;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public float getResult() {
        return result;
    }

    public int getPosOrNeg() {
        return posOrNeg;
    }
}
