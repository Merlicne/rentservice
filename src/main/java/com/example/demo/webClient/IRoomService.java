package com.example.demo.webClient;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.JwtToken;
import com.example.demo.model.RoomModel;

public interface IRoomService {
    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public RoomModel getRoom(int id, JwtToken token);
}
