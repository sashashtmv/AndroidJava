package com.sashashtmv.game4in1.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.sashashtmv.game4in1.model.ModelLevel;

public class AlarmHelper {
    private static AlarmHelper instance;
    private Context mContext;
    private AlarmManager mAlarmManager;

    public static AlarmHelper getInstance() {
        if (instance == null){
            instance = new AlarmHelper();
        }
        return instance;
    }

    public void init(Context context){
        mContext = context;
        mAlarmManager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    //будет создавать ресивер и передавать ему данные
    public  void setAlarm(ModelLevel level){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("time_stamp", level.getTimeStamp());

        //запрашиваем ресивер и в качестве реквест кода получаем time stamp
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), (int)level.getTimeStamp(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //передаем AlarmManager время пробуждения устройства, полную дату задачи и pendingIntent
        //mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis()+ 4000, pendingIntent);
        //mAlarmManager.set(AlarmManager.RTC_WAKEUP, 3000, pendingIntent);
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,SystemClock.elapsedRealtime() + 600000, 12000000, pendingIntent);
    }

    //метод для удаления задачи
    public  void  removeAlarm(long taskTimeStamp){

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), (int)taskTimeStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //отменяем оповещение
        mAlarmManager.cancel(pendingIntent);
    }
}
