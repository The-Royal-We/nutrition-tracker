package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.NutritionalWeek;
import com.myapp.repository.NutritionalWeekRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link NutritionalWeekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NutritionalWeekResourceIT {

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nutritional-weeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NutritionalWeekRepository nutritionalWeekRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNutritionalWeekMockMvc;

    private NutritionalWeek nutritionalWeek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutritionalWeek createEntity(EntityManager em) {
        NutritionalWeek nutritionalWeek = new NutritionalWeek().dateFrom(DEFAULT_DATE_FROM).dateTo(DEFAULT_DATE_TO).name(DEFAULT_NAME);
        return nutritionalWeek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutritionalWeek createUpdatedEntity(EntityManager em) {
        NutritionalWeek nutritionalWeek = new NutritionalWeek().dateFrom(UPDATED_DATE_FROM).dateTo(UPDATED_DATE_TO).name(UPDATED_NAME);
        return nutritionalWeek;
    }

    @BeforeEach
    public void initTest() {
        nutritionalWeek = createEntity(em);
    }

    @Test
    @Transactional
    void createNutritionalWeek() throws Exception {
        int databaseSizeBeforeCreate = nutritionalWeekRepository.findAll().size();
        // Create the NutritionalWeek
        restNutritionalWeekMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isCreated());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeCreate + 1);
        NutritionalWeek testNutritionalWeek = nutritionalWeekList.get(nutritionalWeekList.size() - 1);
        assertThat(testNutritionalWeek.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testNutritionalWeek.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testNutritionalWeek.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createNutritionalWeekWithExistingId() throws Exception {
        // Create the NutritionalWeek with an existing ID
        nutritionalWeek.setId(1L);

        int databaseSizeBeforeCreate = nutritionalWeekRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutritionalWeekMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNutritionalWeeks() throws Exception {
        // Initialize the database
        nutritionalWeekRepository.saveAndFlush(nutritionalWeek);

        // Get all the nutritionalWeekList
        restNutritionalWeekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutritionalWeek.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getNutritionalWeek() throws Exception {
        // Initialize the database
        nutritionalWeekRepository.saveAndFlush(nutritionalWeek);

        // Get the nutritionalWeek
        restNutritionalWeekMockMvc
            .perform(get(ENTITY_API_URL_ID, nutritionalWeek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nutritionalWeek.getId().intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingNutritionalWeek() throws Exception {
        // Get the nutritionalWeek
        restNutritionalWeekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNutritionalWeek() throws Exception {
        // Initialize the database
        nutritionalWeekRepository.saveAndFlush(nutritionalWeek);

        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();

        // Update the nutritionalWeek
        NutritionalWeek updatedNutritionalWeek = nutritionalWeekRepository.findById(nutritionalWeek.getId()).get();
        // Disconnect from session so that the updates on updatedNutritionalWeek are not directly saved in db
        em.detach(updatedNutritionalWeek);
        updatedNutritionalWeek.dateFrom(UPDATED_DATE_FROM).dateTo(UPDATED_DATE_TO).name(UPDATED_NAME);

        restNutritionalWeekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNutritionalWeek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNutritionalWeek))
            )
            .andExpect(status().isOk());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
        NutritionalWeek testNutritionalWeek = nutritionalWeekList.get(nutritionalWeekList.size() - 1);
        assertThat(testNutritionalWeek.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testNutritionalWeek.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testNutritionalWeek.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingNutritionalWeek() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();
        nutritionalWeek.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionalWeekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutritionalWeek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNutritionalWeek() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();
        nutritionalWeek.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalWeekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNutritionalWeek() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();
        nutritionalWeek.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalWeekMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNutritionalWeekWithPatch() throws Exception {
        // Initialize the database
        nutritionalWeekRepository.saveAndFlush(nutritionalWeek);

        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();

        // Update the nutritionalWeek using partial update
        NutritionalWeek partialUpdatedNutritionalWeek = new NutritionalWeek();
        partialUpdatedNutritionalWeek.setId(nutritionalWeek.getId());

        partialUpdatedNutritionalWeek.dateTo(UPDATED_DATE_TO).name(UPDATED_NAME);

        restNutritionalWeekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutritionalWeek.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutritionalWeek))
            )
            .andExpect(status().isOk());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
        NutritionalWeek testNutritionalWeek = nutritionalWeekList.get(nutritionalWeekList.size() - 1);
        assertThat(testNutritionalWeek.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testNutritionalWeek.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testNutritionalWeek.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateNutritionalWeekWithPatch() throws Exception {
        // Initialize the database
        nutritionalWeekRepository.saveAndFlush(nutritionalWeek);

        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();

        // Update the nutritionalWeek using partial update
        NutritionalWeek partialUpdatedNutritionalWeek = new NutritionalWeek();
        partialUpdatedNutritionalWeek.setId(nutritionalWeek.getId());

        partialUpdatedNutritionalWeek.dateFrom(UPDATED_DATE_FROM).dateTo(UPDATED_DATE_TO).name(UPDATED_NAME);

        restNutritionalWeekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutritionalWeek.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutritionalWeek))
            )
            .andExpect(status().isOk());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
        NutritionalWeek testNutritionalWeek = nutritionalWeekList.get(nutritionalWeekList.size() - 1);
        assertThat(testNutritionalWeek.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testNutritionalWeek.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testNutritionalWeek.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingNutritionalWeek() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();
        nutritionalWeek.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionalWeekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nutritionalWeek.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNutritionalWeek() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();
        nutritionalWeek.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalWeekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNutritionalWeek() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalWeekRepository.findAll().size();
        nutritionalWeek.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalWeekMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalWeek))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NutritionalWeek in the database
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNutritionalWeek() throws Exception {
        // Initialize the database
        nutritionalWeekRepository.saveAndFlush(nutritionalWeek);

        int databaseSizeBeforeDelete = nutritionalWeekRepository.findAll().size();

        // Delete the nutritionalWeek
        restNutritionalWeekMockMvc
            .perform(delete(ENTITY_API_URL_ID, nutritionalWeek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NutritionalWeek> nutritionalWeekList = nutritionalWeekRepository.findAll();
        assertThat(nutritionalWeekList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
