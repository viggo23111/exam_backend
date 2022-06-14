package facades;

import dtos.CarDTO;
import dtos.RaceDTO;
import entities.Car;
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
    public RaceDTO getRaceByID(int id){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Race> query = em.createQuery("SELECT r FROM Race r where r.id=:id", Race.class);
            query.setParameter("id",id);
            Race race = query.getSingleResult();
            return new RaceDTO(race);
        } finally {
            em.close();
        }
    }

    public List<CarDTO> getCarsByRaceID(int raceID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c join Race r where r=c.races and r.id =:raceID", Car.class);
            query.setParameter("raceID", raceID);
            List<Car> cars = query.getResultList();
            return CarDTO.getDtos(cars);
        }finally {
            em.close();
        }
    }

    public RaceDTO updateRace(RaceDTO raceDTO) {
        EntityManager em = getEntityManager();
        Race race = em.find(Race.class, raceDTO.getId());
        if (race == null) {
            System.out.println("not found");
        }else {
            race.setName(raceDTO.getName());
            race.setLocation(raceDTO.getLocation());
            race.setStartDate(raceDTO.getStartDate());
            race.setDuration(raceDTO.getDuration());
            em.getTransaction().begin();
            em.merge(race);
            em.getTransaction().commit();
        }
        return new RaceDTO(race);
    }

    public RaceDTO removeCarFromRace(int raceID, int carID) {
        EntityManager em = emf.createEntityManager();
        try{
            Race race = em.find(Race.class,raceID);
            if(race == null){
                System.out.println("Race not found");
            }
            Car car = em.find(Car.class,carID);
            if(car == null){
                System.out.println("Car not found");
            }
            race.removeCar(car);
            RaceDTO updated = new RaceDTO(race);
            em.getTransaction().begin();
            em.merge(race);
            em.merge(car);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    public RaceDTO addCarToRace(int raceID, int carID) {
        EntityManager em = emf.createEntityManager();
        try{
            Race race = em.find(Race.class,raceID);
            if(race == null){
                System.out.println("Race not found");
            }
            Car car = em.find(Car.class,carID);
            if(car == null){
                System.out.println("Car not found");
            }
            race.addCar(car);
            RaceDTO updated = new RaceDTO(race);
            em.getTransaction().begin();
            em.merge(race);
            em.merge(car);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    public void deleteRace(int raceID) {
        EntityManager em = emf.createEntityManager();
        try {
            Race race = em.find(Race.class, raceID);
            if(race == null){
                System.out.println("Race with provided id was not found");
            }
            em.getTransaction().begin();
            em.remove(race);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

}
