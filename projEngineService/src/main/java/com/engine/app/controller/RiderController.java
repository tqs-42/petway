package com.engine.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.engine.app.repository.RiderRepository;
import com.engine.app.model.Rider;
import com.engine.app.exception.ConflictException;
import com.engine.app.model.Person;
import com.engine.app.service.RiderService;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/addRider")
    public ResponseEntity<String> getCovidDataByCountry(@RequestBody Map<String, String> register) {

        String email = register.get("email");
        String fullname = register.get("fullname");
        String address = register.get("address");
        String password = register.get("password");

        System.out.println("REGISTER  --- " + register);
        System.out.println("EMAIL --- " + email);
        System.out.println("EMAIL 2--- " + fullname);
        System.out.println("EMAIL - 3-- " + address);
        System.out.println("EMAIL --- 4  - " + password);

        try {
            riderService.registerRider(email, password, address, fullname);
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Rider registered successfully");
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
