package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.Privilege2Repository;
import com.mycompany.myapp.service.Privilege2Service;
import com.mycompany.myapp.service.dto.Privilege2DTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Privilege2}.
 */
@RestController
@RequestMapping("/api")
public class Privilege2Resource {

    private final Logger log = LoggerFactory.getLogger(Privilege2Resource.class);

    private static final String ENTITY_NAME = "privilege2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Privilege2Service privilege2Service;

    private final Privilege2Repository privilege2Repository;

    public Privilege2Resource(Privilege2Service privilege2Service, Privilege2Repository privilege2Repository) {
        this.privilege2Service = privilege2Service;
        this.privilege2Repository = privilege2Repository;
    }

    /**
     * {@code POST  /privilege-2-s} : Create a new privilege2.
     *
     * @param privilege2DTO the privilege2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new privilege2DTO, or with status {@code 400 (Bad Request)} if the privilege2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/privilege-2-s")
    public ResponseEntity<Privilege2DTO> createPrivilege2(@RequestBody Privilege2DTO privilege2DTO) throws URISyntaxException {
        log.debug("REST request to save Privilege2 : {}", privilege2DTO);
        if (privilege2DTO.getId() != null) {
            throw new BadRequestAlertException("A new privilege2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Privilege2DTO result = privilege2Service.save(privilege2DTO);
        return ResponseEntity
            .created(new URI("/api/privilege-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /privilege-2-s/:id} : Updates an existing privilege2.
     *
     * @param id the id of the privilege2DTO to save.
     * @param privilege2DTO the privilege2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privilege2DTO,
     * or with status {@code 400 (Bad Request)} if the privilege2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the privilege2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/privilege-2-s/{id}")
    public ResponseEntity<Privilege2DTO> updatePrivilege2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Privilege2DTO privilege2DTO
    ) throws URISyntaxException {
        log.debug("REST request to update Privilege2 : {}, {}", id, privilege2DTO);
        if (privilege2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privilege2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privilege2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Privilege2DTO result = privilege2Service.update(privilege2DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privilege2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /privilege-2-s/:id} : Partial updates given fields of an existing privilege2, field will ignore if it is null
     *
     * @param id the id of the privilege2DTO to save.
     * @param privilege2DTO the privilege2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privilege2DTO,
     * or with status {@code 400 (Bad Request)} if the privilege2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the privilege2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the privilege2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/privilege-2-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Privilege2DTO> partialUpdatePrivilege2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Privilege2DTO privilege2DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Privilege2 partially : {}, {}", id, privilege2DTO);
        if (privilege2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privilege2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privilege2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Privilege2DTO> result = privilege2Service.partialUpdate(privilege2DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privilege2DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /privilege-2-s} : get all the privilege2s.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of privilege2s in body.
     */
    @GetMapping("/privilege-2-s")
    public ResponseEntity<List<Privilege2DTO>> getAllPrivilege2s(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Privilege2s");
        Page<Privilege2DTO> page;
        if (eagerload) {
            page = privilege2Service.findAllWithEagerRelationships(pageable);
        } else {
            page = privilege2Service.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /privilege-2-s/:id} : get the "id" privilege2.
     *
     * @param id the id of the privilege2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the privilege2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/privilege-2-s/{id}")
    public ResponseEntity<Privilege2DTO> getPrivilege2(@PathVariable Long id) {
        log.debug("REST request to get Privilege2 : {}", id);
        Optional<Privilege2DTO> privilege2DTO = privilege2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(privilege2DTO);
    }

    /**
     * {@code DELETE  /privilege-2-s/:id} : delete the "id" privilege2.
     *
     * @param id the id of the privilege2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/privilege-2-s/{id}")
    public ResponseEntity<Void> deletePrivilege2(@PathVariable Long id) {
        log.debug("REST request to delete Privilege2 : {}", id);
        privilege2Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
