package com.knowu.repository;

import com.knowu.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByExamDateAfterOrderByExamDateAsc(LocalDate date);
    List<Exam> findBySchoolClassId(Long classId);
}
