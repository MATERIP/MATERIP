package com.ssafy.materip.model.dto;

public class TravelSpots {
	public int id;
	public float mapX;
	public float mapY;
	public String name;
	public String description;
	public int areaCode;
	public int sigunguCode;
	public int contentTypeId;
	public String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMapX() {
        return mapX;
    }

    public void setMapX(float mapX) {
        this.mapX = mapX;
    }

    public float getMapY() {
        return mapY;
    }

    public void setMapY(float mapY) {
        this.mapY = mapY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getSigunguCode() {
        return sigunguCode;
    }

    public void setSigunguCode(int sigunguCode) {
        this.sigunguCode = sigunguCode;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(int contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	@Override
	public String toString() {
		return "TravelSpots [id=" + id + ", mapX=" + mapX + ", mapY=" + mapY + ", name=" + name + ", description="
				+ description + ", areaCode=" + areaCode + ", sigunguCode=" + sigunguCode + ", contentTypeId="
				+ contentTypeId + ", address=" + address + "]";
	}
    
}
