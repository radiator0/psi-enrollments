package com.psi.repository;

import com.psi.domain.ClassGroup;
import com.psi.domain.Request;
import com.psi.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByStudent(Student student);

    List<Request> findAllByClassGroupIn(List<ClassGroup> classGroup);

    Integer countAllByIsExaminedAndClassGroupIn(Boolean isExamined, List<ClassGroup> classGroup);

    Integer countAllByIsExaminedAndStudent(Boolean isExamined, Student student);
}
