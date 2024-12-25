package com.sunbeam.services;

import java.util.List;

import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.BookingDTO;
import com.sunbeam.dto.BookingRespDTO;

public interface BookingService {
	ApiResponse saveBooking(BookingDTO bookingDTO);
	List<BookingRespDTO> getAllBookings();
	BookingRespDTO getBookingById(Long id);
	ApiResponse deleteBooking(Long id);
	ApiResponse updateBooking(Long id, BookingDTO bookingDTO);
}
