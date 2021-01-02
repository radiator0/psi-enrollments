package com.psi.repository;
import com.psi.domain.EnrollmentUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EnrollmentUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentUnitRepository extends JpaRepository<EnrollmentUnit, Long> {

}
