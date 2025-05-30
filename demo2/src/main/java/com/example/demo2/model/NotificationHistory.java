package com.example.demo2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Document(collection = "notification_history")
public class NotificationHistory {
    @Id
    private String id;
    private String title;
    private String content;
    private String type;
    private LocalDateTime createAt;
    private LocalDateTime closingTime;

    private Integer jobTypeId;
    private Integer indexTeam;
    private Custer custer;
    private ConveyorBelt conveyorBelt;
    private String nameJobType;
    public NotificationHistory() {
    }

    public NotificationHistory(String id, String title, String content, String type, LocalDateTime createAt, LocalDateTime closingTime, Integer jobTypeId, Integer indexTeam, Custer custer, ConveyorBelt conveyorBelt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.createAt = createAt;
        this.closingTime = closingTime;
        this.jobTypeId = jobTypeId;
        this.indexTeam = indexTeam;
        this.custer = custer;
        this.conveyorBelt = conveyorBelt;
    }

    public NotificationHistory(String id, String title, String content, String type, LocalDateTime createAt, LocalDateTime closingTime, Integer jobTypeId, Integer indexTeam, Custer custer, ConveyorBelt conveyorBelt, String nameJobType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.createAt = createAt;
        this.closingTime = closingTime;
        this.jobTypeId = jobTypeId;
        this.indexTeam = indexTeam;
        this.custer = custer;
        this.conveyorBelt = conveyorBelt;
        this.nameJobType = nameJobType;
    }

    public String getNameJobType() {
        return nameJobType;
    }

    public void setNameJobType(String nameJobType) {
        this.nameJobType = nameJobType;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Integer getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public Integer getIndexTeam() {
        return indexTeam;
    }

    public void setIndexTeam(Integer indexTeam) {
        this.indexTeam = indexTeam;
    }

    public Custer getCuster() {
        return custer;
    }

    public void setCuster(Custer custer) {
        this.custer = custer;
    }

    public ConveyorBelt getConveyorBelt() {
        return conveyorBelt;
    }

    public void setConveyorBelt(ConveyorBelt conveyorBelt) {
        this.conveyorBelt = conveyorBelt;
    }
}
