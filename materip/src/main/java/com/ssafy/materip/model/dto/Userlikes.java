package com.ssafy.materip.model.dto;

public class Userlikes {
	
	public int sequence;
	public String id;
	public String likedBy;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(String likedBy) {
        this.likedBy = likedBy;
    }

	@Override
	public String toString() {
		return "Userlikes [sequence=" + sequence + ", id=" + id + ", likedBy=" + likedBy + "]";
	}
    
}
