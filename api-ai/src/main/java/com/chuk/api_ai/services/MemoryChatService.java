package com.chuk.api_ai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.MysqlChatMemoryRepositoryDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import com.chuk.api_ai.models.MemoryChatPrompt;

@Service
public class MemoryChatService {

  @Autowired
  private ChatClient.Builder builder;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  //@Autowired
  //private ChatMemory memory; // In memory repository
  private ChatClient chatClient;

  @PostConstruct
  private void init() {
    ChatMemoryRepository chatMemoryRepo = JdbcChatMemoryRepository.builder()
      .jdbcTemplate(jdbcTemplate)
      .dialect(new MysqlChatMemoryRepositoryDialect())
      .build();

    ChatMemory memory = MessageWindowChatMemory.builder()
        .chatMemoryRepository(chatMemoryRepo)
        .maxMessages(100)
        .build();


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
