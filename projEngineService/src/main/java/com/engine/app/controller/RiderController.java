package com.engine.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.model.Rider;
import com.engine.app.service.RiderService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/login")
    public ResponseEntity<String> loginRider(@RequestBody Map<String, String> data) throws Exception {
        try {
            riderService.loginRider(data.get("email"), data.get("password"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerRider(@RequestBody Map<String,String> data) throws Exception {
        try {
            riderService.registerRider(data.get("email"), data.get("password"), data.get("address"), data.get("fullname"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rider/{email}")
    public ResponseEntity<Rider> getRiderByEmail(@Valid @PathVariable String email) {
        try {
            if (riderService.getRiderByEmail(email) == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(riderService.getRiderByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all-riders")
    public ResponseEntity<List<Rider>> getAllRiders() {
        try {
            return ResponseEntity.ok(riderService.getAllRiders());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all-active-riders")
    public ResponseEntity<List<Rider>> getAllActiveRiders() {
        try {
            return ResponseEntity.ok(riderService.getAllActiveRiders());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/total-riders")
    public ResponseEntity<Integer> getTotalRiders() {
        try {
            return ResponseEntity.ok(riderService.getAllRiders().size());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/total-active-riders")
    public ResponseEntity<Integer> getTotalActiveRiders() {
        try {
            return ResponseEntity.ok(riderService.getAllActiveRiders().size());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/change-status/{email}")
    public ResponseEntity<String> changeStatus(@Valid @PathVariable String email) {
        try {
            riderService.changeStatus(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Status changed successfully");
    }

}
