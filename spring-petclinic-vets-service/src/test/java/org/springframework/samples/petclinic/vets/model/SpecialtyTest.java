package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialtyTest {

    @Test
    void testGetAndSetId() {
        Specialty specialty = new Specialty();
        specialty.setId(1);
        assertThat(specialty.getId()).isEqualTo(1);
    }

    @Test
    void testGetAndSetName() {
        Specialty specialty = new Specialty();
        specialty.setName("surgery");
        assertThat(specialty.getName()).isEqualTo("surgery");
    }
}