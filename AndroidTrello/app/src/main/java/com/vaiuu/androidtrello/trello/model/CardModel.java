package com.vaiuu.androidtrello.trello.model;

public class CardModel {
	public String id;
	public String name;
	public String idBoard;
	public String pos;
	public boolean closed;
	public boolean subscribed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdBoard() {
		return idBoard;
	}

	public void setIdBoard(String idBoard) {
		this.idBoard = idBoard;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	@Override
	public String toString() {
		return "CardModel{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", idBoard='" + idBoard + '\'' +
				", pos='" + pos + '\'' +
				", closed=" + closed +
				", subscribed=" + subscribed +
				'}';
	}
}
