package com.akashkannapiran.GateKeeping.controller;

import com.akashkannapiran.GateKeeping.dto.AllPendingVisitsDto;
import com.akashkannapiran.GateKeeping.dto.VisitDto;
import com.akashkannapiran.GateKeeping.ennums.VisitStatus;
import com.akashkannapiran.GateKeeping.entity.User;
import com.akashkannapiran.GateKeeping.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController {
    @Autowired
    private ResidentService residentService;

    @PutMapping("/actOnVisit/{id}")
    public ResponseEntity<String> actOnVisit(@PathVariable Long id, @RequestParam VisitStatus visitStatus) {
        String response = residentService.updateVisit(id, visitStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pendingVisits")
    public ResponseEntity<List<VisitDto>> getPendingVisits(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(residentService.getPendingVisits(user.getId()));
    }

    @GetMapping("/page-pendingVisits")
    public ResponseEntity<AllPendingVisitsDto> getPagePendingVisits(@AuthenticationPrincipal User user, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(residentService.getPendingVisitByPage(user.getId(), pageNo, pageSize));
    }
}
