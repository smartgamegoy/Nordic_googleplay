package com.jetec.nordic_googleplay.NewActivity.Function;

import android.content.Context;
import android.widget.Toast;

import com.jetec.nordic_googleplay.Activity.SearchActivity;
import com.jetec.nordic_googleplay.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchNewList {

    private String TAG = "SearchNewList";
    private List<List<String>> newSavelist;
    private List<String> newTimeList, idList;

    public SearchNewList() {
        newSavelist = new ArrayList<>();
        newTimeList = new ArrayList<>();
        idList = new ArrayList<>();
        newSavelist.clear();
        newTimeList.clear();
        idList.clear();
    }

    public void timeSearchList(Context context, String[] default_model, String chose2, SimpleDateFormat sdf,
                               String timecomparison, List<String> timeList, List<List<String>> saveList) {
        try {
            if (chose2.matches("＞")) {
                if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(timeList.size() - 1)))
                        || sdf.parse(timecomparison).before(sdf.parse(timeList.get(0)))) {
                    Toast.makeText(context, context.getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < timeList.size(); i++) {
                        if (sdf.parse(timecomparison).before(sdf.parse(timeList.get(i)))) {
                            newTimeList.add(timeList.get(i));
                            idList.add(String.valueOf(i + 1));
                        }
                    }
                    for (int i = 0; i < saveList.size(); i++) {
                        List<String> newList = new ArrayList<>();
                        newList.clear();
                        for (int j = 0; j < timeList.size(); j++) {
                            if (sdf.parse(timecomparison).before(sdf.parse(timeList.get(i)))) {
                                newList.add(saveList.get(j).get(i));
                            }
                        }
                        newSavelist.add(newList);
                    }
                    showpage(default_model);
                }
            } else if (chose2.matches("≧")) {
                if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(timeList.size() - 1)))
                        || sdf.parse(timecomparison).before(sdf.parse(timeList.get(0)))) {
                    Toast.makeText(context, context.getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < timeList.size(); i++) {
                        if (sdf.parse(timecomparison).before(sdf.parse(timeList.get(i)))
                                || sdf.parse(timecomparison).equals(sdf.parse(timeList.get(i)))) {
                            newTimeList.add(timeList.get(i));
                            idList.add(String.valueOf(i + 1));
                        }
                    }
                    for (int i = 0; i < saveList.size(); i++) {
                        List<String> newList = new ArrayList<>();
                        newList.clear();
                        for (int j = 0; j < timeList.size(); j++) {
                            if (sdf.parse(timecomparison).before(sdf.parse(timeList.get(i)))
                                    || sdf.parse(timecomparison).equals(sdf.parse(timeList.get(i)))) {
                                newList.add(saveList.get(j).get(i));
                            }
                        }
                        newSavelist.add(newList);
                    }
                    showpage(default_model);
                }
            } else if (chose2.matches("＝")) {
                if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(timeList.size() - 1)))
                        || sdf.parse(timecomparison).before(sdf.parse(timeList.get(0)))) {
                    Toast.makeText(context, context.getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < timeList.size(); i++) {
                        if (sdf.parse(timecomparison).equals(sdf.parse(timeList.get(i)))) {
                            newTimeList.add(timeList.get(i));
                            idList.add(String.valueOf(i + 1));
                        }
                    }
                    for (int i = 0; i < saveList.size(); i++) {
                        List<String> newList = new ArrayList<>();
                        newList.clear();
                        for (int j = 0; j < timeList.size(); j++) {
                            if (sdf.parse(timecomparison).equals(sdf.parse(timeList.get(i)))) {
                                newList.add(saveList.get(j).get(i));
                            }
                        }
                        newSavelist.add(newList);
                    }
                    showpage(default_model);
                }
            } else if (chose2.matches("≦")) {
                if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(timeList.size() - 1)))
                        || sdf.parse(timecomparison).before(sdf.parse(timeList.get(0)))) {
                    Toast.makeText(context, context.getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < timeList.size(); i++) {
                        if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(i)))
                                || sdf.parse(timecomparison).equals(sdf.parse(timeList.get(i)))) {
                            newTimeList.add(timeList.get(i));
                            idList.add(String.valueOf(i + 1));
                        }
                    }
                    for (int i = 0; i < saveList.size(); i++) {
                        List<String> newList = new ArrayList<>();
                        newList.clear();
                        for (int j = 0; j < timeList.size(); j++) {
                            if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(i)))
                                    || sdf.parse(timecomparison).equals(sdf.parse(timeList.get(i)))) {
                                newList.add(saveList.get(j).get(i));
                            }
                        }
                        newSavelist.add(newList);
                    }
                    showpage(default_model);
                }
            } else if (chose2.matches("＜")) {
                if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(timeList.size() - 1)))
                        || sdf.parse(timecomparison).before(sdf.parse(timeList.get(0)))) {
                    Toast.makeText(context, context.getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < timeList.size(); i++) {
                        if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(i)))) {
                            newTimeList.add(timeList.get(i));
                            idList.add(String.valueOf(i + 1));
                        }
                    }
                    for (int i = 0; i < saveList.size(); i++) {
                        List<String> newList = new ArrayList<>();
                        newList.clear();
                        for (int j = 0; j < timeList.size(); j++) {
                            if (sdf.parse(timecomparison).after(sdf.parse(timeList.get(i)))) {
                                newList.add(saveList.get(j).get(i));
                            }
                        }
                        newSavelist.add(newList);
                    }
                    showpage(default_model);
                }
            } else
                Toast.makeText(context, context.getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void idSearchList(Context context, String[] default_model, String chose1, String chose2,
                             String text, ){

    }

    private void showpage(String[] default_model) {

    }
}
