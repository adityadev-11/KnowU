package com.knowu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_results")
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    private Integer marksObtained;
    private Integer maxMarks;
    private String grade;

    @Enumerated(EnumType.STRING)
    private ResultStatus status = ResultStatus.DRAFT;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum ResultStatus { DRAFT, PUBLISHED }

    public ExamResult() {}

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Exam getExam() { return exam; }
    public Integer getMarksObtained() { return marksObtained; }
    public Integer getMaxMarks() { return maxMarks; }
    public String getGrade() { return grade; }
    public ResultStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStudent(Student v) { this.student = v; }
    public void setExam(Exam v) { this.exam = v; }
    public void setMarksObtained(Integer v) { this.marksObtained = v; }
    public void setMaxMarks(Integer v) { this.maxMarks = v; }
    public void setGrade(String v) { this.grade = v; }
    public void setStatus(ResultStatus v) { this.status = v; }

    public Double getPercentage() {
        if (maxMarks == null || maxMarks == 0) return 0.0;
        return (marksObtained * 100.0) / maxMarks;
    }
}
