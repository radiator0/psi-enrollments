package com.psi.repository;

import com.psi.domain.Enrollment;

import com.psi.domain.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Enrollment entity.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByStudent(Student student);
}
