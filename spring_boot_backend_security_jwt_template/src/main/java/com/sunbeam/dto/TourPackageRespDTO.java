package com.sunbeam.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
public class TourPackageRespDTO {
	
	private Long id;
	private String name;
    private String description;
    private Double price;
    private int duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String image;
}
