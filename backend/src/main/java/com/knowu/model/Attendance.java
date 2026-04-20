package com.knowu.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDate date;

    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    private String remarks;

    @Column(updatable = false)
    private LocalDateTime markedAt = LocalDateTime.now();

    public enum AttendanceStatus { PRESENT, ABSENT, LATE, LEAVE }

    public Attendance() {}

    // Getters
    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public LocalDate getDate() { return date; }
    public String getSubject() { return subject; }
    public AttendanceStatus getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getMarkedAt() { return markedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setStudent(Student v) { this.student = v; }
    public void setDate(LocalDate v) { this.date = v; }
    public void setSubject(String v) { this.subject = v; }
    public void setStatus(AttendanceStatus v) { this.status = v; }
    public void setRemarks(String v) { this.remarks = v; }
    public void setMarkedAt(LocalDateTime v) { this.markedAt = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Attendance a = new Attendance();
        public Builder student(Student v)           { a.student = v; return this; }
        public Builder date(LocalDate v)            { a.date = v; return this; }
        public Builder subject(String v)            { a.subject = v; return this; }
        public Builder status(AttendanceStatus v)   { a.status = v; return this; }
        public Builder remarks(String v)            { a.remarks = v; return this; }
        public Attendance build()                   { return a; }
    }
}
