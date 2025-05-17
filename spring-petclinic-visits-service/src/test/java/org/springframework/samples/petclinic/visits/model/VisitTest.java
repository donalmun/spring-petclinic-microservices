package org.springframework.samples.petclinic.visits.model;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
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

    @Test
    void testBuilderPatternWithDate() {
        Date customDate = new Date();
        Visit visit = Visit.VisitBuilder.aVisit()
            .id(1)
            .petId(2)
            .description("Test Visit")
            .date(customDate)
            .build();

        assertThat(visit.getDate()).isEqualTo(customDate);
    }

    @Test
    void testSetDescriptionWithNull() {
        Visit visit = new Visit();
        visit.setDescription(null);
        assertThat(visit.getDescription()).isNull();
    }

    @Test
    void testSetPetIdWithZero() {
        Visit visit = new Visit();
        visit.setPetId(0);
        assertThat(visit.getPetId()).isEqualTo(0);
    }

    @Test
    void testBuilderPatternWithNullValues() {
        Visit visit = Visit.VisitBuilder.aVisit()
            .id(null)
            .petId(0)
            .description(null)
            .date(null)
            .build();

        assertThat(visit.getId()).isNull();
        assertThat(visit.getPetId()).isEqualTo(0);
        assertThat(visit.getDescription()).isNull();
        assertThat(visit.getDate()).isNull();
    }

    @Test
    void testSetIdWithNull() {
        Visit visit = new Visit();
        visit.setId(null);
        assertThat(visit.getId()).isNull();
    }

    @Test
    void testBuilderPatternWithAllFields() {
        Date date = new Date();
        Visit visit = Visit.VisitBuilder.aVisit()
            .id(1)
            .petId(2)
            .description("Complete Visit")
            .date(date)
            .build();

        assertThat(visit.getId()).isEqualTo(1);
        assertThat(visit.getPetId()).isEqualTo(2);
        assertThat(visit.getDescription()).isEqualTo("Complete Visit");
        assertThat(visit.getDate()).isEqualTo(date);
    }
    
    @Test
    void testBuilderWithChainedCalls() {
        Visit.VisitBuilder builder = Visit.VisitBuilder.aVisit();
        builder.id(1);
        builder.petId(2);
        builder.description("Chained calls");
        Date date = new Date();
        builder.date(date);
        
        Visit visit = builder.build();
        
        assertThat(visit.getId()).isEqualTo(1);
        assertThat(visit.getPetId()).isEqualTo(2);
        assertThat(visit.getDescription()).isEqualTo("Chained calls");
        assertThat(visit.getDate()).isEqualTo(date);
    }
    
    @Test
    void testVisitWithLongDescription() {
        String longDescription = "a".repeat(8000);
        Visit visit = new Visit();
        visit.setDescription(longDescription);
        assertThat(visit.getDescription()).isEqualTo(longDescription);
        assertThat(visit.getDescription().length()).isEqualTo(8000);
    }
    
    @Test
    void testVisitWithNegativePetId() {
        Visit visit = new Visit();
        visit.setPetId(-1);
        assertThat(visit.getPetId()).isEqualTo(-1);
    }
    
    @Test
    void testVisitWithPastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date pastDate = calendar.getTime();
        
        Visit visit = new Visit();
        visit.setDate(pastDate);
        assertThat(visit.getDate()).isEqualTo(pastDate);
    }
    
    @Test
    void testVisitWithFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date futureDate = calendar.getTime();
        
        Visit visit = new Visit();
        visit.setDate(futureDate);
        assertThat(visit.getDate()).isEqualTo(futureDate);
    }
}