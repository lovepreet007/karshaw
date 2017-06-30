package com.kershaw.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kershaw.springboot.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
	
	@Query("select c from Review c where c.reviewType = :reviewType")
	public List<Review> getReviewType(@Param("reviewType") String reviewType);

}
