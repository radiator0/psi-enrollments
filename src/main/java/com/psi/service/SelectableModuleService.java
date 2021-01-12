package com.psi.service;

import com.psi.domain.SelectableModule;
import com.psi.repository.SelectableModuleRepository;
import com.psi.service.dto.SelectableModuleDTO;
import com.psi.service.mapper.SelectableModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SelectableModule}.
 */
@Service
@Transactional
public class SelectableModuleService {

    private final Logger log = LoggerFactory.getLogger(SelectableModuleService.class);

    private final SelectableModuleRepository selectableModuleRepository;

    private final SelectableModuleMapper selectableModuleMapper;

    public SelectableModuleService(SelectableModuleRepository selectableModuleRepository, SelectableModuleMapper selectableModuleMapper) {
        this.selectableModuleRepository = selectableModuleRepository;
        this.selectableModuleMapper = selectableModuleMapper;
    }

    /**
     * Save a selectableModule.
     *
     * @param selectableModuleDTO the entity to save.
     * @return the persisted entity.
     */
    public SelectableModuleDTO save(SelectableModuleDTO selectableModuleDTO) {
        log.debug("Request to save SelectableModule : {}", selectableModuleDTO);
        SelectableModule selectableModule = selectableModuleMapper.toEntity(selectableModuleDTO);
        selectableModule = selectableModuleRepository.save(selectableModule);
        return selectableModuleMapper.toDto(selectableModule);
    }

    /**
     * Get all the selectableModules.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SelectableModuleDTO> findAll() {
        log.debug("Request to get all SelectableModules");
        return selectableModuleRepository.findAll().stream()
            .map(selectableModuleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one selectableModule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SelectableModuleDTO> findOne(Long id) {
        log.debug("Request to get SelectableModule : {}", id);
        return selectableModuleRepository.findById(id)
            .map(selectableModuleMapper::toDto);
    }

    /**
     * Delete the selectableModule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SelectableModule : {}", id);
        selectableModuleRepository.deleteById(id);
    }
}
