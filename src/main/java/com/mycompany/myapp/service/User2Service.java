package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.User2DTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.User2}.
 */
public interface User2Service {
    /**
     * Save a user2.
     *
     * @param user2DTO the entity to save.
     * @return the persisted entity.
     */
    User2DTO save(User2DTO user2DTO);

    /**
     * Updates a user2.
     *
     * @param user2DTO the entity to update.
     * @return the persisted entity.
     */
    User2DTO update(User2DTO user2DTO);

    /**
     * Partially updates a user2.
     *
     * @param user2DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<User2DTO> partialUpdate(User2DTO user2DTO);

    /**
     * Get all the user2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<User2DTO> findAll(Pageable pageable);

    /**
     * Get all the user2s with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<User2DTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" user2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<User2DTO> findOne(Long id);

    /**
     * Delete the "id" user2.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
