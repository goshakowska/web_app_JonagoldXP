package com.jonagoldxp.admin.user.controller;

import com.jonagoldxp.admin.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserService service;

    @PostMapping("/users/check_email")
    public String checkDuplicatedEmail(Integer id, String email){
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }

}
