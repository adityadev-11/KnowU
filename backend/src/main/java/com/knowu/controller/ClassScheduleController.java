package com.knowu.controller;

import com.knowu.model.ClassSchedule;
import com.knowu.repository.ClassScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedule")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class ClassScheduleController {

    private final ClassScheduleRepository scheduleRepo;

    public ClassScheduleController(ClassScheduleRepository scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    @GetMapping
    public ResponseEntity<List<ClassSchedule>> getSchedule(@RequestParam Long classId) {
        return ResponseEntity.ok(scheduleRepo.findBySchoolClassIdOrderByDayOfWeekAscStartTimeAsc(classId));
    }

    @PostMapping
    public ResponseEntity<ClassSchedule> createSlot(@RequestBody ClassSchedule slot) {
        return ResponseEntity.status(201).body(scheduleRepo.save(slot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSlot(@PathVariable Long id) {
        scheduleRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Slot removed"));
    }
}
