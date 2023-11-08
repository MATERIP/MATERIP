package com.ssafy.materip.model.dto;

import java.sql.Timestamp;

public class Comments {
	public int sequence;
	public int articleId;
	public String author;
	public String contents;
	public Timestamp createdAt;
	public Timestamp modifiedAt;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	@Override
	public String toString() {
		return "Comments [sequence=" + sequence + ", articleId=" + articleId + ", author=" + author + ", contents="
				+ contents + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + "]";
	}

}
