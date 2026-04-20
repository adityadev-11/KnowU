package com.knowu.repository;

import com.knowu.model.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findBySchoolClassIdOrderByDayOfWeekAscStartTimeAsc(Long classId);
}
