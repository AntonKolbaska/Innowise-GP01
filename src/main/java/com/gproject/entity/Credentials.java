package com.gproject.entity;


import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    private String username;
    private String password;
    private Roles role;
}
