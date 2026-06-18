package com.CommunityCompaintManagement.CommunityCompaintManagement.repository;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByadminname(String adminname);
}
