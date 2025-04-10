package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceNotFoundExceptionTest {

    @Test
    void testConstructor() {
        // Arrange
        String message = "Resource not found";
        
        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(message);
        
        // Assert
        assertEquals(message, exception.getMessage());
    }
}
