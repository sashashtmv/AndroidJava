package com.sashashtmv.taskmanager.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.sashashtmv.taskmanager.MainActivity;
import com.sashashtmv.taskmanager.MyApplication;
import com.sashashtmv.taskmanager.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //при вызове ресивера будем передавать ему параметры, такие как тайтл - заголовок задачи, и т.д.
        String title = intent.getStringExtra("title");
        long timeStamp = intent.getLongExtra("time_stamp", 0);
        int color = intent.getIntExtra("color", 0);

        //будет запускать наше главное активити нашего приложения при нажатии на уведомлении
        Intent resultIntent = new Intent(context, MainActivity.class);
        //чтобы наш ресивер не запускал активити в случае, если она уже запущена создадим здесь еще один класс
        // с методом возвращающим значение видимости активити. Этот метод будет передавать состояние активити, запущена она или работает в фоне

        if(MyApplication.isActivityVisible()){
            resultIntent = intent;
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //позволяет запустить хранящийся внутри него Интент от имени того приложения, а также с теми полномочиями с которыми этот пендингинтент создавался.
        //передает данные приложения сервису для реализации оповещения
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)timeStamp, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //для построение нотификации, для ее заголовка берем имя приложения, а для текста - заголовок задачи
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("TaskManager");
        builder.setContentText(title);
        builder.setColor(context.getResources().getColor(color));
        builder.setSmallIcon(R.drawable.baseline_check_circle_outline_white_48dp);
        //показывает, какие свойства уведомления будут унаследованы от системы по умолчанию
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //этот класс и отвечает за уведомления пользователя. Используется контекст NOTIFICATION_SERVICE для уведомления пользователя о фоновых событиях
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)timeStamp, notification);
    }
}
