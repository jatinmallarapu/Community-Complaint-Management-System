package com.CommunityCompaintManagement.CommunityCompaintManagement.repository;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByname(String name);
}
