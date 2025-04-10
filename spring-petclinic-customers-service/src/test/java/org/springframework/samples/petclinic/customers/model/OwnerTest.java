package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerTest {

    private Owner owner;

    @BeforeEach
    void setup() {
        owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("1234567890");
    }

    @Test
    void testGettersAndSetters() {
        assertThat(owner.getFirstName()).isEqualTo("John");
        assertThat(owner.getLastName()).isEqualTo("Doe");
        assertThat(owner.getAddress()).isEqualTo("123 Main St");
        assertThat(owner.getCity()).isEqualTo("Springfield");
        assertThat(owner.getTelephone()).isEqualTo("1234567890");
        
        owner.setFirstName("Jane");
        assertThat(owner.getFirstName()).isEqualTo("Jane");
    }

    @Test
    void testAddPet() {
        Pet pet = new Pet();
        pet.setName("Fluffy");
        
        owner.addPet(pet);
        
        List<Pet> pets = owner.getPets();
        assertThat(pets).hasSize(1);
        assertThat(pets.get(0).getName()).isEqualTo("Fluffy");
        assertThat(pets.get(0).getOwner()).isEqualTo(owner);
    }

    @Test
    void testToString() {
        String ownerString = owner.toString();
        
        assertThat(ownerString).contains("John");
        assertThat(ownerString).contains("Doe");
        assertThat(ownerString).contains("123 Main St");
        assertThat(ownerString).contains("Springfield");
        assertThat(ownerString).contains("1234567890");
    }
    
    @Test
    void testGetPets() {
        // Initial pets should be empty but not null
        assertThat(owner.getPets()).isNotNull();
        assertThat(owner.getPets()).isEmpty();
        
        // Add multiple pets and verify sorting
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");
        
        Pet pet2 = new Pet();
        pet2.setName("Buddy");
        
        owner.addPet(pet1);
        owner.addPet(pet2);
        
        List<Pet> pets = owner.getPets();
        assertThat(pets).hasSize(2);
        // Pets should be sorted by name (Buddy comes before Fluffy)
        assertThat(pets.get(0).getName()).isEqualTo("Buddy");
        assertThat(pets.get(1).getName()).isEqualTo("Fluffy");
    }
}