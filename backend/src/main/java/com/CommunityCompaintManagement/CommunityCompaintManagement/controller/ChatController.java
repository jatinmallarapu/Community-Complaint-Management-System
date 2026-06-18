package com.CommunityCompaintManagement.CommunityCompaintManagement.controller;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Message;
import com.CommunityCompaintManagement.CommunityCompaintManagement.repository.MessageRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {
    @Autowired
    MessageRespository msgRepo;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    //User sends message to admin
    @MessageMapping("/sendusermessage")
    public void sendAdminMessage(Message msg){
        msg.setResponse("UserId "+msg.getUserId());
        msgRepo.save(msg);
        messagingTemplate.convertAndSendToUser(String.valueOf(msg.getAdminId()), "/queue/usermessage",msg);
    }

    //Admin send the message to the user
    @MessageMapping("/sendadminmessage")
    public void sendUserMessage(Message msg){

        if(msg.getAdminId()==1){
            msg.setRegion("Central");
            msg.setResponse("Admin Central");
        }
        else if(msg.getAdminId()==2){
            msg.setRegion("East");
            msg.setResponse("Admin East");
        }
        else if(msg.getAdminId()==3){
            msg.setRegion("West");
            msg.setResponse("Admin West");
        }
        msgRepo.save(msg);
        messagingTemplate.convertAndSendToUser(String.valueOf(msg.getUserId()),"/queue/adminmessage",msg);
    }


}
