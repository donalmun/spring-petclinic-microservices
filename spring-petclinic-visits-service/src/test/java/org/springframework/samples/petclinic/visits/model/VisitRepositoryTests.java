package org.springframework.samples.petclinic.visits.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class VisitRepositoryTests {

    @Autowired
    private VisitRepository visitRepository;

    @Test
    void shouldFindVisitsByPetId() {
        Collection<Visit> visits = visitRepository.findByPetId(7);
        assertThat(visits).hasSize(2);
        
        Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
        assertThat(visitArr[0].getPetId()).isEqualTo(7);
    }

    @Test
    void shouldFindVisitsByPetIds() {
        List<Visit> visits = visitRepository.findByPetIdIn(List.of(7, 8));
        assertThat(visits).isNotEmpty();
        assertThat(visits).allMatch(v -> List.of(7, 8).contains(v.getPetId()));
    }

    @Test
    void shouldInsertVisit() {
        int visitCountBefore = visitRepository.findByPetId(7).size();
        
        Visit visit = new Visit();
        visit.setPetId(7);
        visit.setDate(new Date());
        visit.setDescription("Test Visit");
        
        visit = visitRepository.save(visit);
        assertThat(visit.getId()).isNotNull();
        
        Collection<Visit> visits = visitRepository.findByPetId(7);
        assertThat(visits.size()).isEqualTo(visitCountBefore + 1);
    }

    @Test
    void shouldReturnEmptyWhenNoVisitsFoundByPetId() {
        Collection<Visit> visits = visitRepository.findByPetId(999);
        assertThat(visits).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenNoVisitsFoundByPetIds() {
        List<Visit> visits = visitRepository.findByPetIdIn(List.of(999, 1000));
        assertThat(visits).isEmpty();
    }

    @Test
    void shouldHandleEmptyPetIdList() {
        List<Visit> visits = visitRepository.findByPetIdIn(List.of());
        assertThat(visits).isEmpty();
    }

    @Test
    void shouldHandleNullDescription() {
        Visit visit = new Visit();
        visit.setPetId(7);
        visit.setDate(new Date());
        visit.setDescription(null);

        visit = visitRepository.save(visit);
        assertThat(visit.getDescription()).isNull();
    }

    @Test
    void shouldHandleNullPetIdInFindByPetIdIn() {
        List<Visit> visits = visitRepository.findByPetIdIn(null);
        assertThat(visits).isEmpty();
    }

    @Test
    void shouldSaveVisitWithFutureDate() {
        Visit visit = new Visit();
        visit.setPetId(7);
        visit.setDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day in the future
        visit.setDescription("Future Visit");

        visit = visitRepository.save(visit);
        assertThat(visit.getId()).isNotNull();
        assertThat(visit.getDate()).isAfter(new Date());
    }

    @Test
    void shouldSaveVisitWithEmptyDescription() {
        Visit visit = new Visit();
        visit.setPetId(7);
        visit.setDate(new Date());
        visit.setDescription(""); // Empty description

        visit = visitRepository.save(visit);
        assertThat(visit.getId()).isNotNull();
        assertThat(visit.getDescription()).isEmpty();
    }
}