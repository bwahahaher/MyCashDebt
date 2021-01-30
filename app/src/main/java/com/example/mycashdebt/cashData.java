package com.example.mycashdebt;

public class cashData {
    String operationName;
    int value;
    int id;

    public cashData(int id, String operationName, int num) {
        this.operationName = operationName;
        this.value = num;
        this.id = id;
    }
}
