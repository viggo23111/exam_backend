package facades;

import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.*;
import errorhandling.UsernameTakenException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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
    private static boolean usernameTaken(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u from User u where u.userName =:username", User.class);
            query.setParameter("username", username);
            try {
                User userFound = query.getSingleResult();
            } catch (NoResultException e) {
                return false;
            }
        } finally {
            em.close();
        }
        return true;
    }

    public DriverDTO createDriver(DriverDTO driverDTO) throws UsernameTakenException {
        if (usernameTaken(driverDTO.getUserName())) {
            throw new UsernameTakenException("Username is taken");
        }
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
    public DriverDTO getDriverByID(int id){
        EntityManager em = emf.createEntityManager();
        try {
            return new DriverDTO( em.find(Driver.class,id));
        } finally {
            em.close();
        }
    }

    public DriverDTO updateDriver(DriverDTO driverDTO) {
        EntityManager em = getEntityManager();
        Driver driver = em.find(Driver.class, driverDTO.getId());
        if (driver == null) {
            System.out.println("not found");
        }else {
            driver.setName(driverDTO.getName());
            driver.setBirthYear(driverDTO.getBirthYear());
            driver.setExperience(driverDTO.getExperience());
            driver.setGender(driverDTO.getGender());
            em.getTransaction().begin();
            em.merge(driver);
            em.getTransaction().commit();
        }
        return new DriverDTO(driver);
    }
}
