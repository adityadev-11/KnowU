package com.knowu.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentDTO {
    private Long id;
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private Long classId;
    private String className;
    private String status;
    private Double attendancePercentage;
    private LocalDateTime createdAt;

    public StudentDTO() {}

    // Getters
    public Long getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public Long getClassId() { return classId; }
    public String getClassName() { return className; }
    public String getStatus() { return status; }
    public Double getAttendancePercentage() { return attendancePercentage; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setStudentId(String v) { this.studentId = v; }
    public void setFirstName(String v) { this.firstName = v; }
    public void setLastName(String v) { this.lastName = v; }
    public void setDateOfBirth(LocalDate v) { this.dateOfBirth = v; }
    public void setGender(String v) { this.gender = v; }
    public void setEmail(String v) { this.email = v; }
    public void setPhone(String v) { this.phone = v; }
    public void setAddress(String v) { this.address = v; }
    public void setClassId(Long v) { this.classId = v; }
    public void setClassName(String v) { this.className = v; }
    public void setStatus(String v) { this.status = v; }
    public void setAttendancePercentage(Double v) { this.attendancePercentage = v; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final StudentDTO d = new StudentDTO();
        public Builder id(Long v)                       { d.id = v; return this; }
        public Builder studentId(String v)              { d.studentId = v; return this; }
        public Builder firstName(String v)              { d.firstName = v; return this; }
        public Builder lastName(String v)               { d.lastName = v; return this; }
        public Builder dateOfBirth(LocalDate v)         { d.dateOfBirth = v; return this; }
        public Builder gender(String v)                 { d.gender = v; return this; }
        public Builder email(String v)                  { d.email = v; return this; }
        public Builder phone(String v)                  { d.phone = v; return this; }
        public Builder address(String v)                { d.address = v; return this; }
        public Builder classId(Long v)                  { d.classId = v; return this; }
        public Builder className(String v)              { d.className = v; return this; }
        public Builder status(String v)                 { d.status = v; return this; }
        public Builder attendancePercentage(Double v)   { d.attendancePercentage = v; return this; }
        public Builder createdAt(LocalDateTime v)       { d.createdAt = v; return this; }
        public StudentDTO build()                       { return d; }
    }
}
