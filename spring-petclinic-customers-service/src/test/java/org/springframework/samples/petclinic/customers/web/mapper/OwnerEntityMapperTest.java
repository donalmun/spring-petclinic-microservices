package org.springframework.samples.petclinic.customers.web.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.web.OwnerRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerEntityMapperTest {

    private OwnerEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OwnerEntityMapper();
    }

    @Test
    void shouldMapOwnerRequestToOwner() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String city = "Springfield";
        String telephone = "1234567890";
        
        OwnerRequest request = new OwnerRequest(firstName, lastName, address, city, telephone);
        Owner owner = new Owner();
        
        // Act
        Owner mappedOwner = mapper.map(owner, request);
        
        // Assert
        assertEquals(firstName, mappedOwner.getFirstName());
        assertEquals(lastName, mappedOwner.getLastName());
        assertEquals(address, mappedOwner.getAddress());
        assertEquals(city, mappedOwner.getCity());
        assertEquals(telephone, mappedOwner.getTelephone());
    }
}
