package com.sashashtmv.fastcredit.credits;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankLab {
    private static BankLab sBankLab;
    private static List<Bank> sBanks;

    public List<Bank> getBanks() {
        return sBanks;
    }



    // метод создания только одного объекта - singleton
    public  static BankLab get(Context context){
        if(sBankLab == null){
            sBankLab = new BankLab(context);
        }
        return sBankLab;
    }

    private BankLab(Context context) {
        sBanks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bank bank = new Bank();
            bank.setSum("from 5000 to 50000");
            bank.setRate("from 12%");
            bank.setTerm("from 3 months to 12 months");
            bank.setAdress(Uri.parse("http://google.com/search?q=кредиты онлайн"));
            sBanks.add(bank);
        }
    }

    public  Bank getBank(UUID id){
        for(Bank bank : sBanks){
            if(bank.getId().equals(id)) return bank;
        }
        return null;
    }
}
