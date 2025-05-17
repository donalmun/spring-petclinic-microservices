package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetTypeTest {

    private PetType petType;
    
    @BeforeEach
    void setUp() {
        petType = new PetType();
        petType.setId(1);
        petType.setName("dog");
    }
    
    @Test
    void testGettersAndSetters() {
        assertEquals(Integer.valueOf(1), petType.getId());
        assertEquals("dog", petType.getName());
        
        petType.setId(2);
        petType.setName("cat");
        
        assertEquals(Integer.valueOf(2), petType.getId());
        assertEquals("cat", petType.getName());
    }
}
