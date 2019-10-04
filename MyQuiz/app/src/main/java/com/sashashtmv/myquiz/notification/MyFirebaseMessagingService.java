package com.sashashtmv.myquiz.notification;

import android.content.Intent;
import android.util.Log;

import com.sashashtmv.myquiz.constants.AppConstants;
import com.sashashtmv.myquiz.data.sqlite.NotificationDbController;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;



public class MyFirebaseMessagingService extends FirebaseMessagingService {


    //срабатывает по приходу уведомления и передает данные уведомления в метод sendNotification, который сохраняет его в базу
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("FCMess", "onMessageReceived: " + remoteMessage);


        if (remoteMessage.getData().size() > 0) {
            Map<String, String> params = remoteMessage.getData();

                sendNotification(params.get("title"), params.get("message"), params.get("url"));
                broadcastNewNotification();
        }
    }


    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.d("FCMess", "onMessageSent: " + s);
    }


    private void sendNotification(String title, String messageBody, String url) {

        // insert data into database
        NotificationDbController notificationDbController = new NotificationDbController(MyFirebaseMessagingService.this);
        notificationDbController.insertData(title, messageBody, url);

    }

    //создает и отправляет широковещательное сообщение об уведомлении
    private void broadcastNewNotification() {
        Intent intent = new Intent(AppConstants.NEW_NOTI);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }

}
