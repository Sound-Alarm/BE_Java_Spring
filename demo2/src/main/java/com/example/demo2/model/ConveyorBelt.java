package com.example.demo2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "conveyor_belts")
public class ConveyorBelt {
    @Id
    private String id;
    private String name;
    private int index;
    private List<Custer> clusters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Custer> getClusters() {
        return clusters;
    }

    public void setClusters(List<Custer> clusters) {
        this.clusters = clusters;
    }
}