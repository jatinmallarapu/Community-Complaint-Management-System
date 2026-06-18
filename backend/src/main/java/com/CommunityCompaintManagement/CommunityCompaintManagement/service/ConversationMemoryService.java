package com.CommunityCompaintManagement.CommunityCompaintManagement.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConversationMemoryService {
    private final Map<String, List<String>> memoryStore = new HashMap<>();

    public List<String> getHistory(String contextId) {
        return memoryStore.computeIfAbsent(contextId, k -> new ArrayList<>());
    }

    public void addMessage(String contextId, String message) {
        getHistory(contextId).add(message);
    }

    public String buildContext(String contextId) {
        List<String> history = getHistory(contextId);
        if (history.isEmpty()) return "";
        StringBuilder sb = new StringBuilder("Previous conversation:\n");
        for (String msg : history) {
            sb.append(msg).append("\n");
        }
        return sb.toString();
    }
}
