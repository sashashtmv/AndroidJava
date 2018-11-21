package com.sashashtmv.fastcredit.loans;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoanerLab {
    private static LoanerLab sLoanerLab;
    private static List<Loaner> sLoaners;

    public List<Loaner> getLoans() {
        return sLoaners;
    }


    // метод создания только одного объекта - singleton
    public  static LoanerLab get(){
        if(sLoanerLab == null){
            sLoanerLab = new LoanerLab(sLoaners);
        }
        return sLoanerLab;
    }

    public LoanerLab(List<Loaner> loaners) {
        sLoaners = loaners;
    }
    //    private LoanerLab(Context context) {
//        sLoaners = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Loaner loaner = new Loaner();
//            loaner.setTitle("Webbankir");
//            loaner.setDescription("Short application. All new customers 0% first loan");
//            loaner.setAdressLoaner(Uri.parse("http://google.com/search?q=займы"));
//            sLoaners.add(loaner);
//        }
//    }

    public Loaner getBank(UUID id){
        for(Loaner loaner : sLoaners){
            if(loaner.getId().equals(id)) return loaner;
        }
        return null;
    }
}
