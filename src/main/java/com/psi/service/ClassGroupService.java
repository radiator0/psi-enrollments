package com.psi.service;

import com.psi.domain.ClassGroup;
import com.psi.repository.ClassGroupRepository;
import com.psi.service.dto.ClassGroupDTO;
import com.psi.service.mapper.ClassGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClassGroup}.
 */
@Service
@Transactional
public class ClassGroupService {

    private final Logger log = LoggerFactory.getLogger(ClassGroupService.class);

    private final ClassGroupRepository classGroupRepository;

    private final ClassGroupMapper classGroupMapper;

    public ClassGroupService(ClassGroupRepository classGroupRepository, ClassGroupMapper classGroupMapper) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupMapper = classGroupMapper;
    }

    /**
     * Save a classGroup.
     *
     * @param classGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassGroupDTO save(ClassGroupDTO classGroupDTO) {
        log.debug("Request to save ClassGroup : {}", classGroupDTO);
        ClassGroup classGroup = classGroupMapper.toEntity(classGroupDTO);
        classGroup = classGroupRepository.save(classGroup);
        return classGroupMapper.toDto(classGroup);
    }

    /**
     * Get all the classGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassGroups");
        return classGroupRepository.findAll(pageable)
            .map(classGroupMapper::toDto);
    }


    /**
     * Get one classGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassGroupDTO> findOne(Long id) {
        log.debug("Request to get ClassGroup : {}", id);
        return classGroupRepository.findById(id)
            .map(classGroupMapper::toDto);
    }

    /**
     * Delete the classGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassGroup : {}", id);
        classGroupRepository.deleteById(id);
    }
}
