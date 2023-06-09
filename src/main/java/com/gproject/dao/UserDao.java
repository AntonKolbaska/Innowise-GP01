package com.gproject.dao;

import com.gproject.entity.User;
import com.gproject.exception.NonExistentUserException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserDao<T, I> {
    Optional<T> findUser(int id) throws SQLException, NonExistentUserException;
    Optional<T> findUser(String login) throws SQLException, NonExistentUserException;
//    Optional<Credentials> getCredentials(String login);
    Collection<T> getAll();

    Collection<T> getAllFromCompany(String company);
    Optional<I> saveUser(T t);
    User updateUser(T t);
    boolean deleteUser(int id);
}
