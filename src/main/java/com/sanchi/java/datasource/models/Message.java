package com.sanchi.java.datasource.models;

import java.time.LocalDateTime;

public class Message {

  private String messageId;
  private String text;
  private LocalDateTime dateTime;

  public Message() {

  }

  public Message(String messageId, String text, LocalDateTime dateTime) {
    this.messageId = messageId;
    this.text = text;
    this.dateTime = dateTime;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }
}
