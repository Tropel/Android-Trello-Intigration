package com.vaiuu.androidtrello.trello.holder;


import com.vaiuu.androidtrello.trello.model.BoardModel;

import java.util.Vector;

public class AllTrelloBoardHolder {
	public static Vector<BoardModel> boardModels = new Vector<BoardModel>();

	public static Vector<BoardModel> getAllBoardlist() {
		return boardModels;
	}

	public static void setAllBoardlist(Vector<BoardModel> boardModels) {
		AllTrelloBoardHolder.boardModels = boardModels;
	}

	public static BoardModel getAllBoardlist(int pos) {
		return boardModels.elementAt(pos);
	}

	public static void setAllBoardlist(BoardModel boardModels) {
		AllTrelloBoardHolder.boardModels.addElement(boardModels);
	}

	public static void removeBoardlist() {
		AllTrelloBoardHolder.boardModels.removeAllElements();
	}
}
