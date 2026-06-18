package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String uri=request.getURI().toString();
        String userId="anonymous";

        if(uri.contains("userid=")){
            userId = uri.substring(uri.indexOf("userid=") + 7);
            if(userId.contains("&")){
                userId=userId.substring(0,userId.indexOf("&"));
            }
        }
        final String finalUserId=userId;
        System.out.println("UserId from the uri is: "+finalUserId);
        return ()->finalUserId;
    }
}
