package com.ssafy.materip.model.dto;

import java.sql.Timestamp;

public class Board {
	public int id;
	public String title;
	public String contents;
	public Timestamp createdAt;
	public Timestamp modifiedAt;
	public String author;
	public int hits;
	public String boardType;
	public int travelSpot;
	public Timestamp travelDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public int getTravelSpot() {
		return travelSpot;
	}

	public void setTravelSpot(int travelSpot) {
		this.travelSpot = travelSpot;
	}

	public Timestamp getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Timestamp travelDate) {
		this.travelDate = travelDate;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", contents=" + contents + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + ", author=" + author + ", hits=" + hits + ", boardType=" + boardType
				+ ", travelSpot=" + travelSpot + ", travelDate=" + travelDate + "]";
	}
	
}
