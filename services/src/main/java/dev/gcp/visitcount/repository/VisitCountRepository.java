package dev.gcp.visitcount.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.gcp.visitcount.entity.VisitCount;

public interface VisitCountRepository extends JpaRepository<VisitCount,Timestamp>{

}
