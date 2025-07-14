package com.jouranlApp.journalApp.Controller;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> geAllUsers() {

        List<UserEntry> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody UserEntry userEntry){
        userService.saveUserAdmin(userEntry);

    }
}
