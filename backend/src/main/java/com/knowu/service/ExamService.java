package com.knowu.service;

import com.knowu.dto.ExamDTO;
import com.knowu.model.Exam;
import com.knowu.repository.ExamRepository;
import com.knowu.repository.SchoolClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamService {

    private final ExamRepository examRepository;
    private final SchoolClassRepository classRepository;

    public ExamService(ExamRepository examRepository, SchoolClassRepository classRepository) {
        this.examRepository = examRepository;
        this.classRepository = classRepository;
    }

    @Transactional(readOnly = true)
    public List<ExamDTO> findAll(String classId, String type, String month) {
        List<Exam> exams = examRepository.findAll();
        if (classId != null && !classId.isBlank()) {
            exams = exams.stream()
                    .filter(e -> e.getSchoolClass() != null &&
                                 e.getSchoolClass().getId().toString().equals(classId))
                    .collect(Collectors.toList());
        }
        if (type != null && !type.isBlank()) {
            exams = exams.stream()
                    .filter(e -> e.getType() != null && e.getType().name().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return exams.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExamDTO findById(Long id) {
        return toDTO(examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found: " + id)));
    }

    public ExamDTO create(ExamDTO dto) {
        return toDTO(examRepository.save(buildExam(dto)));
    }

    public ExamDTO update(Long id, ExamDTO dto) {
        Exam e = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found: " + id));
        e.setTitle(dto.getTitle());
        e.setSubject(dto.getSubject());
        e.setExamDate(dto.getExamDate());
        e.setStartTime(dto.getStartTime());
        e.setDurationMinutes(dto.getDurationMinutes());
        e.setRoom(dto.getRoom());
        e.setMaxMarks(dto.getMaxMarks());
        if (dto.getType()   != null) e.setType(Exam.ExamType.valueOf(dto.getType()));
        if (dto.getStatus() != null) e.setStatus(Exam.ExamStatus.valueOf(dto.getStatus()));
        if (dto.getClassId() != null) classRepository.findById(dto.getClassId()).ifPresent(e::setSchoolClass);
        return toDTO(examRepository.save(e));
    }

    public void delete(Long id) {
        if (!examRepository.existsById(id)) throw new RuntimeException("Exam not found: " + id);
        examRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ExamDTO> findUpcoming() {
        return examRepository.findByExamDateAfterOrderByExamDateAsc(LocalDate.now())
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private Exam buildExam(ExamDTO dto) {
        Exam exam = Exam.builder()
                .title(dto.getTitle())
                .subject(dto.getSubject())
                .examDate(dto.getExamDate())
                .startTime(dto.getStartTime())
                .durationMinutes(dto.getDurationMinutes())
                .room(dto.getRoom())
                .maxMarks(dto.getMaxMarks())
                .build();
        if (dto.getType()    != null) exam.setType(Exam.ExamType.valueOf(dto.getType()));
        if (dto.getStatus()  != null) exam.setStatus(Exam.ExamStatus.valueOf(dto.getStatus()));
        if (dto.getClassId() != null) classRepository.findById(dto.getClassId()).ifPresent(exam::setSchoolClass);
        return exam;
    }

    private ExamDTO toDTO(Exam e) {
        return ExamDTO.builder()
                .id(e.getId())
                .title(e.getTitle())
                .subject(e.getSubject())
                .classId(e.getSchoolClass()   != null ? e.getSchoolClass().getId()   : null)
                .className(e.getSchoolClass() != null ? e.getSchoolClass().getName() : null)
                .type(e.getType()     != null ? e.getType().name()   : null)
                .status(e.getStatus() != null ? e.getStatus().name() : null)
                .examDate(e.getExamDate())
                .startTime(e.getStartTime())
                .durationMinutes(e.getDurationMinutes())
                .room(e.getRoom())
                .maxMarks(e.getMaxMarks())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
