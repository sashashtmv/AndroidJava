package com.sashashtmv.fastcredit.cards;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardLab {
    private static CardLab sCardLab;
    private static List<Card> sCards;

    public List<Card> getCards() {
        return sCards;
    }


    // метод создания только одного объекта - singleton
    public  static CardLab get(Context context){
        if(sCardLab == null){
            sCardLab = new CardLab(context);
        }
        return sCardLab;
    }

    private CardLab(Context context) {
        sCards = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Card card = new Card();
            card.setTitle("Your Card");
            card.setDescription("credit card in 5 minutes. Return up to 30% from purchase. Installment 0% up to 12 months from partners.");
            card.setAdress(Uri.parse("http://google.com/search?q=кредитные карты"));
            sCards.add(card);
        }
    }

    public Card getBank(UUID id){
        for(Card card : sCards){
            if(card.getId().equals(id)) return card;
        }
        return null;
    }
}
