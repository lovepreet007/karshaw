package com.kershaw.springboot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;



@Entity
@Table(name = "LOAN")
@Data
public class Loan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Loan() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "loan_id" , nullable = false)
	private String loanId;

	@NotEmpty
	@Column(name = "borrower_name", nullable = false)
	private String borrowerName;

	@NotEmpty
	@Column(name = "ssn", nullable = false)
	private String ssn;

	@NotEmpty
	@Column(name = "loan_type", nullable = false)
	private String loanType;

	@Column(name = "loan_amount")
	private double loanAmount;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "deal_id")
	private Deal deal;
	
	@Column(name = "process_inst_id")
	private String process_inst_id;

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", borrowerName=" + borrowerName + ", ssn=" + ssn + ", loanType=" + loanType
				+ ", loanAmount=" + loanAmount + ", deal=" + deal + "]";
	}
	
	
	
	

}
