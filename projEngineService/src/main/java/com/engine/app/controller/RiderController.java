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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/email/{email}/address/{address}/fullname/{fullname}/password/{password}")
    public ResponseEntity<String> getCovidDataByCountry(@Valid @PathVariable String email,
            @Valid @PathVariable String address, @Valid @PathVariable String fullname,
            @Valid @PathVariable String password) {
        try {
            riderService.registerRider(email, password, address, fullname);
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Rider registered successfully");
    }

    @GetMapping("/rider/{email}")
    public ResponseEntity<Person> getRiderByEmail(@Valid @PathVariable String email) {
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
    public ResponseEntity<List<Person>> getAllRiders() {
        try {
            return ResponseEntity.ok(riderService.getAllRiders());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // @GetMapping("/all-active-riders")
    // public ResponseEntity<List<Person>> getAllActiveRiders() {
    //     try {
    //         return ResponseEntity.ok(riderService.getAllActiveRiders());
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }

    @GetMapping("/total-riders")
    public ResponseEntity<Integer> getTotalRiders() {
        try {
            return ResponseEntity.ok(riderService.getAllRiders().size());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // @GetMapping("/total-active-riders")
    // public ResponseEntity<Integer> getTotalActiveRiders() {
    //     try {
    //         return ResponseEntity.ok(riderService.getAllActiveRiders().size());
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }
}
 