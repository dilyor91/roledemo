package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.User2;
import com.mycompany.myapp.repository.User2Repository;
import com.mycompany.myapp.service.User2Service;
import com.mycompany.myapp.service.dto.User2DTO;
import com.mycompany.myapp.service.mapper.User2Mapper;
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
 * Integration tests for the {@link User2Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class User2ResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private User2Repository user2Repository;

    @Mock
    private User2Repository user2RepositoryMock;

    @Autowired
    private User2Mapper user2Mapper;

    @Mock
    private User2Service user2ServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUser2MockMvc;

    private User2 user2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User2 createEntity(EntityManager em) {
        User2 user2 = new User2().name(DEFAULT_NAME).position(DEFAULT_POSITION);
        return user2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User2 createUpdatedEntity(EntityManager em) {
        User2 user2 = new User2().name(UPDATED_NAME).position(UPDATED_POSITION);
        return user2;
    }

    @BeforeEach
    public void initTest() {
        user2 = createEntity(em);
    }

    @Test
    @Transactional
    void createUser2() throws Exception {
        int databaseSizeBeforeCreate = user2Repository.findAll().size();
        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);
        restUser2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isCreated());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeCreate + 1);
        User2 testUser2 = user2List.get(user2List.size() - 1);
        assertThat(testUser2.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUser2.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void createUser2WithExistingId() throws Exception {
        // Create the User2 with an existing ID
        user2.setId(1L);
        User2DTO user2DTO = user2Mapper.toDto(user2);

        int databaseSizeBeforeCreate = user2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUser2s() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        // Get all the user2List
        restUser2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUser2sWithEagerRelationshipsIsEnabled() throws Exception {
        when(user2ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUser2MockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(user2ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUser2sWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(user2ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUser2MockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(user2RepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUser2() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        // Get the user2
        restUser2MockMvc
            .perform(get(ENTITY_API_URL_ID, user2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(user2.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingUser2() throws Exception {
        // Get the user2
        restUser2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUser2() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        int databaseSizeBeforeUpdate = user2Repository.findAll().size();

        // Update the user2
        User2 updatedUser2 = user2Repository.findById(user2.getId()).get();
        // Disconnect from session so that the updates on updatedUser2 are not directly saved in db
        em.detach(updatedUser2);
        updatedUser2.name(UPDATED_NAME).position(UPDATED_POSITION);
        User2DTO user2DTO = user2Mapper.toDto(updatedUser2);

        restUser2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, user2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user2DTO))
            )
            .andExpect(status().isOk());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
        User2 testUser2 = user2List.get(user2List.size() - 1);
        assertThat(testUser2.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUser2.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();
        user2.setId(count.incrementAndGet());

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUser2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, user2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();
        user2.setId(count.incrementAndGet());

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();
        user2.setId(count.incrementAndGet());

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser2MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUser2WithPatch() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        int databaseSizeBeforeUpdate = user2Repository.findAll().size();

        // Update the user2 using partial update
        User2 partialUpdatedUser2 = new User2();
        partialUpdatedUser2.setId(user2.getId());

        partialUpdatedUser2.position(UPDATED_POSITION);

        restUser2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUser2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUser2))
            )
            .andExpect(status().isOk());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
        User2 testUser2 = user2List.get(user2List.size() - 1);
        assertThat(testUser2.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUser2.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateUser2WithPatch() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        int databaseSizeBeforeUpdate = user2Repository.findAll().size();

        // Update the user2 using partial update
        User2 partialUpdatedUser2 = new User2();
        partialUpdatedUser2.setId(user2.getId());

        partialUpdatedUser2.name(UPDATED_NAME).position(UPDATED_POSITION);

        restUser2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUser2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUser2))
            )
            .andExpect(status().isOk());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
        User2 testUser2 = user2List.get(user2List.size() - 1);
        assertThat(testUser2.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUser2.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();
        user2.setId(count.incrementAndGet());

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUser2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, user2DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(user2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();
        user2.setId(count.incrementAndGet());

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(user2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();
        user2.setId(count.incrementAndGet());

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUser2MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUser2() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        int databaseSizeBeforeDelete = user2Repository.findAll().size();

        // Delete the user2
        restUser2MockMvc
            .perform(delete(ENTITY_API_URL_ID, user2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
