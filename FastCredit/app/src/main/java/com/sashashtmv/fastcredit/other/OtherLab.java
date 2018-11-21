package com.sashashtmv.fastcredit.other;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OtherLab {
    private static OtherLab sOtherLab;
    private static List<Other> sOthers;

    public List<Other> getOthers() {
        return sOthers;
    }


    // метод создания только одного объекта - singleton
    public  static OtherLab get(){
        if(sOtherLab == null){
            sOtherLab = new OtherLab(sOthers);
        }
        return sOtherLab;
    }

    public OtherLab(List<Other> others) {
        sOthers = others;

    }

    public Other getOther(UUID id){
        for(Other other : sOthers){
            if(other.getId().equals(id)) return other;
        }
        return null;
    }
}
