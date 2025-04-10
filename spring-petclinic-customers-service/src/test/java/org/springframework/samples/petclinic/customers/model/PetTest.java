package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

    @Test
    void testEqualsAndHashCode() {
        // Given
        Pet pet1 = createPet(1, "Max", new Date());
        Pet pet2 = createPet(1, "Max", pet1.getBirthDate());
        Pet pet3 = createPet(2, "Buddy", new Date());

        // Then
        assertEquals(pet1, pet2);
        assertEquals(pet1.hashCode(), pet2.hashCode());
        assertNotEquals(pet1, pet3);
        assertNotEquals(pet1.hashCode(), pet3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Pet pet = createPet(1, "Max", new Date());
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        pet.setOwner(owner);

        // When
        String petString = pet.toString();

        // Then
        assertTrue(petString.contains("id=1"));
        assertTrue(petString.contains("name=Max"));
        assertTrue(petString.contains("ownerFirstname=John"));
        assertTrue(petString.contains("ownerLastname=Doe"));
    }

    @Test
    void testGettersAndSetters() {
        // Given
        Pet pet = new Pet();
        Date birthDate = new Date();
        PetType petType = new PetType();
        petType.setId(1);
        petType.setName("dog");
        Owner owner = new Owner();

        // When
        pet.setId(1);
        pet.setName("Rex");
        pet.setBirthDate(birthDate);
        pet.setType(petType);
        pet.setOwner(owner);

        // Then
        assertEquals(Integer.valueOf(1), pet.getId());
        assertEquals("Rex", pet.getName());
        assertEquals(birthDate, pet.getBirthDate());
        assertEquals(petType, pet.getType());
        assertEquals(owner, pet.getOwner());
    }

    private Pet createPet(int id, String name, Date birthDate) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setBirthDate(birthDate);
        PetType type = new PetType();
        type.setId(1);
        type.setName("dog");
        pet.setType(type);
        return pet;
    }
}
