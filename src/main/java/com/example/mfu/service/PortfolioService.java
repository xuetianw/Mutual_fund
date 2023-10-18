package com.example.mfu.service;

import com.example.mfu.model.MFDetail;
import com.example.mfu.model.PortfolioProjection;
import com.example.mfu.repository.MFDetailRepository;
import com.example.mfu.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class PortfolioService {
	private final PortfolioRepository portfolioRepository;

	@Autowired
	private final MFDetailRepository mfDetailRepository;

	public PortfolioService(PortfolioRepository portfolioRepository, MFDetailRepository mfDetailRepository) {
		this.portfolioRepository = portfolioRepository;
		this.mfDetailRepository = mfDetailRepository;
	}

	/* GET all portfolios of a customer */
	public List<PortfolioProjection> getPortfolioByCustomerId(@PathVariable Long customerId) {
		return portfolioRepository.findByCustomerId(customerId);
	}


//	public List<PortfolioProjection> getPortfolioByCustomerIdv2(@PathVariable Long customerId) {
//		List<PortfolioProjection> portfolioProjections = portfolioRepository.findByCustomerId(customerId);
//		portfolioProjections.forEach(portfolioProjection -> {
//			MFDetail mfDetail= portfolioProjection.getMfDetail();
//		});
//
//		return null;
//	}
}
