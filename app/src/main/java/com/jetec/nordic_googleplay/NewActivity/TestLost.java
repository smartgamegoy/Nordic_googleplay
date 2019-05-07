package com.jetec.nordic_googleplay.NewActivity;

import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToInt;
import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class TestLost {

    private ListView list1;
    private ArrayAdapter<String> listAdapter;

    public TestLost(ArrayAdapter<String> listAdapter, ListView list1){
        this.listAdapter = listAdapter;
        this.list1 = list1;
    }

    public void getlost(List<Integer> recordlist, int i){
        ByteToInt byteToInt = new ByteToInt();
        android.os.Handler lostHandler = new Handler();
        SendString sendString = new SendString();
        final int[] lost_i = {i};
        byte[] getwant = {0x57, 0x41, 0x4E, 0x54};
        lostHandler.postDelayed(() -> {
            if(i < recordlist.size()) {
                SimpleDateFormat get_time = new SimpleDateFormat("HH:mm:ss:SSS");
                String currentDateTimeString = get_time.format(new Date());
                int lost = recordlist.get(i);
                byte[] lostbyte = byteToInt.intToByteArray(lost);
                byte[] wantbyte = new byte[(getwant.length + lostbyte.length)];
                System.arraycopy(getwant, 0, wantbyte, 0, getwant.length);
                System.arraycopy(lostbyte, 0, wantbyte, getwant.length, lostbyte.length);
                StringBuilder hex = new StringBuilder(wantbyte.length * 2);
                for (byte aData : wantbyte) {
                    hex.append(String.format("%02X", aData));
                }
                String gethex = hex.toString();
                listAdapter.add("[" + currentDateTimeString + "] send: " + gethex);
                list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                sendString.sendbyte(wantbyte);
                lost_i[0]++;
                /*try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                getlost(recordlist, lost_i[0]);
            }
            else {
                lostHandler.removeCallbacksAndMessages(null);
            }
        }, 50);
    }
}
