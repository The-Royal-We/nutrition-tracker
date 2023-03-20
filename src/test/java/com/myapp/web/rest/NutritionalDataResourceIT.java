package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.NutritionalData;
import com.myapp.repository.NutritionalDataRepository;
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
 * Integration tests for the {@link NutritionalDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NutritionalDataResourceIT {

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Integer DEFAULT_STEPS = 1;
    private static final Integer UPDATED_STEPS = 2;

    private static final Double DEFAULT_SLEEP = 1D;
    private static final Double UPDATED_SLEEP = 2D;

    private static final Double DEFAULT_WATER = 1D;
    private static final Double UPDATED_WATER = 2D;

    private static final Double DEFAULT_PROTEIN = 1D;
    private static final Double UPDATED_PROTEIN = 2D;

    private static final Double DEFAULT_CARBS = 1D;
    private static final Double UPDATED_CARBS = 2D;

    private static final Double DEFAULT_FAT = 1D;
    private static final Double UPDATED_FAT = 2D;

    private static final Integer DEFAULT_CALORIES = 1;
    private static final Integer UPDATED_CALORIES = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/nutritional-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NutritionalDataRepository nutritionalDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNutritionalDataMockMvc;

    private NutritionalData nutritionalData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutritionalData createEntity(EntityManager em) {
        NutritionalData nutritionalData = new NutritionalData()
            .weight(DEFAULT_WEIGHT)
            .steps(DEFAULT_STEPS)
            .sleep(DEFAULT_SLEEP)
            .water(DEFAULT_WATER)
            .protein(DEFAULT_PROTEIN)
            .carbs(DEFAULT_CARBS)
            .fat(DEFAULT_FAT)
            .calories(DEFAULT_CALORIES)
            .date(DEFAULT_DATE);
        return nutritionalData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutritionalData createUpdatedEntity(EntityManager em) {
        NutritionalData nutritionalData = new NutritionalData()
            .weight(UPDATED_WEIGHT)
            .steps(UPDATED_STEPS)
            .sleep(UPDATED_SLEEP)
            .water(UPDATED_WATER)
            .protein(UPDATED_PROTEIN)
            .carbs(UPDATED_CARBS)
            .fat(UPDATED_FAT)
            .calories(UPDATED_CALORIES)
            .date(UPDATED_DATE);
        return nutritionalData;
    }

    @BeforeEach
    public void initTest() {
        nutritionalData = createEntity(em);
    }

    @Test
    @Transactional
    void createNutritionalData() throws Exception {
        int databaseSizeBeforeCreate = nutritionalDataRepository.findAll().size();
        // Create the NutritionalData
        restNutritionalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isCreated());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeCreate + 1);
        NutritionalData testNutritionalData = nutritionalDataList.get(nutritionalDataList.size() - 1);
        assertThat(testNutritionalData.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testNutritionalData.getSteps()).isEqualTo(DEFAULT_STEPS);
        assertThat(testNutritionalData.getSleep()).isEqualTo(DEFAULT_SLEEP);
        assertThat(testNutritionalData.getWater()).isEqualTo(DEFAULT_WATER);
        assertThat(testNutritionalData.getProtein()).isEqualTo(DEFAULT_PROTEIN);
        assertThat(testNutritionalData.getCarbs()).isEqualTo(DEFAULT_CARBS);
        assertThat(testNutritionalData.getFat()).isEqualTo(DEFAULT_FAT);
        assertThat(testNutritionalData.getCalories()).isEqualTo(DEFAULT_CALORIES);
        assertThat(testNutritionalData.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createNutritionalDataWithExistingId() throws Exception {
        // Create the NutritionalData with an existing ID
        nutritionalData.setId(1L);

        int databaseSizeBeforeCreate = nutritionalDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutritionalDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNutritionalData() throws Exception {
        // Initialize the database
        nutritionalDataRepository.saveAndFlush(nutritionalData);

        // Get all the nutritionalDataList
        restNutritionalDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutritionalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].steps").value(hasItem(DEFAULT_STEPS)))
            .andExpect(jsonPath("$.[*].sleep").value(hasItem(DEFAULT_SLEEP.doubleValue())))
            .andExpect(jsonPath("$.[*].water").value(hasItem(DEFAULT_WATER.doubleValue())))
            .andExpect(jsonPath("$.[*].protein").value(hasItem(DEFAULT_PROTEIN.doubleValue())))
            .andExpect(jsonPath("$.[*].carbs").value(hasItem(DEFAULT_CARBS.doubleValue())))
            .andExpect(jsonPath("$.[*].fat").value(hasItem(DEFAULT_FAT.doubleValue())))
            .andExpect(jsonPath("$.[*].calories").value(hasItem(DEFAULT_CALORIES)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getNutritionalData() throws Exception {
        // Initialize the database
        nutritionalDataRepository.saveAndFlush(nutritionalData);

        // Get the nutritionalData
        restNutritionalDataMockMvc
            .perform(get(ENTITY_API_URL_ID, nutritionalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nutritionalData.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.steps").value(DEFAULT_STEPS))
            .andExpect(jsonPath("$.sleep").value(DEFAULT_SLEEP.doubleValue()))
            .andExpect(jsonPath("$.water").value(DEFAULT_WATER.doubleValue()))
            .andExpect(jsonPath("$.protein").value(DEFAULT_PROTEIN.doubleValue()))
            .andExpect(jsonPath("$.carbs").value(DEFAULT_CARBS.doubleValue()))
            .andExpect(jsonPath("$.fat").value(DEFAULT_FAT.doubleValue()))
            .andExpect(jsonPath("$.calories").value(DEFAULT_CALORIES))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNutritionalData() throws Exception {
        // Get the nutritionalData
        restNutritionalDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNutritionalData() throws Exception {
        // Initialize the database
        nutritionalDataRepository.saveAndFlush(nutritionalData);

        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();

        // Update the nutritionalData
        NutritionalData updatedNutritionalData = nutritionalDataRepository.findById(nutritionalData.getId()).get();
        // Disconnect from session so that the updates on updatedNutritionalData are not directly saved in db
        em.detach(updatedNutritionalData);
        updatedNutritionalData
            .weight(UPDATED_WEIGHT)
            .steps(UPDATED_STEPS)
            .sleep(UPDATED_SLEEP)
            .water(UPDATED_WATER)
            .protein(UPDATED_PROTEIN)
            .carbs(UPDATED_CARBS)
            .fat(UPDATED_FAT)
            .calories(UPDATED_CALORIES)
            .date(UPDATED_DATE);

        restNutritionalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNutritionalData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNutritionalData))
            )
            .andExpect(status().isOk());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
        NutritionalData testNutritionalData = nutritionalDataList.get(nutritionalDataList.size() - 1);
        assertThat(testNutritionalData.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testNutritionalData.getSteps()).isEqualTo(UPDATED_STEPS);
        assertThat(testNutritionalData.getSleep()).isEqualTo(UPDATED_SLEEP);
        assertThat(testNutritionalData.getWater()).isEqualTo(UPDATED_WATER);
        assertThat(testNutritionalData.getProtein()).isEqualTo(UPDATED_PROTEIN);
        assertThat(testNutritionalData.getCarbs()).isEqualTo(UPDATED_CARBS);
        assertThat(testNutritionalData.getFat()).isEqualTo(UPDATED_FAT);
        assertThat(testNutritionalData.getCalories()).isEqualTo(UPDATED_CALORIES);
        assertThat(testNutritionalData.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNutritionalData() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();
        nutritionalData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutritionalData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNutritionalData() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();
        nutritionalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNutritionalData() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();
        nutritionalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNutritionalDataWithPatch() throws Exception {
        // Initialize the database
        nutritionalDataRepository.saveAndFlush(nutritionalData);

        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();

        // Update the nutritionalData using partial update
        NutritionalData partialUpdatedNutritionalData = new NutritionalData();
        partialUpdatedNutritionalData.setId(nutritionalData.getId());

        partialUpdatedNutritionalData.water(UPDATED_WATER).fat(UPDATED_FAT);

        restNutritionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutritionalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutritionalData))
            )
            .andExpect(status().isOk());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
        NutritionalData testNutritionalData = nutritionalDataList.get(nutritionalDataList.size() - 1);
        assertThat(testNutritionalData.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testNutritionalData.getSteps()).isEqualTo(DEFAULT_STEPS);
        assertThat(testNutritionalData.getSleep()).isEqualTo(DEFAULT_SLEEP);
        assertThat(testNutritionalData.getWater()).isEqualTo(UPDATED_WATER);
        assertThat(testNutritionalData.getProtein()).isEqualTo(DEFAULT_PROTEIN);
        assertThat(testNutritionalData.getCarbs()).isEqualTo(DEFAULT_CARBS);
        assertThat(testNutritionalData.getFat()).isEqualTo(UPDATED_FAT);
        assertThat(testNutritionalData.getCalories()).isEqualTo(DEFAULT_CALORIES);
        assertThat(testNutritionalData.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNutritionalDataWithPatch() throws Exception {
        // Initialize the database
        nutritionalDataRepository.saveAndFlush(nutritionalData);

        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();

        // Update the nutritionalData using partial update
        NutritionalData partialUpdatedNutritionalData = new NutritionalData();
        partialUpdatedNutritionalData.setId(nutritionalData.getId());

        partialUpdatedNutritionalData
            .weight(UPDATED_WEIGHT)
            .steps(UPDATED_STEPS)
            .sleep(UPDATED_SLEEP)
            .water(UPDATED_WATER)
            .protein(UPDATED_PROTEIN)
            .carbs(UPDATED_CARBS)
            .fat(UPDATED_FAT)
            .calories(UPDATED_CALORIES)
            .date(UPDATED_DATE);

        restNutritionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutritionalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutritionalData))
            )
            .andExpect(status().isOk());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
        NutritionalData testNutritionalData = nutritionalDataList.get(nutritionalDataList.size() - 1);
        assertThat(testNutritionalData.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testNutritionalData.getSteps()).isEqualTo(UPDATED_STEPS);
        assertThat(testNutritionalData.getSleep()).isEqualTo(UPDATED_SLEEP);
        assertThat(testNutritionalData.getWater()).isEqualTo(UPDATED_WATER);
        assertThat(testNutritionalData.getProtein()).isEqualTo(UPDATED_PROTEIN);
        assertThat(testNutritionalData.getCarbs()).isEqualTo(UPDATED_CARBS);
        assertThat(testNutritionalData.getFat()).isEqualTo(UPDATED_FAT);
        assertThat(testNutritionalData.getCalories()).isEqualTo(UPDATED_CALORIES);
        assertThat(testNutritionalData.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNutritionalData() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();
        nutritionalData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nutritionalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNutritionalData() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();
        nutritionalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNutritionalData() throws Exception {
        int databaseSizeBeforeUpdate = nutritionalDataRepository.findAll().size();
        nutritionalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionalData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NutritionalData in the database
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNutritionalData() throws Exception {
        // Initialize the database
        nutritionalDataRepository.saveAndFlush(nutritionalData);

        int databaseSizeBeforeDelete = nutritionalDataRepository.findAll().size();

        // Delete the nutritionalData
        restNutritionalDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, nutritionalData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NutritionalData> nutritionalDataList = nutritionalDataRepository.findAll();
        assertThat(nutritionalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
