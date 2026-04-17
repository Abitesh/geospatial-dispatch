package com.abitesh.geospatial.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.abitesh.geospatial.dto.RideCreateRequest;
import com.abitesh.geospatial.dto.RideResponse;
import com.abitesh.geospatial.services.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin(origins = "*")   // <-- add this line
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public RideResponse createRide(@RequestBody RideCreateRequest request) {
        return requestService.createRequest(request);
    }

    @GetMapping("/{id}")
    public RideResponse getRide(@PathVariable UUID id) {
        return requestService.getRequest(id);
    }
}