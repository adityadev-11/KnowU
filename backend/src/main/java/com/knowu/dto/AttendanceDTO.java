package com.knowu.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private LocalDate date;
    private String subject;
    private String status;
    private String remarks;
    private LocalDateTime markedAt;

    public AttendanceDTO() {}

    // Getters
    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public LocalDate getDate() { return date; }
    public String getSubject() { return subject; }
    public String getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getMarkedAt() { return markedAt; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setStudentId(Long v) { this.studentId = v; }
    public void setStudentName(String v) { this.studentName = v; }
    public void setDate(LocalDate v) { this.date = v; }
    public void setSubject(String v) { this.subject = v; }
    public void setStatus(String v) { this.status = v; }
    public void setRemarks(String v) { this.remarks = v; }
    public void setMarkedAt(LocalDateTime v) { this.markedAt = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final AttendanceDTO d = new AttendanceDTO();
        public Builder id(Long v)               { d.id = v; return this; }
        public Builder studentId(Long v)        { d.studentId = v; return this; }
        public Builder studentName(String v)    { d.studentName = v; return this; }
        public Builder date(LocalDate v)        { d.date = v; return this; }
        public Builder subject(String v)        { d.subject = v; return this; }
        public Builder status(String v)         { d.status = v; return this; }
        public Builder remarks(String v)        { d.remarks = v; return this; }
        public Builder markedAt(LocalDateTime v){ d.markedAt = v; return this; }
        public AttendanceDTO build()            { return d; }
    }
}
