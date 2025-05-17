package com.akashkannapiran.GateKeeping.controller;

import com.akashkannapiran.GateKeeping.dto.VisitDto;
import com.akashkannapiran.GateKeeping.dto.VisitorDto;
import com.akashkannapiran.GateKeeping.service.GateKeeperService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/gatekeeper")
public class GateKeeperController {

    private Logger LOGGER = LoggerFactory.getLogger(GateKeeperController.class);

    @Autowired
    private GateKeeperService gateKeeperService;

    @Value("${static.domain.name}")
    private String staticDomainName;

    @Value("${image.upload.home}")
    private String imageUploadHome;

    @GetMapping("/getVisitor")
    ResponseEntity<VisitorDto> getVisitor(@RequestParam String number) {
        VisitorDto visitorDto = gateKeeperService.getVisitor(number);

        if (visitorDto == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(visitorDto, HttpStatus.OK);
    }

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createVisitor(@RequestBody @Valid VisitorDto visitorDto) {
        Long id = gateKeeperService.createVisitor(visitorDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/createVisit")
    ResponseEntity<Long> createVisit(@RequestBody @Valid VisitDto visitDto) {
        Long id = gateKeeperService.createVisit(visitDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
//                .path("/{id}")
//                .buildAndExpand(id)
//                .toUri();
//        return ResponseEntity.created(location).body(id);
    }

    @PutMapping("/markEntry/{id}")
    ResponseEntity<String> markEntry(@PathVariable Long id) {
        return ResponseEntity.ok(gateKeeperService.markEntry(id));
    }

    @PutMapping("/markExit/{id}")
    ResponseEntity<String> markExit(@PathVariable Long id) {
        return ResponseEntity.ok(gateKeeperService.markExit(id));
    }

    @PostMapping("/image-upload")
    public ResponseEntity<String> imageUpload(@RequestParam MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadPath = imageUploadHome + fileName;
        String publicUrl = staticDomainName + "content/" + fileName;

        try {
            file.transferTo(new File(uploadPath));
            return ResponseEntity.ok(publicUrl);
        } catch (IOException e) {
            LOGGER.error("Exception while uploading image: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception while uploading image");
        }
    }
}
