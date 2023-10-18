package com.mutualfund.wishlist.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name="customer")
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long customerId;

    @Column(name="fname")
    @NonNull
    private String fname;

    @Column(name="lname")
    @NonNull
    private String lname;

    @Column(name="email")
    @NonNull
    private String email;

    @Column(name="date_of_birth")
    @NonNull
    private LocalDate date_of_birth;
//	@OneToOne(mappedBy = "customer", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//	private CustomerWishlist customerWishlist;
}
