package com.gproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gproject.annotations.Controller;
import com.gproject.annotations.HttpMethod;
import com.gproject.annotations.RequestMapping;
import com.gproject.dto.UserDto;
import com.gproject.services.impl.UserServiceImpl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Optional;

@Controller
public class UserController {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    private HttpServletResponse sendError(int errorCode, String errorReason, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(errorCode);
        PrintWriter out = resp.getWriter();
        out.println(errorReason);
        return resp;
    }


    @RequestMapping(url = "/workstation", method = HttpMethod.GET)
    public HttpServletResponse getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("id") == null) {
            Collection<UserDto> usersList = userService.getAllUsers();
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(jsonMapper.writeValueAsString(usersList));
            return resp;
        } else {
            Optional<UserDto> userOpt = Optional.of(userService.getUserById(Integer.parseInt(req.getParameter("id"))));
            if (userOpt.isPresent()) {
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print(jsonMapper.writeValueAsString(userOpt));
                return resp;
            } else {
                return sendError(404, "User not found", resp);
            }
        }
    }

    @RequestMapping(url = "/workstation", method = HttpMethod.POST)
    public HttpServletResponse saveUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto creatingUserDto = jsonMapper.readValue(req.getReader(), UserDto.class);
        Optional <Integer> result= userService.createUser(creatingUserDto);
        resp.setContentType("application/json");
        if(result != null){
            resp.setStatus(201);
            return resp;
        }
        else{
            //not sure code 500 is OK here
            return sendError(500, "Something went wrong", resp);
        }
    }

    @RequestMapping(url = "/workstation", method = HttpMethod.PUT)
    public HttpServletResponse updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("id") != null) {

            UserDto updatingUserDto = jsonMapper.readValue(req.getReader(), UserDto.class);

            if (userService.getUserById(Integer.parseInt(req.getParameter("id"))) != null) {
                userService.updateUser(updatingUserDto);
                return resp;
            } else {
                return sendError(404, "No employee found to update", resp);
            }
        } else {
            return sendError(400, "User id not specified", resp);
        }
    }


    @RequestMapping(url = "/workstation", method = HttpMethod.DELETE)
    public HttpServletResponse deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("id") != null) {
            if (userService.deleteUser(Integer.parseInt(req.getParameter("id")))) {
                return resp;
            } else {
                return sendError(404, "User not found", resp);
            }
        } else {
            return sendError(400, "User id not specified", resp);
        }
    }
}
