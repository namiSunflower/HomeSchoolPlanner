package com.example.homeschoolplanner;

import java.io.Serializable;

public class ChildModel implements Serializable {
    public String childName;

    public ChildModel(String childName){
        this.childName = childName;
    }
    public String getChildName() {
        return childName;
    }
    public void setChildName(String childName) {
        this.childName = childName;
    }
}
