package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Role2;
import com.mycompany.myapp.repository.Role2Repository;
import com.mycompany.myapp.service.dto.Role2DTO;
import com.mycompany.myapp.service.mapper.Role2Mapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link Role2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Role2ResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/role-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Role2Repository role2Repository;

    @Autowired
    private Role2Mapper role2Mapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRole2MockMvc;

    private Role2 role2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role2 createEntity(EntityManager em) {
        Role2 role2 = new Role2().name(DEFAULT_NAME);
        return role2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role2 createUpdatedEntity(EntityManager em) {
        Role2 role2 = new Role2().name(UPDATED_NAME);
        return role2;
    }

    @BeforeEach
    public void initTest() {
        role2 = createEntity(em);
    }

    @Test
    @Transactional
    void createRole2() throws Exception {
        int databaseSizeBeforeCreate = role2Repository.findAll().size();
        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);
        restRole2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(role2DTO)))
            .andExpect(status().isCreated());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeCreate + 1);
        Role2 testRole2 = role2List.get(role2List.size() - 1);
        assertThat(testRole2.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRole2WithExistingId() throws Exception {
        // Create the Role2 with an existing ID
        role2.setId(1L);
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        int databaseSizeBeforeCreate = role2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRole2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(role2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRole2s() throws Exception {
        // Initialize the database
        role2Repository.saveAndFlush(role2);

        // Get all the role2List
        restRole2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRole2() throws Exception {
        // Initialize the database
        role2Repository.saveAndFlush(role2);

        // Get the role2
        restRole2MockMvc
            .perform(get(ENTITY_API_URL_ID, role2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(role2.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRole2() throws Exception {
        // Get the role2
        restRole2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRole2() throws Exception {
        // Initialize the database
        role2Repository.saveAndFlush(role2);

        int databaseSizeBeforeUpdate = role2Repository.findAll().size();

        // Update the role2
        Role2 updatedRole2 = role2Repository.findById(role2.getId()).get();
        // Disconnect from session so that the updates on updatedRole2 are not directly saved in db
        em.detach(updatedRole2);
        updatedRole2.name(UPDATED_NAME);
        Role2DTO role2DTO = role2Mapper.toDto(updatedRole2);

        restRole2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, role2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(role2DTO))
            )
            .andExpect(status().isOk());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
        Role2 testRole2 = role2List.get(role2List.size() - 1);
        assertThat(testRole2.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRole2() throws Exception {
        int databaseSizeBeforeUpdate = role2Repository.findAll().size();
        role2.setId(count.incrementAndGet());

        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRole2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, role2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(role2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRole2() throws Exception {
        int databaseSizeBeforeUpdate = role2Repository.findAll().size();
        role2.setId(count.incrementAndGet());

        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRole2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(role2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRole2() throws Exception {
        int databaseSizeBeforeUpdate = role2Repository.findAll().size();
        role2.setId(count.incrementAndGet());

        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRole2MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(role2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRole2WithPatch() throws Exception {
        // Initialize the database
        role2Repository.saveAndFlush(role2);

        int databaseSizeBeforeUpdate = role2Repository.findAll().size();

        // Update the role2 using partial update
        Role2 partialUpdatedRole2 = new Role2();
        partialUpdatedRole2.setId(role2.getId());

        partialUpdatedRole2.name(UPDATED_NAME);

        restRole2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRole2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRole2))
            )
            .andExpect(status().isOk());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
        Role2 testRole2 = role2List.get(role2List.size() - 1);
        assertThat(testRole2.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRole2WithPatch() throws Exception {
        // Initialize the database
        role2Repository.saveAndFlush(role2);

        int databaseSizeBeforeUpdate = role2Repository.findAll().size();

        // Update the role2 using partial update
        Role2 partialUpdatedRole2 = new Role2();
        partialUpdatedRole2.setId(role2.getId());

        partialUpdatedRole2.name(UPDATED_NAME);

        restRole2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRole2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRole2))
            )
            .andExpect(status().isOk());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
        Role2 testRole2 = role2List.get(role2List.size() - 1);
        assertThat(testRole2.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRole2() throws Exception {
        int databaseSizeBeforeUpdate = role2Repository.findAll().size();
        role2.setId(count.incrementAndGet());

        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRole2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, role2DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(role2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRole2() throws Exception {
        int databaseSizeBeforeUpdate = role2Repository.findAll().size();
        role2.setId(count.incrementAndGet());

        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRole2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(role2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRole2() throws Exception {
        int databaseSizeBeforeUpdate = role2Repository.findAll().size();
        role2.setId(count.incrementAndGet());

        // Create the Role2
        Role2DTO role2DTO = role2Mapper.toDto(role2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRole2MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(role2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Role2 in the database
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRole2() throws Exception {
        // Initialize the database
        role2Repository.saveAndFlush(role2);

        int databaseSizeBeforeDelete = role2Repository.findAll().size();

        // Delete the role2
        restRole2MockMvc
            .perform(delete(ENTITY_API_URL_ID, role2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Role2> role2List = role2Repository.findAll();
        assertThat(role2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
