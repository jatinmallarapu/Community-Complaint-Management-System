package com.CommunityCompaintManagement.CommunityCompaintManagement.repository;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
