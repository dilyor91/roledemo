package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Privilege2;
import com.mycompany.myapp.repository.Privilege2Repository;
import com.mycompany.myapp.service.Privilege2Service;
import com.mycompany.myapp.service.dto.Privilege2DTO;
import com.mycompany.myapp.service.mapper.Privilege2Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link Privilege2Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class Privilege2ResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/privilege-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Privilege2Repository privilege2Repository;

    @Mock
    private Privilege2Repository privilege2RepositoryMock;

    @Autowired
    private Privilege2Mapper privilege2Mapper;

    @Mock
    private Privilege2Service privilege2ServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrivilege2MockMvc;

    private Privilege2 privilege2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Privilege2 createEntity(EntityManager em) {
        Privilege2 privilege2 = new Privilege2().name(DEFAULT_NAME);
        return privilege2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Privilege2 createUpdatedEntity(EntityManager em) {
        Privilege2 privilege2 = new Privilege2().name(UPDATED_NAME);
        return privilege2;
    }

    @BeforeEach
    public void initTest() {
        privilege2 = createEntity(em);
    }

    @Test
    @Transactional
    void createPrivilege2() throws Exception {
        int databaseSizeBeforeCreate = privilege2Repository.findAll().size();
        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);
        restPrivilege2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privilege2DTO)))
            .andExpect(status().isCreated());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeCreate + 1);
        Privilege2 testPrivilege2 = privilege2List.get(privilege2List.size() - 1);
        assertThat(testPrivilege2.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPrivilege2WithExistingId() throws Exception {
        // Create the Privilege2 with an existing ID
        privilege2.setId(1L);
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        int databaseSizeBeforeCreate = privilege2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivilege2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privilege2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrivilege2s() throws Exception {
        // Initialize the database
        privilege2Repository.saveAndFlush(privilege2);

        // Get all the privilege2List
        restPrivilege2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privilege2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrivilege2sWithEagerRelationshipsIsEnabled() throws Exception {
        when(privilege2ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrivilege2MockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(privilege2ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrivilege2sWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(privilege2ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrivilege2MockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(privilege2RepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPrivilege2() throws Exception {
        // Initialize the database
        privilege2Repository.saveAndFlush(privilege2);

        // Get the privilege2
        restPrivilege2MockMvc
            .perform(get(ENTITY_API_URL_ID, privilege2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(privilege2.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPrivilege2() throws Exception {
        // Get the privilege2
        restPrivilege2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrivilege2() throws Exception {
        // Initialize the database
        privilege2Repository.saveAndFlush(privilege2);

        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();

        // Update the privilege2
        Privilege2 updatedPrivilege2 = privilege2Repository.findById(privilege2.getId()).get();
        // Disconnect from session so that the updates on updatedPrivilege2 are not directly saved in db
        em.detach(updatedPrivilege2);
        updatedPrivilege2.name(UPDATED_NAME);
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(updatedPrivilege2);

        restPrivilege2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, privilege2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privilege2DTO))
            )
            .andExpect(status().isOk());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
        Privilege2 testPrivilege2 = privilege2List.get(privilege2List.size() - 1);
        assertThat(testPrivilege2.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPrivilege2() throws Exception {
        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();
        privilege2.setId(count.incrementAndGet());

        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivilege2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, privilege2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privilege2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrivilege2() throws Exception {
        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();
        privilege2.setId(count.incrementAndGet());

        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilege2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privilege2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrivilege2() throws Exception {
        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();
        privilege2.setId(count.incrementAndGet());

        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilege2MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privilege2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrivilege2WithPatch() throws Exception {
        // Initialize the database
        privilege2Repository.saveAndFlush(privilege2);

        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();

        // Update the privilege2 using partial update
        Privilege2 partialUpdatedPrivilege2 = new Privilege2();
        partialUpdatedPrivilege2.setId(privilege2.getId());

        restPrivilege2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivilege2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivilege2))
            )
            .andExpect(status().isOk());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
        Privilege2 testPrivilege2 = privilege2List.get(privilege2List.size() - 1);
        assertThat(testPrivilege2.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePrivilege2WithPatch() throws Exception {
        // Initialize the database
        privilege2Repository.saveAndFlush(privilege2);

        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();

        // Update the privilege2 using partial update
        Privilege2 partialUpdatedPrivilege2 = new Privilege2();
        partialUpdatedPrivilege2.setId(privilege2.getId());

        partialUpdatedPrivilege2.name(UPDATED_NAME);

        restPrivilege2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivilege2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivilege2))
            )
            .andExpect(status().isOk());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
        Privilege2 testPrivilege2 = privilege2List.get(privilege2List.size() - 1);
        assertThat(testPrivilege2.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPrivilege2() throws Exception {
        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();
        privilege2.setId(count.incrementAndGet());

        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivilege2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, privilege2DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privilege2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrivilege2() throws Exception {
        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();
        privilege2.setId(count.incrementAndGet());

        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilege2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privilege2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrivilege2() throws Exception {
        int databaseSizeBeforeUpdate = privilege2Repository.findAll().size();
        privilege2.setId(count.incrementAndGet());

        // Create the Privilege2
        Privilege2DTO privilege2DTO = privilege2Mapper.toDto(privilege2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilege2MockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(privilege2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Privilege2 in the database
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrivilege2() throws Exception {
        // Initialize the database
        privilege2Repository.saveAndFlush(privilege2);

        int databaseSizeBeforeDelete = privilege2Repository.findAll().size();

        // Delete the privilege2
        restPrivilege2MockMvc
            .perform(delete(ENTITY_API_URL_ID, privilege2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Privilege2> privilege2List = privilege2Repository.findAll();
        assertThat(privilege2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
