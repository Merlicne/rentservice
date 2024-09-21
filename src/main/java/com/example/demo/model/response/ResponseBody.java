package com.example.demo.model.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody<T> {
    private int status;
    private String message;
    private T data;
}
