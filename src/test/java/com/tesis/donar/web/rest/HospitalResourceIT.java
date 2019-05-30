package com.tesis.donar.web.rest;

import com.tesis.donar.DonarApp;
import com.tesis.donar.domain.Hospital;
import com.tesis.donar.repository.HospitalRepository;
import com.tesis.donar.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tesis.donar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link HospitalResource} REST controller.
 */
@SpringBootTest(classes = DonarApp.class)
public class HospitalResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHospitalMockMvc;

    private Hospital hospital;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HospitalResource hospitalResource = new HospitalResource(hospitalRepository);
        this.restHospitalMockMvc = MockMvcBuilders.standaloneSetup(hospitalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createEntity(EntityManager em) {
        Hospital hospital = new Hospital()
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO);
        return hospital;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createUpdatedEntity(EntityManager em) {
        Hospital hospital = new Hospital()
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO);
        return hospital;
    }

    @BeforeEach
    public void initTest() {
        hospital = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospital() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();

        // Create the Hospital
        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospital)))
            .andExpect(status().isCreated());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate + 1);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testHospital.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testHospital.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testHospital.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createHospitalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();

        // Create the Hospital with an existing ID
        hospital.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospital)))
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setNombre(null);

        // Create the Hospital, which fails.

        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospital)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setDireccion(null);

        // Create the Hospital, which fails.

        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospital)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setTelefono(null);

        // Create the Hospital, which fails.

        restHospitalMockMvc.perform(post("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospital)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHospitals() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList
        restHospitalMockMvc.perform(get("/api/hospitals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }
    
    @Test
    @Transactional
    public void getHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get the hospital
        restHospitalMockMvc.perform(get("/api/hospitals/{id}", hospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hospital.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHospital() throws Exception {
        // Get the hospital
        restHospitalMockMvc.perform(get("/api/hospitals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Update the hospital
        Hospital updatedHospital = hospitalRepository.findById(hospital.getId()).get();
        // Disconnect from session so that the updates on updatedHospital are not directly saved in db
        em.detach(updatedHospital);
        updatedHospital
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO);

        restHospitalMockMvc.perform(put("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHospital)))
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHospital.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testHospital.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHospital.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void updateNonExistingHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Create the Hospital

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalMockMvc.perform(put("/api/hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hospital)))
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeDelete = hospitalRepository.findAll().size();

        // Delete the hospital
        restHospitalMockMvc.perform(delete("/api/hospitals/{id}", hospital.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hospital.class);
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        Hospital hospital2 = new Hospital();
        hospital2.setId(hospital1.getId());
        assertThat(hospital1).isEqualTo(hospital2);
        hospital2.setId(2L);
        assertThat(hospital1).isNotEqualTo(hospital2);
        hospital1.setId(null);
        assertThat(hospital1).isNotEqualTo(hospital2);
    }
}
