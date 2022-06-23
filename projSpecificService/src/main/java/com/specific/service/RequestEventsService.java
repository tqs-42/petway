package com.specific.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Cart;
import com.specific.model.Client;
import com.specific.model.Product;
import com.specific.model.Request;
import com.specific.model.RequestEvents;
import com.specific.model.RequestStatus;
import com.specific.repository.ClientRepository;
import com.specific.repository.RequestEventsRepository;
import com.specific.repository.RequestRepository;

@Service
public class RequestEventsService {

    @Autowired
    private RequestEventsRepository requestEventsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RequestRepository requestRepository;

    public Set<RequestEvents> getAllRequestEvents(String email) {
        Set<RequestEvents> requestEvents = requestEventsRepository.findRequestEventsByEmail(email);
        return requestEvents;
    }

    public RequestEvents getRequestEvents(long id) {
        RequestEvents requestEvents = requestEventsRepository.findRequestEventsByRequestId(id);
        return requestEvents;
    }

    public List<Map<String, Object>> getProductsInfoByOrderId(long orderId) {
        return requestEventsRepository.getProductByOrderId(orderId);
    }

    public RequestEvents addRequestEvents(String userEmail)
            throws ResourceNotFoundException, ConflictException {
        Client client = clientRepository.findByEmail(userEmail);
        System.out.println(client);
        if (client == null) {
            throw new ResourceNotFoundException("Client " + userEmail + " not found!");
        } else {
            Cart cart = client.getCart();
            System.out.println(cart);
            if (cart == null) {
                throw new ResourceNotFoundException("Cart not found!");
            } else {



                Request request = requestRepository.findByCart(cart);
                if (request == null) {
                    throw new ResourceNotFoundException("Request not found!");
                } else {
                    RequestEvents requestEvents = new RequestEvents(request);
                    requestEventsRepository.save(requestEvents);
                    return requestEvents;

                }
            }
        }
    }

    public RequestEvents addRequestEventsWithStatus(String id, String status)
            throws ResourceNotFoundException, ConflictException {

        System.out.println("SERVICE AddrequestsEvents!");
        System.out.println(id);
        System.out.println(status);
        System.out.println(RequestStatus.getEnum(status));

        Optional<Request> request = requestRepository.findById(Long.parseLong(id));
        if (request.isEmpty()) {
            throw new ResourceNotFoundException("Request not found!");
        } else {
            RequestEvents requestEvents = new RequestEvents(request.get(), RequestStatus.valueOf(status));
            requestEventsRepository.save(requestEvents);
            return requestEvents;
        }
    }

}
