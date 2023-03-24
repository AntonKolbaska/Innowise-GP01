package com.gproject.services.impl;

import com.gproject.dao.impl.UserDaoImpl;
import com.gproject.dto.UserDto;
import com.gproject.entity.User;
import com.gproject.exception.NonExistentUserException;
import com.gproject.mappers.UserMapper;
import com.gproject.services.UserService;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;
    private UserMapper mapper;

    private static UserServiceImpl instance;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
        mapper = Mappers.getMapper(UserMapper.class);
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }


    public <Optional> UserDto getUserById(int id) {
        return mapper.userToDto(userDao.get(id).get());
    }


    public <Optional> UserDto getUserByUsername(String username){
        return mapper.userToDto(userDao.get(username).get());
    }


    public Collection<UserDto> getAllUsers() {
        return mapper.userToDtoCollection(userDao.getAll());
    }


    public Collection<UserDto> getAllUsersFromCompany(String company) {
        return mapper.userToDtoCollection(userDao.getAllFromCompany(company));
    }

    public Optional<Integer> createUser(final UserDto userDto) {
        User user = mapper.dtoToUser(userDto);
        return userDao.save(user);
    }


    public UserDto updateUser(final UserDto userDto) {
        return mapper.userToDto(
                userDao.update(mapper.dtoToUser(userDto)));
    }

    public boolean deleteUser(int id) {
        User userToDelete = userDao.get(id).get();
        return userDao.delete(userToDelete);
    }
}
