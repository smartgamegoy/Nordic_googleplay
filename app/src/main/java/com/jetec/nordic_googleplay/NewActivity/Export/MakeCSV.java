package com.jetec.nordic_googleplay.NewActivity.Export;

import android.os.StrictMode;
import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.GetUnit;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeCSV {

    private GetUnit getUnit = new GetUnit();
    private String TAG = "MakeCSV";

    public MakeCSV() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public void todocsv(File file, String filePath, List<String> nameList, List<String> timeList, List<List<String>> saveList) {
        try {
            List<String> data = new ArrayList<>();
            data.clear();
            data.add("id");
            data.add("dateTime");
            for (int i = 0; i < nameList.size(); i++) {
                data.add(getUnit.getcsvtitle(nameList.get(i), i));
            }
            String[] data_array = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                data_array[i] = data.get(i);
            }
            String[] data2 = new String[2 + saveList.size()];
            CSVWriter writer;
            if (file.exists() && !file.isDirectory()) {
                FileWriter mFileWriter = new FileWriter(filePath, false);
                writer = new CSVWriter(mFileWriter);
                writer.writeNext(data_array);
                for (int i = 0; i < timeList.size(); i++) {
                    data2[0] = String.valueOf(i);
                    data2[1] = timeList.get(i);
                    for(int j = 0; j < saveList.size(); j++){
                        data2[j + 2] = saveList.get(j).get(i);
                    }
                    writer.writeNext(data2);
                }
                writer.close();
            } else {
                writer = new CSVWriter(new FileWriter(filePath));
                writer.writeNext(data_array);
                for (int i = 0; i < timeList.size(); i++) {
                    data2[0] = String.valueOf(i);
                    data2[1] = timeList.get(i);
                    for(int j = 0; j < saveList.size(); j++){
                        data2[j + 2] = saveList.get(j).get(i);
                    }
                    writer.writeNext(data2);
                }
                writer.close();
            }
            Log.e(TAG, "做好囉");
        } catch (IOException e) {
            Log.e(TAG, "失敗" + e);
            e.printStackTrace();
        }

    }
}
