package com.sunbeam.services;

import java.util.List;

import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.ReviewDTO;
import com.sunbeam.dto.ReviewRespDTO;

public interface ReviewService {
	ApiResponse saveReview(ReviewDTO reviewDTO);
	List<ReviewRespDTO> getAllReviews();
	ReviewRespDTO getReviewById(Long id);
	ApiResponse deleteReview(Long id);
	ApiResponse updateReview(Long id, ReviewDTO reviewDTO);
}
