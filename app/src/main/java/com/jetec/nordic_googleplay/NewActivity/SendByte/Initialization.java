package com.jetec.nordic_googleplay.NewActivity.SendByte;

import com.jetec.nordic_googleplay.Service.BluetoothLeService;

import java.util.ArrayList;

import static java.lang.Thread.enumerate;
import static java.lang.Thread.sleep;

public class Initialization {

    private ArrayList<Character> model = new ArrayList<>();
    private BluetoothLeService mBluetoothLeService;
    private Send send;
    private final String[] T = {"PV", "EH", "EL", "CR"};
    private final String[] H = {"PV", "EH", "EL", "CR"};
    private final String[] C = {"PV", "EH", "EL", "CR"};
    private final String[] D = {"PV", "EH", "EL", "CR"};
    private final String[] E = {"PV", "EH", "EL", "CR"};
    private final String[] I = {"PV", "EH", "EL", "IH", "IL", "CR"};
    private final String[] L = {"COUNT", "INTER", "DATE", "TIME", "LOG"};
    private final String[] SP = {"SPK"};
    private final String[] RL = {"RL1", "RL2", "RL3"};
    private final String[] OT = {"OT1", "OT2", "OT3"};


    public Initialization() {
        super();
    }

    public void setinitial(String str, BluetoothLeService mBluetoothLeService) {
        send = new Send(mBluetoothLeService);
        model.clear();
        for (int i = 0; i < str.length(); i++) {
            model.add(str.charAt(i));
        }
    }

    public void startinitial() {
        try {
            String str = "";
            for (int i = 0; i < model.size(); i++) {
                if (model.get(i).toString().matches("T")) {   //-10~65
                    for (int j = 0; j < T.length; j++) {
                        if (T[j].matches("PV")) {
                            str = T[j] + (i + 1) + "+" + "0";
                        } else if (T[j].matches("EH")) {
                            str = T[j] + (i + 1) + "+" + "65";
                        } else if (T[j].matches("EL")) {
                            str = T[j] + (i + 1) + "+" + "-10";
                        } else if (T[j].matches("CR")) {
                            str = T[j] + (i + 1) + "+" + "0";
                        }
                        send.sendinitial(str);
                        sleep(100);
                    }
                    for (int j = 0; j < RL.length; j++) {
                        str = RL[j] + (i + 1) + "+" + "0";
                        send.sendinitial(str);
                        sleep(100);
                    }
                } else if (model.get(i).toString().matches("H")) {  //0~100
                    for (int j = 0; j < H.length; j++) {
                        if (H[j].matches("PV")) {
                            str = H[j] + (i + 1) + "+" + "0";
                        } else if (H[j].matches("EH")) {
                            str = H[j] + (i + 1) + "+" + "100";
                        } else if (H[j].matches("EL")) {
                            str = H[j] + (i + 1) + "+" + "0";
                        } else if (H[j].matches("CR")) {
                            str = H[j] + (i + 1) + "+" + "0";
                        }
                        send.sendinitial(str);
                        sleep(100);
                    }
                    for (int j = 0; j < RL.length; j++) {
                        str = RL[j] + (i + 1) + "+" + "0";
                        send.sendinitial(str);
                        sleep(100);
                    }
                } else if (model.get(i).toString().matches("C")) {  //0~2000
                    for (int j = 0; j < C.length; j++) {
                        if (C[j].matches("PV")) {
                            str = C[j] + (i + 1) + "+" + "0";
                        } else if (C[j].matches("EH")) {
                            str = C[j] + (i + 1) + "+" + "2000";
                        } else if (C[j].matches("EL")) {
                            str = C[j] + (i + 1) + "+" + "0";
                        } else if (C[j].matches("CR")) {
                            str = C[j] + (i + 1) + "+" + "0";
                        }
                        send.sendinitial(str);
                        sleep(100);
                    }
                    for (int j = 0; j < RL.length; j++) {
                        str = RL[j] + (i + 1) + "+" + "0";
                        send.sendinitial(str);
                        sleep(100);
                    }
                } else if (model.get(i).toString().matches("D")) {  //0~3000
                    for (int j = 0; j < D.length; j++) {
                        if (D[j].matches("PV")) {
                            str = D[j] + (i + 1) + "+" + "0";
                        } else if (D[j].matches("EH")) {
                            str = D[j] + (i + 1) + "+" + "3000";
                        } else if (D[j].matches("EL")) {
                            str = D[j] + (i + 1) + "+" + "0";
                        } else if (D[j].matches("CR")) {
                            str = D[j] + (i + 1) + "+" + "0";
                        }
                        send.sendinitial(str);
                        sleep(100);
                    }
                    for (int j = 0; j < RL.length; j++) {
                        str = RL[j] + (i + 1) + "+" + "0";
                        send.sendinitial(str);
                        sleep(100);
                    }
                } else if (model.get(i).toString().matches("E")) {  //0~5000
                    for (int j = 0; j < E.length; j++) {
                        if (E[j].matches("PV")) {
                            str = E[j] + (i + 1) + "+" + "0";
                        } else if (E[j].matches("EH")) {
                            str = E[j] + (i + 1) + "+" + "5000";
                        } else if (E[j].matches("EL")) {
                            str = E[j] + (i + 1) + "+" + "0";
                        } else if (E[j].matches("CR")) {
                            str = E[j] + (i + 1) + "+" + "0";
                        }
                        send.sendinitial(str);
                        sleep(100);
                    }
                    for (int j = 0; j < RL.length; j++) {
                        str = RL[j] + (i + 1) + "+" + "0";
                        send.sendinitial(str);
                        sleep(100);
                    }
                } else if (model.get(i).toString().matches("I")) {  //9999~-999
                    for (int j = 0; j < I.length; j++) {
                        if(I[j].matches("IH")){
                            str = I[j] + (i + 1) + "+" + "9999";
                        }
                        else if(I[j].matches("IL")){
                            str = I[j] + (i + 1) + "+" + "-999";
                        }
                        else if (I[j].matches("PV")) {
                            str = I[j] + (i + 1) + "+" + "0";
                        } else if (I[j].matches("EH")) {
                            str = I[j] + (i + 1) + "+" + "9999";
                        } else if (I[j].matches("EL")) {
                            str = I[j] + (i + 1) + "+" + "-999";
                        } else if (I[j].matches("CR")) {
                            str = I[j] + (i + 1) + "+" + "0";
                        }
                        send.sendinitial(str);
                        sleep(100);
                    }
                    for (int j = 0; j < RL.length; j++) {
                        str = RL[j] + (i + 1) + "+" + "0";
                        send.sendinitial(str);
                        sleep(100);
                    }
                }
            }
            /*for (int i = 0; i < OT.length; i++) {
                str = OT[i] + "+" + "0";
                send.sendinitial(str);
                sleep(100);
            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
