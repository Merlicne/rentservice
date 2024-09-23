package com.example.demo.webClient.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;

import com.example.demo.model.JwtToken;
import com.example.demo.model.RoomModel;
import com.example.demo.model.response.ResponseBody;
import com.example.demo.webClient.IRoomService;

// import lombok.RequiredArgsConstructor;

@Service
public class RoomService implements IRoomService {
    private final WebClient webClient;

    public RoomService(@Qualifier("webClientRoomService") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public RoomModel getRoom(int id, JwtToken token) {
        ResponseBody<RoomModel> response =  webClient.get()
                .uri("/room/{id}", id)
                .header("Authorization","Bearer "+ token.getToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBody<RoomModel>>() {})
                .block();   
        if (response.getStatus() != 200) {
            throw new RuntimeException("Room get failed : " + response.getMessage());
        }
        return response.getData();
    }

    @Override
    public RoomModel updateRoom(int id, RoomModel room, JwtToken token) {
        ResponseBody<RoomModel> response =  webClient.put()
                .uri("/room/{id}", id)
                .header("Authorization","Bearer "+ token.getToken())
                .bodyValue(room)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBody<RoomModel>>() {})
                .block();

        if(response.getStatus() != 200 && response.getStatus() != 201) {
            throw new RuntimeException("Room update failed : " + response.getMessage());
        }
        return response.getData();
    }
}
