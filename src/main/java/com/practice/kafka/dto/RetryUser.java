package com.practice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetryUser {

    private String givenName;

    private String mobileNo;

    private String email;

    private String country;
}
