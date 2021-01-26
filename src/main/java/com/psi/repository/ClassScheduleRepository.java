package com.psi.repository;

import com.psi.domain.ClassGroup;
import com.psi.domain.ClassSchedule;

import com.psi.domain.Lecturer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Spring Data  repository for the ClassSchedule entity.
 */
@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findAllByLecturer(Lecturer lecturer);
    List<ClassSchedule> findAllByClassGroup(ClassGroup classGroup);
}
