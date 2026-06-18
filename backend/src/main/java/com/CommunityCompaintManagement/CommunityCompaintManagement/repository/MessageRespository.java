package com.CommunityCompaintManagement.CommunityCompaintManagement.repository;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MessageRespository extends JpaRepository<Message,Integer> {

    List<Message> findAllByuserId(int userId);

    List<Message> findAllByadminId(int adminId);
}
