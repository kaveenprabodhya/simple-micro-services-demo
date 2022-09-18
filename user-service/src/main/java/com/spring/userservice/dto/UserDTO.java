package com.spring.userservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private Integer id;
    private String name;
    private Integer balance;
}
