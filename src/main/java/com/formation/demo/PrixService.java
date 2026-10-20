package com.formation.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrixService {

    public BigDecimal calculerTTC(BigDecimal prixHT, BigDecimal tauxTva) {
        if (prixHT == null || tauxTva == null) {
            throw new IllegalArgumentException("Prix HT et taux de TVA obligatoires");
        }
        if (prixHT.signum() < 0) {
            throw new IllegalArgumentException("Le prix HT ne peut pas être négatif");
        }
        return prixHT.multiply(BigDecimal.ONE.add(tauxTva))
                     .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal appliquerRemise(BigDecimal prix, int pourcentage) {
        if (pourcentage < 0 || pourcentage > 100) {
            throw new IllegalArgumentException("Remise entre 0 et 100 %");
        }
        BigDecimal facteur = BigDecimal.valueOf(100L - pourcentage)
                                       .divide(BigDecimal.valueOf(100));
        return prix.multiply(facteur).setScale(2, RoundingMode.HALF_UP);
    }
}
