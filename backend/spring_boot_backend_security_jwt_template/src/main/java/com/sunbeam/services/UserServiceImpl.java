package com.sunbeam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbeam.custom_exceptions.ResourceNotFoundException;
import com.sunbeam.daos.UserDao;
import com.sunbeam.dto.ApiResponse;
import com.sunbeam.dto.AuthRequest;
import com.sunbeam.dto.BookingRespDTO;
import com.sunbeam.dto.UserDTO;
import com.sunbeam.dto.UserReqDTO;
import com.sunbeam.entities.User;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;



@Service
@Transactional
public class UserServiceImpl implements UserService{
	
    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse saveUser(UserReqDTO user) {
    	if(userDao.existsByEmail(user.getEmail()))
			return new ApiResponse("Email already exists !!!");
    	User u = modelMapper.map(user, User.class);
    	
    	u.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(u);
        return new ApiResponse(true,"User Added Successfully");
    }

    public List<UserDTO>getAllUsers() {
        List<UserDTO> users = userDao.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return  users;
    }

    public UserDTO getUserById(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return modelMapper.map(user, UserDTO.class);
    }

    public ApiResponse deleteUser(Long id) {
    	if(userDao.existsById(id)) {
        userDao.deleteById(id); 
        return new ApiResponse("User deleted successfully");
        }
    	 return new ApiResponse("User deleted Failed");
    }

    public ApiResponse updateUser(Long id, User user) {
    	String mesg = "Category Updation Failed : invalid id !!!!";
    	if(userDao.existsById(id)) {
            userDao.save(user);
            mesg = "Updated user details";
        }
        	 return new ApiResponse(mesg);
    }
    
//    public UserDTO authenticateUser(AuthRequest dto) {
//
//		User user = userDao.findByEmailAndPassword(
//				dto.getEmail(), dto.getPassword())
//				.orElseThrow(() ->
//				new ResourceNotFoundException("Invalid Email or Password !!!!!!"));
//		return modelMapper.map(user, UserDTO.class);
//	}
	
}
