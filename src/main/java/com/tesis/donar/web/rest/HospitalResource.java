package com.tesis.donar.web.rest;

import com.tesis.donar.domain.Hospital;
import com.tesis.donar.repository.HospitalRepository;
import com.tesis.donar.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tesis.donar.domain.Hospital}.
 */
@RestController
@RequestMapping("/api")
public class HospitalResource {

    private final Logger log = LoggerFactory.getLogger(HospitalResource.class);

    private static final String ENTITY_NAME = "hospital";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalRepository hospitalRepository;

    public HospitalResource(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * {@code POST  /hospitals} : Create a new hospital.
     *
     * @param hospital the hospital to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospital, or with status {@code 400 (Bad Request)} if the hospital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospitals")
    public ResponseEntity<Hospital> createHospital(@Valid @RequestBody Hospital hospital) throws URISyntaxException {
        log.debug("REST request to save Hospital : {}", hospital);
        if (hospital.getId() != null) {
            throw new BadRequestAlertException("A new hospital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hospital result = hospitalRepository.save(hospital);
        return ResponseEntity.created(new URI("/api/hospitals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospitals} : Updates an existing hospital.
     *
     * @param hospital the hospital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospital,
     * or with status {@code 400 (Bad Request)} if the hospital is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospitals")
    public ResponseEntity<Hospital> updateHospital(@Valid @RequestBody Hospital hospital) throws URISyntaxException {
        log.debug("REST request to update Hospital : {}", hospital);
        if (hospital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hospital result = hospitalRepository.save(hospital);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospital.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hospitals} : get all the hospitals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitals in body.
     */
    @GetMapping("/hospitals")
    public List<Hospital> getAllHospitals() {
        log.debug("REST request to get all Hospitals");
        return hospitalRepository.findAll();
    }

    /**
     * {@code GET  /hospitals/:id} : get the "id" hospital.
     *
     * @param id the id of the hospital to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospital, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospitals/{id}")
    public ResponseEntity<Hospital> getHospital(@PathVariable Long id) {
        log.debug("REST request to get Hospital : {}", id);
        Optional<Hospital> hospital = hospitalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hospital);
    }

    /**
     * {@code DELETE  /hospitals/:id} : delete the "id" hospital.
     *
     * @param id the id of the hospital to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospitals/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        log.debug("REST request to delete Hospital : {}", id);
        hospitalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
