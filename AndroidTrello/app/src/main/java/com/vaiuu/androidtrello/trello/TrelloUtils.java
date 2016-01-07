package com.vaiuu.androidtrello.trello;

public class TrelloUtils {
	/**********************
	 * Trello Constant
	 *************/
	public static final String APPLICATION_ID = "c1b395f3bf98ab45d6f170678c66f164";
	public static final String APPLICATION_NAME = "MEA Error Reports";
	public static String TRELLO_SUCCESS = "Successfully Added New card";
	public static String TRELLO_SUBMIT_CARD = "Submitting Cards Please Wait ...";
	public static String TRELLO_LOADING_BOARDS = "Loading Boards Please Wait ...";
	public static String TRELLO_LOADING_CARDS = "Loading Cards Please Wait ...";
	public static String INTERNET_ERROR = "Internet Connection dry !!!!";
	public static final String TRELLO_URL = "https://trello.com/";
	public static final String TRELLO_API_URL = "https://api.trello.com/1/";
	public static final String ME_BOARDS = "data/me/boards";
	public static final String DATA_BOARD = "data/board";
	public static final String DATA = "data";
	public static final String API_APP = "api/app";
	public static final String TRELLO_AUTH_URL = TRELLO_API_URL + "authorize?" + "response_type=token&key="
			+ APPLICATION_ID
			+ "&return_url=http://127.0.0.1:8080/&callback_method=fragment&scope=read%2Cwrite&expiration=never&name="
			+ APPLICATION_NAME + "";

	public static String getAllBoardsUrl(String token) {
		return TRELLO_API_URL + "members/" + "me/" + "boards/" + "all?" + "key=" + APPLICATION_ID + "&token=" + token;
	}
	public static String getListsByBoard(String token,String boardId) {
		return TRELLO_API_URL + "boards/" +boardId+ "/lists?"+"key=" + APPLICATION_ID + "&token=" + token+"&cards=none";
	}

}
