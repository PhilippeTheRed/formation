package fr.insee.formation.hibernate.archi_hex.application.port.out;

import fr.insee.formation.hibernate.archi_hex.domain.Secteur;

public interface SecteurRepository {
    Secteur find(int id);
    Secteur findByCodeNaf(String codeNaf);
    Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(String codeNaf);
    Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(String codeNaf);
}
