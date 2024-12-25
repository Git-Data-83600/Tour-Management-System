package com.sunbeam.daos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {
}
