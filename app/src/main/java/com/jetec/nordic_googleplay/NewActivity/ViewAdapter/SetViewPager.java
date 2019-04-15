package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jetec.nordic_googleplay.NewActivity.New_Dialog.DpDialog;
import com.jetec.nordic_googleplay.NewActivity.New_Dialog.New_WriteDialog;
import com.jetec.nordic_googleplay.R;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class SetViewPager {

    private String TAG = "SetViewPager";
    private New_WriteDialog new_writeDialog = new New_WriteDialog();
    private DpDialog dpDialog = new DpDialog();
    private byte[] b_dp = {0x00};
    private int a = 0;
    private boolean dpflag = false;

    public SetViewPager(){
        super();
    }

    public View setView(Context context, String s, List<byte[]> list, Vibrator vibrator){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int point = 0;
        int count = list.size();
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

        byte[] getarr = list.get(0);
        byte[] getdp = Arrays.copyOfRange(getarr, 2, 3);
        if (Arrays.equals(getdp,b_dp)) {
            point = byteArrayToInt(getdp);
            Log.e(TAG,"point = " + point);
        }
        else {
            point = byteArrayToInt(getdp);
            Log.e(TAG,"point = " + point);
        }

        for(int i = 0; i <= count; i++) {
            if(i != count) {
                if (i == 0) {
                    b1.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0) {
                        dpflag = false;
                        b1.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    }
                    else {
                        dpflag = true;
                        b1.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    }
                    int finalI = i;
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b1, vibrator, s);
                        }
                    });
                } else if (i == 1) {
                    b2.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b2.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b2.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b2, vibrator, s);
                        }
                    });
                } else if (i == 2) {
                    b3.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b3.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b3.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b3, vibrator, s);
                        }
                    });
                } else if (i == 3) {
                    b4.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b4.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b4.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b4, vibrator, s);
                        }
                    });
                } else if (i == 4) {
                    b5.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b5.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b5.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b5, vibrator, s);
                        }
                    });
                } else if (i == 5) {
                    b6.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b6.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b6.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b6, vibrator, s);
                        }
                    });
                } else if (i == 6) {
                    b7.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b7.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b7.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b7, vibrator, s);
                        }
                    });
                } else if (i == 7) {
                    b8.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b8.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b8.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b8, vibrator, s);
                        }
                    });
                } else if (i == 8) {
                    b9.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b9.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + byteArrayToInt(value));
                    else
                        b9.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b9, vibrator, s);
                        }
                    });
                } else if (i == 9) {
                    b10.setVisibility(View.VISIBLE);
                    byte[] arr = list.get(i);
                    byte[] str = Arrays.copyOfRange(arr, 0, 2);
                    byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                    byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                    double p = Math.pow(10, byteArrayToInt(dp));
                    if(byteArrayToInt(dp) == 0)
                        b10.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]))
                                + "\n" + byteArrayToInt(value));
                    else
                        b10.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]))
                                + "\n" + (byteArrayToInt(value) / p));
                    int finalI = i;
                    b10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);
                            new_writeDialog.set_Dialog(context, byteArrayToInt(dp), setButtontext(context, s,
                                    get_String(list.get(finalI)[1], list.get(finalI)[0])), list, finalI, str, b10, vibrator, s);
                        }
                    });
                }
            }
            else{
                if(i == 0){
                    b1.setVisibility(View.VISIBLE);
                    b1.setText(context.getString(R.string.dpp));
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 1){
                    b2.setVisibility(View.VISIBLE);
                    b2.setText(context.getString(R.string.dpp));
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 2){
                    b3.setVisibility(View.VISIBLE);
                    b3.setText(context.getString(R.string.dpp));
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 3){
                    b4.setVisibility(View.VISIBLE);
                    b4.setText(context.getString(R.string.dpp));
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 4){
                    b5.setVisibility(View.VISIBLE);
                    b5.setText(context.getString(R.string.dpp));
                    b5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 5){
                    b6.setVisibility(View.VISIBLE);
                    b6.setText(context.getString(R.string.dpp));
                    b6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 6){
                    b7.setVisibility(View.VISIBLE);
                    b7.setText(context.getString(R.string.dpp));
                    b7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 7){
                    b8.setVisibility(View.VISIBLE);
                    b8.setText(context.getString(R.string.dpp));
                    b8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 8){
                    b9.setVisibility(View.VISIBLE);
                    b9.setText(context.getString(R.string.dpp));
                    b9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
                else if(i == 9){
                    b10.setVisibility(View.VISIBLE);
                    b10.setText(context.getString(R.string.dpp));
                    b10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vibrator.vibrate(100);

                        }
                    });
                }
            }
        }

        Log.e(TAG,s + " = " + point);
        return view;
    }



    private String setButtontext(Context context, String s, String gets){
        String str = "";
        if(s.matches("T")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.T) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.T) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.T) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.T) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("H")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.H) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.H) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.H) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.H) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("C")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("D")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("E")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }else if(s.matches("I")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.table_i) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.table_i) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.table_i) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.table_i) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("IH")){
                str = context.getString(R.string.IH);
            }
            else if(gets.startsWith("IL")){
                str = context.getString(R.string.IL);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
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

    private int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return (b[0] & 0xff) << 8 | (b[1] & 0xff);
        else if(b.length == 1)
            return b[0] & 0xff;
        return 0;
    }
}
