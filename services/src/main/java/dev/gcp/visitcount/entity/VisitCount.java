package dev.gcp.visitcount.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VISIT_COUNT")
public class VisitCount {
	
	@Id
	private Timestamp timestamp;
	
    private int visits;

    public VisitCount(Timestamp timestamp, int visits) {
		super();
		this.timestamp = timestamp;
		this.visits = visits;
	}

	public VisitCount() {}
    
    public int getVisits() {
		return visits;
	}

	public void setVisits(int visits) {
		this.visits = visits;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "VisitCount [timestamp=" + timestamp + ", visits=" + visits + "]";
	}

	
	
}
