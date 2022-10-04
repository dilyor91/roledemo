package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Role2;
import com.mycompany.myapp.repository.Role2Repository;
import com.mycompany.myapp.service.Role2Service;
import com.mycompany.myapp.service.dto.Role2DTO;
import com.mycompany.myapp.service.mapper.Role2Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Role2}.
 */
@Service
@Transactional
public class Role2ServiceImpl implements Role2Service {

    private final Logger log = LoggerFactory.getLogger(Role2ServiceImpl.class);

    private final Role2Repository role2Repository;

    private final Role2Mapper role2Mapper;

    public Role2ServiceImpl(Role2Repository role2Repository, Role2Mapper role2Mapper) {
        this.role2Repository = role2Repository;
        this.role2Mapper = role2Mapper;
    }

    @Override
    public Role2DTO save(Role2DTO role2DTO) {
        log.debug("Request to save Role2 : {}", role2DTO);
        Role2 role2 = role2Mapper.toEntity(role2DTO);
        role2 = role2Repository.save(role2);
        return role2Mapper.toDto(role2);
    }

    @Override
    public Role2DTO update(Role2DTO role2DTO) {
        log.debug("Request to update Role2 : {}", role2DTO);
        Role2 role2 = role2Mapper.toEntity(role2DTO);
        role2 = role2Repository.save(role2);
        return role2Mapper.toDto(role2);
    }

    @Override
    public Optional<Role2DTO> partialUpdate(Role2DTO role2DTO) {
        log.debug("Request to partially update Role2 : {}", role2DTO);

        return role2Repository
            .findById(role2DTO.getId())
            .map(existingRole2 -> {
                role2Mapper.partialUpdate(existingRole2, role2DTO);

                return existingRole2;
            })
            .map(role2Repository::save)
            .map(role2Mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Role2s");
        return role2Repository.findAll(pageable).map(role2Mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role2DTO> findOne(Long id) {
        log.debug("Request to get Role2 : {}", id);
        return role2Repository.findById(id).map(role2Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role2 : {}", id);
        role2Repository.deleteById(id);
    }
}
