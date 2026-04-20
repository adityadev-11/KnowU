package com.knowu.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_issues")
public class BookIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private LibraryBook book;

    private LocalDate issuedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.ISSUED;

    private Double fineAmount;

    public enum IssueStatus { ISSUED, RETURNED, OVERDUE }

    public BookIssue() {}

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public LibraryBook getBook() { return book; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public IssueStatus getStatus() { return status; }
    public Double getFineAmount() { return fineAmount; }

    public void setStudent(Student v) { this.student = v; }
    public void setBook(LibraryBook v) { this.book = v; }
    public void setIssuedDate(LocalDate v) { this.issuedDate = v; }
    public void setDueDate(LocalDate v) { this.dueDate = v; }
    public void setReturnedDate(LocalDate v) { this.returnedDate = v; }
    public void setStatus(IssueStatus v) { this.status = v; }
    public void setFineAmount(Double v) { this.fineAmount = v; }
}
