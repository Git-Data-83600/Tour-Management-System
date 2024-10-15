package com.sunbeam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.sunbeam.dto.AuthRequest;
import com.sunbeam.dto.SigninResponse;
import com.sunbeam.dto.UserReqDTO;
import com.sunbeam.entities.User;
import com.sunbeam.security.CustomUserDetailsService;
import com.sunbeam.security.JwtUtils;
import com.sunbeam.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
	private JwtUtils jwtUtils;
    
	
	@Autowired
	private AuthenticationManager authMgr;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserReqDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }
    
    @GetMapping
//  @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
    
    @PostMapping("/signin") 
	public ResponseEntity<?> signInUser(
			@RequestBody AuthRequest request) {
    	UsernamePasswordAuthenticationToken token=new 
				UsernamePasswordAuthenticationToken(request.getEmail(), 
						request.getPassword());
		//invoke auth mgr's authenticate method;
		Authentication verifiedToken = authMgr.authenticate(token);
		//=> auth successful !
		System.out.println(verifiedToken.getPrincipal().getClass());//custom user details object
		//create JWT n send it to the clnt in response
		SigninResponse resp=new SigninResponse
				(jwtUtils.generateJwtToken(verifiedToken),
				"Successful Auth!!!!");
		return ResponseEntity. 	
				status(HttpStatus.CREATED).body(resp);
		
	}

}
