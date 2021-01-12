package com.psi.repository;

import com.psi.domain.EnrollmentRight;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EnrollmentRight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentRightRepository extends JpaRepository<EnrollmentRight, Long> {
}
