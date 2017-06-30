package com.kershaw.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("customDao")
public class HibernateCustomDao implements CustomDao {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private ProcessEngineConfiguration processEngine;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getReviewType(Integer dealId) {
		String sql = "select r.review_type from deals d, REVIEW_TYPE r, DEAL_REVIEW_TYPE re "
				+ "where d.deal_id= re.deal_id " + "and r.review_id = re.review_id " + "and d.deal_id= ?";
		Query query = manager.createNativeQuery(sql);
		query.setParameter(1, dealId);
		return query.getResultList();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object[]> getAllTaskDashboard1() {
//		
//        TaskService taskService = processEngine.getTaskService();
//		
//		List<org.activiti.engine.task.Task> remainingTasks = taskService.createTaskQuery().orderByProcessInstanceId().list();
//		
//		for (int i = 0; i < remainingTasks.size(); i++) {
//			taskService.setVariableLocal(remainingTasks.get(i).getId(),"jobStatus", "pending");
//			
//		}
//		
//		String sql = "select distinct d.deal_id as dealId, d.deal_name as dealName, l.loan_id as loanId, art.NAME_ as taskName,art.CREATE_TIME_ as timesStamp from "
//				+ "deals d, " + "LOAN l, " + "act_ru_task art, "
//				+ "(select art.NAME_ as taskName, art.CREATE_TIME_  as createTime, l.loan_id as loanId, art.ID_ as taskId from "
//				+ "act_ru_task art, " + "act_ru_execution are, " + "act_ru_identitylink ari, " + "act_ru_variable arv, "
//				+ "LOAN l " + "where art.EXECUTION_ID_=are.ID_ " + "and are.PROC_INST_ID_= ari.PROC_INST_ID_ "
//				+ "and ari.PROC_INST_ID_=arv.PROC_INST_ID_ " + "and arv.TEXT2_= l.loan_id)task_data "
//				+ "where l.loan_id = task_data.loanId " + "and art.ID_=task_data.taskId " + "and l.deal_id=d.deal_id";
//		Query query = manager.createNativeQuery(sql);
//
//		return query.getResultList();
//
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllTaskDashboard() {
		String sql = "select distinct d.deal_id as dealId, d.deal_name as dealName, l.loan_id as loanId, art.NAME_ as taskName,art.CREATE_TIME_ as timesStamp, d.status from "
				+ "deals d, " + "LOAN l, " + "act_ru_task art, "
				+ "(select art.NAME_ as taskName, art.CREATE_TIME_  as createTime, l.loan_id as loanId, art.ID_ as taskId from "
				+ "act_ru_task art, " + "act_ru_execution are, " + "act_ru_identitylink ari, " + "act_ru_variable arv, "
				+ "LOAN l " + "where art.EXECUTION_ID_=are.ID_ " + "and are.PROC_INST_ID_= ari.PROC_INST_ID_ "
				+ "and ari.PROC_INST_ID_=arv.PROC_INST_ID_ " + "and arv.TEXT2_= l.loan_id)task_data "
				+ "where l.loan_id = task_data.loanId " + "and art.ID_=task_data.taskId " + "and l.deal_id=d.deal_id";
		Query query = manager.createNativeQuery(sql);

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTaskDtailsById(String loanId, String taskName) {
		String sql = "select art.ID_ as taskId, art.NAME_ as taskName, l.loan_id as loanId, art.CREATE_TIME_  as createTime,d.deal_id as dealId from "
				+ "act_ru_task art, " + "act_ru_execution are, " + "act_ru_identitylink ari, " + "act_ru_variable arv, "
				+ "LOAN l, " + "deals d " + "where art.EXECUTION_ID_=are.ID_ "
				+ "and are.PROC_INST_ID_= ari.PROC_INST_ID_ " + "and ari.PROC_INST_ID_=arv.PROC_INST_ID_ "
				+ "and arv.TEXT2_= l.loan_id " + "and l.deal_id = d.deal_id " + "and arv.TEXT2_=? "
				+ "and art.NAME_ = ?";
		Query query = manager.createNativeQuery(sql);
		query.setParameter(1, loanId);
		query.setParameter(2, taskName);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isTaskExist(String taskId) {
		TaskService taskService = processEngine.getTaskService();
		List<org.activiti.engine.task.Task> remainingTasks = taskService.createTaskQuery().list();
		for (int i=0; i<remainingTasks.size(); i++) {
			Task task =  remainingTasks.get(i);
			if(task.getId().equals(taskId)) {
				return true;
			}
		}
		
		return false;
	}

}
