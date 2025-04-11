package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OwnerRequestTest {

    @Test
    void testEquality() {
        OwnerRequest request1 = new OwnerRequest("John", "Doe", "123 Main St", "Springfield", "1234567890");
        OwnerRequest request2 = new OwnerRequest("John", "Doe", "123 Main St", "Springfield", "1234567890");
        OwnerRequest request3 = new OwnerRequest("Jane", "Doe", "123 Main St", "Springfield", "1234567890");
        
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }
    
    @Test
    void testGetters() {
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String city = "Springfield";
        String telephone = "1234567890";
        
        OwnerRequest request = new OwnerRequest(firstName, lastName, address, city, telephone);
        
        assertEquals(firstName, request.firstName());
        assertEquals(lastName, request.lastName());
        assertEquals(address, request.address());
        assertEquals(city, request.city());
        assertEquals(telephone, request.telephone());
    }
}
