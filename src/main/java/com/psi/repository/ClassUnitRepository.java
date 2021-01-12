package com.psi.repository;

import com.psi.domain.ClassUnit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClassUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassUnitRepository extends JpaRepository<ClassUnit, Long> {
}
