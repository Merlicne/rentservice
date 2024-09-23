package com.example.demo.webClient.implement;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;

import com.example.demo.model.BuildingModel;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RoomModel;
import com.example.demo.model.response.ResponseBody;
import com.example.demo.webClient.IDormService;


@Service
public class DormService implements IDormService {
    
    private final  WebClient webClient;

    public DormService(@Qualifier("webClientDormService") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Optional<BuildingModel> getBuilding(int id, JwtToken token) {
        ResponseBody<BuildingModel> response =  webClient.get()
                .uri("/building/{id}", id)
                .header("Authorization", "Bearer " + token.getToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBody<BuildingModel>>() {})
                .block();   
        return Optional.of(response.getData());
    }

    @Override
    public Optional<DormModel> getDormInfo(JwtToken token) {
        ResponseBody<DormModel> response =  webClient.get()
                .uri("/dorm")
                .header("Authorization", "Bearer " + token.getToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseBody<DormModel>>() {})
                .block();

        return Optional.of(response.getData());
    }


}
