package com.cg.ecom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_details")
public class Customers {

	@Id
	@Column(name = "customerId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	
	@Column(name = "customerName", nullable = false)
	@NotNull(message = "Customer is required")
	private String customerName;
	private String address;
	private Long mobilenumber;
	private String emailId;

//	@OneToOne(cascade = CascadeType.ALL)
//	private Cart cartId;
////	@JoinColumn(name = "userId")
	



}
