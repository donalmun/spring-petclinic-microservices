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
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetRepository;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link PetResource}
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(PetResource.class)
@ActiveProfiles("test")
class PetResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PetRepository petRepository;

    @MockBean
    OwnerRepository ownerRepository;

    @Test
    void shouldGetAPetInJSonFormat() throws Exception {
        Pet pet = setupPet();

        given(petRepository.findById(2)).willReturn(Optional.of(pet));

        mvc.perform(get("/owners/2/pets/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Basil"))
            .andExpect(jsonPath("$.type.id").value(6));
    }

    @Test
    void shouldGetAllPetTypes() throws Exception {
        List<PetType> petTypes = new ArrayList<>();
        
        PetType dog = new PetType();
        dog.setId(1);
        dog.setName("Dog");
        
        PetType cat = new PetType();
        cat.setId(2);
        cat.setName("Cat");
        
        petTypes.add(dog);
        petTypes.add(cat);
        
        given(petRepository.findPetTypes()).willReturn(petTypes);

        mvc.perform(get("/petTypes").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Dog"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Cat"));
    }

    @Test
    void shouldCreateNewPet() throws Exception {
        Pet pet = setupPet();
        
        PetRequest petRequest = new PetRequest();
        petRequest.setName("Basil");
        petRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-15"));
        petRequest.setTypeId(6);
        
        Owner owner = pet.getOwner();
        
        given(ownerRepository.findById(2)).willReturn(Optional.of(owner));
        given(petRepository.save(any(Pet.class))).willReturn(pet);
        given(petRepository.findPetTypeById(6)).willReturn(Optional.of(pet.getType()));

        mvc.perform(post("/owners/2/pets")
                .content(asJsonString(petRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Basil"));

        verify(petRepository).save(any(Pet.class));
    }

    @Test
    void shouldUpdatePet() throws Exception {
        Pet pet = setupPet();
        
        PetRequest petRequest = new PetRequest();
        petRequest.setName("Updated Basil");
        petRequest.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-15"));
        petRequest.setTypeId(6);
        
        given(petRepository.findById(2)).willReturn(Optional.of(pet));
        given(petRepository.save(any(Pet.class))).willReturn(pet);
        given(petRepository.findPetTypeById(6)).willReturn(Optional.of(pet.getType()));

        mvc.perform(put("/owners/2/pets/2")
                .content(asJsonString(petRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(petRepository).save(any(Pet.class));
    }

    @Test
    void shouldReturnNotFoundForNonExistentPet() throws Exception {
        given(petRepository.findById(99)).willReturn(Optional.empty());

        mvc.perform(get("/owners/2/pets/99").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private Pet setupPet() throws ParseException {
        Owner owner = new Owner();
        owner.setFirstName("George");
        owner.setLastName("Bush");
        owner.setId(2);
        owner.setAddress("123 Main St");
        owner.setCity("Wichita");
        owner.setTelephone("1234567890");

        Pet pet = new Pet();
        pet.setName("Basil");
        pet.setId(2);
        pet.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-15"));

        PetType petType = new PetType();
        petType.setId(6);
        petType.setName("dog");
        pet.setType(petType);

        owner.addPet(pet);
        return pet;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}