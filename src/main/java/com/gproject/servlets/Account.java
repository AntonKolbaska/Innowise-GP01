package com.gproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gproject.dao.UserDao;
import com.gproject.dao.impl.UserDaoImpl;
import com.gproject.exception.NonExistentEntityException;
import com.gproject.exception.NonExistentUserException;
import com.gproject.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


@WebServlet("/hidden")
public class Account extends HttpServlet {
    private static final UserDao<User, Integer> USER_DAO = UserDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<String> auth = readCookie("auth", req);
        if (auth.isPresent()) {
            Optional<String> authCompany = readCookie("authcompany", req);

            Collection<User> users = USER_DAO.getAllFromCompany(authCompany.get());
            String json = objectMapper.writeValueAsString(users);
            resp.setContentType("application/json");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(json);
            printWriter.close();

        } else {
            //autorize pls you fucking shit
            //dd redirwct to login page
        }
    }

    public Optional<String> readCookie(String key, HttpServletRequest req) {
        return Arrays.stream(req.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

//    public static User getUserById(int id) throws NonExistentEntityException {
////        Optional<User> User = USER_DAO.findUser(id);
////        return User.orElseThrow(NonExistentUserException::new);
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String reqBody = req.getReader().lines().reduce("", String::concat);
        User editedUser = objectMapper.readValue(reqBody, User.class);
        resp.setContentType("application/json");
        USER_DAO.updateUser(editedUser);
    }
}
