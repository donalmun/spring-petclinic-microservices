package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PetTypeTest {

    @Test
    void testGettersAndSetters() {
        PetType petType = new PetType();
        
        petType.setId(1);
        petType.setName("Cat");
        
        assertThat(petType.getId()).isEqualTo(1);
        assertThat(petType.getName()).isEqualTo("Cat");
        
        petType.setName("Dog");
        assertThat(petType.getName()).isEqualTo("Dog");
    }
}