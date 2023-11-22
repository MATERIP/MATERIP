package com.ssafy.materip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.materip.model.dao.AttractionInfoDao;
import com.ssafy.materip.model.dto.AttractionInfo;

@Service
public class AttractionInfoServiceImpl implements AttractionInfoService {
	
	private final AttractionInfoDao attractionInfoDao;
	

	public AttractionInfoServiceImpl(AttractionInfoDao attractionInfoDao) {
		super();
		this.attractionInfoDao = attractionInfoDao;
	}
	
	@Override
	public List<String> getTravelNameList() {
		
		return attractionInfoDao.getAttractionNameList();
	}

	@Override
	public List<AttractionInfo> getTravelList() {
		// TODO Auto-generated method stub
		return attractionInfoDao.getAttractionInfoList();
	}

	@Override
	public List<String> getTravelNameSearchList(String title) {
		// TODO Auto-generated method stub
		return attractionInfoDao.getAttractionSearchNameList(title);
	}

	@Override
	public List<AttractionInfo> getTravelListBySido(int sidoCode) {
		return attractionInfoDao.getAttractionInfoBySido(sidoCode);
	}
	
	@Override
	public List<String> getTravelListBySidoGugun(int sidoCode, int gugunCode) {
		return attractionInfoDao.getAttractionInfoBySidoGugun(sidoCode, gugunCode);
	}

	@Override
	public Integer getTravelSpot(AttractionInfo attractionInfo) {
		return attractionInfoDao.getAttractionContentId(attractionInfo);
	}
	
}
