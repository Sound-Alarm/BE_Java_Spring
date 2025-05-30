package com.example.demo2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "team")
public class Team {
    private String id;
    private String name;
    private int index;
}