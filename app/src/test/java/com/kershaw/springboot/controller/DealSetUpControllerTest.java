package com.kershaw.springboot.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kershaw.springboot.model.Deal;
import com.kershaw.springboot.model.Loan;
import com.kershaw.springboot.model.PriorityType;
import com.kershaw.springboot.model.Review;
import com.kershaw.springboot.service.DealService;
import com.kershaw.springboot.vo.LoanVO;
import com.kershaw.springboot.vo.TaskDetailsVO;

//@RunWith(SpringJUnit4ClassRunner.class)
public class DealSetUpControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DealService dealService;

	@InjectMocks
	private DealSetupController dealSetUpController;

	@Mock
	private Deal deal1;

	@Mock
	private Review review;

	@Mock
	private ProcessEngineConfiguration processEngine;

	@Mock
	private TaskService taskService;
	
	
	@Mock
	private RuntimeService runtimeService;
	
	@Mock
	private ProcessInstance processInstance;

	String exampleDealJson = "{\"id\":\"1\",\"clientName\":\"testClientName\",\"dealName\":\"testDealName\",\"reviewDeal\":[{\"reviewType\":\"Compliance\"},{\"reviewType\":\"Loan Mod\"}]}";

	String taskDetailsJson = "{\"taskId\":\"1\",\"dealId\":\"11\",\"dealName\":\"testDealName\",\"loanId\":\"Ln1\",\"taskName\":\"Credit_Paystub\",\"status\":\"completed\",\"timestamp\":\"1\"}";

	String taskDetailsRetrieveJson = "{\"loanId\":\"LN1\",\"taskName\":\"test_taskName\"}";

	/*
	 * [ { "dealId": 3, "loanId": "Ln1", "borrowerName": "B1", "ssn": "2222",
	 * "loanType": "121212", "loanAmount": 12 } ]
	 */

	String createLoanJson = "[{\"dealId\":\"1\",\"loanId\":\"LN1\",\"borrowerName\":\"testBorrowerName\",\"ssn\":\"12345678\",\"loanType\":\"testLoanType\",\"loanAmount\":\"10\"}]";

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(dealSetUpController).build();
	}

	@Test
	public void createDealTest() throws Exception {

		/*
		 * Mockito.when(dealService.saveDealSetUp(deal1)).thenReturn(deal1);
		 * 
		 * RequestBuilder requestBuilder =
		 * MockMvcRequestBuilders.post("/deal/createDeal")
		 * .contentType(MediaType.APPLICATION_JSON).accept(MediaType.
		 * APPLICATION_JSON) .content(convertObjectToJsonBytes(mockDeal()));
		 * 
		 * .content(file) .content((desc))
		 * 
		 * 
		 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		 * 
		 * MockHttpServletResponse response = result.getResponse();
		 * 
		 * assertEquals(HttpStatus.OK.value(), response.getStatus());
		 */

		when(dealService.saveDealSetUp(any(Deal.class))).thenReturn(deal1);

		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/deal/createDeal").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(exampleDealJson))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

	}

	@Test
	public void getAllReviewTypeTest() throws Exception {

		List<Review> reviewTypes = new ArrayList<>();
		review = new Review();
		review.setReviewType("Credit");

		Review review2 = new Review();
		review.setReviewType("Credit");

		Review review3 = new Review();
		review.setReviewType("Credit");

		Review review4 = new Review();
		review.setReviewType("Credit");

		reviewTypes.add(review2);
		reviewTypes.add(review3);
		reviewTypes.add(review4);

		when(dealService.findAllReviews()).thenReturn(reviewTypes);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/deal/reviewType")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Success", 200, result.getResponse().getStatus());
	}

	@Test
	public void getPriorityTypeTest() throws Exception {

		List<PriorityType> priorities = new ArrayList<>();
		priorities.add(PriorityType.HIGH);
		priorities.add(PriorityType.MEDIUM);
		priorities.add(PriorityType.LOW);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/deal/priorityType")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Success", 200, result.getResponse().getStatus());

	}

	
	@Test
	public void createLoanTest() throws Exception {

		when(dealService.isLoanExist(any(List.class))).thenReturn(Boolean.FALSE);

		List<String> reviewTypes=new ArrayList<>();
		reviewTypes.add("Credit");
		reviewTypes.add("Compliance");
		reviewTypes.add("Securitization");
		reviewTypes.add("Loan Mod");

		when(dealService.getReviewType(any(Integer.class))).thenReturn(reviewTypes);
		when(runtimeService.startProcessInstanceByKey(any(String.class), anyMap())).thenReturn(processInstance);
		
		when(dealService.findDealByDealId(any(Integer.class))).thenReturn(deal1);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/deal/createLoan").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(createLoanJson))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Success", 200, result.getResponse().getStatus());
		
		/*
		 * [ { "dealId": 3, "loanId": "Ln1", "borrowerName": "B1", "ssn":
		 * "2222", "loanType": "121212", "loanAmount": 12 } ]
		 */

	}

	private List<LoanVO> mockLoanVO() {

		List<LoanVO> vos = new ArrayList<>();
		LoanVO loanVo = new LoanVO();

		loanVo.setBorrowerName("testBorrowerName");
		loanVo.setDealId(1);
		loanVo.setLoanAmount(new Double(10));
		loanVo.setLoanId("LN1");
		loanVo.setLoanType("testLoanType");
		loanVo.setSsn("123456789");
		vos.add(loanVo);
		return vos;
	}

	@Test
	public void getAllTaskDashboardTest() throws Exception {

		List<TaskDetailsVO> taskDetailsVoList = new ArrayList<>();

		TaskDetailsVO taskDetailsVO = new TaskDetailsVO();
		taskDetailsVO.setDealName("testDealName");
		taskDetailsVO.setDescription("testDesc");
		taskDetailsVO.setLoanId("LN1");
		taskDetailsVO.setStatus("tsetStatus");
		taskDetailsVO.setTaskId("TSK1");
		taskDetailsVO.setTaskName("test_taskName");
		taskDetailsVO.setTimesStamp(new Date());
		taskDetailsVoList.add(taskDetailsVO);

		when(dealService.getAllTaskDashboard()).thenReturn(taskDetailsVoList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/deal/getTaskDashboard")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Success", 200, result.getResponse().getStatus());
	}

	@Test
	public void getTaskDtailsByIdTest() throws Exception {
		List<TaskDetailsVO> taskDetailsVoList = new ArrayList<>();

		TaskDetailsVO taskDetailsVO = new TaskDetailsVO();
		taskDetailsVO.setDealName("testDealName");
		taskDetailsVO.setDescription("testDesc");
		taskDetailsVO.setLoanId("LN1");
		taskDetailsVO.setStatus("tsetStatus");
		taskDetailsVO.setTaskId("TSK1");
		taskDetailsVO.setTaskName("test_taskName");
		taskDetailsVO.setTimesStamp(new Date());
		taskDetailsVoList.add(taskDetailsVO);

		when(dealService.getTaskDtailsById("LN1", "test_taskName")).thenReturn(taskDetailsVoList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/deal/getTaskDetails").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(taskDetailsRetrieveJson))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Success", 200, result.getResponse().getStatus());
	}

	@Test
	public void completeTaskTest() throws Exception {

		when(processEngine.getTaskService()).thenReturn(taskService);
		TaskDetailsVO taskDetailsVO = new TaskDetailsVO();
		taskDetailsVO.setTaskId("54");
	
		when(dealService.isTaskExist(any(String.class))).thenReturn(Boolean.TRUE);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/deal/completeTask")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(taskDetailsJson))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Success", 200, result.getResponse().getStatus());

	}

	private List<Loan> mockLoan(List<LoanVO> loanVos) {

		List<Loan> loans = new ArrayList<>();
		for (LoanVO loanvo : loanVos) {
			Loan loan = new Loan();
			loan.setLoanId(loanvo.getLoanId());
			loan.setBorrowerName(loanvo.getBorrowerName());
			loan.setLoanAmount(loanvo.getLoanAmount());
			loan.setLoanType(loanvo.getLoanType());
			loan.setSsn(loanvo.getSsn());
			loans.add(loan);
		}

		return loans;
	}

	private Deal mockDeal() {
		deal1.setClientName("testClientName");
		deal1.setDealName("testDealName");
		deal1.setDealDate(new Date());
		deal1.setPriority("High");
		deal1.setDealId(1);

		List<Review> reviewTypes = new ArrayList<>();
		review = new Review();
		review.setReviewType("Credit");
		reviewTypes.add(review);

		deal1.setReviewDeal(reviewTypes);
		
		return deal1;

	}

}
