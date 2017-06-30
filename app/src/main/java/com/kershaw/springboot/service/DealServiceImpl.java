package com.kershaw.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kershaw.springboot.dao.CustomDao;
import com.kershaw.springboot.model.Deal;
import com.kershaw.springboot.model.Loan;
import com.kershaw.springboot.model.Review;
import com.kershaw.springboot.repository.DealRepository;
import com.kershaw.springboot.repository.LoanRepository;
import com.kershaw.springboot.repository.ReviewRepository;
import com.kershaw.springboot.vo.TaskDetailsVO;

@Service("dealService")
@Transactional
public class DealServiceImpl implements DealService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DealRepository dealRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private CustomDao customDao;

	List<String> reviewlist = new ArrayList<>();

	@Override
	public Deal saveDealSetUp(Deal deal) {

		return dealRepository.save(deal);
	}

	@Override
	public List<Review> findAllReviews() {
		return (List<Review>) reviewRepository.findAll();
	}

	@Override
	public void saveLoan(List<Loan> loans) {
		
		for (Loan loan : loans) {
			try {
				loanRepository.save(loan);
			} catch (Exception e) {
				logger.error("failed to save loan, lona id :"+ loan.getLoanId());
			}
			
		}
	}

	@Override
	public boolean isLoanExist(List<Loan> loans) {
		for (Loan loan : loans) {
			if (findByLoanId(loan.getLoanId()) != null) {
				return Boolean.TRUE;
			}
			continue;
		}
		return Boolean.FALSE;
	}

	private Loan findByLoanId(String loanId) {
		return loanRepository.findByLoanId(loanId);
	}

	@Override
	public List<String> getReviewType(Integer dealId) {
		reviewlist = customDao.getReviewType(dealId);
		return reviewlist;
	}

	@Override
	public List<TaskDetailsVO> getAllTaskDashboard() {
		List<TaskDetailsVO> taskDeatailsList=new ArrayList<>();
		List<Object[]> results= this.customDao.getAllTaskDashboard();

		results.stream().forEach((record) -> {
			TaskDetailsVO userDetailsBO = new TaskDetailsVO();
			userDetailsBO.setDealId((Integer) record[0]);
			userDetailsBO.setDealName((String) record[1]);
			userDetailsBO.setLoanId((String) record[2]);
			userDetailsBO.setTaskName((String) record[3]);
			userDetailsBO.setTimesStamp((Date) record[4]);
			userDetailsBO.setStatus((String) record[5]);
			taskDeatailsList.add(userDetailsBO);
		});
		return taskDeatailsList;
		
	}

	@Override
	public List<String> getActivitiReviewType() {
		return reviewlist;
	}

	@Override
	public boolean isTaskExist(String taskId) {
		return this.customDao.isTaskExist(taskId);
	}

	@Override
	public List<TaskDetailsVO> getTaskDtailsById(String loanId, String taskName) {
		List<TaskDetailsVO> listTaskDetails=new ArrayList<>();
		List<Object[]> results= this.customDao.getTaskDtailsById(loanId, taskName);
		results.stream().forEach((record) -> {
			TaskDetailsVO taskDetails = new TaskDetailsVO();
			taskDetails.setTaskId((String) record[0]);
			taskDetails.setTaskName((String) record[1]);
			taskDetails.setLoanId((String) record[2]);
			taskDetails.setTimesStamp((Date) record[3]);
			taskDetails.setDealId((Integer) record[4]);
			listTaskDetails.add(taskDetails);
		});
		return listTaskDetails;
	}

	@Override
	public List<Review> getReviewType(String reviewType) {
		return this.reviewRepository.getReviewType(reviewType);
	}

	@Override
	public Deal findDealByDealId(Integer dealId) {
		return dealRepository.findByDealId(dealId);
	}
	
}
