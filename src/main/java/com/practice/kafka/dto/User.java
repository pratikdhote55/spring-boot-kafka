package com.practice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private String givenName;
	
	private String mobileNo;
	
	private String email;
	
	private String country;

}
