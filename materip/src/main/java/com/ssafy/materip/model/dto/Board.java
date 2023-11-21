package com.ssafy.materip.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Board {
	public int id;
	public String title;
	public String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/seoul")
	public Timestamp createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/seoul")
	public Timestamp modifiedAt;
	public String author;
	public int hits;
	public String boardType;
	public int travelSpot;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/seoul")
	public Timestamp travelDate;
	public int maxCount;
	public int currentCount;

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

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	
	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", contents=" + contents + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + ", author=" + author + ", hits=" + hits + ", boardType=" + boardType
				+ ", travelSpot=" + travelSpot + ", travelDate=" + travelDate + ", maxCount=" + maxCount
				+ ", currentCount=" + currentCount + "]";
	}
	
}