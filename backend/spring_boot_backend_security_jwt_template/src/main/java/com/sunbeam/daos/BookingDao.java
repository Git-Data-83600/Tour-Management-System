package com.sunbeam.daos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.Booking;
import com.sunbeam.entities.TourPackage;

public interface BookingDao extends JpaRepository<Booking, Long> {
	List<Booking> findByTourPackage(TourPackage tourPkg);
}
