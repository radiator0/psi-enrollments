package com.psi.repository;
import com.psi.domain.CourseUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourseUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseUnitRepository extends JpaRepository<CourseUnit, Long> {

}
