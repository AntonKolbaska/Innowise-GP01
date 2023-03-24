package com.gproject.dao;

import com.gproject.entity.Credentials;
import com.gproject.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao<T, I> {
    Optional<T> get(int id);
    Optional<T> get(String login);
    Optional<Credentials> getCredentials(String login);
    Collection<T> getAll();

    Collection<T> getAllFromCompany(String company);
    Optional<I> save(T t);
    User update(T t);
    boolean delete(T t);
}
