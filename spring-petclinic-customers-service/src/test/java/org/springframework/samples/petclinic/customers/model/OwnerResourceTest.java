package org.springframework.samples.petclinic.customers.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.samples.petclinic.customers.web.mapper.OwnerEntityMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link OwnerResource}
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerResource.class)
@ActiveProfiles("test")
class OwnerResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OwnerRepository ownerRepository;

    @MockBean
    OwnerEntityMapper ownerEntityMapper;

    @Test
    void shouldGetOwnerById() throws Exception {
        Owner owner = createOwner();
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));

        mvc.perform(get("/owners/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.address").value("123 Main St"))
            .andExpect(jsonPath("$.city").value("New York"))
            .andExpect(jsonPath("$.telephone").value("1234567890"));
    }

    @Test
    void shouldGetAllOwners() throws Exception {
        Owner owner1 = createOwner();
        Owner owner2 = new Owner();
        owner2.setId(2);
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setAddress("456 Oak Ave");
        owner2.setCity("Boston");
        owner2.setTelephone("0987654321");

        List<Owner> owners = Arrays.asList(owner1, owner2);
        given(ownerRepository.findAll()).willReturn(owners);

        mvc.perform(get("/owners")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void shouldCreateOwner() throws Exception {
        OwnerRequest ownerRequest = new OwnerRequest(
            "John", "Doe", "123 Main St", "New York", "1234567890");
        
        Owner newOwner = createOwner();
        
        given(ownerEntityMapper.map(any(Owner.class), eq(ownerRequest))).willReturn(newOwner);
        given(ownerRepository.save(any(Owner.class))).willReturn(newOwner);

        mvc.perform(post("/owners")
                .content(asJsonString(ownerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("John"));
        
        verify(ownerEntityMapper).map(any(Owner.class), eq(ownerRequest));
        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void shouldUpdateOwner() throws Exception {
        OwnerRequest ownerRequest = new OwnerRequest(
            "John", "Updated", "123 Main St", "New York", "1234567890");
        
        Owner existingOwner = createOwner();
        given(ownerRepository.findById(1)).willReturn(Optional.of(existingOwner));
        
        mvc.perform(put("/owners/1")
                .content(asJsonString(ownerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        verify(ownerEntityMapper).map(eq(existingOwner), eq(ownerRequest));
        verify(ownerRepository).save(existingOwner);
    }

    @Test
    void shouldReturnNotFoundForInvalidOwnerId() throws Exception {
        given(ownerRepository.findById(99)).willReturn(Optional.empty());

        mvc.perform(get("/owners/99")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    private Owner createOwner() {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("New York");
        owner.setTelephone("1234567890");
        return owner;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}