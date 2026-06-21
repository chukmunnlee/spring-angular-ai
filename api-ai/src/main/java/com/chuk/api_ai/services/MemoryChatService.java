package com.chuk.api_ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import com.chuk.api_ai.models.MemoryChatPrompt;

@Service
public class MemoryChatService {

  @Autowired
  private ChatClient.Builder builder;

  @Autowired
  private ChatMemory memory;

  private ChatClient chatClient;

  @PostConstruct
  private void init() {
    chatClient = builder
      // Add chat memory to the chat client
      .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
      .build();
  }

  public String sendToLLM(MemoryChatPrompt prompt) {
    var response = chatClient.prompt()
        .user(prompt.prompt())
        .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, prompt.conversationId()))
        .call()
        .content();
    return response;
  }
}
