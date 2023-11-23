package com.ssafy.materip.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.materip.model.dao.AttractionDescriptionDao;
import com.ssafy.materip.model.dto.AttractionDescription;

@Service
public class AttractionDescriptionServiceImpl implements AttractionDescriptionService{
	
	
	private final AttractionDescriptionDao attractionDescriptionDao;
	
	
	public AttractionDescriptionServiceImpl(AttractionDescriptionDao attractionDescriptionDao) {
		super();
		this.attractionDescriptionDao = attractionDescriptionDao;
	}



	@Override
	public AttractionDescription getAttractionDescription(int contentId) {
		// TODO Auto-generated method stub
		
		return attractionDescriptionDao.getAttractionDescription(contentId);
	}
	

}
