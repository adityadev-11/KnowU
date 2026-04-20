package com.knowu.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private String feeType;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private LocalDate dueDate;
    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    private FeeStatus status = FeeStatus.PENDING;

    private String transactionId;
    private String paymentMode;
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum FeeStatus { PAID, PENDING, OVERDUE, WAIVED }

    public Fee() {}

    // Getters
    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public String getFeeType() { return feeType; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getPaidDate() { return paidDate; }
    public FeeStatus getStatus() { return status; }
    public String getTransactionId() { return transactionId; }
    public String getPaymentMode() { return paymentMode; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setStudent(Student v) { this.student = v; }
    public void setFeeType(String v) { this.feeType = v; }
    public void setAmount(BigDecimal v) { this.amount = v; }
    public void setDueDate(LocalDate v) { this.dueDate = v; }
    public void setPaidDate(LocalDate v) { this.paidDate = v; }
    public void setStatus(FeeStatus v) { this.status = v; }
    public void setTransactionId(String v) { this.transactionId = v; }
    public void setPaymentMode(String v) { this.paymentMode = v; }
}
