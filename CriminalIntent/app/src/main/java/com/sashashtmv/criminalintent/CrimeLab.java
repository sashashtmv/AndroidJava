package com.sashashtmv.criminalintent;

// класс для записи преступлений

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;// объект, который будет хранить список преступлений
    private static List<Crime> mCrimes;

    // метод создания только одного объекта - singleton
    public  static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        //mAppContext = appContext;
        mCrimes = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2==0);//для каждого второго объекта обозначаем раскрытие преступления
            mCrimes.add(crime);
        }

    }
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    public  Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id)) return crime;
        }
        return null;
    }
}
