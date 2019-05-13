package com.jetec.nordic_googleplay.NewActivity.GetString;

import android.content.Context;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Value;
import java.util.ArrayList;
import java.util.List;

public class GetUnit {

    public GetUnit(){
        super();
    }

    public String getItemString(Context context, String item, int i) {
        String name = "";

        if (item.matches("T")) {
            name = context.getString(R.string.T);
        } else if (item.matches("H")) {
            name = context.getString(R.string.H);
        } else if (item.matches("C") || item.matches("D") || item.matches("E")) {
            name = context.getString(R.string.C);
        } else if (item.matches("I")) {
            String model = Value.deviceModel;
            String[] arr = model.split("-");
            String namearr = arr[2];
            List<Integer> num = new ArrayList<>();
            num.clear();
            for (int j = 0; j < namearr.length(); j++) {
                if (!String.valueOf(name.charAt(j)).matches("Y") || !String.valueOf(name.charAt(j)).matches("Z")) {
                    num.add(j);
                }
            }
            if (num.get(i) == 0) {
                name = context.getString(R.string.I1row);
            } else if (num.get(i) == 1) {
                name = context.getString(R.string.I2row);
            } else if (num.get(i) == 2) {
                name = context.getString(R.string.I3row);
            } else if (num.get(i) == 3) {
                name = context.getString(R.string.I4row);
            } else if (num.get(i) == 4) {
                name = context.getString(R.string.I5row);
            } else if (num.get(i) == 5) {
                name = context.getString(R.string.I6row);
            }
        } else if (item.matches("M")) {
            name = context.getString(R.string.pm);
        }

        return name;
    }

    public String getcsvtitle(String item, int i){
        String title = "";

        if (item.matches("T")) {
            title = "Temperature/C";
        } else if (item.matches("H")) {
            title = "Humidity/%";
        } else if (item.matches("C") || item.matches("D") || item.matches("E")) {
            title = "CO2/ppm";
        } else if (item.matches("I")) {
            String model = Value.deviceModel;
            String[] arr = model.split("-");
            String namearr = arr[2];
            List<Integer> num = new ArrayList<>();
            num.clear();
            for (int j = 0; j < namearr.length(); j++) {
                if (!String.valueOf(title.charAt(j)).matches("Y") || !String.valueOf(title.charAt(j)).matches("Z")) {
                    num.add(j);
                }
            }
            if (num.get(i) == 0) {
                title = "Analog1";
            } else if (num.get(i) == 1) {
                title = "Analog2";
            } else if (num.get(i) == 2) {
                title = "Analog3";
            } else if (num.get(i) == 3) {
                title = "Analog4";
            } else if (num.get(i) == 4) {
                title = "Analog5";
            } else if (num.get(i) == 5) {
                title = "Analog6";
            }
        } else if (item.matches("M")) {
            title = "PM2.5/ug/m3";
        }

        return title;
    }

    public String getunit(String str) {

        if(str.matches("T")){
            str = (char)(186) + "C";
        }
        else if(str.matches("H")){
            str = "%";
        }
        else if(str.matches("C") || str.matches("D") || str.matches("E")){
            str = "ppm";
        }
        else if(str.matches("I")){
            str = "";
        }
        else if(str.matches("M")){
            str = (char) (181) + "g/m" + (char) (179);
        }

        return str;
    }
}
