package facades;

import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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


    public List<RaceDTO> getRacesByDriverID(int driverID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Race> query = em.createQuery("SELECT d FROM Driver d WHERE d.car.id=:driverID ", Race.class);
            query.setParameter("driverID", driverID);
            List<Race> races = query.getResultList();
            if(races.size()==0) {
                System.out.println("None races on that driver");
            }
            return RaceDTO.getDtos(races);
        }finally {
            em.close();
        }
    }
}
