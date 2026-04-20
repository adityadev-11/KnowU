package com.knowu.controller;

import com.knowu.dto.ExamDTO;
import com.knowu.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/exams")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams(
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String month) {
        return ResponseEntity.ok(examService.findAll(classId, type, month));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExam(@PathVariable Long id) {
        return ResponseEntity.ok(examService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO dto) {
        return ResponseEntity.status(201).body(examService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long id, @RequestBody ExamDTO dto) {
        return ResponseEntity.ok(examService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExam(@PathVariable Long id) {
        examService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Exam deleted"));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ExamDTO>> getUpcomingExams() {
        return ResponseEntity.ok(examService.findUpcoming());
    }
}
