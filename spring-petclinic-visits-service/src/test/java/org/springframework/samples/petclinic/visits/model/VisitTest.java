package org.springframework.samples.petclinic.visits.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class VisitTest {

    @Test
    void testGetAndSetId() {
        Visit visit = new Visit();
        visit.setId(1);
        assertThat(visit.getId()).isEqualTo(1);
    }

    @Test
    void testGetAndSetDate() {
        Visit visit = new Visit();
        Date date = new Date();
        visit.setDate(date);
        assertThat(visit.getDate()).isEqualTo(date);
    }

    @Test
    void testGetAndSetDescription() {
        Visit visit = new Visit();
        visit.setDescription("Routine check");
        assertThat(visit.getDescription()).isEqualTo("Routine check");
    }

    @Test
    void testGetAndSetPetId() {
        Visit visit = new Visit();
        visit.setPetId(5);
        assertThat(visit.getPetId()).isEqualTo(5);
    }
    
    @Test
    void testBuilderPattern() {
        Visit visit = Visit.VisitBuilder.aVisit()
            .id(1)
            .petId(2)
            .description("Test Visit")
            .build();
            
        assertThat(visit.getId()).isEqualTo(1);
        assertThat(visit.getPetId()).isEqualTo(2);
        assertThat(visit.getDescription()).isEqualTo("Test Visit");
    }

    @Test
    void testDefaultDateInitialization() {
        Visit visit = new Visit();
        assertThat(visit.getDate()).isNotNull();
    }
}