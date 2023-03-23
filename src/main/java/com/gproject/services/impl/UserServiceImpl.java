package com.gproject.services.impl;

import com.gproject.dao.impl.UserDaoImpl;
import com.gproject.dto.UserDto;
import com.gproject.entity.User;
import com.gproject.exception.NonExistentUserException;
import com.gproject.mappers.UserMapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Optional;

public class UserServiceImpl {

    private UserDaoImpl userDao;
    private UserMapper mapper;

    private static UserServiceImpl instance;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
        mapper =  Mappers.getMapper(UserMapper.class);
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
//            System.out.println("service instance created=========================");
        }
        return instance;
    }





    public  UserDto getUserById(int id){
        return mapper.userToDto(userDao.get(id).get());
    }

    public UserDto getUserByUsername(String username) throws NonExistentUserException {
//        System.out.println("serive===BEF============" + username);
        User tempuser = userDao.get(username).get();
//        System.out.println("serive===AFT============" + tempuser.getUsername());
        return mapper.userToDto(tempuser);
    }


    public Collection<User> getAllUsers() {
        return userDao.getAll();
    }


    public Collection<UserDto> getAllUsersFromCompany(String company) {
        return mapper.userToDtoCollection(userDao.getAllFromCompany(company));
    }

    public Optional<Integer> createUser(final UserDto userDto) {
        User user = mapper.dtoToUser(userDto);
        return userDao.save(user);
    }


    public UserDto updateUser(final UserDto userDto) {

        int userId = userDto.getId();

        UserDto userDtoToUpdate = mapper.userToDto(userDao.get(userId).get());
        return userDtoToUpdate;
    }

    public void deleteUser(int id) {
        User userToDelete = userDao.get(id).get();
        userDao.delete(userToDelete);
    }
}
