package com.kershaw.springboot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kershaw.springboot.constant.ConstantValue;
import com.kershaw.springboot.model.Deal;
import com.kershaw.springboot.model.Loan;
import com.kershaw.springboot.model.PriorityType;
import com.kershaw.springboot.model.Review;
import com.kershaw.springboot.service.DealService;
import com.kershaw.springboot.util.CustomErrorType;
import com.kershaw.springboot.vo.LoanVO;
import com.kershaw.springboot.vo.TaskDetailsVO;

@RestController
@RequestMapping("/deal")
public class DealSetupController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DealService dealService;


	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private ProcessEngineConfiguration processEngine;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/createDeal", method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> createDeal(@RequestBody Deal deal) {

		List<Review> list = new ArrayList<>();
		if (deal.getReviewDeal() == null || deal.getReviewDeal().size() == 0) {
			logger.error("Review Type should not be empty or null");
			return new ResponseEntity(new CustomErrorType("Review deal should not be empty or null"),
					HttpStatus.BAD_REQUEST);
		}
		for (int i = 0; i < deal.getReviewDeal().size(); i++) {
			list.addAll(this.dealService.getReviewType(deal.getReviewDeal().get(i).getReviewType()));
		}
		
		if(deal.getStatus() == null) {
			deal.setStatus(ConstantValue.statusNew);
		}
		
		deal.setReviewDeal(list);
		deal.setStatus(ConstantValue.statusPending);
		Deal deals = null;
		try {
			deals = this.dealService.saveDealSetUp(deal);
		} catch (Exception e) {
			logger.error("Failed to create deal");
			return new ResponseEntity(new CustomErrorType("Unable to create deal " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

		Deal currentDeal = new Deal();
		currentDeal.setDealId(deals.getDealId());
		currentDeal.setClientName(deals.getClientName());
		currentDeal.setDealName(deals.getDealName());
		return new ResponseEntity<Deal>(currentDeal, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/reviewType", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllReviewType() {
		List<Review> reviews = dealService.findAllReviews();
		if (CollectionUtils.isEmpty(reviews)) {
			logger.error("not found review type");
			return new ResponseEntity(new CustomErrorType("not found review type"), HttpStatus.NO_CONTENT);
		}
		List<String> reviewTypes = reviews.stream().map(x -> x.getReviewType()).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(reviewTypes)) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<String>>(reviewTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "/priorityType", method = RequestMethod.GET)
	public ResponseEntity<List<PriorityType>> getPriorityType() {

		List<PriorityType> lists = new ArrayList<PriorityType>();
		for (PriorityType status : PriorityType.values()) {
			lists.add(status);
		}

		return new ResponseEntity<List<PriorityType>>(lists, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/createLoan", method = RequestMethod.POST)
	public ResponseEntity<String> createLoan(@RequestBody List<LoanVO> loanVos) {

		List<Loan> loans = new ArrayList<>();
		for (LoanVO loanvo : loanVos) {
			Loan loan = new Loan();
			loan.setLoanId(loanvo.getLoanId());
			loan.setBorrowerName(loanvo.getBorrowerName());
			loan.setLoanAmount(loanvo.getLoanAmount());
			loan.setLoanType(loanvo.getLoanType());
			loan.setSsn(loanvo.getSsn());
			Deal deal =dealService.findDealByDealId(loanvo.getDealId());
			deal.setDealId(loanvo.getDealId());
			loan.setDeal(deal);
			loans.add(loan);
		}
		if (this.dealService.isLoanExist(loans)) {
			logger.error("Unable to create. A Loan with id already exist.");
			return new ResponseEntity(new CustomErrorType("Unable to create. A Loan with id already exist."),
					HttpStatus.CONFLICT);
		}
		for (int i = 0; i < loanVos.size(); i++) {
			List<String> list = this.dealService.getReviewType(loanVos.get(i).getDealId());
			Map<String, Object> vars = Collections.<String, Object>singletonMap("loan", loans.get(i));
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("userAssign", vars);
			loans.get(i).setProcess_inst_id(pi.getId());
		}
		try {

			this.dealService.saveLoan(loans);

		} catch (Exception e) {

			logger.error("Unable to create loan.");
			return new ResponseEntity(new CustomErrorType("Unable to create loan."), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Saved Loans", HttpStatus.OK);
	}

	@RequestMapping(value = "/getTaskDashboard", method = RequestMethod.GET)
	public ResponseEntity<List<TaskDetailsVO>> getAllTaskDashboard() {
		 List<TaskDetailsVO> listTaskDetails = this.dealService.getAllTaskDashboard();
		return new ResponseEntity<List<TaskDetailsVO>>(listTaskDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTaskDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<List<TaskDetailsVO>> getTaskDtailsById(@RequestBody TaskDetailsVO taskDetailsVO) {
		List<TaskDetailsVO> listTaskDetails = this.dealService.getTaskDtailsById(taskDetailsVO.getLoanId(),
				taskDetailsVO.getTaskName());
		String currentTaskName = listTaskDetails.get(0).getTaskName();
		String[] task = currentTaskName.split("_");
		String description = task[1] + " Loan#" + listTaskDetails.get(0).getLoanId() + " of Deal ID CLY00"
				+ listTaskDetails.get(0).getDealId() + " for Review Type " + task[0];
		listTaskDetails.get(0).setTaskName(task[1]);
		listTaskDetails.get(0).setDescription(description);
		return new ResponseEntity<List<TaskDetailsVO>>(listTaskDetails, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/completeTask", method = RequestMethod.POST)
	public ResponseEntity<?> completeTask(@RequestBody TaskDetailsVO taskDetailsVO) {
		TaskService taskService = processEngine.getTaskService();
		if (this.dealService.isTaskExist(taskDetailsVO.getTaskId())) {
			taskService.complete(taskDetailsVO.getTaskId());
			return new ResponseEntity<TaskDetailsVO>(taskDetailsVO, HttpStatus.OK);
		} else {
			logger.error("Task id :- "+taskDetailsVO.getTaskId()+ "already completeed found");
			return new ResponseEntity(
					new CustomErrorType(
							"Unable to Complete Task with id " + taskDetailsVO.getTaskId() + "Found completed."),
					HttpStatus.NOT_FOUND);
		}
	}

}
