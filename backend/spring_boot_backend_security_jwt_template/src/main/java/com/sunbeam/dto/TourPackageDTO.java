package com.sunbeam.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
public class TourPackageDTO {
	
    private Long id;
    private String name;
    private String description;
    private Long guidId;
}
