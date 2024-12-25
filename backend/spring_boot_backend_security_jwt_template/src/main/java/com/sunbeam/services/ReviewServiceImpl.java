package com.sunbeam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbeam.custom_exceptions.ResourceNotFoundException;
import com.sunbeam.daos.ReviewDao;
import com.sunbeam.daos.TourPackageDao;
import com.sunbeam.daos.UserDao;
import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.ReviewDTO;
import com.sunbeam.dto.ReviewRespDTO;
import com.sunbeam.entities.Review;
import com.sunbeam.entities.TourPackage;
import com.sunbeam.entities.User;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;
    
    @Autowired
    private TourPackageDao tourPackageDao;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    public ApiResponse saveReview(ReviewDTO reviewDTO) {
    	TourPackage tourPackage =  tourPackageDao.findById(reviewDTO.getTourPackageId())
    	.orElseThrow(() -> new ResourceNotFoundException("Invalid TourPackage id !!!!"));
    	 User customer = userDao.findById(reviewDTO.getCustomerId())
    	.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer id !!!!"));
        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setCustomerId(customer);
        tourPackage.addReview(review);
        review = reviewDao.save(review);
        
        return new ApiResponse("Review created successfully");
    }

    public List<ReviewRespDTO> getAllReviews() {
    	List<ReviewRespDTO> reviews = reviewDao.findAll().stream()
                .map(review -> modelMapper.map(review, ReviewRespDTO.class))
                .collect(Collectors.toList());
        return reviews;
    }

    public ReviewRespDTO getReviewById(Long id) {
        Review review = reviewDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return modelMapper.map(review, ReviewRespDTO.class);
    }

    public ApiResponse deleteReview(Long id) {
        Review review = reviewDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        TourPackage tourPackage = tourPackageDao.findById(review.getTourPackage().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Tour Package not Available!!!!"));
        review.setCustomerId(null);
        tourPackage.removeReview(review);
        return new ApiResponse("Review deleted successfully");
    }

    public ApiResponse updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review = reviewDao.save(review);
        return new ApiResponse("Review updated successfully");
    }
}
