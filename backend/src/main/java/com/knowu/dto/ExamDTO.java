package com.knowu.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExamDTO {
    private Long id;
    private String title;
    private String subject;
    private Long classId;
    private String className;
    private String type;
    private LocalDate examDate;
    private LocalTime startTime;
    private Integer durationMinutes;
    private String room;
    private Integer maxMarks;
    private String status;
    private LocalDateTime createdAt;

    public ExamDTO() {}

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public Long getClassId() { return classId; }
    public String getClassName() { return className; }
    public String getType() { return type; }
    public LocalDate getExamDate() { return examDate; }
    public LocalTime getStartTime() { return startTime; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public String getRoom() { return room; }
    public Integer getMaxMarks() { return maxMarks; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setTitle(String v) { this.title = v; }
    public void setSubject(String v) { this.subject = v; }
    public void setClassId(Long v) { this.classId = v; }
    public void setClassName(String v) { this.className = v; }
    public void setType(String v) { this.type = v; }
    public void setExamDate(LocalDate v) { this.examDate = v; }
    public void setStartTime(LocalTime v) { this.startTime = v; }
    public void setDurationMinutes(Integer v) { this.durationMinutes = v; }
    public void setRoom(String v) { this.room = v; }
    public void setMaxMarks(Integer v) { this.maxMarks = v; }
    public void setStatus(String v) { this.status = v; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final ExamDTO d = new ExamDTO();
        public Builder id(Long v)                   { d.id = v; return this; }
        public Builder title(String v)              { d.title = v; return this; }
        public Builder subject(String v)            { d.subject = v; return this; }
        public Builder classId(Long v)              { d.classId = v; return this; }
        public Builder className(String v)          { d.className = v; return this; }
        public Builder type(String v)               { d.type = v; return this; }
        public Builder examDate(LocalDate v)        { d.examDate = v; return this; }
        public Builder startTime(LocalTime v)       { d.startTime = v; return this; }
        public Builder durationMinutes(Integer v)   { d.durationMinutes = v; return this; }
        public Builder room(String v)               { d.room = v; return this; }
        public Builder maxMarks(Integer v)          { d.maxMarks = v; return this; }
        public Builder status(String v)             { d.status = v; return this; }
        public Builder createdAt(LocalDateTime v)   { d.createdAt = v; return this; }
        public ExamDTO build()                      { return d; }
    }
}
