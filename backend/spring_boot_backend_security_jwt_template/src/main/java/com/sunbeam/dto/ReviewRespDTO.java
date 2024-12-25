package com.sunbeam.dto;

import java.time.LocalDate;

import com.sunbeam.entities.BookingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
public class ReviewRespDTO {
	private Long id;
    private Integer rating;
    private String comment;
}
