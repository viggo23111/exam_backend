package facades;

import entities.Car;
import entities.Driver;
import entities.Race;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverFacadeTest {
    private static EntityManagerFactory emf;
    private static DriverFacade facade;
    Race race1;
    Race race2;
    Race race3;
    Car car1;
    Car car2;
    Driver driver1;
    Driver driver2;
    Driver driver3;
    Driver driver4;

    public DriverFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DriverFacade.getDriverFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() throws ParseException {
        EntityManager em = emf.createEntityManager();
        race1 = new Race("Race1","Vigersalle 37","31/12/2022",80);
        race2 = new Race("Race2","Vigersalle 37","15/10/2022",60);
        race3 = new Race("Race3","Vigersalle 37","15/01/2022",40);
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
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
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
    void ShowAllDriversTest() {
        System.out.println("Test show all cars");
        assertEquals(4,facade.getAllDrivers().size());
    }

}