package com.mf.mutualFund.service;

import java.util.List;

import com.mf.mutualFund.model.MFDetail;

public interface MFService {
	
	MFDetail mfDetailService( Integer schemaId ) throws Exception;
	List<MFDetail> mfTopFiveDetailService() throws Exception;
	List<MFDetail> mfBottomFiveDetailService() throws Exception;
	List<MFDetail> mfSearchByName(String name) throws Exception;
	List<MFDetail> mfSearchByCategory(String category) throws Exception;
	List<MFDetail> mfSearchByFundHouse(String fundHouse) throws Exception;
	List<MFDetail> mfGetAllDetails() throws Exception;
}
