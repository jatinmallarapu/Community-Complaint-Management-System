package com.CommunityCompaintManagement.CommunityCompaintManagement.repository;

import java.util.List;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Notification;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    Notification findByuser(User user);

    List<Notification> getAllByuser(User user);}
