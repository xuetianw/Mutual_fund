package com.mutualfund.wishlist.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWishlist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int wishlistId;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "customerId")
	private Customer customer;
	private String mfName;
	private String mfFundHouse;
	private int mfSchemaId;
}
