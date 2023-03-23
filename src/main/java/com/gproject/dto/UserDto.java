package com.gproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String company;
}
