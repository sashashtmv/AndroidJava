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
    public  static OtherLab get(Context context){
        if(sOtherLab == null){
            sOtherLab = new OtherLab(context);
        }
        return sOtherLab;
    }

    private OtherLab(Context context) {
        sOthers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Other other = new Other();
            other.setTitle("Title");
            other.setDescription("Description");
            other.setAdress(Uri.parse("http://google.com/search?q="));
            sOthers.add(other);
        }
    }

    public Other getOther(UUID id){
        for(Other other : sOthers){
            if(other.getId().equals(id)) return other;
        }
        return null;
    }
}
