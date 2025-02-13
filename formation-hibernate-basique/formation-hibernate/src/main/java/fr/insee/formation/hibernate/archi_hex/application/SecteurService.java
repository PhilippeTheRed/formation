package fr.insee.formation.hibernate.archi_hex.application;

import fr.insee.formation.hibernate.archi_hex.application.port.out.SecteurRepository;
import fr.insee.formation.hibernate.archi_hex.domain.Secteur;

public class SecteurService {
    private final SecteurRepository secteurRepository;

    public SecteurService(SecteurRepository secteurRepository) {
        this.secteurRepository = secteurRepository;
    }

    public Secteur getSecteurById(int id) {
        return secteurRepository.find(id);
    }

    public Secteur getSecteurByCodeNaf(String codeNaf) {
        return secteurRepository.findByCodeNaf(codeNaf);
    }

    public Secteur getSecteurWithDetailsByCodeNafJPQL(String codeNaf) {
        return secteurRepository.findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(codeNaf);
    }

    public Secteur getSecteurWithDetailsByCodeNafCriteria(String codeNaf) {
        return secteurRepository.findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(codeNaf);
    }
}
