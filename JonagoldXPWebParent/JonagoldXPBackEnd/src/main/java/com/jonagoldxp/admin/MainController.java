package com.jonagoldxp.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")  // handles the HTTP GET request to the homepage of this application
    public String viewHomePage(){
        return "index";  // logical view name
    }
}
