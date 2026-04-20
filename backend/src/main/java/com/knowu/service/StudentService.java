package com.knowu.service;

import com.knowu.dto.StudentDTO;
import com.knowu.model.Student;
import com.knowu.repository.StudentRepository;
import com.knowu.repository.SchoolClassRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolClassRepository classRepository;

    public StudentService(StudentRepository studentRepository, SchoolClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    public Page<StudentDTO> findAll(String search, String status, String classId, Pageable pageable) {
        Page<Student> students;
        if (search != null && !search.isBlank()) {
            students = studentRepository.searchStudents(search.toLowerCase(), pageable);
        } else {
            students = studentRepository.findAll(pageable);
        }
        return students.map(this::toDTO);
    }

    public StudentDTO findById(Long id) {
        return toDTO(studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id)));
    }

    public StudentDTO create(StudentDTO dto) {
        String newId = generateStudentId();
        Student student = Student.builder()
                .studentId(newId)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender() != null ? Student.Gender.valueOf(dto.getGender()) : null)
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .status(Student.StudentStatus.PENDING)
                .build();

        if (dto.getClassId() != null) {
            classRepository.findById(dto.getClassId()).ifPresent(student::setSchoolClass);
        }
        return toDTO(studentRepository.save(student));
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        if (dto.getStatus() != null) {
            student.setStatus(Student.StudentStatus.valueOf(dto.getStatus()));
        }
        if (dto.getClassId() != null) {
            classRepository.findById(dto.getClassId()).ifPresent(student::setSchoolClass);
        }
        return toDTO(studentRepository.save(student));
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) throw new RuntimeException("Student not found");
        studentRepository.deleteById(id);
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalStudents",     studentRepository.count());
        stats.put("activeStudents",    studentRepository.countByStatus(Student.StudentStatus.ACTIVE));
        stats.put("pendingStudents",   studentRepository.countByStatus(Student.StudentStatus.PENDING));
        stats.put("suspendedStudents", studentRepository.countByStatus(Student.StudentStatus.SUSPENDED));
        return stats;
    }

    private String generateStudentId() {
        long count = studentRepository.count();
        return "STU" + String.format("%04d", count + 1);
    }

    private StudentDTO toDTO(Student s) {
        return StudentDTO.builder()
                .id(s.getId())
                .studentId(s.getStudentId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .dateOfBirth(s.getDateOfBirth())
                .gender(s.getGender() != null ? s.getGender().name() : null)
                .email(s.getEmail())
                .phone(s.getPhone())
                .address(s.getAddress())
                .classId(s.getSchoolClass() != null ? s.getSchoolClass().getId() : null)
                .className(s.getSchoolClass() != null ? s.getSchoolClass().getName() : null)
                .status(s.getStatus().name())
                .createdAt(s.getCreatedAt())
                .build();
    }
}
