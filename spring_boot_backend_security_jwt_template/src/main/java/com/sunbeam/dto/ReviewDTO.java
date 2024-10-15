package com.sunbeam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
	private Long id;
    private Long tourPackageId;
    private Long customerId;
    private Integer rating;
    private String comment;

}
