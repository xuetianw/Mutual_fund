package com.example.mfu.entity;

import com.example.mfu.entity.Customer;
import com.example.mfu.model.PortfolioProjection;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
public class Portfolio implements PortfolioProjection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "portfolio_id")
	private Long portfolioId;

	@ManyToOne // multiple CustomerPortfolio <-> 1 Customer
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;

	@Column(name = "mf_name")
	private String mfName;

	@Column(name = "mf_fund_house")
	private String mfFundHouse;

	@Column(name = "mf_units_available", nullable = false, columnDefinition = "int default 0")
	private Integer mfUnitsAvailable;

	@Column(name = "currency")
	private String currency;

	@Column(name = "transaction_date")
	private LocalDate transactionDate;

	@ManyToOne
	@JoinColumn(name = "schemaidfk")
	private MFDetail mfDetail;

	public void setCustomerId(Long customerId) {
		if (customer == null) {
			customer = new Customer();
		}
		customer.setCustomerId(customerId);
	}

	public Long getCustomerId() {
		return customer.getCustomerId();
	}
}
