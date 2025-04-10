package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.*;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetResource.class)
public class PetResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PetRepository petRepository;
    
    @MockBean
    OwnerRepository ownerRepository;
    
    private List<PetType> petTypes;
    private Owner owner;
    private Pet pet;
    
    @BeforeEach
    void setup() {
        // Create pet types
        petTypes = new ArrayList<>();
        PetType dog = new PetType();
        dog.setId(1);
        dog.setName("Dog");
        petTypes.add(dog);
        
        PetType cat = new PetType();
        cat.setId(2);
        cat.setName("Cat");
        petTypes.add(cat);
        
        // Create owner
        owner = new Owner();
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        
        // Create pet
        pet = new Pet();
        pet.setName("Fluffy");
        pet.setBirthDate(new Date());
        pet.setType(dog);
        pet.setOwner(owner);
    }
    
    @Test
    void testGetPetTypes() throws Exception {
        given(petRepository.findPetTypes()).willReturn(petTypes);
        
        mvc.perform(get("/petTypes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("Dog"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("Cat"));
    }
    
    @Test
    void testCreatePet() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = dateFormat.format(new Date());
        
        given(petRepository.findPetTypeById(1)).willReturn(Optional.of(petTypes.get(0)));
        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        
        mvc.perform(post("/owners/1/pets")
                .content("{\"name\": \"Fluffy\", \"birthDate\": \"" + birthDate + "\", \"typeId\": 1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    void testCreatePetBadRequest() throws Exception {
        // Missing required fields should cause a bad request
        mvc.perform(post("/owners/1/pets")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}