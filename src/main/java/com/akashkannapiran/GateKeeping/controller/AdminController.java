package com.akashkannapiran.GateKeeping.controller;

import com.akashkannapiran.GateKeeping.dto.UserDto;
import com.akashkannapiran.GateKeeping.service.AdminService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    
    @Autowired
    private AdminService adminService;

    @PostMapping("/createUser")
    public ResponseEntity<Long> createUser(@RequestBody @Valid UserDto userDto) {
        Long id = adminService.createUser(userDto);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
