package com.chuk.api_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class ChatController {

  private final ChatClient chatClient;

  public ChatController(@NotNull ChatClient.Builder chatBuilder) {
    this.chatClient = chatBuilder.build();
  }

  @PostMapping(path = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> generation(@RequestBody ChatPrompt prompt) {

    //System.out.println(">>> Prompt: %s".formatted(prompt));
    System.out.println(prompt);

    var response = this.chatClient.prompt()
        .user(prompt.prompt())
        .call()
        .content();

    return ResponseEntity.ok(
        Json.createObjectBuilder()
          .add("response", response)
          .build().toString());
  }
}
