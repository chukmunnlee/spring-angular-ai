package com.chuk.api_ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class ChatService {

  @Autowired
  private ChatClient.Builder builder;

  private ChatClient chatClient;

  @PostConstruct
  private void init() {
    chatClient = builder.build();
  }

  public String sendToLLM(String prompt) {
    return chatClient.prompt()
      .user(prompt)
      .call()
      .content();
  }
}
