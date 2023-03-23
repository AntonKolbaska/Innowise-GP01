package com.gproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gproject.dto.UserDto;
import com.gproject.exception.NonExistentEntityException;
import com.gproject.entity.Credentials;
import com.gproject.entity.User;
import com.gproject.services.impl.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

//import static com.gproject.UserApplication.getUserByUsername;

@WebServlet("/login")
public class Login extends HttpServlet {

    private static final String AUTH_COOKIE = "auth";
    private static final String AUTH_COMP_COOKIE = "authcompany";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie userNameCookieRemove = new Cookie(AUTH_COOKIE, "");
        Cookie userCompanyCookieRemove = new Cookie(AUTH_COMP_COOKIE, "");
        userNameCookieRemove.setMaxAge(0);
        userCompanyCookieRemove.setMaxAge(0);
        resp.addCookie(userNameCookieRemove);
        resp.addCookie(userCompanyCookieRemove);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String reqBody = req.getReader().lines().reduce("",String::concat);
        Credentials anonymous = objectMapper.readValue(reqBody, Credentials.class);
        System.out.println(reqBody);
        resp.setContentType("application/json");
        try {
            System.out.println(anonymous.toString());
            System.out.println(UserServiceImpl.getInstance());

            Optional<UserDto> credentials = Optional.of(
                    UserServiceImpl.getInstance().getUserByUsername(anonymous.getUsername()));

            System.out.println("doPost=====================" + credentials.get().getUsername());

//            Optional<User> credentials = Optional.of(getUserByUsername(anonymous.getUsername()));

            if (DigestUtils.sha256Hex(anonymous.getPassword()).equals(credentials.get().getPassword())) {
                //auth cookie
                String authCookie = DigestUtils.sha256Hex(anonymous.getUsername());
                Cookie authUiCookie = new Cookie(AUTH_COOKIE, authCookie);
                resp.addCookie(authUiCookie);
                authUiCookie.setDomain(req.getContextPath());

                String companyCookie = credentials.get().getCompany();
                Cookie compUiCookie = new Cookie(AUTH_COMP_COOKIE, companyCookie);
                resp.addCookie(compUiCookie);
                compUiCookie.setDomain(req.getContextPath());
            } else {
                //error
            }

        } catch (NonExistentEntityException ex) {
//            LOGGER.log(Level.WARNING, ex.getMessage());
        }


    }



}
