package com.engine.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.model.Rider;
import com.engine.app.service.RiderService;

@RestController
@RequestMapping("/riders")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:19006","http://localhost:6868", "http://localhost:6869", "http://0.0.0.0:6869"})
public class RiderController {

    @Autowired
    private RiderService riderService;

    @GetMapping("/rider")
    public ResponseEntity<Rider> getRiderByEmail(@RequestParam() String email) {
        Rider rider = riderService.getRiderByEmail(email);
        if (rider == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(rider);
    }

    @GetMapping("/allRiders")
    public ResponseEntity<List<Rider>> getAllRiders() {
        return ResponseEntity.ok(riderService.getAllRiders());
    }

    @GetMapping("/activeRiders")
    public ResponseEntity<List<Rider>> getAllActiveRiders() {
        return ResponseEntity.ok(riderService.getAllActiveRiders());
    }

    @GetMapping("/totalRiders")
    public ResponseEntity<Integer> getTotalRiders() {
        return ResponseEntity.ok(riderService.getAllRiders().size());
    }

    @GetMapping("/totalActiveRiders")
    public ResponseEntity<Integer> getTotalActiveRiders() {
        return ResponseEntity.ok(riderService.getAllActiveRiders().size());
    }

    @PutMapping("/changeStatus/{email}")
    public ResponseEntity<String> changeStatus(@Valid @PathVariable String email) throws BadCredentialsException {
        try {
            riderService.changeStatus(email);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Status changed successfully");
    }

}
