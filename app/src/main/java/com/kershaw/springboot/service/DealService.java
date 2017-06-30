package com.kershaw.springboot.service;

import java.util.List;

import com.kershaw.springboot.model.Deal;
import com.kershaw.springboot.model.Loan;
import com.kershaw.springboot.model.Review;
import com.kershaw.springboot.vo.TaskDetailsVO;

public interface DealService {

	public Deal saveDealSetUp(Deal deal);

	public List<Review> findAllReviews();

	public void saveLoan(List<Loan> loans);

	public boolean isLoanExist(List<Loan> loans);

	public List<String> getReviewType(Integer dealId);

	public List<TaskDetailsVO> getAllTaskDashboard();

	public List<String> getActivitiReviewType();

	public List<TaskDetailsVO> getTaskDtailsById(String loanId, String taskName);

	public boolean isTaskExist(String taskId);

	public List<Review> getReviewType(String reviewType);

	public Deal findDealByDealId(Integer dealId);
}
