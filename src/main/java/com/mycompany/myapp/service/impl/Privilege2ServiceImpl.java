package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Privilege2;
import com.mycompany.myapp.repository.Privilege2Repository;
import com.mycompany.myapp.service.Privilege2Service;
import com.mycompany.myapp.service.dto.Privilege2DTO;
import com.mycompany.myapp.service.mapper.Privilege2Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Privilege2}.
 */
@Service
@Transactional
public class Privilege2ServiceImpl implements Privilege2Service {

    private final Logger log = LoggerFactory.getLogger(Privilege2ServiceImpl.class);

    private final Privilege2Repository privilege2Repository;

    private final Privilege2Mapper privilege2Mapper;

    public Privilege2ServiceImpl(Privilege2Repository privilege2Repository, Privilege2Mapper privilege2Mapper) {
        this.privilege2Repository = privilege2Repository;
        this.privilege2Mapper = privilege2Mapper;
    }

    @Override
    public Privilege2DTO save(Privilege2DTO privilege2DTO) {
        log.debug("Request to save Privilege2 : {}", privilege2DTO);
        Privilege2 privilege2 = privilege2Mapper.toEntity(privilege2DTO);
        privilege2 = privilege2Repository.save(privilege2);
        return privilege2Mapper.toDto(privilege2);
    }

    @Override
    public Privilege2DTO update(Privilege2DTO privilege2DTO) {
        log.debug("Request to update Privilege2 : {}", privilege2DTO);
        Privilege2 privilege2 = privilege2Mapper.toEntity(privilege2DTO);
        privilege2 = privilege2Repository.save(privilege2);
        return privilege2Mapper.toDto(privilege2);
    }

    @Override
    public Optional<Privilege2DTO> partialUpdate(Privilege2DTO privilege2DTO) {
        log.debug("Request to partially update Privilege2 : {}", privilege2DTO);

        return privilege2Repository
            .findById(privilege2DTO.getId())
            .map(existingPrivilege2 -> {
                privilege2Mapper.partialUpdate(existingPrivilege2, privilege2DTO);

                return existingPrivilege2;
            })
            .map(privilege2Repository::save)
            .map(privilege2Mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Privilege2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Privilege2s");
        return privilege2Repository.findAll(pageable).map(privilege2Mapper::toDto);
    }

    public Page<Privilege2DTO> findAllWithEagerRelationships(Pageable pageable) {
        return privilege2Repository.findAllWithEagerRelationships(pageable).map(privilege2Mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Privilege2DTO> findOne(Long id) {
        log.debug("Request to get Privilege2 : {}", id);
        return privilege2Repository.findOneWithEagerRelationships(id).map(privilege2Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Privilege2 : {}", id);
        privilege2Repository.deleteById(id);
    }
}
