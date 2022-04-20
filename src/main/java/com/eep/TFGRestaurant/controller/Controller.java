package com.eep.TFGRestaurant.controller;

import com.eep.TFGRestaurant.entity.user.UserDto;
import com.eep.TFGRestaurant.entity.user.UserEntity;
import com.eep.TFGRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutionException;

@org.springframework.stereotype.Controller
public class Controller {

    private static final String index = "index";
    private static final String inicio = "inicio";
    private static final String addUser = "adduser";
    private static final String deleteUser = "deleteuser";
    private static final String updateUser = "updateuser";

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @GetMapping("/")
    public ModelAndView index(){

        ModelAndView mav = new ModelAndView(index);

        mav.addObject("user", new UserDto());

        return mav;

    }

    @PostMapping("/inicio")
    public String inicio(@ModelAttribute("user") UserDto userDto){

        if (userService.validar(userService.DtoToEntity(userDto))){

            return inicio;

        }else {

            return index;

        }

    }

    @GetMapping("/addUser")
    public ModelAndView addUser(){

        ModelAndView mav = new ModelAndView(addUser);

        mav.addObject("user", new UserDto());

        return mav;

    }

    @PostMapping("/addUser")
    public String anyadirUser(@ModelAttribute("user") UserDto userDto){

        userService.add(userService.DtoToEntity(userDto));

        return inicio;

    }

    @GetMapping("/deleteUser")
    public ModelAndView deleteUser() throws ExecutionException, InterruptedException {

        ModelAndView mav = new ModelAndView(deleteUser);

        mav.addObject("nombre", new UserEntity());
        mav.addObject("lista", userService.listAll());

        return mav;

    }

    @PostMapping("/deleteUser")
    public String borrarUser(@ModelAttribute("nombre") UserEntity id){

        userService.delete(id.getUser());

        return inicio;

    }

    @GetMapping("/updateUser")
    public ModelAndView updateUser() throws ExecutionException, InterruptedException {

        ModelAndView mav = new ModelAndView(updateUser);

        mav.addObject("user", new UserEntity());
        mav.addObject("lista", userService.listAll());

        return mav;

    }

    @PostMapping("/updateUser")
    public String modificarUser(@ModelAttribute("user") UserEntity userEntity){

        userService.update(userEntity);

        return inicio;

    }

}