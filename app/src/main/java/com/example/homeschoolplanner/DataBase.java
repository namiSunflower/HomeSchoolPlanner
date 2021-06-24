package com.example.homeschoolplanner;

public class DataBase {
    String server_url;
    String password;
    int household_id;
    String user_name;

    public static int createHousehold() {
        return 1;
    }

    public static boolean createParent() {
        return false;
    }

    public static boolean createChild() {
        return false;
    }

    public static boolean createAssignment() {
        return false;
    }

    public static String parentGetAllAssignments() {
        return "";
    }

    public static String kidGetHomeworkList() {
        return "";
    }

    public static boolean kidCompleteAssignment() {
        return false;
    }

    public static boolean parentConfirmAssignmentComplete() {
        return false;
    }

    public static boolean addClassToHouseHold() {
        return false;
    }


}
