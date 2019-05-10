package com.jetec.nordic_googleplay.NewActivity;

import com.jetec.nordic_googleplay.Value;

import java.util.ArrayList;
import java.util.List;

public class Simulate {

    private String TAG = "模擬資料";

    public List<List<String>> simulate(){
        List<List<String>> simulateList = new ArrayList<>();
        simulateList.clear();

        String name = "THCCTH";
        List<String> nameList = new ArrayList<>();
        nameList.clear();

        for(int i = 0; i < name.length(); i++){
            nameList.add(String.valueOf(name.charAt(i)));
        }
        simulateList.add(nameList);
        for(int i = 0; i < nameList.size() + 1; i++) {
            List<String> testList = new ArrayList<>();
            testList.clear();
            for(int j = 55555; j > 45555; j--){
                double a = j + 111.1;
                testList.add(String.valueOf(a));
            }
            simulateList.add(testList);
        }

        return simulateList;
    }
}
