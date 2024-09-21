package com.example.demo.webClient;

import com.example.demo.model.BuildingModel;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;

public interface IDormService {
    public BuildingModel getBuilding(int id, JwtToken token);
    public DormModel getDormInfo(JwtToken token);

}
