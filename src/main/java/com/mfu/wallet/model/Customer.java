package com.mfu.wallet.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Table(name = "customer")
@Entity
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long id;

	@NonNull
	private String fname;

	@NonNull
	private String lname;

	@NonNull
	private String pwd;

	@NonNull
	private String email;

	@Column(name = "date_of_birth")
	@NonNull
	private LocalDate dateOfBirth;
}
