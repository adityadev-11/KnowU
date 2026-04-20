package com.knowu.controller;

import com.knowu.model.User;
import com.knowu.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository classRepository;
    private final ExamRepository examRepository;
    private final AttendanceRepository attendanceRepository;

    public AdminController(UserRepository userRepository,
                           StudentRepository studentRepository,
                           SchoolClassRepository classRepository,
                           ExamRepository examRepository,
                           AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.examRepository = examRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> listUsers() {
        List<Map<String, Object>> users = userRepository.findAll().stream()
                .map(u -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", u.getId());
                    map.put("name", u.getName());
                    map.put("email", u.getEmail());
                    map.put("role", u.getRole().name());
                    map.put("createdAt", u.getCreatedAt());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "User not found"));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    @GetMapping("/system-stats")
    public ResponseEntity<Map<String, Object>> systemStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("adminUsers", userRepository.countByRole(User.Role.ADMIN));
        stats.put("regularUsers", userRepository.countByRole(User.Role.USER));
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalClasses", classRepository.count());
        stats.put("totalExams", examRepository.count());
        stats.put("totalAttendanceRecords", attendanceRepository.count());
        return ResponseEntity.ok(stats);
    }
}
