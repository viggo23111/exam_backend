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
        race1 = new Race("Race1","Vigersalle 37","31/12/2022",80);
        race2 = new Race("Race2","Vigersalle 37","15/10/2022",60);
        race3 = new Race("Race3","Vigersalle 37","15/01/2022",40);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Race.deleteAllRows").executeUpdate();
            em.persist(race1);
            em.persist(race2);
            em.persist(race3);
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

}