package com.sashashtmv.myReminderEveryDay;

import java.text.SimpleDateFormat;

public class Utils {
    public static String getDate(long date){
        //определяем формат выводимой даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String getTime(long time){
        //определяем формат выводимой даты
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(time);
    }

    public static String getFullDate(long date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        return simpleDateFormat.format(date);
    }
}
