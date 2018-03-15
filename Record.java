package com.company;

public class Record {
    private String name;
    int value;

    public Record(String name, int value){
        this.name = name;
        this.value = value;
    }

    public void SetValue(int increment){
        this.value = this.value + increment;
    }

    public String GetRecordName(){
        return name;
    }

    public int GetRecordValue(){
        return value;
    }

    public void GetInfo(){
        System.out.println(name + " " + value);
    }
}
