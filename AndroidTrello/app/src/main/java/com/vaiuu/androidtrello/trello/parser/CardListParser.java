package com.vaiuu.androidtrello.trello.parser;

import android.content.Context;

import com.vaiuu.androidtrello.trello.holder.AllTrelloCardHolder;
import com.vaiuu.androidtrello.trello.model.CardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CardListParser {
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String ID_BOARD = "idBoard";
	private static final String POSITION = "pos";
	private static final String CLOSED = "closed";
	private static final String SUBCRIBED = "subscribed";

	public static boolean connect(Context con, String result) throws JSONException, IOException {

		AllTrelloCardHolder.removeCardlist();
		if (result.length() < 1) {
			return false;
		}
		final JSONArray jsonObject = new JSONArray(result);
		CardModel cardModel;
		firstData();
		for (int i = 0; i < jsonObject.length(); i++) {
			JSONObject board_jsonObject = jsonObject.getJSONObject(i);
			cardModel = new CardModel();
			AllTrelloCardHolder trelloCardHolder = new AllTrelloCardHolder();
			cardModel.setId(board_jsonObject.getString(ID));
			cardModel.setName(board_jsonObject.getString(NAME));
			cardModel.setIdBoard(board_jsonObject.getString(ID_BOARD));
			cardModel.setPos(board_jsonObject.getString(POSITION));
			cardModel.setClosed(board_jsonObject.getBoolean(CLOSED));
			cardModel.setSubscribed(board_jsonObject.getBoolean(SUBCRIBED));
			trelloCardHolder.setAllCardlist(cardModel);
			cardModel = null;
		}

		return true;
	}
	public static void firstData() {
		AllTrelloCardHolder trelloCardHolder = new AllTrelloCardHolder();
		CardModel cardModel = new CardModel();
		cardModel.setId("");
		cardModel.setName("Select");
		cardModel.setIdBoard("");
		cardModel.setPos("");
		cardModel.setClosed(true);
		cardModel.setSubscribed(true);
		trelloCardHolder.setAllCardlist(cardModel);
	}

}
