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
		return null;
	}
}