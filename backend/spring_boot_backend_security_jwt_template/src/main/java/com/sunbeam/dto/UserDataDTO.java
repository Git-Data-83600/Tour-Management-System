package com.sunbeam.dto;

import com.sunbeam.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class UserDataDTO {
		private long id;
	 	private String name;
	    private String email;
	    private Role role;
}
