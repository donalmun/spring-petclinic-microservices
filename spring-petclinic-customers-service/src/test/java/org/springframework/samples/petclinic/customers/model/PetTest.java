package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PetTest {

    private Pet pet;
    private Owner owner;
    private PetType petType;

    @BeforeEach
    void setup() {
        pet = new Pet();
        
        owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        
        petType = new PetType();
        petType.setId(1);
        petType.setName("Cat");
        
        pet.setName("Fluffy");
        pet.setBirthDate(new Date());
        pet.setOwner(owner);
        pet.setType(petType);
    }

    @Test
    void testGettersAndSetters() {
        assertThat(pet.getName()).isEqualTo("Fluffy");
        assertThat(pet.getBirthDate()).isNotNull();
        assertThat(pet.getOwner()).isEqualTo(owner);
        assertThat(pet.getType()).isEqualTo(petType);
        
        Date newDate = new Date(2020, 1, 1);
        pet.setBirthDate(newDate);
        assertThat(pet.getBirthDate()).isEqualTo(newDate);
        
        pet.setName("Buddy");
        assertThat(pet.getName()).isEqualTo("Buddy");
    }

    @Test
    void testToString() {
        String petString = pet.toString();
        
        assertThat(petString).contains("Fluffy");
        assertThat(petString).contains("Cat");
        assertThat(petString).contains("John");
        assertThat(petString).contains("Doe");
    }
    
    @Test
    void testEqualsAndHashCode() {
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("Fluffy");
        pet1.setBirthDate(pet.getBirthDate());
        pet1.setOwner(owner);
        pet1.setType(petType);
        
        Pet pet2 = new Pet();
        pet2.setId(1);
        pet2.setName("Fluffy");
        pet2.setBirthDate(pet.getBirthDate());
        pet2.setOwner(owner);
        pet2.setType(petType);
        
        assertThat(pet1).isEqualTo(pet2);
        assertThat(pet1.hashCode()).isEqualTo(pet2.hashCode());
        
        // Change one attribute
        pet2.setName("Buddy");
        assertThat(pet1).isNotEqualTo(pet2);
    }
}