package com.engine.app.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import com.engine.app.exception.ConflictException;
import com.engine.app.model.Rider;
import com.engine.app.service.RiderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.exception.ConflictException;
import com.engine.app.model.Rider;
import com.engine.app.service.RiderService;

@RestController
@RequestMapping("/riders")
@CrossOrigin("http://localhost:4200")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/register")
    public ResponseEntity<Rider> registerRider(@RequestBody Map<String,String> data) throws Exception {
        Rider rider;
        try {
            rider = riderService.registerRider(data.get("email"), data.get("password"), data.get("address"), data.get("fullname"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(rider);
    }

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
