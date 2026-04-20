package com.knowu.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @Enumerated(EnumType.STRING)
    private ExamType type;

    private LocalDate examDate;
    private LocalTime startTime;
    private Integer durationMinutes;
    private String room;
    private Integer maxMarks;

    @Enumerated(EnumType.STRING)
    private ExamStatus status = ExamStatus.SCHEDULED;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum ExamType { UNIT_TEST, MID_TERM, FINAL, PRACTICAL, ASSIGNMENT }
    public enum ExamStatus { SCHEDULED, ONGOING, COMPLETED, CANCELLED }

    public Exam() {}

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public SchoolClass getSchoolClass() { return schoolClass; }
    public ExamType getType() { return type; }
    public LocalDate getExamDate() { return examDate; }
    public LocalTime getStartTime() { return startTime; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public String getRoom() { return room; }
    public Integer getMaxMarks() { return maxMarks; }
    public ExamStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setTitle(String v) { this.title = v; }
    public void setSubject(String v) { this.subject = v; }
    public void setSchoolClass(SchoolClass v) { this.schoolClass = v; }
    public void setType(ExamType v) { this.type = v; }
    public void setExamDate(LocalDate v) { this.examDate = v; }
    public void setStartTime(LocalTime v) { this.startTime = v; }
    public void setDurationMinutes(Integer v) { this.durationMinutes = v; }
    public void setRoom(String v) { this.room = v; }
    public void setMaxMarks(Integer v) { this.maxMarks = v; }
    public void setStatus(ExamStatus v) { this.status = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Exam e = new Exam();
        public Builder title(String v)            { e.title = v; return this; }
        public Builder subject(String v)          { e.subject = v; return this; }
        public Builder schoolClass(SchoolClass v) { e.schoolClass = v; return this; }
        public Builder type(ExamType v)           { e.type = v; return this; }
        public Builder examDate(LocalDate v)      { e.examDate = v; return this; }
        public Builder startTime(LocalTime v)     { e.startTime = v; return this; }
        public Builder durationMinutes(Integer v) { e.durationMinutes = v; return this; }
        public Builder room(String v)             { e.room = v; return this; }
        public Builder maxMarks(Integer v)        { e.maxMarks = v; return this; }
        public Builder status(ExamStatus v)       { e.status = v; return this; }
        public Exam build()                       { return e; }
    }
}
