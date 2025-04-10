package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.samples.petclinic.customers.web.mapper.OwnerEntityMapper;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerResource.class)
public class OwnerResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OwnerRepository ownerRepository;
    
    @MockBean
    OwnerEntityMapper ownerEntityMapper;
    
    private Owner owner;
    
    @BeforeEach
    void setup() {
        owner = new Owner();
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");
    }
    
    @Test
    void testGetOwnerSuccess() throws Exception {
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        
        mvc.perform(get("/owners/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("George"))
                .andExpect(jsonPath("$.lastName").value("Franklin"))
                .andExpect(jsonPath("$.address").value("110 W. Liberty St."))
                .andExpect(jsonPath("$.city").value("Madison"))
                .andExpect(jsonPath("$.telephone").value("6085551023"));
    }
    
    @Test
    void testGetOwnerNotFound() throws Exception {
        given(ownerRepository.findById(2)).willReturn(Optional.empty());
        
        mvc.perform(get("/owners/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
    
    @Test
    void testGetAllOwners() throws Exception {
        Owner owner2 = new Owner();
        owner2.setFirstName("Betty");
        owner2.setLastName("Davis");
        
        List<Owner> owners = Arrays.asList(owner, owner2);
        given(ownerRepository.findAll()).willReturn(owners);
        
        mvc.perform(get("/owners")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("George"))
                .andExpect(jsonPath("$.[1].firstName").value("Betty"));
    }
    
    @Test
    void testCreateOwner() throws Exception {
        given(ownerEntityMapper.map(any(Owner.class), any(OwnerRequest.class))).willReturn(owner);
        given(ownerRepository.save(any(Owner.class))).willReturn(owner);
        
        mvc.perform(post("/owners")
                .content("{\"firstName\": \"George\", \"lastName\": \"Franklin\", \"address\": \"110 W. Liberty St.\", \"city\": \"Madison\", \"telephone\": \"6085551023\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    void testUpdateOwner() throws Exception {
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        
        mvc.perform(put("/owners/1")
                .content("{\"firstName\": \"George Updated\", \"lastName\": \"Franklin\", \"address\": \"110 W. Liberty St.\", \"city\": \"Madison\", \"telephone\": \"6085551023\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
                
        verify(ownerEntityMapper).map(any(Owner.class), any(OwnerRequest.class));
        verify(ownerRepository).save(any(Owner.class));
    }
}