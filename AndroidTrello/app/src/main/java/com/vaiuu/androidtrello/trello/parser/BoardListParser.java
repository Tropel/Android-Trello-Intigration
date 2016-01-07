package com.vaiuu.androidtrello.trello.parser;

import android.content.Context;

import com.vaiuu.androidtrello.trello.holder.AllTrelloBoardHolder;
import com.vaiuu.androidtrello.trello.model.BoardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class BoardListParser {
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESC = "desc";
	private static final String IDORGANIZATION = "idOrganization";
	private static final String URL = "url";
	private static final String VOTING = "voting";
	private static final String PERMISSIONLEVEL = "permissionLevel";
	private static final String INVITATIONS = "invitations";
	private static final String COMMENTS = "comments";
	private static final String CLOSED = "closed";

	public static boolean connect(Context con, String result) throws JSONException, IOException {

		AllTrelloBoardHolder.removeBoardlist();
		if (result.length() < 1) {
			return false;

		}
		final JSONArray jsonObject = new JSONArray(result);
		BoardModel boardModel;
		for (int i = 0; i < jsonObject.length(); i++) {
			JSONObject board_jsonObject = jsonObject.getJSONObject(i);
			JSONObject pref_json_obj = board_jsonObject.getJSONObject("prefs");
			boardModel = new BoardModel();
			AllTrelloBoardHolder allTrelloBoardHolder = new AllTrelloBoardHolder();
			boardModel.setId(board_jsonObject.getString(ID));
			boardModel.setName(board_jsonObject.getString(NAME));
			boardModel.setDesc(board_jsonObject.getString(DESC));
			boardModel.setIdOrganization(board_jsonObject.getString(IDORGANIZATION));
			boardModel.setUrl(board_jsonObject.getString(URL));
			boardModel.setVoting(pref_json_obj.getString(VOTING));
			boardModel.setPermissionLevel(pref_json_obj.getString(PERMISSIONLEVEL));
			boardModel.setInvitations(pref_json_obj.getString(INVITATIONS));
			boardModel.setComments(pref_json_obj.getString(COMMENTS));
			boardModel.setClosed(board_jsonObject.getBoolean(CLOSED));

			allTrelloBoardHolder.setAllBoardlist(boardModel);
			boardModel = null;
		}

		return true;
	}
}
