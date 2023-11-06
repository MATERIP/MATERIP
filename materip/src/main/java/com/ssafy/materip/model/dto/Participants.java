package com.ssafy.materip.model.dto;

public class Participants {
	public int boardId;
	public String userId;

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
		return "Participants [boardId=" + boardId + ", userId=" + userId + "]";
	}
    
}
