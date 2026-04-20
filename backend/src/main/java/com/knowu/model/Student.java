package com.knowu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String studentId;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;
    private String phone;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @Enumerated(EnumType.STRING)
    private StudentStatus status = StudentStatus.ACTIVE;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @PreUpdate
    void setUpdatedAt() { this.updatedAt = LocalDateTime.now(); }

    public enum Gender { MALE, FEMALE, OTHER }
    public enum StudentStatus { ACTIVE, PENDING, SUSPENDED, GRADUATED }

    public Student() {}

    // Getters
    public Long getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public Gender getGender() { return gender; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public SchoolClass getSchoolClass() { return schoolClass; }
    public StudentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setStudentId(String v) { this.studentId = v; }
    public void setFirstName(String v) { this.firstName = v; }
    public void setLastName(String v) { this.lastName = v; }
    public void setDateOfBirth(LocalDate v) { this.dateOfBirth = v; }
    public void setGender(Gender v) { this.gender = v; }
    public void setEmail(String v) { this.email = v; }
    public void setPhone(String v) { this.phone = v; }
    public void setAddress(String v) { this.address = v; }
    public void setSchoolClass(SchoolClass v) { this.schoolClass = v; }
    public void setStatus(StudentStatus v) { this.status = v; }

    public String getFullName() { return firstName + " " + lastName; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Student s = new Student();
        public Builder studentId(String v)       { s.studentId = v; return this; }
        public Builder firstName(String v)        { s.firstName = v; return this; }
        public Builder lastName(String v)         { s.lastName = v; return this; }
        public Builder dateOfBirth(LocalDate v)   { s.dateOfBirth = v; return this; }
        public Builder gender(Gender v)           { s.gender = v; return this; }
        public Builder email(String v)            { s.email = v; return this; }
        public Builder phone(String v)            { s.phone = v; return this; }
        public Builder address(String v)          { s.address = v; return this; }
        public Builder schoolClass(SchoolClass v) { s.schoolClass = v; return this; }
        public Builder status(StudentStatus v)    { s.status = v; return this; }
        public Student build()                    { return s; }
    }
}
