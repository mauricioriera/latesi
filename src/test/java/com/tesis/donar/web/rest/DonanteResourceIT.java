package com.tesis.donar.web.rest;

import com.tesis.donar.DonarApp;
import com.tesis.donar.domain.Donante;
import com.tesis.donar.repository.DonanteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.tesis.donar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DonanteResource} REST controller.
 */
@SpringBootTest(classes = DonarApp.class)
public class DonanteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_SANGUINEO = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_SANGUINEO = "BBBBBBBBBB";

    private static final String DEFAULT_FACTOR_SANGUINEO = "AAAAAAAAAA";
    private static final String UPDATED_FACTOR_SANGUINEO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private DonanteRepository donanteRepository;

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

    private MockMvc restDonanteMockMvc;

    private Donante donante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonanteResource donanteResource = new DonanteResource(donanteRepository);
        this.restDonanteMockMvc = MockMvcBuilders.standaloneSetup(donanteResource)
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
    public static Donante createEntity(EntityManager em) {
        Donante donante = new Donante()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .sexo(DEFAULT_SEXO)
            .grupoSanguineo(DEFAULT_GRUPO_SANGUINEO)
            .factorSanguineo(DEFAULT_FACTOR_SANGUINEO)
            .activo(DEFAULT_ACTIVO);
        return donante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donante createUpdatedEntity(EntityManager em) {
        Donante donante = new Donante()
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .grupoSanguineo(UPDATED_GRUPO_SANGUINEO)
            .factorSanguineo(UPDATED_FACTOR_SANGUINEO)
            .activo(UPDATED_ACTIVO);
        return donante;
    }

    @BeforeEach
    public void initTest() {
        donante = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonante() throws Exception {
        int databaseSizeBeforeCreate = donanteRepository.findAll().size();

        // Create the Donante
        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isCreated());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeCreate + 1);
        Donante testDonante = donanteList.get(donanteList.size() - 1);
        assertThat(testDonante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDonante.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testDonante.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDonante.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDonante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDonante.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testDonante.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testDonante.getGrupoSanguineo()).isEqualTo(DEFAULT_GRUPO_SANGUINEO);
        assertThat(testDonante.getFactorSanguineo()).isEqualTo(DEFAULT_FACTOR_SANGUINEO);
        assertThat(testDonante.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createDonanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donanteRepository.findAll().size();

        // Create the Donante with an existing ID
        donante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setNombre(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setApellido(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setDireccion(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setTelefono(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setFechaNacimiento(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setSexo(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrupoSanguineoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setGrupoSanguineo(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFactorSanguineoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donanteRepository.findAll().size();
        // set the field null
        donante.setFactorSanguineo(null);

        // Create the Donante, which fails.

        restDonanteMockMvc.perform(post("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDonantes() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        // Get all the donanteList
        restDonanteMockMvc.perform(get("/api/donantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].grupoSanguineo").value(hasItem(DEFAULT_GRUPO_SANGUINEO.toString())))
            .andExpect(jsonPath("$.[*].factorSanguineo").value(hasItem(DEFAULT_FACTOR_SANGUINEO.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDonante() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        // Get the donante
        restDonanteMockMvc.perform(get("/api/donantes/{id}", donante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donante.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.grupoSanguineo").value(DEFAULT_GRUPO_SANGUINEO.toString()))
            .andExpect(jsonPath("$.factorSanguineo").value(DEFAULT_FACTOR_SANGUINEO.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDonante() throws Exception {
        // Get the donante
        restDonanteMockMvc.perform(get("/api/donantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonante() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();

        // Update the donante
        Donante updatedDonante = donanteRepository.findById(donante.getId()).get();
        // Disconnect from session so that the updates on updatedDonante are not directly saved in db
        em.detach(updatedDonante);
        updatedDonante
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .grupoSanguineo(UPDATED_GRUPO_SANGUINEO)
            .factorSanguineo(UPDATED_FACTOR_SANGUINEO)
            .activo(UPDATED_ACTIVO);

        restDonanteMockMvc.perform(put("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonante)))
            .andExpect(status().isOk());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
        Donante testDonante = donanteList.get(donanteList.size() - 1);
        assertThat(testDonante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDonante.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testDonante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDonante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDonante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonante.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testDonante.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDonante.getGrupoSanguineo()).isEqualTo(UPDATED_GRUPO_SANGUINEO);
        assertThat(testDonante.getFactorSanguineo()).isEqualTo(UPDATED_FACTOR_SANGUINEO);
        assertThat(testDonante.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingDonante() throws Exception {
        int databaseSizeBeforeUpdate = donanteRepository.findAll().size();

        // Create the Donante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonanteMockMvc.perform(put("/api/donantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donante)))
            .andExpect(status().isBadRequest());

        // Validate the Donante in the database
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonante() throws Exception {
        // Initialize the database
        donanteRepository.saveAndFlush(donante);

        int databaseSizeBeforeDelete = donanteRepository.findAll().size();

        // Delete the donante
        restDonanteMockMvc.perform(delete("/api/donantes/{id}", donante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Donante> donanteList = donanteRepository.findAll();
        assertThat(donanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donante.class);
        Donante donante1 = new Donante();
        donante1.setId(1L);
        Donante donante2 = new Donante();
        donante2.setId(donante1.getId());
        assertThat(donante1).isEqualTo(donante2);
        donante2.setId(2L);
        assertThat(donante1).isNotEqualTo(donante2);
        donante1.setId(null);
        assertThat(donante1).isNotEqualTo(donante2);
    }
}
