package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Role2DTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Role2}.
 */
public interface Role2Service {
    /**
     * Save a role2.
     *
     * @param role2DTO the entity to save.
     * @return the persisted entity.
     */
    Role2DTO save(Role2DTO role2DTO);

    /**
     * Updates a role2.
     *
     * @param role2DTO the entity to update.
     * @return the persisted entity.
     */
    Role2DTO update(Role2DTO role2DTO);

    /**
     * Partially updates a role2.
     *
     * @param role2DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Role2DTO> partialUpdate(Role2DTO role2DTO);

    /**
     * Get all the role2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Role2DTO> findAll(Pageable pageable);

    /**
     * Get the "id" role2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Role2DTO> findOne(Long id);

    /**
     * Delete the "id" role2.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
