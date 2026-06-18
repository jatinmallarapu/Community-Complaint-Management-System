package com.CommunityCompaintManagement.CommunityCompaintManagement.controller;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.ChatResponse;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.QueryRequest;
import com.CommunityCompaintManagement.CommunityCompaintManagement.service.ConversationMemoryService;
import com.CommunityCompaintManagement.CommunityCompaintManagement.service.RAGService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/v2/rag")
public class RAGController {

    private final ChatClient chatClient;
    private final RAGService ragService;
    private final ConversationMemoryService memoryService;

    @Autowired
    public RAGController(ChatClient.Builder chatClientBuilder,
                         RAGService ragService,
                         ConversationMemoryService memoryService) {
        this.chatClient = chatClientBuilder.build();
        this.ragService = ragService;
        this.memoryService = memoryService;
    }

    @PostMapping("/chat/{username}")
    public ChatResponse chat(@RequestBody QueryRequest request, @PathVariable String username) {
        String contextId = request.getConversationId();
        if (contextId == null || contextId.isBlank()) {
            contextId = UUID.randomUUID().toString();
        }

        String query = request.getQuery();
        memoryService.addMessage(contextId, username + ": " + query);

        // RAG retrieval
        String relevantDocs = ragService.retrieveRelevantText(query);

        if (relevantDocs == null || relevantDocs.isBlank()) {
            ChatResponse chatResponse = new ChatResponse();
            chatResponse.setResponseId(contextId);
            chatResponse.setResponse(username+", I don't know");
            return chatResponse;
        }

        // Build full conversation context
        String historyContext = memoryService.buildContext(contextId);

        String systemPrompt = """
You are a strict factual assistant. Answer ONLY using the following context and knowledge base.
Do NOT use your prior knowledge or make assumptions.
You can also use the conversation history to refer to the user's name if previously mentioned.
Your first action is to greet the user and say: 'Hello %s, how can I assist you today?'
After that, respond only when the user sends a message.
If the answer is not in the knowledge base, respond only with "I don't know".
The username of the person you are talking to is: %s
Conversation history:
%s

KNOWLEDGE BASE:
--- 
%s
""".formatted(username, username, historyContext, relevantDocs);


        Message systemMessage = new SystemMessage(systemPrompt);
        Message userMessage = new UserMessage(query);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String aiResponse = chatClient.prompt(prompt).call().content();

        memoryService.addMessage(contextId, "AI: " + aiResponse);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setResponseId(contextId);
        chatResponse.setResponse(aiResponse);

        return chatResponse;
    }
}
