package com.ssafy.materip.model.dto;

public class Participants {
	public int id;
	public int boardId;
	public String userId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Participants [id=" + id + ", boardId=" + boardId + ", userId=" + userId + "]";
	}
    
}
