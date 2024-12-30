package com.sunbeam.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	private String message;
	private boolean status;
	private LocalDateTime timeStamp;

	public ApiResponse(String message) {
		this.message = message;
		this.timeStamp = LocalDateTime.now();
		
	}
	
	public ApiResponse(boolean status ,String message) {
		this.status = status;
		this.message = message;
		this.timeStamp = LocalDateTime.now();
    }
	
}
