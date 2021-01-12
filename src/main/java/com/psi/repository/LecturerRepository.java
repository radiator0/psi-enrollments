package com.psi.repository;

import com.psi.domain.Lecturer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lecturer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}
