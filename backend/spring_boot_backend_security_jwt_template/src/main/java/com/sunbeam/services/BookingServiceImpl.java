package com.sunbeam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbeam.custom_exceptions.ResourceNotFoundException;
import com.sunbeam.daos.BookingDao;
import com.sunbeam.daos.TourPackageDao;
import com.sunbeam.daos.UserDao;
import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.BookingDTO;
import com.sunbeam.dto.BookingRespDTO;
import com.sunbeam.entities.Booking;
import com.sunbeam.entities.BookingStatus;
import com.sunbeam.entities.TourPackage;
import com.sunbeam.entities.User;

import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
	
    @Autowired
    private BookingDao bookingDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private TourPackageDao tourPackageDao;

    @Autowired
    private ModelMapper modelMapper;


    public ApiResponse saveBooking(BookingDTO bookingDTO) {
    	User customer = userDao.findById(bookingDTO.getCustomerId())
    			.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer id !!!!"));
    	TourPackage tourPkg= tourPackageDao.findById(bookingDTO.getTourPackageId())
    	.orElseThrow(() -> new ResourceNotFoundException("Invalid Package id !!!!"));
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        booking.setTourPackage(tourPkg);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDate.now());
        customer.addBooking(booking);
        booking = bookingDao.save(booking);
        return new ApiResponse("Booking created successfully");
    }

    public List<BookingRespDTO> getAllBookings() {
        List<BookingRespDTO> bookings = bookingDao.findAll().stream()
                .map(booking -> modelMapper.map(booking, BookingRespDTO.class))
                .collect(Collectors.toList());
        return bookings;
    }

    public BookingRespDTO getBookingById(Long id) {
        Booking booking = bookingDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return modelMapper.map(booking, BookingRespDTO.class);
    }

    public ApiResponse deleteBooking(Long id) {
        Booking booking = bookingDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        User user = userDao.findById(booking.getCustomer().getId())
    			.orElseThrow(() -> new ResourceNotFoundException("Invalid Customer  !!!!"));
        booking.setTourPackage(null);
        user.removeBooking(booking);
    
        return new ApiResponse("Booking deleted successfully");
    }

    public ApiResponse updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(bookingDTO.getStatus());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking = bookingDao.save(booking);
        return new ApiResponse("Booking updated successfully");
    }
}
