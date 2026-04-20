package com.knowu.repository;

import com.knowu.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    long countByStatus(Student.StudentStatus status);

    @Query("""
        SELECT s FROM Student s
        WHERE LOWER(s.firstName) LIKE %:q%
           OR LOWER(s.lastName)  LIKE %:q%
           OR LOWER(s.studentId) LIKE %:q%
           OR LOWER(s.email)     LIKE %:q%
        """)
    Page<Student> searchStudents(@Param("q") String query, Pageable pageable);

    boolean existsByStudentId(String studentId);
}
