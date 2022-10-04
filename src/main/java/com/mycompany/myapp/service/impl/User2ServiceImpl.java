package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.User2;
import com.mycompany.myapp.repository.User2Repository;
import com.mycompany.myapp.service.User2Service;
import com.mycompany.myapp.service.dto.User2DTO;
import com.mycompany.myapp.service.mapper.User2Mapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link User2}.
 */
@Service
@Transactional
public class User2ServiceImpl implements User2Service {

    private final Logger log = LoggerFactory.getLogger(User2ServiceImpl.class);

    private final User2Repository user2Repository;

    private final User2Mapper user2Mapper;

    public User2ServiceImpl(User2Repository user2Repository, User2Mapper user2Mapper) {
        this.user2Repository = user2Repository;
        this.user2Mapper = user2Mapper;
    }

    @Override
    public User2DTO save(User2DTO user2DTO) {
        log.debug("Request to save User2 : {}", user2DTO);
        User2 user2 = user2Mapper.toEntity(user2DTO);
        user2 = user2Repository.save(user2);
        return user2Mapper.toDto(user2);
    }

    @Override
    public User2DTO update(User2DTO user2DTO) {
        log.debug("Request to update User2 : {}", user2DTO);
        User2 user2 = user2Mapper.toEntity(user2DTO);
        user2 = user2Repository.save(user2);
        return user2Mapper.toDto(user2);
    }

    @Override
    public Optional<User2DTO> partialUpdate(User2DTO user2DTO) {
        log.debug("Request to partially update User2 : {}", user2DTO);

        return user2Repository
            .findById(user2DTO.getId())
            .map(existingUser2 -> {
                user2Mapper.partialUpdate(existingUser2, user2DTO);

                return existingUser2;
            })
            .map(user2Repository::save)
            .map(user2Mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all User2s");
        return user2Repository.findAll(pageable).map(user2Mapper::toDto);
    }

    public Page<User2DTO> findAllWithEagerRelationships(Pageable pageable) {
        return user2Repository.findAllWithEagerRelationships(pageable).map(user2Mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User2DTO> findOne(Long id) {
        log.debug("Request to get User2 : {}", id);
        return user2Repository.findOneWithEagerRelationships(id).map(user2Mapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User2 : {}", id);
        user2Repository.deleteById(id);
    }
}
