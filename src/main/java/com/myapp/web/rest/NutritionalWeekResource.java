package com.myapp.web.rest;

import com.myapp.domain.NutritionalWeek;
import com.myapp.repository.NutritionalWeekRepository;
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
 * REST controller for managing {@link com.myapp.domain.NutritionalWeek}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NutritionalWeekResource {

    private final Logger log = LoggerFactory.getLogger(NutritionalWeekResource.class);

    private static final String ENTITY_NAME = "nutritionalWeek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NutritionalWeekRepository nutritionalWeekRepository;

    public NutritionalWeekResource(NutritionalWeekRepository nutritionalWeekRepository) {
        this.nutritionalWeekRepository = nutritionalWeekRepository;
    }

    /**
     * {@code POST  /nutritional-weeks} : Create a new nutritionalWeek.
     *
     * @param nutritionalWeek the nutritionalWeek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nutritionalWeek, or with status {@code 400 (Bad Request)} if the nutritionalWeek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nutritional-weeks")
    public ResponseEntity<NutritionalWeek> createNutritionalWeek(@RequestBody NutritionalWeek nutritionalWeek) throws URISyntaxException {
        log.debug("REST request to save NutritionalWeek : {}", nutritionalWeek);
        if (nutritionalWeek.getId() != null) {
            throw new BadRequestAlertException("A new nutritionalWeek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NutritionalWeek result = nutritionalWeekRepository.save(nutritionalWeek);
        return ResponseEntity
            .created(new URI("/api/nutritional-weeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nutritional-weeks/:id} : Updates an existing nutritionalWeek.
     *
     * @param id the id of the nutritionalWeek to save.
     * @param nutritionalWeek the nutritionalWeek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionalWeek,
     * or with status {@code 400 (Bad Request)} if the nutritionalWeek is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nutritionalWeek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nutritional-weeks/{id}")
    public ResponseEntity<NutritionalWeek> updateNutritionalWeek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NutritionalWeek nutritionalWeek
    ) throws URISyntaxException {
        log.debug("REST request to update NutritionalWeek : {}, {}", id, nutritionalWeek);
        if (nutritionalWeek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionalWeek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionalWeekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NutritionalWeek result = nutritionalWeekRepository.save(nutritionalWeek);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionalWeek.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nutritional-weeks/:id} : Partial updates given fields of an existing nutritionalWeek, field will ignore if it is null
     *
     * @param id the id of the nutritionalWeek to save.
     * @param nutritionalWeek the nutritionalWeek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionalWeek,
     * or with status {@code 400 (Bad Request)} if the nutritionalWeek is not valid,
     * or with status {@code 404 (Not Found)} if the nutritionalWeek is not found,
     * or with status {@code 500 (Internal Server Error)} if the nutritionalWeek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nutritional-weeks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NutritionalWeek> partialUpdateNutritionalWeek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NutritionalWeek nutritionalWeek
    ) throws URISyntaxException {
        log.debug("REST request to partial update NutritionalWeek partially : {}, {}", id, nutritionalWeek);
        if (nutritionalWeek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionalWeek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionalWeekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NutritionalWeek> result = nutritionalWeekRepository
            .findById(nutritionalWeek.getId())
            .map(existingNutritionalWeek -> {
                if (nutritionalWeek.getDateFrom() != null) {
                    existingNutritionalWeek.setDateFrom(nutritionalWeek.getDateFrom());
                }
                if (nutritionalWeek.getDateTo() != null) {
                    existingNutritionalWeek.setDateTo(nutritionalWeek.getDateTo());
                }
                if (nutritionalWeek.getName() != null) {
                    existingNutritionalWeek.setName(nutritionalWeek.getName());
                }

                return existingNutritionalWeek;
            })
            .map(nutritionalWeekRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionalWeek.getId().toString())
        );
    }

    /**
     * {@code GET  /nutritional-weeks} : get all the nutritionalWeeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nutritionalWeeks in body.
     */
    @GetMapping("/nutritional-weeks")
    public List<NutritionalWeek> getAllNutritionalWeeks() {
        log.debug("REST request to get all NutritionalWeeks");
        return nutritionalWeekRepository.findAll();
    }

    /**
     * {@code GET  /nutritional-weeks/:id} : get the "id" nutritionalWeek.
     *
     * @param id the id of the nutritionalWeek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nutritionalWeek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nutritional-weeks/{id}")
    public ResponseEntity<NutritionalWeek> getNutritionalWeek(@PathVariable Long id) {
        log.debug("REST request to get NutritionalWeek : {}", id);
        Optional<NutritionalWeek> nutritionalWeek = nutritionalWeekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nutritionalWeek);
    }

    /**
     * {@code DELETE  /nutritional-weeks/:id} : delete the "id" nutritionalWeek.
     *
     * @param id the id of the nutritionalWeek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nutritional-weeks/{id}")
    public ResponseEntity<Void> deleteNutritionalWeek(@PathVariable Long id) {
        log.debug("REST request to delete NutritionalWeek : {}", id);
        nutritionalWeekRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
