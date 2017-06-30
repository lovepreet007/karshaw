package com.kershaw.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kershaw.springboot.model.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

	public Loan findByLoanId(String loanId);

}
