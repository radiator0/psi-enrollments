package com.psi.repository;

import com.psi.domain.ClassGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ClassGroup entity.
 */
@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
    Optional<ClassGroup> findOneByCode(String code);
}
