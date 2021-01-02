package com.psi.repository;
import com.psi.domain.StudyProgram;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudyProgram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {

}
