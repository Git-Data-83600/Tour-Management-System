package com.sunbeam.daos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.TourPackage;

public interface TourPackageDao extends JpaRepository<TourPackage, Long> {
	List<TourPackage> findByGuideId(Long guideId);
}
