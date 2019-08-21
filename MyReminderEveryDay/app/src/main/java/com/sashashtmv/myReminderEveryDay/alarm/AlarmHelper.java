package com.sashashtmv.myReminderEveryDay.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.sashashtmv.myReminderEveryDay.model.ModelTask;

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
    public  void setAlarm(ModelTask task){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("time_stamp", task.getTimeStamp());
        intent.putExtra("color", task.getPriorityColor());

        //запрашиваем ресивер и в качестве реквест кода получаем time stamp
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), (int)task.getTimeStamp(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //передаем AlarmManager время пробуждения устройства, полную дату задачи и pendingIntent
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, task.getDate(), pendingIntent);
    }

    //метод для удаления задачи
    public  void  removeAlarm(long taskTimeStamp){

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), (int)taskTimeStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //отменяем оповещение
        mAlarmManager.cancel(pendingIntent);
    }
}
