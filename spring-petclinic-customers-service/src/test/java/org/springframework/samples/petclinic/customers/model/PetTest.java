package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class PetTest {

    @Test
    void testGetAndSetBirthDate() {
        Pet pet = new Pet();
        Date birthDate = new Date();
        pet.setBirthDate(birthDate);
        
        assertThat(pet.getBirthDate()).isEqualTo(birthDate);
    }
    
    @Test
    void testGetAndSetName() {
        Pet pet = new Pet();
        pet.setName("Fluffy");
        
        assertThat(pet.getName()).isEqualTo("Fluffy");
    }
    
    @Test
    void testGetAndSetOwner() {
        Pet pet = new Pet();
        Owner owner = new Owner();
        owner.setFirstName("John");
        pet.setOwner(owner);
        
        assertThat(pet.getOwner()).isEqualTo(owner);
    }
    
    @Test
    void testGetAndSetType() {
        Pet pet = new Pet();
        PetType petType = new PetType();
        petType.setName("dog");
        pet.setType(petType);
        
        assertThat(pet.getType()).isEqualTo(petType);
    }
}