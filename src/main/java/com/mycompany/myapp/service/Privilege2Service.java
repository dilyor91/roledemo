package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Privilege2DTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Privilege2}.
 */
public interface Privilege2Service {
    /**
     * Save a privilege2.
     *
     * @param privilege2DTO the entity to save.
     * @return the persisted entity.
     */
    Privilege2DTO save(Privilege2DTO privilege2DTO);

    /**
     * Updates a privilege2.
     *
     * @param privilege2DTO the entity to update.
     * @return the persisted entity.
     */
    Privilege2DTO update(Privilege2DTO privilege2DTO);

    /**
     * Partially updates a privilege2.
     *
     * @param privilege2DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Privilege2DTO> partialUpdate(Privilege2DTO privilege2DTO);

    /**
     * Get all the privilege2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Privilege2DTO> findAll(Pageable pageable);

    /**
     * Get all the privilege2s with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Privilege2DTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" privilege2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Privilege2DTO> findOne(Long id);

    /**
     * Delete the "id" privilege2.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
