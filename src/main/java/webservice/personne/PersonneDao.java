package webservice.personne;

import com.app.entity.Personne;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PersonneDao {

    @PersistenceContext
    private EntityManager em;

    public List<Personne> findAll() {
        return em.createQuery(
            "SELECT p FROM Personne p ORDER BY p.nom,p.prenom",
            Personne.class
        ).getResultList();
    }


    public Personne findById(Long id) {
        return em.find(Personne.class, id);
    }

    public void save(Personne p) {
        em.persist(p);
    }
}
