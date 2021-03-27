package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CustomerContactInfo;
import com.mycompany.myapp.repository.CustomerContactInfoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerContactInfoResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerContactInfoResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    @Autowired
    private CustomerContactInfoRepository customerContactInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerContactInfoMockMvc;

    private CustomerContactInfo customerContactInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerContactInfo createEntity(EntityManager em) {
        CustomerContactInfo customerContactInfo = new CustomerContactInfo()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE);
        return customerContactInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerContactInfo createUpdatedEntity(EntityManager em) {
        CustomerContactInfo customerContactInfo = new CustomerContactInfo()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE);
        return customerContactInfo;
    }

    @BeforeEach
    public void initTest() {
        customerContactInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerContactInfo() throws Exception {
        int databaseSizeBeforeCreate = customerContactInfoRepository.findAll().size();
        // Create the CustomerContactInfo
        restCustomerContactInfoMockMvc.perform(post("/api/customer-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerContactInfo)))
            .andExpect(status().isCreated());

        // Validate the CustomerContactInfo in the database
        List<CustomerContactInfo> customerContactInfoList = customerContactInfoRepository.findAll();
        assertThat(customerContactInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerContactInfo testCustomerContactInfo = customerContactInfoList.get(customerContactInfoList.size() - 1);
        assertThat(testCustomerContactInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerContactInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerContactInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerContactInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomerContactInfo.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void createCustomerContactInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerContactInfoRepository.findAll().size();

        // Create the CustomerContactInfo with an existing ID
        customerContactInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerContactInfoMockMvc.perform(post("/api/customer-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerContactInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerContactInfo in the database
        List<CustomerContactInfo> customerContactInfoList = customerContactInfoRepository.findAll();
        assertThat(customerContactInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerContactInfos() throws Exception {
        // Initialize the database
        customerContactInfoRepository.saveAndFlush(customerContactInfo);

        // Get all the customerContactInfoList
        restCustomerContactInfoMockMvc.perform(get("/api/customer-contact-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerContactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)));
    }
    
    @Test
    @Transactional
    public void getCustomerContactInfo() throws Exception {
        // Initialize the database
        customerContactInfoRepository.saveAndFlush(customerContactInfo);

        // Get the customerContactInfo
        restCustomerContactInfoMockMvc.perform(get("/api/customer-contact-infos/{id}", customerContactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerContactInfo.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerContactInfo() throws Exception {
        // Get the customerContactInfo
        restCustomerContactInfoMockMvc.perform(get("/api/customer-contact-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerContactInfo() throws Exception {
        // Initialize the database
        customerContactInfoRepository.saveAndFlush(customerContactInfo);

        int databaseSizeBeforeUpdate = customerContactInfoRepository.findAll().size();

        // Update the customerContactInfo
        CustomerContactInfo updatedCustomerContactInfo = customerContactInfoRepository.findById(customerContactInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerContactInfo are not directly saved in db
        em.detach(updatedCustomerContactInfo);
        updatedCustomerContactInfo
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE);

        restCustomerContactInfoMockMvc.perform(put("/api/customer-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerContactInfo)))
            .andExpect(status().isOk());

        // Validate the CustomerContactInfo in the database
        List<CustomerContactInfo> customerContactInfoList = customerContactInfoRepository.findAll();
        assertThat(customerContactInfoList).hasSize(databaseSizeBeforeUpdate);
        CustomerContactInfo testCustomerContactInfo = customerContactInfoList.get(customerContactInfoList.size() - 1);
        assertThat(testCustomerContactInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerContactInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerContactInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerContactInfo.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomerContactInfo.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = customerContactInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerContactInfoMockMvc.perform(put("/api/customer-contact-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerContactInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerContactInfo in the database
        List<CustomerContactInfo> customerContactInfoList = customerContactInfoRepository.findAll();
        assertThat(customerContactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerContactInfo() throws Exception {
        // Initialize the database
        customerContactInfoRepository.saveAndFlush(customerContactInfo);

        int databaseSizeBeforeDelete = customerContactInfoRepository.findAll().size();

        // Delete the customerContactInfo
        restCustomerContactInfoMockMvc.perform(delete("/api/customer-contact-infos/{id}", customerContactInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerContactInfo> customerContactInfoList = customerContactInfoRepository.findAll();
        assertThat(customerContactInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
