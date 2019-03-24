package com.sanchi.java.datasource.controllers;

import com.sanchi.java.datasource.models.Message;
import com.sanchi.java.datasource.services.MysqlDataService;
import com.sanchi.java.datasource.services.PostgresDataService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  @Autowired
  private PostgresDataService postgresDataService;

  @Autowired
  private MysqlDataService mysqlDataService;

  @PostMapping(value = "/all/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity postMessage(@RequestBody Message message) {
    // generate a unique message identifier
    String messageId = UUID.randomUUID().toString();
    message.setMessageId(messageId);
    message.setDateTime(LocalDateTime.now());

    // save message in all data sources
    postgresDataService.saveMessage(message);
    mysqlDataService.saveMessage(message);
    return ResponseEntity.status(HttpStatus.CREATED).body(message);
  }

  @GetMapping(value = "/postgres/messages/{message-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> getPostgresMessage(@PathVariable("message-id") String messageId) {
    Message message = postgresDataService.getMessage(messageId);
    if (message == null) {
      throw new MessageNotFoundException("Message not found in postgres database");
    }
    return ResponseEntity.ok(message);
  }

  @GetMapping(value = "/mysql/messages/{message-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> getMysqlMessage(@PathVariable("message-id") String messageId) {
    Message message = mysqlDataService.getMessage(messageId);
    if (message == null) {
      throw new MessageNotFoundException("Message not found in mysql database");
    }
    return ResponseEntity.ok(message);
  }

  class MessageNotFoundException extends RuntimeException {

    MessageNotFoundException(String errorMessage) {
      super(errorMessage);
    }
  }

}
