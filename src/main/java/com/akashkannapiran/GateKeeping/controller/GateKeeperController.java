package com.akashkannapiran.GateKeeping.controller;

import com.akashkannapiran.GateKeeping.dto.VisitDto;
import com.akashkannapiran.GateKeeping.dto.VisitorDto;
import com.akashkannapiran.GateKeeping.service.GateKeeperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gatekeeper")
public class GateKeeperController {

    @Autowired
    private GateKeeperService gateKeeperService;

    @GetMapping("/getVisitor")
    ResponseEntity<VisitorDto> getVisitor(@RequestParam String number) {
        VisitorDto visitorDto = gateKeeperService.getVisitorByIdNumber(number);

        if (visitorDto == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(visitorDto, HttpStatus.OK);
    }

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createVisitor(@RequestBody @Valid VisitorDto visitorDto) {
        Long id = gateKeeperService.createVisitor(visitorDto);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/createVisit")
    ResponseEntity<Long> createVisit(@RequestBody @Valid VisitDto visitDto) {
        Long id = gateKeeperService.createVisit(visitDto);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
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
}
