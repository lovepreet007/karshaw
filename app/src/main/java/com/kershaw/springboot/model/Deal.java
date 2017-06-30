package com.kershaw.springboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "DEALS")
@Data
public class Deal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "deal_id")
	private Integer dealId;

	@NotEmpty
	@Column(name = "deal_name", nullable = false)
	private String dealName;

	@NotEmpty
	@Column(name = "client_name", nullable = false)
	private String clientName;

	@Column(name = "due_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dealDate;

	@NotEmpty
	@Column(name = "priority_id", nullable = false)
	private String priority;

	@Column(name = "status")
	private String status;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "DEAL_REVIEW_TYPE", joinColumns = @JoinColumn(name = "deal_id", referencedColumnName = "deal_id"),
		inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "review_id"))
	private List<Review> reviewDeal;

	@Override
	public String toString() {
		return "Deal [dealId=" + dealId + ", dealName=" + dealName + ", clientName=" + clientName + ", dealDate="
				+ dealDate + ", priority=" + priority + ", status=" + status + ", reviewDeal=" + reviewDeal + "]";
	}

	
	
	

}
