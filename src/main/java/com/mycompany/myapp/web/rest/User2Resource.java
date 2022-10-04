package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.User2Repository;
import com.mycompany.myapp.service.User2Service;
import com.mycompany.myapp.service.dto.User2DTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.User2}.
 */
@RestController
@RequestMapping("/api")
public class User2Resource {

    private final Logger log = LoggerFactory.getLogger(User2Resource.class);

    private static final String ENTITY_NAME = "user2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final User2Service user2Service;

    private final User2Repository user2Repository;

    public User2Resource(User2Service user2Service, User2Repository user2Repository) {
        this.user2Service = user2Service;
        this.user2Repository = user2Repository;
    }

    /**
     * {@code POST  /user-2-s} : Create a new user2.
     *
     * @param user2DTO the user2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user2DTO, or with status {@code 400 (Bad Request)} if the user2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-2-s")
    public ResponseEntity<User2DTO> createUser2(@RequestBody User2DTO user2DTO) throws URISyntaxException {
        log.debug("REST request to save User2 : {}", user2DTO);
        if (user2DTO.getId() != null) {
            throw new BadRequestAlertException("A new user2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User2DTO result = user2Service.save(user2DTO);
        return ResponseEntity
            .created(new URI("/api/user-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-2-s/:id} : Updates an existing user2.
     *
     * @param id the id of the user2DTO to save.
     * @param user2DTO the user2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user2DTO,
     * or with status {@code 400 (Bad Request)} if the user2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the user2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-2-s/{id}")
    public ResponseEntity<User2DTO> updateUser2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody User2DTO user2DTO
    ) throws URISyntaxException {
        log.debug("REST request to update User2 : {}, {}", id, user2DTO);
        if (user2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, user2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!user2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        User2DTO result = user2Service.update(user2DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, user2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-2-s/:id} : Partial updates given fields of an existing user2, field will ignore if it is null
     *
     * @param id the id of the user2DTO to save.
     * @param user2DTO the user2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user2DTO,
     * or with status {@code 400 (Bad Request)} if the user2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the user2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the user2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-2-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<User2DTO> partialUpdateUser2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody User2DTO user2DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update User2 partially : {}, {}", id, user2DTO);
        if (user2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, user2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!user2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<User2DTO> result = user2Service.partialUpdate(user2DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, user2DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-2-s} : get all the user2s.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of user2s in body.
     */
    @GetMapping("/user-2-s")
    public ResponseEntity<List<User2DTO>> getAllUser2s(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of User2s");
        Page<User2DTO> page;
        if (eagerload) {
            page = user2Service.findAllWithEagerRelationships(pageable);
        } else {
            page = user2Service.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-2-s/:id} : get the "id" user2.
     *
     * @param id the id of the user2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the user2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-2-s/{id}")
    public ResponseEntity<User2DTO> getUser2(@PathVariable Long id) {
        log.debug("REST request to get User2 : {}", id);
        Optional<User2DTO> user2DTO = user2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(user2DTO);
    }

    /**
     * {@code DELETE  /user-2-s/:id} : delete the "id" user2.
     *
     * @param id the id of the user2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-2-s/{id}")
    public ResponseEntity<Void> deleteUser2(@PathVariable Long id) {
        log.debug("REST request to delete User2 : {}", id);
        user2Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
