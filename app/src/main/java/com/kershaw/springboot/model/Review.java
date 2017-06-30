package com.kershaw.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;


@Entity
@Table(name = "REVIEW_TYPE")
@Data
public class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_id")
	private Long reviewId;

	@NotEmpty
	@Column(name = "review_type", nullable = false)
	private String reviewType;
	
	@ManyToMany(mappedBy = "reviewDeal")
	private List<Deal> deals;

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", reviewType=" + reviewType + ", deals=" + deals + "]";
	}

	


}

