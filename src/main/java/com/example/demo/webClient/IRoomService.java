package com.example.demo.webClient;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.JwtToken;
import com.example.demo.model.RoomModel;

import java.util.Optional;

public interface IRoomService {
    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public Optional<RoomModel> getRoom(int id, JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public Optional<RoomModel> updateRoom(int id, RoomModel room, JwtToken token);
}
