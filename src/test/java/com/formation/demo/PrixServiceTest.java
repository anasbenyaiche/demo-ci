package com.formation.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PrixServiceTest {

    private final PrixService service = new PrixService();

    @Test
    void calculerTTC_avecTva20_majoreLePrix() {
        assertEquals(new BigDecimal("120.00"),
            service.calculerTTC(new BigDecimal("100"), new BigDecimal("0.20")));
    }

    // TODO : écrire les tests demandés dans l'énoncé
}
