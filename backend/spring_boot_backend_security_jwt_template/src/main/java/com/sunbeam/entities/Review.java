package com.sunbeam.entities;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@Getter
@Setter
@ToString
public class Review extends BaseEntity{
    
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "tour_package_id")
	    private TourPackage tourPackage;
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "customer_id")
		private User customerId;
	    private Integer rating;
	    private String comment;
}
