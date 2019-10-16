package com.google.android.gms.samples.vision.face.facetracker;

public class Data {
    private final String userId;
    private final String password;
    private final float result;

    public Data(String userId, String password, float result) {
        this.userId = userId;
        this.password = password;
        this.result = result;
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
}
