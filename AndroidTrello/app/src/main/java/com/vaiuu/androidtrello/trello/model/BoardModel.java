package com.vaiuu.androidtrello.trello.model;

public class BoardModel {
	public String id;
	public String name;
	public String desc;
	public String idOrganization;
	public String url;
	public String voting;
	public String permissionLevel;
	public String invitations;
	public String comments;
	public boolean closed;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIdOrganization() {
		return idOrganization;
	}

	public void setIdOrganization(String idOrganization) {
		this.idOrganization = idOrganization;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVoting() {
		return voting;
	}

	public void setVoting(String voting) {
		this.voting = voting;
	}

	public String getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public String getInvitations() {
		return invitations;
	}

	public void setInvitations(String invitations) {
		this.invitations = invitations;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "BoardModel [id=" + id + ", name=" + name + ", desc=" + desc + ", idOrganization=" + idOrganization
				+ ", url=" + url + ", voting=" + voting + ", permissionLevel=" + permissionLevel + ", invitations="
				+ invitations + ", comments=" + comments + ", closed=" + closed + "]";
	}

}
