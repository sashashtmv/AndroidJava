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
    public  static BankLab get(){
        if(sBankLab == null){
            sBankLab = new BankLab(sBanks);
        }
        return sBankLab;
    }

    public BankLab(List<Bank> banks) {
        sBanks = banks;
    }

    public  Bank getBank(UUID id){
        for(Bank bank : sBanks){
            if(bank.getId().equals(id)) return bank;
        }
        return null;
    }
}
