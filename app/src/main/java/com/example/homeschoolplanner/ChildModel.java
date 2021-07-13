package com.example.homeschoolplanner;

import java.io.Serializable;
//this class is used to set up the list of each child's info under parent node
public class ChildModel implements Serializable {
    public String childName;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String childId;

    public ChildModel(String childId, String childName){
        this.childId = childId;
        this.childName = childName;
    }
}
