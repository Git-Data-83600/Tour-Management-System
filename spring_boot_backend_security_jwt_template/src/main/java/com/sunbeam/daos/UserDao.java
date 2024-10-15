package com.sunbeam.daos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.entities.User;

public interface UserDao extends JpaRepository<User, Long> {
	Optional<User> findByEmailAndPassword(String email,String password);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);

}
