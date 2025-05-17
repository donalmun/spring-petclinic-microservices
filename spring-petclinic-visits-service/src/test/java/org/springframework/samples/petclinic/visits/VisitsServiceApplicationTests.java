package org.springframework.samples.petclinic.visits;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class VisitsServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring context loads successfully
    }

    @Test
    void applicationStarts() {
        // Test the main method (will increase coverage)
        VisitsServiceApplication.main(new String[] {});
    }
} 