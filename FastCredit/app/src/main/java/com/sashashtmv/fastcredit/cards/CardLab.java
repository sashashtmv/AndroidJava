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
    public  static CardLab get(){
        if(sCardLab == null){
            sCardLab = new CardLab(sCards);
        }
        return sCardLab;
    }

    public CardLab(List<Card> cards) {
       sCards = cards;
    }

    public Card getBank(UUID id){
        for(Card card : sCards){
            if(card.getId().equals(id)) return card;
        }
        return null;
    }
}
