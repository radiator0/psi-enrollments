package com.psi.service;

import com.psi.domain.StudyProgram;
import com.psi.repository.StudyProgramRepository;
import com.psi.service.dto.StudyProgramDTO;
import com.psi.service.mapper.StudyProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StudyProgram}.
 */
@Service
@Transactional
public class StudyProgramService {

    private final Logger log = LoggerFactory.getLogger(StudyProgramService.class);

    private final StudyProgramRepository studyProgramRepository;

    private final StudyProgramMapper studyProgramMapper;

    public StudyProgramService(StudyProgramRepository studyProgramRepository, StudyProgramMapper studyProgramMapper) {
        this.studyProgramRepository = studyProgramRepository;
        this.studyProgramMapper = studyProgramMapper;
    }

    /**
     * Save a studyProgram.
     *
     * @param studyProgramDTO the entity to save.
     * @return the persisted entity.
     */
    public StudyProgramDTO save(StudyProgramDTO studyProgramDTO) {
        log.debug("Request to save StudyProgram : {}", studyProgramDTO);
        StudyProgram studyProgram = studyProgramMapper.toEntity(studyProgramDTO);
        studyProgram = studyProgramRepository.save(studyProgram);
        return studyProgramMapper.toDto(studyProgram);
    }

    /**
     * Get all the studyPrograms.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudyProgramDTO> findAll() {
        log.debug("Request to get all StudyPrograms");
        return studyProgramRepository.findAll().stream()
            .map(studyProgramMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one studyProgram by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudyProgramDTO> findOne(Long id) {
        log.debug("Request to get StudyProgram : {}", id);
        return studyProgramRepository.findById(id)
            .map(studyProgramMapper::toDto);
    }

    /**
     * Delete the studyProgram by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudyProgram : {}", id);
        studyProgramRepository.deleteById(id);
    }
}
