package com.vaiuu.androidtrello.trello.holder;


import com.vaiuu.androidtrello.trello.model.CardModel;

import java.util.Vector;

public class AllTrelloCardHolder {
    public static Vector<CardModel> cardModels = new Vector<CardModel>();

    public static Vector<CardModel> getAllCardlist() {
        return cardModels;
    }

    public static void setAllCardlist(Vector<CardModel> cardModels) {
        AllTrelloCardHolder.cardModels = cardModels;
    }

    public static CardModel getAllCardlist(int pos) {
        return cardModels.elementAt(pos);
    }

    public static void setAllCardlist(CardModel cardModels) {
        AllTrelloCardHolder.cardModels.addElement(cardModels);
    }

    public static void removeCardlist() {
        AllTrelloCardHolder.cardModels.removeAllElements();
    }
}
