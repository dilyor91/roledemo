package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.Role2Repository;
import com.mycompany.myapp.service.Role2Service;
import com.mycompany.myapp.service.dto.Role2DTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Role2}.
 */
@RestController
@RequestMapping("/api")
public class Role2Resource {

    private final Logger log = LoggerFactory.getLogger(Role2Resource.class);

    private static final String ENTITY_NAME = "role2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Role2Service role2Service;

    private final Role2Repository role2Repository;

    public Role2Resource(Role2Service role2Service, Role2Repository role2Repository) {
        this.role2Service = role2Service;
        this.role2Repository = role2Repository;
    }

    /**
     * {@code POST  /role-2-s} : Create a new role2.
     *
     * @param role2DTO the role2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new role2DTO, or with status {@code 400 (Bad Request)} if the role2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-2-s")
    public ResponseEntity<Role2DTO> createRole2(@RequestBody Role2DTO role2DTO) throws URISyntaxException {
        log.debug("REST request to save Role2 : {}", role2DTO);
        if (role2DTO.getId() != null) {
            throw new BadRequestAlertException("A new role2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Role2DTO result = role2Service.save(role2DTO);
        return ResponseEntity
            .created(new URI("/api/role-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-2-s/:id} : Updates an existing role2.
     *
     * @param id the id of the role2DTO to save.
     * @param role2DTO the role2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated role2DTO,
     * or with status {@code 400 (Bad Request)} if the role2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the role2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-2-s/{id}")
    public ResponseEntity<Role2DTO> updateRole2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Role2DTO role2DTO
    ) throws URISyntaxException {
        log.debug("REST request to update Role2 : {}, {}", id, role2DTO);
        if (role2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, role2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!role2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Role2DTO result = role2Service.update(role2DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, role2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-2-s/:id} : Partial updates given fields of an existing role2, field will ignore if it is null
     *
     * @param id the id of the role2DTO to save.
     * @param role2DTO the role2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated role2DTO,
     * or with status {@code 400 (Bad Request)} if the role2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the role2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the role2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-2-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Role2DTO> partialUpdateRole2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Role2DTO role2DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Role2 partially : {}, {}", id, role2DTO);
        if (role2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, role2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!role2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Role2DTO> result = role2Service.partialUpdate(role2DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, role2DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /role-2-s} : get all the role2s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of role2s in body.
     */
    @GetMapping("/role-2-s")
    public ResponseEntity<List<Role2DTO>> getAllRole2s(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Role2s");
        Page<Role2DTO> page = role2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-2-s/:id} : get the "id" role2.
     *
     * @param id the id of the role2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the role2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-2-s/{id}")
    public ResponseEntity<Role2DTO> getRole2(@PathVariable Long id) {
        log.debug("REST request to get Role2 : {}", id);
        Optional<Role2DTO> role2DTO = role2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(role2DTO);
    }

    /**
     * {@code DELETE  /role-2-s/:id} : delete the "id" role2.
     *
     * @param id the id of the role2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-2-s/{id}")
    public ResponseEntity<Void> deleteRole2(@PathVariable Long id) {
        log.debug("REST request to delete Role2 : {}", id);
        role2Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
