package com.example.homeschoolplanner;

import java.io.Serializable;
import java.util.ArrayList;

public class Household implements Serializable {
    String household_name;
    String household_id;
    ArrayList<String> class_list;

    Household(String name, String id, ArrayList<String> classes) {
        household_name = name;
        household_id = id;
        class_list = classes;
    }
    String getHouseholdName() {
        return household_name;
    }
    String getHouseholdId() {
        return household_id;
    }
    ArrayList<String> getClassList() {
        return class_list;
    }

    void AddClass(String new_class) {
        class_list.add(new_class);
    }

}
