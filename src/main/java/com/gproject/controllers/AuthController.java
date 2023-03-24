package com.gproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gproject.annotations.Controller;
import com.gproject.annotations.HttpMethod;
import com.gproject.annotations.RequestMapping;
import com.gproject.dto.UserDto;
import com.gproject.entity.Credentials;
import com.gproject.services.impl.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class AuthController {
    private static final String AUTH_COOKIE = "auth";


    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    private HttpServletResponse sendError(int errorCode, String errorReason, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(errorCode);
        PrintWriter out = resp.getWriter();
        out.println(errorReason);
        return resp;
    }


    @RequestMapping(url = "/login", method = HttpMethod.POST)
    public HttpServletResponse login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //get credentials from req
        Credentials anonymous = jsonMapper.readValue(req.getReader(), Credentials.class);
        System.out.println(anonymous.toString());
        //check credentials
        if (DigestUtils.sha256Hex(
                userService.getUserByUsername(anonymous.getUsername())
                        .getPassword()).equals(anonymous.getPassword())) {

            //create uniqe token
            //PLACEHOLDER====================================PLACEHOLDER

            String authCookie = DigestUtils.sha256Hex(anonymous.getUsername());
            Cookie authUiCookie = new Cookie(AUTH_COOKIE, authCookie);
            resp.addCookie(authUiCookie);
            authUiCookie.setDomain(req.getContextPath());


            //PLACEHOLDER====================================PLACEHOLDER


            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println();
            return resp;
        } else {
            return sendError(401, "Login and/or password is incorrect", resp);
        }


    }

}
