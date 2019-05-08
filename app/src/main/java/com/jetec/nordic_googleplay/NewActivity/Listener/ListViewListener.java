package com.jetec.nordic_googleplay.NewActivity.Listener;

import java.util.List;

public interface ListViewListener {
    void convert(List<String> nameList, List<String> timeList, List<List<String>> saveList);
    void makecsv();
    void makepdf();
}
