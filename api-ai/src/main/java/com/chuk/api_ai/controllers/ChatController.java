package com.chuk.api_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.validation.constraints.NotNull;

@RestController
public class ChatController {

  private final ChatClient chatClient;

  public ChatController(@NotNull ChatClient.Builder chatBuilder) {
    this.chatClient = chatBuilder.build();
  }

  @GetMapping(path = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> generation(@RequestParam  String prompt) {

    //System.out.println(">>> Prompt: %s".formatted(prompt));
    System.out.println(prompt);

    var response = this.chatClient.prompt()
        .user(prompt)
        .call()
        .content();

    return ResponseEntity.ok(
        Json.createObjectBuilder()
          .add("response", response)
          .build().toString());
  }
}
