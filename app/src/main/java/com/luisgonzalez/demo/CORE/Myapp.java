package com.luisgonzalez.demo.CORE;

import android.app.Application;
import android.location.Location;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luisemilio on 19-04-17.
 */

public class Myapp extends Application {
    private static Myapp mInstance;
    private static final String TAG = "singleton-app";
    private Location location =null;
    private String JsonStringUser;

    private static String URL_FOR_REQUEST_REST_API= "https://smartclarity.herokuapp.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Myapp getInstance() { return mInstance; }


    public void setJsonStringUser(String jsonStringUser) {
        JsonStringUser = jsonStringUser;
    }

    public String getJsonStringUser() {
        return JsonStringUser;
    }

    public static String getUrlForRequestRestApi() {
        return URL_FOR_REQUEST_REST_API;
    }

    public byte[] getHash(String password) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }

    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }


}
