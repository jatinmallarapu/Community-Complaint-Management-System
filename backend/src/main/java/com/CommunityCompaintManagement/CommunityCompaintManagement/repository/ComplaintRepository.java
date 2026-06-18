package com.CommunityCompaintManagement.CommunityCompaintManagement.repository;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    List<Complaint> findAllByUserId(int userId);

    List<Complaint> findByUserId(int userId);
}
