package com.ssafy.materip.model.service;

import com.ssafy.materip.model.dao.AttractionDescriptionDao;
import com.ssafy.materip.model.dao.AttractionInfoDao;
import com.ssafy.materip.model.dto.AttractionDescription;
import com.ssafy.materip.model.dto.AttractionInfo;

public interface AttractionDescriptionService {
	
	AttractionDescription getAttractionDescription(int contentId);
	
	
	
	

}
