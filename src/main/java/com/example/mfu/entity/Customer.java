package com.example.mfu.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "fname")
	private String firstName;

	@Column(name = "lname")
	private String lastName;

	@Column(name = "date_of_birth")
	private LocalDate birthDate;

	@Column(name = "email")
	private String email;

	@NonNull
	private String pwd;

//	@Column(name = "verification_status")
//	private String verificationStatus;
}
