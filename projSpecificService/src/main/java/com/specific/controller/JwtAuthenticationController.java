package com.specific.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.specific.exception.ConflictException;
import com.specific.exception.InvalidCredentialsException;
import com.specific.model.JwtRequest;
import com.specific.model.JwtResponse;
import com.specific.service.JwtUserDetailsService;
import com.specific.model.Client;
import com.specific.model.Manager;
import com.specific.model.Store;
import com.specific.service.ClientService;
import com.specific.service.ManagerService;
import com.specific.service.StoreService;

@RequestMapping
@RestController
@CrossOrigin(origins =  {"http://localhost:4201", "http://0.0.0.0:6868"})
public class JwtAuthenticationController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private StoreService storeService;

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws InvalidCredentialsException, UsernameNotFoundException {

        return ResponseEntity.ok(userDetailsService.generateTokenLogin(authenticationRequest));

    }

    @PostMapping("/registerClient")
    public ResponseEntity<Client> registerClient(@RequestBody Map<String, String> data) throws ConflictException {
        Client client;
        try {
            client = clientService.saveClient(
                    new Client(data.get("email"), data.get("password"), data.get("fullname"), data.get("address")));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }

        // Map<String,String> payload = new HashMap<>();
        // payload.put("email", data.get("email"));
        // payload.put("password", data.get("password"));


        // RestTemplate template = new RestTemplate();
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // HttpEntity<String> entity = new HttpEntity<String>(payload.toString(),headers);

        // ResponseEntity<String> response = template.exchange("http://localhost:6869/deliveries/delivery", HttpMethod.POST, entity, String.class);
        // System.out.println("RESPOTAAAAAAAAAAAAAAAAA");
        // System.out.println(response);



        return ResponseEntity.ok(client);
    }

    @PostMapping("/registerManager")
    public ResponseEntity<Manager> registerManager(@RequestBody Map<String, String> data) throws ConflictException  {
        Store store;
        try {
            store = storeService.saveStore(new Store(data.get("name"), data.get("address"), true));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        Manager manager;
        try {
            manager = managerService
                    .saveManager(new Manager(data.get("email"), data.get("password"), data.get("fullname"), store));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(manager);
    }

}