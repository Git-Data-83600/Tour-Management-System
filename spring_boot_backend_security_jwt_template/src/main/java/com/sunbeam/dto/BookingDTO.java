package com.sunbeam.dto;

import java.time.LocalDate;

import com.sunbeam.entities.BookingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor 
public class BookingDTO {
	private Long id;
    private Long tourPackageId;
    private Long customerId;
    private BookingStatus status;
    private LocalDate bookingDate;
}
