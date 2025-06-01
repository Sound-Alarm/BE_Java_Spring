package com.example.demo2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "emergency_notice")
public class EmergencyNotice {
    @Id
    private String id;
    private String content;
    private TypeNotification typeNotification;
    private String timeAt;
    private List<String> listOfReleaseDates;
    private Date createAt; // Không khởi tạo ở đây


    public EmergencyNotice(String id, String content, TypeNotification typeNotification, List<String> listOfReleaseDates) {
        this.id = id;
        this.content = content;
        this.typeNotification = typeNotification;
        this.listOfReleaseDates = listOfReleaseDates;
    }

    public EmergencyNotice() {

    }

    public String getTimeAt() {
        return timeAt;
    }

    public void setTimeAt(String timeAt) {
        this.timeAt = timeAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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

    public TypeNotification getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(TypeNotification typeNotification) {
        this.typeNotification = typeNotification;
    }

    public List<String> getListOfReleaseDates() {
        return listOfReleaseDates;
    }

    public void setListOfReleaseDates(List<String> listOfReleaseDates) {
        this.listOfReleaseDates = listOfReleaseDates;
    }

    @Override
    public String toString() {
        return "EmergencyNotice{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", typeNotification=" + typeNotification +
                ", listOfReleaseDates=" + listOfReleaseDates +
                '}';
    }
}
