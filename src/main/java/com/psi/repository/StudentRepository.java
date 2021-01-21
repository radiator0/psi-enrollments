package com.psi.repository;

import com.psi.domain.Student;

import com.psi.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findOneByInternalUser(User internalUser);
}
