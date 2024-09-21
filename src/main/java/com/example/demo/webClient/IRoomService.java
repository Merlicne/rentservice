package com.example.demo.webClient;

import com.example.demo.model.JwtToken;
import com.example.demo.model.RoomModel;

public interface IRoomService {
    public RoomModel getRoom(int id, JwtToken token);
}
