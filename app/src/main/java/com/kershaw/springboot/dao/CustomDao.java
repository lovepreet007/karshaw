package com.kershaw.springboot.dao;

import java.util.List;

public interface CustomDao {

	public List<String> getReviewType(Integer dealId);

	public List<Object[]> getAllTaskDashboard();

	public List<Object[]> getTaskDtailsById(String loanId, String taskName);

	public boolean isTaskExist(String taskId);

	//List<Object[]> getAllTaskDashboard1();

}
