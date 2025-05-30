package com.example.demo2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "emergency_notice")
public class EmergencyNotice {
    @Id
    private String id;
    private String content;
    private List<TypeNotification> typeNotification;
    private List<String> listOfReleaseDates;

    public EmergencyNotice(String id, String content, List<TypeNotification> typeNotification, List<String> listOfReleaseDates) {
        this.id = id;
        this.content = content;
        this.typeNotification = typeNotification;
        this.listOfReleaseDates = listOfReleaseDates;
    }

    public EmergencyNotice() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TypeNotification> getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(List<TypeNotification> typeNotification) {
        this.typeNotification = typeNotification;
    }

    public List<String> getListOfReleaseDates() {
        return listOfReleaseDates;
    }

    public void setListOfReleaseDates(List<String> listOfReleaseDates) {
        this.listOfReleaseDates = listOfReleaseDates;
    }
}
