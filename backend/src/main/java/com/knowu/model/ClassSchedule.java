package com.knowu.model;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "class_schedule")
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private String subject;
    private String teacherName;
    private String room;

    public ClassSchedule() {}

    // Getters
    public Long getId() { return id; }
    public SchoolClass getSchoolClass() { return schoolClass; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getSubject() { return subject; }
    public String getTeacherName() { return teacherName; }
    public String getRoom() { return room; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setSchoolClass(SchoolClass v) { this.schoolClass = v; }
    public void setDayOfWeek(DayOfWeek v) { this.dayOfWeek = v; }
    public void setStartTime(LocalTime v) { this.startTime = v; }
    public void setEndTime(LocalTime v) { this.endTime = v; }
    public void setSubject(String v) { this.subject = v; }
    public void setTeacherName(String v) { this.teacherName = v; }
    public void setRoom(String v) { this.room = v; }
}
