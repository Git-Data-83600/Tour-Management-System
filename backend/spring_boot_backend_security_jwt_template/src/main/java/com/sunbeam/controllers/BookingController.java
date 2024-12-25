package com.sunbeam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sunbeam.dto.BookingDTO;
import com.sunbeam.services.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.saveBooking(bookingDTO));
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{bookinId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookinId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookinId));
    }

    @PutMapping("/{bookinId}")
    public ResponseEntity<?> updateBooking(@PathVariable Long bookinId, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(bookinId, bookingDTO));
    }

    @DeleteMapping("/{bookinId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookinId) {
        return ResponseEntity.ok(bookingService.deleteBooking(bookinId));
    }
}
