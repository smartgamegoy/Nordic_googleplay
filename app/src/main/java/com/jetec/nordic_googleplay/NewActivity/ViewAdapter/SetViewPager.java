package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jetec.nordic_googleplay.NewActivity.New_Dialog.*;
import com.jetec.nordic_googleplay.NewActivity.GetString.Parase;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetViewPager {

    private String TAG = "SetViewPager";
    private DpDialog dpDialog = new DpDialog();
    private Parase parase = new Parase();
    private byte[] b_dp = {0x00};

    public SetViewPager() {
        super();
    }

    @SuppressLint("SetTextI18n")
    public View setView(Context context, String s, int getlist_i, Vibrator vibrator, List<Character> nameList) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.tablayoutview, null);

        Button b1 = view.findViewById(R.id.button1);
        Button b2 = view.findViewById(R.id.button2);
        Button b3 = view.findViewById(R.id.button3);
        Button b4 = view.findViewById(R.id.button4);
        Button b5 = view.findViewById(R.id.button5);
        Button b6 = view.findViewById(R.id.button6);
        Button b7 = view.findViewById(R.id.button7);
        Button b8 = view.findViewById(R.id.button8);
        Button b9 = view.findViewById(R.id.button9);
        Button b10 = view.findViewById(R.id.button10);

        b1.setVisibility(View.GONE); //View.VISIBLE
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.GONE);
        b6.setVisibility(View.GONE);
        b7.setVisibility(View.GONE);
        b8.setVisibility(View.GONE);
        b9.setVisibility(View.GONE);
        b10.setVisibility(View.GONE);

        List<Button> buttonList = new ArrayList<>();
        List<byte[]> list = new ArrayList<>();
        list.clear();
        buttonList.clear();
        buttonList.add(b1);
        buttonList.add(b2);
        buttonList.add(b3);
        buttonList.add(b4);
        buttonList.add(b5);
        buttonList.add(b6);
        buttonList.add(b7);
        buttonList.add(b8);
        buttonList.add(b9);
        buttonList.add(b10);

        list = getList(getlist_i);
        int point;
        int count = list.size();
        byte[] getarr = list.get(0);
        byte[] getdp = Arrays.copyOfRange(getarr, 2, 3);
        if (Arrays.equals(getdp, b_dp)) {
            point = parase.byteArrayToInt(getdp);
            Log.e(TAG, "point = " + point);
        } else {
            point = parase.byteArrayToInt(getdp);
            Log.e(TAG, "point = " + point);
        }

        for (int i = 0; i <= count; i++) {
            if (i != count) {
                if (!get_String(list.get(i)[1], list.get(i)[0]).startsWith("RL")) {
                    if (i == 0) {
                        b1.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b1.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b1.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b1.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b1, vibrator, s);
                            });
                        } else {
                            b1.setClickable(false);
                        }
                    } else if (i == 1) {
                        b2.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b2.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b2.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b2.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b2, vibrator, s);
                            });
                        } else {
                            b2.setClickable(false);
                        }
                    } else if (i == 2) {
                        b3.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b3.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b3.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b3.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b3, vibrator, s);
                            });
                        } else {
                            b3.setClickable(false);
                        }
                    } else if (i == 3) {
                        b4.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b4.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b4.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b4.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b4, vibrator, s);
                            });
                        } else {
                            b4.setClickable(false);
                        }
                    } else if (i == 4) {
                        b5.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b5.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b5.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b5.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b5, vibrator, s);
                            });
                        } else {
                            b5.setClickable(false);
                        }
                    } else if (i == 5) {
                        b6.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b6.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b6.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b6.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b6, vibrator, s);
                            });
                        } else {
                            b6.setClickable(false);
                        }
                    } else if (i == 6) {
                        b7.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b7.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b7.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b7.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b7, vibrator, s);
                            });
                        } else {
                            b7.setClickable(false);
                        }
                    } else if (i == 7) {
                        b8.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b8.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b8.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b8.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b8, vibrator, s);
                            });
                        } else {
                            b8.setClickable(false);
                        }
                    } else if (i == 8) {
                        b9.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b9.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b9.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b9.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b9, vibrator, s);
                            });
                        } else {
                            b9.setClickable(false);
                        }
                    } else if (i == 9) {
                        b10.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] str = Arrays.copyOfRange(arr, 0, 2);
                        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        double p = Math.pow(10, parase.byteArrayToInt(dp));
                        if (parase.byteArrayToInt(dp) == 0)
                            b10.setText(title + "\n" + parase.byteArrayToInt(value));
                        else
                            b10.setText(title + "\n" + (parase.byteArrayToInt(value) / p));
                        int finalI = i;
                        if (Value.passwordFlag != 4) {
                            b10.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                New_WriteDialog new_writeDialog = new New_WriteDialog();
                                new_writeDialog.set_Dialog(context, title, getlist_i, finalI, str, b10, vibrator, s);
                            });
                        } else {
                            b10.setClickable(false);
                        }
                    }
                } else {
                    int finalI = i;
                    if (i == 0) {
                        b1.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b1.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b1.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b1, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b1, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b1.setClickable(false);
                        }
                    } else if (i == 1) {
                        b2.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b2.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b2.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b2, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b2, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b2.setClickable(false);
                        }
                    } else if (i == 2) {
                        b3.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b3.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b3.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b3, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b3, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b3.setClickable(false);
                        }
                    } else if (i == 3) {
                        b4.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b4.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b4.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b4, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b4, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b4.setClickable(false);
                        }
                    } else if (i == 4) {
                        b5.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b5.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b5.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b5, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b5, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b5.setClickable(false);
                        }
                    } else if (i == 5) {
                        b6.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b6.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b6.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b6, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b6, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b6.setClickable(false);
                        }
                    } else if (i == 6) {
                        b7.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b7.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b7.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b7, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b7, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b7.setClickable(false);
                        }
                    } else if (i == 7) {
                        b8.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b8.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b8.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b8, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b8, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b8.setClickable(false);
                        }
                    } else if (i == 8) {
                        b9.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b9.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b9.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b9, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b9, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b9.setClickable(false);
                        }
                    } else if (i == 9) {
                        b10.setVisibility(View.VISIBLE);
                        String title = setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]));
                        byte[] arr = list.get(i);
                        byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                        int chose = parase.byteArrayToInt(value);
                        String buttontext = getText(nameList, chose, context);
                        b10.setText(title + "\n" + buttontext);
                        if (Value.passwordFlag != 4) {
                            b10.setOnClickListener(v -> {
                                vibrator.vibrate(100);
                                AlertButton alertButton = new AlertButton();
                                alertButton.set_Dialog(context, b10, vibrator, title, getlist_i, finalI);
                                //resetButton.set_Dialog(context, b10, vibrator, nameList, title, getlist_i, finalI);
                            });
                        } else {
                            b10.setClickable(false);
                        }
                    }
                }
            } else {
                int finalI = i;
                if (i == 1) {
                    b2.setVisibility(View.VISIBLE);
                    b2.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b2.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b2.setClickable(false);
                    }
                } else if (i == 2) {
                    b3.setVisibility(View.VISIBLE);
                    b3.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b3.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b3.setClickable(false);
                    }
                } else if (i == 3) {
                    b4.setVisibility(View.VISIBLE);
                    b4.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b4.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b4.setClickable(false);
                    }
                } else if (i == 4) {
                    b5.setVisibility(View.VISIBLE);
                    b5.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b5.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b5.setClickable(false);
                    }
                } else if (i == 5) {
                    b6.setVisibility(View.VISIBLE);
                    b6.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b6.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b6.setClickable(false);
                    }
                } else if (i == 6) {
                    b7.setVisibility(View.VISIBLE);
                    b7.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b7.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b7.setClickable(false);
                    }
                } else if (i == 7) {
                    b8.setVisibility(View.VISIBLE);
                    b8.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b8.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b8.setClickable(false);
                    }
                } else if (i == 8) {
                    b9.setVisibility(View.VISIBLE);
                    b9.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b9.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b9.setClickable(false);
                    }
                } else if (i == 9) {
                    b10.setVisibility(View.VISIBLE);
                    b10.setText(context.getString(R.string.dpp));
                    if (Value.passwordFlag != 4) {
                        b10.setOnClickListener(v -> {
                            vibrator.vibrate(100);
                            dpDialog.set_Dialog(context, vibrator, context.getString(R.string.dpp),
                                    getlist_i, finalI, s, buttonList);
                        });
                    } else {
                        b10.setClickable(false);
                    }
                }
            }
        }

        Log.e(TAG, s + " = " + point);
        return view;
    }

    private String getText(List<Character> nameList, int chose, Context context) {
        String str = "";
        if (chose == 0) {
            str = context.getString(R.string.off);
        } else {
            str = context.getString(R.string.on);
            /*if(nameList.get((chose - 1)).toString().matches("T")){
                str = context.getString(R.string.T);
            }else if(nameList.get((chose - 1)).toString().matches("H")){
                str = context.getString(R.string.H);
            }else if(nameList.get((chose - 1)).toString().matches("C")){
                str = context.getString(R.string.C);
            }else if(nameList.get((chose - 1)).toString().matches("D")){
                str = context.getString(R.string.C);
            }else if(nameList.get((chose - 1)).toString().matches("E")){
                str = context.getString(R.string.C);
            }else if(nameList.get((chose - 1)).toString().matches("M")){
                str = context.getString(R.string.pm);
            }else if(nameList.get((chose - 1)).toString().matches("I")){
                List<Integer> ilist = new ArrayList<>();
                ilist.clear();
                for(int i  = 0; i < nameList.size(); i++){
                    if(nameList.get(i).toString().matches("I")){
                        ilist.add((i + 1));
                    }
                }
                for(int i = 0; i < ilist.size(); i++){
                    if((ilist.get(i) + 1) == chose){
                        str = context.getString(R.string.table_i) + i;
                    }
                }
            }*/
        }
        return str;
    }

    private String setButtontext(Context context, String s, String gets) {
        String str = "";
        if (s.matches("T")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.T) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.T) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.T) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.T) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("H")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.H) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.H) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.H) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.H) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("C")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("D")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("E")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("I")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.CR1);
            } else if (gets.startsWith("IH")) {
                str = context.getString(R.string.IH);
            } else if (gets.startsWith("IL")) {
                str = context.getString(R.string.IL);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        }

        return str;
    }

    private String get_String(byte a, byte b) {
        String str = "";

        if (a == 0x01) {
            str = "PV" + b;
        } else if (a == 0x02) {
            str = "EH" + b;
        } else if (a == 0x03) {
            str = "EL" + b;
        } else if (a == 0x04) {
            str = "IH" + b;
        } else if (a == 0x05) {
            str = "IL" + b;
        } else if (a == 0x06) {
            str = "CR" + b;
        } else if (a == 0x07) {
            str = "ADR";
        } else if (a == 0x08) {
            str = "Register" + b;
        } else if (a == 0x09) {
            str = "length" + b;
        } else if (a == 0x0A) {
            str = "RL1";
        } else if (a == 0x0B) {
            str = "RL2";
        } else if (a == 0x0C) {
            str = "RL3";
        } else if (a == 0x0D) { //4-20mA output
            str = "OT1";
        } else if (a == 0x0E) {
            str = "OT2";
        } else if (a == 0x0F) {
            str = "OT3";
        }
        return str;
    }

    private List<byte[]> getList(int i) {
        List<byte[]> list = new ArrayList<>();
        list.clear();

        list = NewModel.viewList.get(i);

        return list;
    }
}
