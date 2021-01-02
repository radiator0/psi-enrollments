package com.psi.repository;
import com.psi.domain.Enrollment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enrollment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
