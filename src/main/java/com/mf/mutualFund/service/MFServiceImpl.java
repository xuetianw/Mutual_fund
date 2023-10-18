package com.mf.mutualFund.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mf.mutualFund.exception.MutualFundDetailsNotFoundException;
import com.mf.mutualFund.exception.MutualFundNotFoundException;
import com.mf.mutualFund.model.MFDetail;
import com.mf.mutualFund.model.MFDetailHistory;
import com.mf.mutualFund.repository.MFDetailHistoryRepository;
import com.mf.mutualFund.repository.MFDetailRepository;

@Service
public class MFServiceImpl implements MFService {
	
	@Autowired
	private MFDetailRepository mfDetailRepository;
	
	@Autowired
	private MFDetailHistoryRepository mfDetailHistoryRepository;
	
	@Override
	public MFDetail mfDetailService( Integer schemaId ) throws Exception {
		
		if(schemaId.equals(null)) {
			Exception exception = new Exception("Schema Id for mutual fund cannot be empty");
			throw exception;
		}
		List<MFDetailHistory> mfDetailHistory = mfDetailHistoryRepository.findBySchemaId(schemaId); 
//		if(mfDetailHistory.isEmpty()) {
//			MutualFundDetailsNotFoundException exception = new MutualFundDetailsNotFoundException("Historical data not available for the mutual fund in the database");
//			throw exception;
//		}
		MFDetail mfDetail = mfDetailRepository.findBySchemaId(schemaId);
		if(mfDetail.equals(null)) {
			MutualFundNotFoundException exception = new MutualFundNotFoundException("Mutual fund is not available in database.");
			throw exception;
		}
		mfDetail.setMfDetailHistory(mfDetailHistory);
		return mfDetail;	
	}
	
	@Override
	public List<MFDetail> mfTopFiveDetailService() throws Exception {
		
		List<MFDetail> mfDetails = mfDetailRepository.findAll();
		mfDetails.sort((mf1, mf2) -> mf2.getDelta().compareTo(mf1.getDelta()));
		List<MFDetail> mfTopFiveDetails = mfDetails.stream().limit(5).collect(Collectors.toList());
		if(mfTopFiveDetails.isEmpty()) {
			MutualFundNotFoundException exception = new MutualFundNotFoundException("Top five mutual funds not found in database");
			throw exception;
		}
		for(MFDetail mfDetail : mfTopFiveDetails) {
			mfDetail.setMfDetailHistory(mfDetailHistoryRepository.findBySchemaId(mfDetail.getSchemaId()));
		}
		return mfTopFiveDetails;
	}
	
	@Override
	public List<MFDetail> mfBottomFiveDetailService() throws Exception {
		
		List<MFDetail> mfDetails = mfDetailRepository.findAll();
		mfDetails.sort((mf1, mf2) -> mf1.getDelta().compareTo(mf2.getDelta()));
		List<MFDetail> mfBottomFiveDetails = mfDetails.stream().limit(5).collect(Collectors.toList());
		if(mfBottomFiveDetails.isEmpty()) {
			MutualFundNotFoundException exception = new MutualFundNotFoundException("Bottom five mutual funds not found in database");
			throw exception;
		}
		for(MFDetail mfDetail : mfBottomFiveDetails) {
			mfDetail.setMfDetailHistory(mfDetailHistoryRepository.findBySchemaId(mfDetail.getSchemaId()));
		}
		return mfBottomFiveDetails;
	}
	
	@Override
	public List<MFDetail> mfSearchByName(String name) throws Exception {
		
		if(name.isEmpty()) {
			Exception exception = new Exception("Name field cannot be empty");
			throw exception;
		}
		List<MFDetail> mfDetails = mfDetailRepository.findBySchemaNameContaining(name);
		if(mfDetails.isEmpty()) {
			MutualFundNotFoundException exception = new MutualFundNotFoundException("No mutual funds found with the given name");
			throw exception;
		}
		for(MFDetail mfDetail : mfDetails) {
			mfDetail.setMfDetailHistory(mfDetailHistoryRepository.findBySchemaId(mfDetail.getSchemaId()));
		}
		
		return mfDetails;
	}
	
	@Override
	public List<MFDetail> mfSearchByCategory(String category) throws Exception {
		
		if(category.isEmpty()) {
			Exception exception = new Exception("Category field cannot be empty");
			throw exception;
		}
		List<MFDetail> mfDetails = mfDetailRepository.findBySchemaCategoryContaining(category);
		if(mfDetails.isEmpty()) {
			MutualFundNotFoundException exception = new MutualFundNotFoundException("No mutual funds found with the given category");
			throw exception;
		}
		for(MFDetail mfDetail : mfDetails) {
			mfDetail.setMfDetailHistory(mfDetailHistoryRepository.findBySchemaId(mfDetail.getSchemaId()));
		}
		return mfDetails;
	}
	
	@Override
	public List<MFDetail> mfSearchByFundHouse(String fundHouse) throws Exception {
		
		if(fundHouse.isEmpty()) {
			Exception exception = new Exception("Fund house field cannot be empty");
			throw exception;
		}
		List<MFDetail> mfDetails = mfDetailRepository.findByFundHouseContaining(fundHouse);
		if(mfDetails.isEmpty()) {
			MutualFundNotFoundException exception = new MutualFundNotFoundException("No mutual funds found with the given fund house");
			throw exception;
		}
		for(MFDetail mfDetail : mfDetails) {
			mfDetail.setMfDetailHistory(mfDetailHistoryRepository.findBySchemaId(mfDetail.getSchemaId()));
		}
		return mfDetails;
	}
	
	@Override
	public List<MFDetail> mfGetAllDetails() throws Exception {
		List<MFDetail> mfDetails = mfDetailRepository.findAll();
		for(MFDetail mfDetail : mfDetails) {
			mfDetail.setMfDetailHistory(mfDetailHistoryRepository.findBySchemaId(mfDetail.getSchemaId()));
		}
		return mfDetails;
	}

}
