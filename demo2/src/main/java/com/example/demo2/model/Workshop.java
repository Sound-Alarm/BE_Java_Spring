package com.example.demo2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "workshops")
public class Workshop {
    @Id
    private String id;
    private String name;
    private String manager;
    private String code;
    private List<ConveyorBelt> conveyorBelts;
    private String description;
}