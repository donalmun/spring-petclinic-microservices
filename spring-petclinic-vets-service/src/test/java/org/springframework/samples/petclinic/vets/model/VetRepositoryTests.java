package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class VetRepositoryTests {

    @Autowired
    private VetRepository vetRepository;

    @Test
    void shouldFindAllVets() {
        List<Vet> vets = vetRepository.findAll();
        assertThat(vets).isNotEmpty();
    }

    @Test
    void shouldInsertVet() {
        int vetsBefore = vetRepository.findAll().size();
        
        Vet vet = new Vet();
        vet.setFirstName("John");
        vet.setLastName("Doe");
        
        Specialty specialty = new Specialty();
        specialty.setId(1);
        specialty.setName("radiology");
        vet.addSpecialty(specialty);
        
        vetRepository.save(vet);
        
        List<Vet> vets = vetRepository.findAll();
        assertThat(vets.size()).isEqualTo(vetsBefore + 1);
        
        Vet found = vets.stream()
            .filter(v -> "John".equals(v.getFirstName()) && "Doe".equals(v.getLastName()))
            .findFirst()
            .orElse(null);
            
        assertThat(found).isNotNull();
        assertThat(found.getSpecialties()).hasSize(1);
        assertThat(found.getSpecialties().get(0).getName()).isEqualTo("radiology");
    }
}