package webservice.demandeur;

import com.app.entity.Demandeur;
import com.app.entity.Personne;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DemandeurDao {

    @PersistenceContext
    private EntityManager em;

    public List<Demandeur> findAll() {
        return em.createQuery(
            "SELECT d FROM Demandeur d ",
            Demandeur.class
        ).getResultList();
    }
    public List<Demandeur> demandeurAutoEmploi() {
        return em.createQuery(
            "SELECT d FROM Demandeur d where d.autoemploi=1",
            Demandeur.class
        ).getResultList();
    }


    public Demandeur findById(Long id) {
        return em.find(Demandeur.class, id);
    }

    public void save(Demandeur d) {
        em.persist(d);
    }
}
