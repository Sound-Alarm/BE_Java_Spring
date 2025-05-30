package com.example.demo2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "notification")
public class Notification {
    @Id
    private String id;
    private String title;
    private String content;
    private String type;
    private LocalDateTime createAt;
    private boolean isRead;
    private String audioBase64;
    private Integer jobTypeId;
    private List<Integer> indexTeam;
    private List<Team> totalTeam;
    private Custer custer;
    private ConveyorBelt conveyorBelt;
    private String nameJobType;
//    private List<ConveyorBelt> conveyorBeltList;


    public String getNameJobType() {
        return nameJobType;
    }

    public void setNameJobType(String nameJobType) {
        this.nameJobType = nameJobType;
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

    public Integer getJobTypeId() {
        return jobTypeId;
    }

    public List<Team> getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(List<Team> totalTeam) {
        this.totalTeam = totalTeam;
    }

    public void setJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public List<Integer> getIndexTeam() {
        return indexTeam;
    }

    public void setIndexTeam(List<Integer> indexTeam) {
        this.indexTeam = indexTeam;
    }

    // Constructors
    public Notification() {
        this.createAt = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters and Setters
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAudioBase64() {
        return audioBase64;
    }

    public void setAudioBase64(String audioBase64) {
        this.audioBase64 = audioBase64;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification notification = (Notification) o;
        return isRead == notification.isRead &&
                id.equals(notification.id) &&
                title.equals(notification.title) &&
                content.equals(notification.content) &&
                type.equals(notification.type) &&
                createAt.equals(notification.createAt) &&
                audioBase64.equals(notification.audioBase64) &&
                jobTypeId.equals(notification.jobTypeId) &&
                indexTeam.equals(notification.indexTeam) &&
                totalTeam.equals(notification.totalTeam) &&
                custer.equals(notification.custer) &&
                conveyorBelt.equals(notification.conveyorBelt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, type, createAt, isRead, audioBase64, jobTypeId, indexTeam, totalTeam, custer, conveyorBelt);
    }

    @Override
    public String toString() {
        return "ThongBao{" +
                "id='" + id + '\'' +
                ", tieuDe='" + title + '\'' +
                ", noiDung='" + content + '\'' +
                ", type='" + type + '\'' +
                ", thoiGianTao=" + createAt +
                ", daDoc=" + isRead +
                ", audioBase64='" + audioBase64 + '\'' +
                ", jobTypeId=" + jobTypeId +
                ", indexTeam=" + indexTeam +
                ", totalTeam=" + totalTeam +
                ", custer=" + custer +
                ", conveyorBelt=" + conveyorBelt +
                '}';
    }
}