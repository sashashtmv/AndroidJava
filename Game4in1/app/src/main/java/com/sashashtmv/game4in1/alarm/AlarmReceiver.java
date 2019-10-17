package com.sashashtmv.game4in1.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.sashashtmv.game4in1.activity.MainActivity;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.model.MyApplication;
import com.sashashtmv.game4in1.model.PreferenceHelper;

import static android.content.ContentValues.TAG;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    PreferenceHelper.getInstance().init(context);
    PreferenceHelper mPreferenceHelper = PreferenceHelper.getInstance();
        //android.os.Debug.waitForDebugger();
        long timeStamp = intent.getLongExtra("time_stamp", 0);
//        DBHelper dbHelper = new DBHelper(context);
//        MainActivity activity = (MainActivity)context;
//        List<ModelLevel> levels = dbHelper.query().getLevels();
//        ModelLevel level = dbHelper.query().getLevel(timeStamp);
        //при вызове ресивера будем передавать ему параметры, такие как тайтл - заголовок задачи, и т.д.
        String title = "Новое слово ждет тебя;)";
//        String title = intent.getStringExtra("title");
        //int color = intent.getIntExtra("color", 0);

        //будет запускать наше главное активити нашего приложения при нажатии на уведомлении
        Intent resultIntent = new Intent(context, MainActivity.class);
     //   resultIntent.putExtra("From", "notifyFrag");

        mPreferenceHelper.putString("From", "notifyFrag");
        Log.i(TAG, "onReceive: run - " + "received");

        //чтобы наш ресивер не запускал активити в случае, если она уже запущена создадим здесь еще один класс
        // с методом возвращающим значение видимости активити. Этот метод будет передавать состояние активити, запущена она или работает в фоне

        if(MyApplication.isActivityVisible()){
            //Log.i(TAG, "onReceive: result - " + activity.getIntent().getStringExtra("From"));
            resultIntent = intent;
            resultIntent.putExtra("From", "notifyFrag");
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //позволяет запустить хранящийся внутри него Интент от имени того приложения, а также с теми полномочиями с которыми этот пендингинтент создавался.
        //передает данные приложения сервису для реализации оповещения
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)timeStamp, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //для построение нотификации, для ее заголовка берем имя приложения, а для текста - заголовок задачи
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Это срочно");
        builder.setContentText(title);
        //builder.setColor(context.getResources().getColor(color));
        builder.setSmallIcon(R.drawable.baseline_input_black_48dp);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo, options);
        builder.setLargeIcon(bitmap);
        //Vibration
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        builder.setLights(Color.RED, 3000, 3000);

        //Ton
        //builder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //показывает, какие свойства уведомления будут унаследованы от системы по умолчанию
        //builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        //notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //этот класс и отвечает за уведомления пользователя. Используется контекст NOTIFICATION_SERVICE для уведомления пользователя о фоновых событиях
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)timeStamp, notification);
    }
}
