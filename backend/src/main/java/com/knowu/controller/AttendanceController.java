package com.knowu.controller;

import com.knowu.dto.AttendanceDTO;
import com.knowu.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/attendance")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> markBulkAttendance(@RequestBody List<AttendanceDTO> dtos) {
        int saved = attendanceService.markBulk(dtos);
        return ResponseEntity.ok(Map.of("message", "Attendance saved", "count", saved));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAttendance(
            @RequestParam Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String subject) {
        return ResponseEntity.ok(attendanceService.getForClass(classId, date, subject));
    }

    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<Map<String, Object>> getStudentSummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getSummaryForStudent(studentId));
    }

    @GetMapping("/weekly-overview")
    public ResponseEntity<Map<String, Double>> getWeeklyOverview() {
        return ResponseEntity.ok(attendanceService.getWeeklyOverview());
    }
}
