package com.kershaw.springboot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;



@Entity
@Table(name = "REVIEW_TASK_TYPE")
public class ReviewTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_task_type_id")
	private Long reviewTaskId;

	@NotEmpty
	@Column(name = "task_name", nullable = false)
	private String taskName;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "review_id")
	private Review review;

	@Override
	public String toString() {
		return "ReviewTask [reviewTaskId=" + reviewTaskId + ", taskName=" + taskName + ", review=" + review + "]";
	}

}

