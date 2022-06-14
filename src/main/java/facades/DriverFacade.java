package facades;

import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class DriverFacade {

    private static DriverFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DriverFacade() {
    }

    public static DriverFacade getDriverFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DriverFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<DriverDTO> getAllDrivers(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d", Driver.class);
            List<Driver> drivers = query.getResultList();
            return DriverDTO.getDtos(drivers);
        } finally {
            em.close();
        }
    }

    public DriverDTO createDriver(DriverDTO driverDTO){
        User user = new User(driverDTO.getUserName(), driverDTO.getPassword());
        Driver driver = new Driver(driverDTO.getName(), driverDTO.getBirthYear(),driverDTO.getExperience(),driverDTO.getGender());
        driver.setUser(user);
        EntityManager em = getEntityManager();
        try {
            Role driverRole =em.find(Role.class,"driver");
            user.setRole(driverRole);
            em.getTransaction().begin();
            em.persist(user);
            em.persist(driver);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DriverDTO(driver);
    }


    public List<RaceDTO> getRacesByUserID(int userID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("SELECT D FROM Driver d join User u where u=d.user and u.id =:userID", Driver.class);
            query.setParameter("userID", userID);
            Driver driver = query.getSingleResult();
            Car car = driver.getCar();
            List<Race> races = car.getRaces();
            return RaceDTO.getDtos(races);
        }finally {
            em.close();
        }
    }
}
