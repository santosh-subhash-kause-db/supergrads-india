package dev.gcp.visitcount.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import dev.gcp.visitcount.service.VisitCountService;

@CrossOrigin(origins = "*")
@RestController
public class VisitCountController {
	
	@Autowired
	private VisitCountService service;

    @CrossOrigin(origins = "*")
    @GetMapping("/count")
    public int getTotalVisitCount() {
    	return service.getVisitCount();
    }

    @PostMapping("/increment")
    public String incrementVisits() {
    	return service.incrementVisit();
    	
    }
    
}