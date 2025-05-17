package org.springframework.samples.petclinic.visits.web;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.visits.model.Visit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VisitsTest {

    @Test
    void shouldCreateVisitsWithItems() {
        // Given
        List<Visit> visitList = Arrays.asList(
            Visit.VisitBuilder.aVisit().id(1).petId(1).description("Visit 1").build(),
            Visit.VisitBuilder.aVisit().id(2).petId(2).description("Visit 2").build()
        );

        // When
        VisitResource.Visits visits = new VisitResource.Visits(visitList);

        // Then
        assertThat(visits.items()).isEqualTo(visitList);
        assertThat(visits.items()).hasSize(2);
    }

    @Test
    void shouldCreateVisitsWithEmptyList() {
        // Given
        List<Visit> emptyList = Collections.emptyList();

        // When
        VisitResource.Visits visits = new VisitResource.Visits(emptyList);

        // Then
        assertThat(visits.items()).isEmpty();
    }
} 