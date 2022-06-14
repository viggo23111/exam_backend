package facades;

import dtos.CarDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Race;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RaceFacadeTest {

    private static EntityManagerFactory emf;
    private static RaceFacade facade;
    Car car1;
    Car car2;
    Race race1;
    Race race2;
    Race race3;

    public RaceFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RaceFacade.getRaceFacadeExample(emf);
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
        race1 = new Race("Race1","Vigersalle 37","31/12/2022",80);
        race2 = new Race("Race2","Vigersalle 37","15/10/2022",60);
        race3 = new Race("Race3","Vigersalle 37","15/01/2022",40);

        race1.addCar(car1);
        race1.addCar(car2);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            em.persist(race1);
            em.persist(race2);
            em.persist(race3);
            em.persist(car1);
            em.persist(car2);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ShowAllRaces() {
        System.out.println("Test show all races");
        assertEquals(3,facade.getAllRaces().size());
    }

    @Test
    void createRace() {
        System.out.println("Test create Race");
        RaceDTO raceDTO = new RaceDTO(new Race("testRace","Lyngby vej 23","25/07/2022",180));
        facade.createRace(raceDTO);
        assertEquals(4,facade.getAllRaces().size());
    }

    @Test
    void getRaceByID() {
        System.out.println("Test get race by id");
        assertEquals("Race2",facade.getRaceByID(race2.getId()).getName());
    }

    @Test
    void getCarsByRaceID() {
        System.out.println("Test get cars by RaceID");
        assertEquals(2,facade.getCarsByRaceID(race1.getId()).size());
    }

    @Test
    void updateRace() {
        System.out.println("Test update race");
        RaceDTO raceDTO = new RaceDTO(race1);
        raceDTO.setName("race1Updated");
        raceDTO.setDuration(99);
        raceDTO.setLocation("vigerslev test");
        raceDTO.setStartDate("31/11/2022");
        assertEquals("race1Updated",facade.updateRace(raceDTO).getName());
    }

    @Test
    void removeCarFromRace() {
        System.out.println("Test remove car from race");
        facade.removeCarFromRace(race1.getId(),car1.getId());
        assertEquals(1,facade.getCarsByRaceID(race1.getId()).size());
    }

    @Test
    void AddCarToRace() {
        System.out.println("Test add car to race");
        facade.addCarToRace(race3.getId(),car1.getId());
        assertEquals(1,facade.getCarsByRaceID(race3.getId()).size());
    }
}