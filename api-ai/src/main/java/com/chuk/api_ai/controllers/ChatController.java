package com.chuk.api_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;

import com.chuk.api_ai.services.ChatService;

@RestController
@RequestMapping("/api")
public class ChatController {

  @Autowired
  private ChatService chatService;

  @PostMapping(path = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> generation(@RequestBody ChatPrompt prompt) {

    System.out.println(">>> Prompt: %s".formatted(prompt));
    var response = chatService.sendToLLM(prompt.prompt());

    return ResponseEntity.ok(
        Json.createObjectBuilder()
          .add("response", response)
          .build().toString());
  }
}
