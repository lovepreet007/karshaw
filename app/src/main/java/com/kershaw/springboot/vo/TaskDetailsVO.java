package com.kershaw.springboot.vo;

import java.util.Date;

public class TaskDetailsVO {

	private String taskId;

	private Integer dealId;

	private String dealName;

	private String loanId;

	private String taskName;

	private String description;

	private String status;

	private Date timesStamp;

	public Integer getDealId() {
		return dealId;
	}

	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTimesStamp() {
		return timesStamp;
	}

	public void setTimesStamp(Date timesStamp) {
		this.timesStamp = timesStamp;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TaskDetailsVO [taskId=" + taskId + ", dealId=" + dealId + ", dealName=" + dealName + ", loanId="
				+ loanId + ", taskName=" + taskName + ", description=" + description + ", status=" + status
				+ ", timesStamp=" + timesStamp + "]";
	}

}
