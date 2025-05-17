package org.springframework.samples.petclinic.visits.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class VisitResourceIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGetVisitsForPet() {
        ResponseEntity<List<Visit>> response = restTemplate.exchange(
            "/owners/*/pets/7/visits",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Visit>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Visit> visits = response.getBody();
        assertThat(visits).isNotNull();
        assertThat(visits).allMatch(v -> v.getPetId() == 7);
    }

    @Test
    void shouldGetVisitsForMultiplePets() {
        ResponseEntity<VisitResource.Visits> response = restTemplate.exchange(
            "/pets/visits?petId=7,8",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<VisitResource.Visits>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        VisitResource.Visits visits = response.getBody();
        assertThat(visits).isNotNull();
        assertThat(visits.items()).isNotEmpty();
        assertThat(visits.items()).allMatch(v -> v.getPetId() == 7 || v.getPetId() == 8);
    }

    @Test
    void shouldCreateNewVisit() {
        Map<String, Object> visitMap = new HashMap<>();
        visitMap.put("description", "Integration Test Visit");

        ResponseEntity<Visit> response = restTemplate.postForEntity(
            "/owners/*/pets/7/visits",
            visitMap,
            Visit.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Visit createdVisit = response.getBody();
        assertThat(createdVisit).isNotNull();
        assertThat(createdVisit.getId()).isNotNull();
        assertThat(createdVisit.getPetId()).isEqualTo(7);
        assertThat(createdVisit.getDescription()).isEqualTo("Integration Test Visit");
    }

    @Test
    void shouldReturnEmptyListForNonExistentPet() {
        ResponseEntity<List<Visit>> response = restTemplate.exchange(
            "/owners/*/pets/999/visits",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Visit>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Visit> visits = response.getBody();
        assertThat(visits).isNotNull();
        assertThat(visits).isEmpty();
    }
} 