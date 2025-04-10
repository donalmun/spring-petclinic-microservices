package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class OwnerRepositoryTests {

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void shouldFindOwnersByLastName() {
        Collection<Owner> owners = ownerRepository.findByLastName("Davis");
        assertThat(owners.size()).isEqualTo(2);

        owners = ownerRepository.findByLastName("Daviss");
        assertThat(owners).isEmpty();
    }

    @Test
    void shouldFindSingleOwnerWithPet() {
        Owner owner = ownerRepository.findById(1).orElse(null);
        assertThat(owner).isNotNull();
        assertThat(owner.getFirstName()).isEqualTo("George");
        assertThat(owner.getPets().size()).isEqualTo(1);
    }

    @Test
    void shouldInsertOwner() {
        Collection<Owner> owners = ownerRepository.findByLastName("Schultz");
        int found = owners.size();

        Owner owner = new Owner();
        owner.setFirstName("Sam");
        owner.setLastName("Schultz");
        owner.setAddress("4, Evans Street");
        owner.setCity("Wollongong");
        owner.setTelephone("4444444444");
        ownerRepository.save(owner);
        assertThat(owner.getId()).isNotNull();

        owners = ownerRepository.findByLastName("Schultz");
        assertThat(owners.size()).isEqualTo(found + 1);
    }

    @Test
    void shouldUpdateOwner() {
        Owner owner = ownerRepository.findById(1).orElse(null);
        assertThat(owner).isNotNull();
        String oldLastName = owner.getLastName();
        String newLastName = oldLastName + "X";

        owner.setLastName(newLastName);
        ownerRepository.save(owner);

        owner = ownerRepository.findById(1).orElse(null);
        assertThat(owner).isNotNull();
        assertThat(owner.getLastName()).isEqualTo(newLastName);
    }
}