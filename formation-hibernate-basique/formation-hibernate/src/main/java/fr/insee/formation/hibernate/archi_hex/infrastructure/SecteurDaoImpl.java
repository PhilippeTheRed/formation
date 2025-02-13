package fr.insee.formation.hibernate.archi_hex.infrastructure;

import org.springframework.stereotype.Repository;
import fr.insee.formation.hibernate.archi_hex.application.port.out.SecteurRepository;
import fr.insee.formation.hibernate.archi_hex.domain.Entreprise;
import fr.insee.formation.hibernate.archi_hex.domain.Entreprise_;
import fr.insee.formation.hibernate.archi_hex.domain.Secteur;
import fr.insee.formation.hibernate.archi_hex.domain.Secteur_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Repository
public class SecteurDaoImpl implements SecteurRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Secteur find(int id) {
        return entityManager.find(Secteur.class, id);
    }

    @Override
    public Secteur findByCodeNaf(String codeNaf) {
        return entityManager.createQuery("SELECT sect FROM Secteur sect WHERE sect.codeNaf = :codeNaf", Secteur.class)
                .setParameter("codeNaf", codeNaf).getSingleResult();
    }

    @Override
    public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(String codeNaf) {
        String requete = "SELECT secteur FROM Secteur secteur " + " JOIN FETCH secteur.indices indice "
                + " JOIN FETCH secteur.entreprises entreprise " + " JOIN FETCH entreprise.declarations declaration "
                + " WHERE secteur.codeNaf = :codeNaf ";

        TypedQuery<Secteur> query = entityManager.createQuery(requete, Secteur.class);
        query.setParameter("codeNaf", codeNaf);
        return query.getSingleResult();
    }

    @Override
    public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(String codeNaf) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Secteur> criteria = builder.createQuery(Secteur.class);
        Root<Secteur> root = criteria.from(Secteur.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Secteur_.codeNaf), codeNaf));

        root.fetch(Secteur_.indices, JoinType.INNER);
        Fetch<Secteur, Entreprise> fetchEntreprise = root.fetch(Secteur_.entreprises, JoinType.INNER);
        fetchEntreprise.fetch(Entreprise_.declarations, JoinType.INNER);

        return entityManager.createQuery(criteria).getSingleResult();
    }
}
