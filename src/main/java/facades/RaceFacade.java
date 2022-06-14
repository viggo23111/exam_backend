package facades;

import dtos.RaceDTO;
import entities.Race;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public class RaceFacade {


    private static RaceFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private RaceFacade() {
    }

    public static RaceFacade getRaceFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RaceFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<RaceDTO> getAllRaces(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Race> query = em.createQuery("SELECT r FROM Race r", Race.class);
            List<Race> races = query.getResultList();
            return RaceDTO.getDtos(races);
        } finally {
            em.close();
        }
    }

    public RaceDTO createRace(RaceDTO raceDTO){
        Race race = new Race(raceDTO.getName(),raceDTO.getLocation(), raceDTO.getStartDate(), raceDTO.getDuration());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(race);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RaceDTO(race);
    }
}
