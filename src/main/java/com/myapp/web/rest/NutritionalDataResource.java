package com.myapp.web.rest;

import com.myapp.domain.NutritionalData;
import com.myapp.repository.NutritionalDataRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.NutritionalData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NutritionalDataResource {

    private final Logger log = LoggerFactory.getLogger(NutritionalDataResource.class);

    private static final String ENTITY_NAME = "nutritionalData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NutritionalDataRepository nutritionalDataRepository;

    public NutritionalDataResource(NutritionalDataRepository nutritionalDataRepository) {
        this.nutritionalDataRepository = nutritionalDataRepository;
    }

    /**
     * {@code POST  /nutritional-data} : Create a new nutritionalData.
     *
     * @param nutritionalData the nutritionalData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nutritionalData, or with status {@code 400 (Bad Request)} if the nutritionalData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nutritional-data")
    public ResponseEntity<NutritionalData> createNutritionalData(@RequestBody NutritionalData nutritionalData) throws URISyntaxException {
        log.debug("REST request to save NutritionalData : {}", nutritionalData);
        if (nutritionalData.getId() != null) {
            throw new BadRequestAlertException("A new nutritionalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NutritionalData result = nutritionalDataRepository.save(nutritionalData);
        return ResponseEntity
            .created(new URI("/api/nutritional-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nutritional-data/:id} : Updates an existing nutritionalData.
     *
     * @param id the id of the nutritionalData to save.
     * @param nutritionalData the nutritionalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionalData,
     * or with status {@code 400 (Bad Request)} if the nutritionalData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nutritionalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nutritional-data/{id}")
    public ResponseEntity<NutritionalData> updateNutritionalData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NutritionalData nutritionalData
    ) throws URISyntaxException {
        log.debug("REST request to update NutritionalData : {}, {}", id, nutritionalData);
        if (nutritionalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionalData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NutritionalData result = nutritionalDataRepository.save(nutritionalData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionalData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nutritional-data/:id} : Partial updates given fields of an existing nutritionalData, field will ignore if it is null
     *
     * @param id the id of the nutritionalData to save.
     * @param nutritionalData the nutritionalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionalData,
     * or with status {@code 400 (Bad Request)} if the nutritionalData is not valid,
     * or with status {@code 404 (Not Found)} if the nutritionalData is not found,
     * or with status {@code 500 (Internal Server Error)} if the nutritionalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nutritional-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NutritionalData> partialUpdateNutritionalData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NutritionalData nutritionalData
    ) throws URISyntaxException {
        log.debug("REST request to partial update NutritionalData partially : {}, {}", id, nutritionalData);
        if (nutritionalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionalData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NutritionalData> result = nutritionalDataRepository
            .findById(nutritionalData.getId())
            .map(existingNutritionalData -> {
                if (nutritionalData.getWeight() != null) {
                    existingNutritionalData.setWeight(nutritionalData.getWeight());
                }
                if (nutritionalData.getSteps() != null) {
                    existingNutritionalData.setSteps(nutritionalData.getSteps());
                }
                if (nutritionalData.getSleep() != null) {
                    existingNutritionalData.setSleep(nutritionalData.getSleep());
                }
                if (nutritionalData.getWater() != null) {
                    existingNutritionalData.setWater(nutritionalData.getWater());
                }
                if (nutritionalData.getProtein() != null) {
                    existingNutritionalData.setProtein(nutritionalData.getProtein());
                }
                if (nutritionalData.getCarbs() != null) {
                    existingNutritionalData.setCarbs(nutritionalData.getCarbs());
                }
                if (nutritionalData.getFat() != null) {
                    existingNutritionalData.setFat(nutritionalData.getFat());
                }
                if (nutritionalData.getCalories() != null) {
                    existingNutritionalData.setCalories(nutritionalData.getCalories());
                }
                if (nutritionalData.getDate() != null) {
                    existingNutritionalData.setDate(nutritionalData.getDate());
                }

                return existingNutritionalData;
            })
            .map(nutritionalDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionalData.getId().toString())
        );
    }

    /**
     * {@code GET  /nutritional-data} : get all the nutritionalData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nutritionalData in body.
     */
    @GetMapping("/nutritional-data")
    public List<NutritionalData> getAllNutritionalData() {
        log.debug("REST request to get all NutritionalData");
        return nutritionalDataRepository.findAll();
    }

    /**
     * {@code GET  /nutritional-data/:id} : get the "id" nutritionalData.
     *
     * @param id the id of the nutritionalData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nutritionalData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nutritional-data/{id}")
    public ResponseEntity<NutritionalData> getNutritionalData(@PathVariable Long id) {
        log.debug("REST request to get NutritionalData : {}", id);
        Optional<NutritionalData> nutritionalData = nutritionalDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nutritionalData);
    }

    /**
     * {@code DELETE  /nutritional-data/:id} : delete the "id" nutritionalData.
     *
     * @param id the id of the nutritionalData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nutritional-data/{id}")
    public ResponseEntity<Void> deleteNutritionalData(@PathVariable Long id) {
        log.debug("REST request to delete NutritionalData : {}", id);
        nutritionalDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
