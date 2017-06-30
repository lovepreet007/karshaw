package com.kershaw.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kershaw.springboot.model.Deal;
import com.kershaw.springboot.model.Loan;

@Repository
public interface DealRepository extends CrudRepository<Deal, Long> {
	
	public Deal findByDealId(Integer dealId);

}
