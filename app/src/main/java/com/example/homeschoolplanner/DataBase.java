package com.example.homeschoolplanner;

import android.util.Log;

import java.io.Serializable;

public class DataBase implements Serializable {
    static public final String TAG = "DataBase";

    String server_url;
    String server_password;
    int household_id;
    String user_name;

    public static User retrieveUserData(String user_name, String password) {
        return new User();
    }

    public static String createHousehold() {
        Log.d(TAG, "Send Household data to Server.");
        return "";
    }

    public static String createParent() {
        Log.d(TAG, "Sent new parent data to Server.");
        return "";
    }

    public static boolean createChild() {
        Log.d(TAG, "Sent new child data to Server.");
        return false;
    }

    public static boolean createAssignment() {
        Log.d(TAG, "Sent new assignment data to Server.");
        return false;
    }

    public static String parentGetAllAssignments() {
        Log.d(TAG, "Requesting assignment data for all children.");
        return "";
    }

    public static String kidGetHomeworkList() {
        Log.d(TAG, "Requesting homework list for child.");
        return "";
    }

    public static boolean kidCompleteAssignment() {
        Log.d(TAG, "Mark assignment complete.");
        return false;
    }

    public static boolean parentConfirmAssignmentComplete() {
        Log.d(TAG, "Confirm assignment complete.");
        return false;
    }

    public static boolean addClassToHouseHold() {
        Log.d(TAG, "Sending class data to Server.");
        return false;
    }


}
