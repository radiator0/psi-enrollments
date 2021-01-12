package com.psi.repository;

import com.psi.domain.EnrollmentDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EnrollmentDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentDateRepository extends JpaRepository<EnrollmentDate, Long> {
}
