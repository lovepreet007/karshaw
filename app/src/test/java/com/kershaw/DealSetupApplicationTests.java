package com.kershaw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kershaw.springboot.model.Deal;
import com.kershaw.springboot.model.Loan;
import com.kershaw.springboot.model.PriorityType;
import com.kershaw.springboot.model.Review;
import com.kershaw.springboot.service.DealService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DealSetupApplicationTests {

	@Autowired
	DealService dealService;
	
	@Autowired
	private ProcessEngineConfiguration processEngine;

	@Test
	public void testSaveDealSetUp() { // test case for saveDealSetUp function

		Deal deal = new Deal();
		deal.setClientName("deepa");
		deal.setDealName("abc");
		deal.setPriority(PriorityType.HIGH.toString());
		deal.setDealDate(new Date());
		Deal dbDeal = dealService.saveDealSetUp(deal);

		Assert.assertEquals(deal.getClientName(), dbDeal.getClientName());
		Assert.assertEquals(deal.getDealName(), dbDeal.getDealName());
		Assert.assertEquals(deal.getPriority(), dbDeal.getPriority());
		Assert.assertEquals(deal.getDealDate(), dbDeal.getDealDate());

		// For Negative scenarios we can not pass null value
		Assert.assertNotEquals("ABC", dbDeal.getClientName());
		Assert.assertNotEquals("NBFF", dbDeal.getDealName());
		Assert.assertNotEquals("low", dbDeal.getPriority());
		Assert.assertNotEquals("2017-06-17", dbDeal.getDealDate());
	}

	@Test
	public void testFindAllReviews() { // test case for findAllReviews function
		List<Review> listReviews = dealService.findAllReviews();
		Assert.assertEquals(4, listReviews.size());
		// negative scenario
		Assert.assertNotEquals(6, listReviews.size());
	}

	@Test
	public void testSaveLoan() { // test case for saveLoan function
		Loan testloan1 = new Loan();
		testloan1.setBorrowerName("Deepika");
		testloan1.setLoanId("LN0012");
		testloan1.setLoanType("ABC");
		testloan1.setSsn("123-12-1234");
		testloan1.setLoanAmount(300);

		Loan testloan2 = new Loan();
		testloan2.setBorrowerName("Deepika");
		testloan2.setLoanId("LN0013");
		testloan2.setLoanType("XYZ");
		testloan2.setSsn("123-12-1234");
		testloan2.setLoanAmount(800);

		List<Loan> loans = new ArrayList<Loan>();
		loans.add(testloan1);
		loans.add(testloan2);

		dealService.saveLoan(loans);
		Assert.assertTrue(dealService.isLoanExist(loans)); // test case for isLoanExist function
	}

	@Test
	public void testgetReviewType() { // test case for getReviewType function
		Assert.assertNotNull("At least on review type should be there", dealService.getReviewType(1));
	}

	@Test
	public void testGetAllTaskDashboard() { // test case for getAllTaskDashboard function

		//System.out.println(numberOfTasks.size());
	}

	@Test
	public void testGetActivitiReviewType() { // test case for getAllTaskDashboard function

		List<String> listReviews = dealService.getReviewType(4);
		List<String> listActivitiReviews = dealService.getActivitiReviewType();
		//System.out.println(listReviews.size());
		//System.out.println(listActivitiReviews.size());
		Assert.assertEquals(listReviews, listActivitiReviews);
	}

	@Test
	public void testIsTaskExist() { // test case for isTaskExist function
		TaskService taskService = processEngine.getTaskService();
		List<org.activiti.engine.task.Task> remainingTasks = taskService.createTaskQuery().list();
		Assert.assertEquals(dealService.isTaskExist(remainingTasks.get(0).getId()), true);
	}

}