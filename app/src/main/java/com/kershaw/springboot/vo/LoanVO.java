package com.kershaw.springboot.vo;

import lombok.Data;

@Data
public class LoanVO {
	
	private String loanId;
	
	private String borrowerName;
	
	private String ssn;
	
	private String loanType;
	
	private Double loanAmount;
	
	private Integer dealId;

//	public String getLoanId() {
//		return loanId;
//	}
//
//	public void setLoanId(String loanId) {
//		this.loanId = loanId;
//	}
//
//	public String getBorrowerName() {
//		return borrowerName;
//	}
//
//	public void setBorrowerName(String borrowerName) {
//		this.borrowerName = borrowerName;
//	}
//
//	public String getSsn() {
//		return ssn;
//	}
//
//	public void setSsn(String ssn) {
//		this.ssn = ssn;
//	}
//
//	public String getLoanType() {
//		return loanType;
//	}
//
//	public void setLoanType(String loanType) {
//		this.loanType = loanType;
//	}
//
//
//	public Integer getDealId() {
//		return dealId;
//	}
//
//	public void setDealId(Integer dealId) {
//		this.dealId = dealId;
//	}
//
//	public Double getLoanAmount() {
//		return loanAmount;
//	}
//
//	public void setLoanAmount(Double loanAmount) {
//		this.loanAmount = loanAmount;
//	}
//	
//	

}
