package com.akashkannapiran.GateKeeping.controller;

import com.akashkannapiran.GateKeeping.dto.AddressDto;
import com.akashkannapiran.GateKeeping.dto.UserDto;
import com.akashkannapiran.GateKeeping.ennums.Role;
import com.akashkannapiran.GateKeeping.service.AdminService;
import jakarta.validation.Valid;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/bulk-user-creation")
    public ResponseEntity<List<String>> createBulkUser(@RequestParam MultipartFile file) {
        LOGGER.info("File:" + file.getOriginalFilename());

        List<String> response = new ArrayList<>();
        String currentIdNumber = "";

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                try {
                    AddressDto addressDto = AddressDto.builder()
                            .line1(csvRecord.get("line1"))
                            .line2(csvRecord.get("line2"))
                            .city(csvRecord.get("city"))
                            .pincode(csvRecord.get("pincode"))
                            .build();

                    UserDto userDto = UserDto.builder()
                            .name(csvRecord.get("name"))
                            .email(csvRecord.get("email"))
                            .phone(csvRecord.get("phone"))
                            .flatNo(csvRecord.get("flatNo"))
                            .role(Role.valueOf(csvRecord.get("role")))
                            .idNumber(csvRecord.get("idNumber"))
                            .address(addressDto)
                            .build();

                    currentIdNumber = userDto.getIdNumber();

                    Long userId = adminService.createUser(userDto);

                    response.add("Created User: " + userDto.getName() + " with ID: " + userId);
                } catch (RuntimeException e) {
                    response.add("Got Exception while creating Id: " + currentIdNumber);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
