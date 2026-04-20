package com.knowu.repository;

import com.knowu.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByDate(LocalDate date);

    Optional<Attendance> findByStudentIdAndDateAndSubject(Long studentId, LocalDate date, String subject);

    @Query("""
        SELECT a FROM Attendance a
        JOIN a.student s
        WHERE s.schoolClass.id = :classId
          AND a.date = :date
          AND (:subject IS NULL OR a.subject = :subject)
        """)
    List<Attendance> findByClassIdAndDate(
            @Param("classId")  Long classId,
            @Param("date")     LocalDate date,
            @Param("subject")  String subject
    );

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT'")
    long countPresentByStudentId(@Param("studentId") Long studentId);
}
