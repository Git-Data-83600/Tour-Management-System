package com.sunbeam.entities;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour_packages")
@Getter
@Setter
@ToString
public class TourPackage extends BaseEntity{

		private String name;
	    private String description;
	    private Double price;
	    private int duration;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    
	    private String image;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "guide_id")
	    private User guide;

	    @OneToMany(mappedBy = "tourPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    private List<Review> reviews = new ArrayList<>();

	    // Getters and setters

	    public void addReview(Review review) {
	        reviews.add(review);
	        review.setTourPackage(this);
	    }

	    public void removeReview(Review review) {
	        reviews.remove(review);
	        review.setTourPackage(null);
	    }
}
