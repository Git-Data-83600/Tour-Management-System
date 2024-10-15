package com.sunbeam.daos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.TourPackage;

public interface TourPackageDao extends JpaRepository<TourPackage, Long> {
}
