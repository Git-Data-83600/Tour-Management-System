package com.sunbeam.services;

import java.util.List;

import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.AuthRequest;
import com.sunbeam.dto.UserDTO;
import com.sunbeam.dto.UserReqDTO;
import com.sunbeam.entities.User;

public interface UserService {
	ApiResponse saveUser(UserReqDTO user);
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long id);
	ApiResponse deleteUser(Long id);
	ApiResponse updateUser(Long id, User user);
	//ApiResponse addTourPackageToGuide(Long guideId, TourPackageDTO tourPackageDTO);
	//UserDTO authenticateUser(AuthRequest dto);
}
