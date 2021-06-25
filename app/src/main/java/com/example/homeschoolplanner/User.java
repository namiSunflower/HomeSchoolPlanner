package com.example.homeschoolplanner;

import java.io.Serializable;

public class User implements Serializable {
    String user_id;
    boolean is_parent;
    String password;
    String user_name;
    String household_id;
    Household household;
    DataBase database;

    User() {
        this.user_id = null;
        this.is_parent = true;
        this.password = null;
        this.user_name = null;
        this.household_id = null;
        this.household = null;
        this.database = null;
    }

    User(String user_id, boolean is_parent, String password, String user_name, String household_id, Household household, DataBase database) {
        this.user_id = user_id;
        this.is_parent = is_parent;
        this.password = password;
        this.user_name =user_name;
        this.household_id =household_id;
        this.household =household;
        this.database = database;

    }

    String getUserId() {
        return user_id;
    }

    Boolean getIsParent() {
        return is_parent;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String new_password) {
        password = new_password;
    }

    String getUserName() {
        return user_name;
    }

    String getHouseholdId() {
        return household_id;
    }

    void setHouseholdId(String new_id) {
        household_id = new_id;
    }

    Household getHousehold() {
        return household;
    }

    void setHousehold(Household new_household) {
        household = new_household;
    }

    DataBase getDataBase() {
        return database;
    }

    void setDataBase(DataBase new_database) {
        database = new_database;
    }


}
