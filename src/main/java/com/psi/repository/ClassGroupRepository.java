package com.psi.repository;

import com.psi.domain.ClassGroup;
import com.psi.domain.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ClassGroup entity.
 */
@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
    Optional<ClassGroup> findOneByCode(String code);

    List<ClassGroup> findAllByMainLecturer(Lecturer mainLecturer);
}
