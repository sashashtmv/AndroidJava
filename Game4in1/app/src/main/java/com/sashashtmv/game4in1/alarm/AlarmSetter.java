package com.sashashtmv.game4in1.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sashashtmv.game4in1.database.DBHelper;
import com.sashashtmv.game4in1.model.ModelLevel;

import java.util.ArrayList;
import java.util.List;

public class AlarmSetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper dbHelper = new DBHelper(context);

        AlarmHelper.getInstance().init(context);
        AlarmHelper alarmHelper = AlarmHelper.getInstance();

        List<ModelLevel> modelLevels = dbHelper.query().getLevels();


        //устанавливать напоминание будем через цикл
        for(ModelLevel modelLevel : modelLevels){
            if(modelLevel.getStatus() == ModelLevel.STATUS_AVALABLE){
                alarmHelper.setAlarm(modelLevel);
            }
        }
        dbHelper.close();
    }
}
