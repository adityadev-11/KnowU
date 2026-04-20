package com.knowu.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "school_classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String section;
    private String stream;
    private Integer grade;

    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.LAZY)
    private List<Student> students;

    public SchoolClass() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSection() { return section; }
    public String getStream() { return stream; }
    public Integer getGrade() { return grade; }
    public List<Student> getStudents() { return students; }

    // Setters
    public void setId(Long v) { this.id = v; }
    public void setName(String v) { this.name = v; }
    public void setSection(String v) { this.section = v; }
    public void setStream(String v) { this.stream = v; }
    public void setGrade(Integer v) { this.grade = v; }
    public void setStudents(List<Student> v) { this.students = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final SchoolClass c = new SchoolClass();
        public Builder name(String v)    { c.name = v; return this; }
        public Builder section(String v) { c.section = v; return this; }
        public Builder stream(String v)  { c.stream = v; return this; }
        public Builder grade(Integer v)  { c.grade = v; return this; }
        public SchoolClass build()       { return c; }
    }
}
