package com.psi.repository;

import com.psi.domain.Lecturer;

import com.psi.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Lecturer entity.
 */
@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findOneByInternalUser(User internalUser);
}
