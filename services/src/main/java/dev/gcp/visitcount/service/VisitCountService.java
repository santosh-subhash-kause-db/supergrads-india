package dev.gcp.visitcount.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gcp.visitcount.entity.VisitCount;
import dev.gcp.visitcount.repository.VisitCountRepository;

@Service
public class VisitCountService {

	@Autowired
	private VisitCountRepository repository;
	
	public int getVisitCount() {
		List<VisitCount> v = repository.findAll();
		
		if(v.isEmpty())
		{
			return 1;   	// No old visit log in database. I am the first visitor :)
		}
		
		int visits = v.get(0).getVisits();
			
		return visits;
	}
	
	public String incrementVisit() {
		List<VisitCount> v = repository.findAll();
		
		if(v.isEmpty())
		{
			repository.save(new VisitCount(new Timestamp(System.currentTimeMillis()),1));
			return "This was first visit : Visitors = 1";
		}
		
		int visits = v.get(0).getVisits();
		
		repository.delete(v.get(0));
		repository.save(new VisitCount(new Timestamp(System.currentTimeMillis()),++visits));
		
		return "Visits = "+visits;
	}
	
	
}
