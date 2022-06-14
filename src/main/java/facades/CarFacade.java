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
import java.util.Collection;
import java.util.List;

public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CarFacade() {
    }

    public static CarFacade getCarFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<CarDTO> getAllCars(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = query.getResultList();
            return CarDTO.getDtos(cars);
        } finally {
            em.close();
        }
    }

    public CarDTO createCar(CarDTO carDTO){
        Car car = new Car(carDTO.getName(), carDTO.getBrand(), carDTO.getMake(), carDTO.getYear(), carDTO.getSponsor(), carDTO.getColor(), carDTO.getImage());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CarDTO(car);
    }


    public CarDTO getCarByID(int id){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c where c.id=:id", Car.class);
            query.setParameter("id",id);
            Car car = query.getSingleResult();
            return new CarDTO(car);
        } finally {
            em.close();
        }
    }

    public List<DriverDTO> getDriversByCarID(int carID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d WHERE d.car.id=:carID ", Driver.class);
            query.setParameter("carID", carID);
            List<Driver> drivers = query.getResultList();
            if(drivers.size()==0) {
                System.out.println("None drivers on that car");
                // throw new NotFoundException("Provided Zipcode not found");
            }
            return DriverDTO.getDtos(drivers);
        }finally {
            em.close();
        }
    }

    public CarDTO removeDriverFromCar(int carID, int driverID) {
        EntityManager em = emf.createEntityManager();
        try{
            Car car = em.find(Car.class,carID);
            if(car == null){
                System.out.println("Car not found");
            }
            Driver driver = em.find(Driver.class,driverID);
            if(driver == null){
                System.out.println("Driver not found");
            }
            car.removeDriver(driver);
            CarDTO updated = new CarDTO(car);
            em.getTransaction().begin();
            em.merge(car);
            em.merge(driver);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    public CarDTO addDriverToCar(int carID, int driverID) {
        EntityManager em = emf.createEntityManager();
        try{
            Car car = em.find(Car.class,carID);
            if(car == null){
                System.out.println("Car not found");
            }
            Driver driver = em.find(Driver.class,driverID);
            if(driver == null){
                System.out.println("Driver not found");
            }
            car.addDriver(driver);
            CarDTO updated = new CarDTO(car);
            em.getTransaction().begin();
            em.merge(car);
            em.merge(driver);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    public CarDTO updateCar(CarDTO carDTO) {
        System.out.println(carDTO.getName());
        EntityManager em = getEntityManager();
        Car car = em.find(Car.class, carDTO.getId());
        if (car == null) {
            System.out.println("not found");
        }else {
            car.setName(carDTO.getName());
            car.setBrand(carDTO.getBrand());
            car.setMake(carDTO.getMake());
            car.setYear(carDTO.getYear());
            car.setColor(carDTO.getColor());
            car.setSponsor(carDTO.getSponsor());
            car.setImage(carDTO.getImage());

            em.getTransaction().begin();
            em.merge(car);
            em.getTransaction().commit();
        }
        return new CarDTO(car);
    }
}
