package facades;

import dtos.CarDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CarFacadeTest {
    private static EntityManagerFactory emf;
    private static CarFacade facade;
    Car car1;
    Car car2;
    Driver driver1;
    Driver driver2;
    Driver driver3;
    Driver driver4;

    public CarFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CarFacade.getCarFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() throws ParseException {
        EntityManager em = emf.createEntityManager();
        car1 = new Car("car1","audi","RS 7",2022,"Audi sponsor","orange");
        car2 = new Car("car2","Mercedes","CLS 500",2022,"mercedes sponsor","black");
        driver1 = new Driver("Steve",1990,"10 years","male");
        driver2 = new Driver("Charles",1995,"5 years","male");
        driver3 = new Driver("Susan",1993,"4 years","female");
        driver4 = new Driver("Rachel",2000,"3 years","female");

        driver1.setCar(car1);
        driver2.setCar(car1);
        driver3.setCar(car2);
        driver4.setCar(car2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();

            em.persist(car1);
            em.persist(car2);
            em.persist(driver1);
            em.persist(driver2);
            em.persist(driver3);
            em.persist(driver4);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ShowAllCars() {
        System.out.println("Test show all cars");
        assertEquals(2,facade.getAllCars().size());
    }

    @Test
    void getDriversByCarID() {
        System.out.println("Gets drivers by car");
        System.out.println(facade.getDriversByCarID(car2.getId()));
        assertEquals(2,facade.getDriversByCarID(car1.getId()).size());
    }

    @Test
    void getCarByID() {
        System.out.println("Test get car by id");
        assertEquals("car1",facade.getCarByID(car1.getId()).getName());
    }

    @Test
    void createCar() {
        System.out.println("Test create car");
        CarDTO carDTO = new CarDTO(new Car("testCar","testbrand","testMake",2022,"testsponsor","testcolor"));
        facade.createCar(carDTO);
        assertEquals(3,facade.getAllCars().size());
    }

    @Test
    void removeDriverFromCar() {
        System.out.println("Test remove driver from car");
        facade.removeDriverFromCar(car1.getId(),driver1.getId());
        assertEquals(1,facade.getDriversByCarID(car1.getId()).size());
    }

    @Test
    void AddDriverToCar() {
        System.out.println("Test add driver to car");
        facade.addDriverToCar(car1.getId(), driver3.getId());
        assertEquals(3,facade.getDriversByCarID(car1.getId()).size());
    }

    @Test
    void updateCar() {
        System.out.println("Test update car");
        CarDTO carDTO = new CarDTO(car1);
        carDTO.setName("car1updated");
        assertEquals("car1updated",facade.updateCar(carDTO).getName());
    }
}