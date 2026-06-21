package com.chuk.api_ai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;

import com.chuk.api_ai.services.MemoryChatService;

import com.chuk.api_ai.models.MemoryChatPrompt;

@RestController
@RequestMapping("/api/memory")
public class MemoryChatController {

  @Autowired
  private MemoryChatService chatSvc;

  @PostMapping(path="/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> generation(@RequestBody MemoryChatPrompt prompt) {
    var response = this.chatSvc.sendToLLM(prompt);
    return ResponseEntity.ok(
        Json.createObjectBuilder()
          .add("response", response)
          .build().toString()
    );
  }
}


