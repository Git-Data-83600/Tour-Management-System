package com.sunbeam.daos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.Booking;

public interface BookingDao extends JpaRepository<Booking, Long> {
}
