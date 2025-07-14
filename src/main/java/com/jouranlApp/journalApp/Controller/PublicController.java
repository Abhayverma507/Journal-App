package com.jouranlApp.journalApp.Controller;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController{
    @Autowired
    private UserService Service;


    @GetMapping("/Health-Check")
    public String healthCheck(){
        return "OK SIR";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserEntry userEntry) {
        Service.createNewUser(userEntry);
        return "USER IS CREATED SUCCESSFUL";
    }
}
