package com.jetec.nordic_googleplay.NewActivity.Function;

import android.widget.EditText;

import com.jetec.nordic_googleplay.NewActivity.EditListener.SearchEditListener;

import java.util.List;

public class SetEditHint {

    private String TAG = "SetEditHint";
    private SearchEditListener searchEditListener = new SearchEditListener();

    public SetEditHint(){
        super();
    }

    public void seteditHint(EditText e1, List<String> nameList, int position){
        String getName = nameList.get(position - 3);
        searchEditListener.setValue(e1, getName);
        if(getName.matches("T")){
            e1.setHint(" - 10 ~ 65");
        }else if(getName.matches("H")){
            e1.setHint(" 0 ~ 100");
        }else if(getName.matches("C")){
            e1.setHint(" 0 ~ 2000");
        }else if(getName.matches("D")){
            e1.setHint(" 0 ~ 3000");
        }else if(getName.matches("E")){
            e1.setHint(" 0 ~ 5000");
        }else if(getName.matches("I")){
            e1.setHint(" -999 ~ 9999");
        }else if(getName.matches("M")){
            e1.setHint(" 0 ~ 1000");
        }
        e1.addTextChangedListener(searchEditListener);
    }
}
