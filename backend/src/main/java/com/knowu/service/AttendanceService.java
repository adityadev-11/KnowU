package com.knowu.service;

import com.knowu.dto.AttendanceDTO;
import com.knowu.model.Attendance;
import com.knowu.model.Student;
import com.knowu.repository.AttendanceRepository;
import com.knowu.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public int markBulk(List<AttendanceDTO> dtos) {
        List<Attendance> records = new ArrayList<>();
        for (AttendanceDTO dto : dtos) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + dto.getStudentId()));

            attendanceRepository
                    .findByStudentIdAndDateAndSubject(dto.getStudentId(), dto.getDate(), dto.getSubject())
                    .ifPresent(attendanceRepository::delete);

            records.add(Attendance.builder()
                    .student(student)
                    .date(dto.getDate())
                    .subject(dto.getSubject())
                    .status(Attendance.AttendanceStatus.valueOf(dto.getStatus()))
                    .remarks(dto.getRemarks())
                    .build());
        }
        return attendanceRepository.saveAll(records).size();
    }

    @Transactional(readOnly = true)
    public List<AttendanceDTO> getForClass(Long classId, LocalDate date, String subject) {
        return attendanceRepository.findByClassIdAndDate(classId, date, subject)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSummaryForStudent(Long studentId) {
        List<Attendance> all = attendanceRepository.findByStudentId(studentId);
        long total   = all.size();
        long present = all.stream().filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT).count();
        long absent  = all.stream().filter(a -> a.getStatus() == Attendance.AttendanceStatus.ABSENT).count();
        long late    = all.stream().filter(a -> a.getStatus() == Attendance.AttendanceStatus.LATE).count();
        long leave   = all.stream().filter(a -> a.getStatus() == Attendance.AttendanceStatus.LEAVE).count();
        double pct   = total > 0 ? Math.round((present * 100.0 / total) * 10.0) / 10.0 : 0.0;

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalDays",  total);
        summary.put("present",    present);
        summary.put("absent",     absent);
        summary.put("late",       late);
        summary.put("leave",      leave);
        summary.put("percentage", pct);
        return summary;
    }

    @Transactional(readOnly = true)
    public Map<String, Double> getWeeklyOverview() {
        LocalDate today  = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        Map<String, Double> overview = new LinkedHashMap<>();
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        for (int i = 0; i < 5; i++) {
            LocalDate day = monday.plusDays(i);
            if (day.isAfter(today)) { overview.put(labels[i], null); continue; }
            List<Attendance> recs = attendanceRepository.findByDate(day);
            long total   = recs.size();
            long present = recs.stream().filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT).count();
            overview.put(labels[i], total > 0 ? Math.round((present * 100.0 / total) * 10.0) / 10.0 : 0.0);
        }
        return overview;
    }

    private AttendanceDTO toDTO(Attendance a) {
        return AttendanceDTO.builder()
                .id(a.getId())
                .studentId(a.getStudent().getId())
                .studentName(a.getStudent().getFullName())
                .date(a.getDate())
                .subject(a.getSubject())
                .status(a.getStatus().name())
                .remarks(a.getRemarks())
                .markedAt(a.getMarkedAt())
                .build();
    }
}
