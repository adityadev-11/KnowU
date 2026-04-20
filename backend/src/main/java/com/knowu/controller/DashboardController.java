package com.knowu.controller;

import com.knowu.model.Attendance;
import com.knowu.model.Student;
import com.knowu.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class DashboardController {

    private final StudentRepository    studentRepo;
    private final AttendanceRepository attendanceRepo;
    private final ExamRepository       examRepo;
    private final SchoolClassRepository classRepo;

    public DashboardController(StudentRepository studentRepo,
                               AttendanceRepository attendanceRepo,
                               ExamRepository examRepo,
                               SchoolClassRepository classRepo) {
        this.studentRepo    = studentRepo;
        this.attendanceRepo = attendanceRepo;
        this.examRepo       = examRepo;
        this.classRepo      = classRepo;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalStudents",   studentRepo.count());
        stats.put("activeStudents",  studentRepo.countByStatus(Student.StudentStatus.ACTIVE));
        stats.put("pendingStudents", studentRepo.countByStatus(Student.StudentStatus.PENDING));
        stats.put("totalClasses",    classRepo.count());
        stats.put("upcomingExams",   examRepo.findByExamDateAfterOrderByExamDateAsc(LocalDate.now()).size());

        List<Attendance> todayRecords = attendanceRepo.findByDate(LocalDate.now());
        long todayTotal   = todayRecords.size();
        long todayPresent = todayRecords.stream()
                .filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT).count();
        double todayPct = todayTotal > 0 ? Math.round((todayPresent * 100.0 / todayTotal) * 10.0) / 10.0 : 0;

        stats.put("todayAttendancePct", todayPct);
        stats.put("todayPresent",       todayPresent);
        stats.put("todayAbsent",        todayTotal - todayPresent);
        return ResponseEntity.ok(stats);
    }
}
