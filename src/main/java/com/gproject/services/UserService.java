package com.gproject.services;

import com.gproject.dto.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    <Optional> UserDto getUserById(int id);

    <Optional>UserDto getUserByUsername(String username);

    Collection<UserDto> getAllUsers();

    Collection<UserDto> getAllUsersFromCompany(String company);

    Optional<Integer> createUser(final UserDto userDto);

    UserDto updateUser(final UserDto userDto);

    boolean deleteUser(int id);
}
