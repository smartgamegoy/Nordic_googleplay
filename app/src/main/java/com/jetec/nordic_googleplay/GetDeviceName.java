package com.jetec.nordic_googleplay;

import android.content.Context;

import java.util.ArrayList;

public class GetDeviceName {

    private Context context;
    private String TAG = "GetDeviceName", devicename;
    private ArrayList<Character> name;

    public GetDeviceName(Context context, String devicename){
        this.context = context;
        this.devicename = devicename.substring(5, devicename.length());
        name = new ArrayList<>();
        for (int i = 0; i < this.devicename.length(); i++) {
            name.add(this.devicename.charAt(i));
        }
        Value.name = name;
    }

    public String get(String str){

        String rename = "";

        if(str.startsWith("PV1")){
            if(name.get(0).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.PV1);
            }
            else if(name.get(0).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.PV1);
            }
            else if(name.get(0).toString().matches("C") ||
                    name.get(0).toString().matches("D") ||
                    name.get(0).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.PV1);
            }
            else if(name.get(0).toString().matches("I")){
                rename = this.context.getString(R.string.I1) + " " + this.context.getString(R.string.PV1);
            }
        }
        if(str.startsWith("PV2")){
            if(name.get(1).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.PV2);
            }
            else if(name.get(1).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.PV2);
            }
            else if(name.get(1).toString().matches("C") ||
                    name.get(1).toString().matches("D") ||
                    name.get(1).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.PV2);
            }
            else if(name.get(1).toString().matches("I")){
                rename = this.context.getString(R.string.I2) + " " + this.context.getString(R.string.PV2);
            }
        }
        if(str.startsWith("PV3")){
            if(name.get(2).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.PV3);
            }
            else if(name.get(2).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.PV3);
            }
            else if(name.get(2).toString().matches("C") ||
                    name.get(2).toString().matches("D") ||
                    name.get(2).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.PV3);
            }
            else if(name.get(2).toString().matches("I")){
                rename = this.context.getString(R.string.I3) + " " + this.context.getString(R.string.PV3);
            }
        }
        if(str.startsWith("EH1")){
            if(name.get(0).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.EH1);
            }
            else if(name.get(0).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.EH1);
            }
            else if(name.get(0).toString().matches("C") ||
                    name.get(0).toString().matches("D") ||
                    name.get(0).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.EH1);
            }
            else if(name.get(0).toString().matches("I")){
                rename = this.context.getString(R.string.I1) + " " + this.context.getString(R.string.EH1);
            }
        }
        if(str.startsWith("EH2")){
            if(name.get(1).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.EH2);
            }
            else if(name.get(1).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.EH2);
            }
            else if(name.get(1).toString().matches("C") ||
                    name.get(1).toString().matches("D") ||
                    name.get(1).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.EH2);
            }
            else if(name.get(1).toString().matches("I")){
                rename = this.context.getString(R.string.I2) + " " + this.context.getString(R.string.EH2);
            }
        }
        if(str.startsWith("EH3")){
            if(name.get(2).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.EH3);
            }
            else if(name.get(2).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.EH3);
            }
            else if(name.get(2).toString().matches("C") ||
                    name.get(2).toString().matches("D") ||
                    name.get(2).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.EH3);
            }
            else if(name.get(2).toString().matches("I")){
                rename = this.context.getString(R.string.I3) + " " + this.context.getString(R.string.EH3);
            }
        }
        if(str.startsWith("EL1")){
            if(name.get(0).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.EL1);
            }
            else if(name.get(0).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.EL1);
            }
            else if(name.get(0).toString().matches("C") ||
                    name.get(0).toString().matches("D") ||
                    name.get(0).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.EL1);
            }
            else if(name.get(0).toString().matches("I")){
                rename = this.context.getString(R.string.I1) + " " + this.context.getString(R.string.EL1);
            }
        }
        if(str.startsWith("EL2")){
            if(name.get(1).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.EL2);
            }
            else if(name.get(1).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.EL2);
            }
            else if(name.get(1).toString().matches("C") ||
                    name.get(1).toString().matches("D") ||
                    name.get(1).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.EL2);
            }
            else if(name.get(1).toString().matches("I")){
                rename = this.context.getString(R.string.I2) + " " + this.context.getString(R.string.EL2);
            }
        }
        if(str.startsWith("EL3")){
            if(name.get(2).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.EL3);
            }
            else if(name.get(2).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.EL3);
            }
            else if(name.get(2).toString().matches("C") ||
                    name.get(2).toString().matches("D") ||
                    name.get(2).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.EL3);
            }
            else if(name.get(2).toString().matches("I")){
                rename = this.context.getString(R.string.I3) + " " + this.context.getString(R.string.EL3);
            }
        }
        if(str.startsWith("CR1")){
            if(name.get(0).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.CR1);
            }
            else if(name.get(0).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.CR1);
            }
            else if(name.get(0).toString().matches("C") ||
                    name.get(0).toString().matches("D") ||
                    name.get(0).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.CR1);
            }
            else if(name.get(0).toString().matches("I")){
                rename = this.context.getString(R.string.I1) + " " + this.context.getString(R.string.CR1);
            }
        }
        if(str.startsWith("CR2")){
            if(name.get(1).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.CR2);
            }
            else if(name.get(1).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.CR2);
            }
            else if(name.get(1).toString().matches("C") ||
                    name.get(1).toString().matches("D") ||
                    name.get(1).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.CR2);
            }
            else if(name.get(1).toString().matches("I")){
                rename = this.context.getString(R.string.I2) + " " + this.context.getString(R.string.CR2);
            }
        }
        if(str.startsWith("CR3")){
            if(name.get(2).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.CR3);
            }
            else if(name.get(2).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.CR3);
            }
            else if(name.get(2).toString().matches("C") ||
                    name.get(2).toString().matches("D") ||
                    name.get(2).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.CR3);
            }
            else if(name.get(2).toString().matches("I")){
                rename = this.context.getString(R.string.I3) + " " + this.context.getString(R.string.CR3);
            }
        }
        if(str.startsWith("ADR")){
            rename = this.context.getString(R.string.ADR);
        }
        if(str.startsWith("DP1")){
            if(name.get(0).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.DP1);
            }
            else if(name.get(0).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.DP1);
            }
            else if(name.get(0).toString().matches("C") ||
                    name.get(0).toString().matches("D") ||
                    name.get(0).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.DP1);
            }
            else if(name.get(0).toString().matches("I")){
                rename = this.context.getString(R.string.I1) + " " + this.context.getString(R.string.DP1);
            }
        }
        if(str.startsWith("DP2")){
            if(name.get(1).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.DP2);
            }
            else if(name.get(1).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.DP2);
            }
            else if(name.get(1).toString().matches("C") ||
                    name.get(1).toString().matches("D") ||
                    name.get(1).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.DP2);
            }
            else if(name.get(1).toString().matches("I")){
                rename = this.context.getString(R.string.I2) + " " + this.context.getString(R.string.DP2);
            }
        }
        if(str.startsWith("DP3")){
            if(name.get(2).toString().matches("T")){
                rename = this.context.getString(R.string.T) + " " + this.context.getString(R.string.DP3);
            }
            else if(name.get(2).toString().matches("H")){
                rename = this.context.getString(R.string.H) + " " + this.context.getString(R.string.DP3);
            }
            else if(name.get(2).toString().matches("C") ||
                    name.get(2).toString().matches("D") ||
                    name.get(2).toString().matches("E")){
                rename = this.context.getString(R.string.C) + " " + this.context.getString(R.string.DP3);
            }
            else if(name.get(2).toString().matches("I")){
                rename = this.context.getString(R.string.I3) + " " + this.context.getString(R.string.DP3);
            }
        }
        if(str.startsWith("INTER")){
            rename = this.context.getString(R.string.INTER);
        }
        if(str.startsWith("IH1")){
            rename = this.context.getString(R.string.IH1);
        }
        if(str.startsWith("IH2")){
            rename = this.context.getString(R.string.IH2);
        }
        if(str.startsWith("IH3")){
            rename = this.context.getString(R.string.IH3);
        }
        if(str.startsWith("IL1")){
            rename = this.context.getString(R.string.IL1);
        }
        if(str.startsWith("IL2")){
            rename = this.context.getString(R.string.IL2);
        }
        if(str.startsWith("IL3")){
            rename = this.context.getString(R.string.IL3);
        }
        if(str.startsWith("SPK")){
            rename = this.context.getString(R.string.SPK);
        }
        if(str.startsWith("LOG")){
            rename = this.context.getString(R.string.LOG);
        }
        return rename;
    }
}
