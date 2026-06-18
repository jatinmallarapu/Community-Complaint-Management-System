package com.CommunityCompaintManagement.CommunityCompaintManagement.controller;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Announcment;
import com.CommunityCompaintManagement.CommunityCompaintManagement.repository.AnnouncmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @Autowired
    AnnouncmentRepository anrepo;

    @MessageMapping("/sendannouncement")
    @SendTo("/topic/announcement")
    public Announcment sendAnnouncement(@Payload Announcment announcment){
        anrepo.save(announcment);
        System.out.println(announcment.getPostdate()+" "+announcment.getPosttime());
        return announcment;
    }
}
