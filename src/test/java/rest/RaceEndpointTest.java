package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class RaceEndpointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    Driver driver1;
    Driver driver2;
    Car car1;
    Race race1;
    Race race2;
    User driver1user;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }


    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from Driver").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();


            Role driverRole = new Role("driver");
            Role adminRole = new Role("admin");
            User admin = new User("admin", "test");
            driver1user = new User("driver", "test");
            driver1 = new Driver("Steve",1990,"10 years","male");
            driver2 = new Driver("Charles",1995,"5 years","male");
            car1 = new Car("car1","audi","RS 7",2022,"Audi sponsor","orange","image");
            race1 = new Race("Race1","Vigersalle 37","31/12/2022",80);
            race2 = new Race("Race2","Vigersalle 37","15/10/2022",60);

            race1.addCar(car1);
            race2.addCar(car1);
            driver1.setCar(car1);

            admin.setRole(adminRole);
            driver1user.setRole(driverRole);

            driver1.setUser(driver1user);

            em.persist(adminRole);
            em.persist(driverRole);
            em.persist(admin);
            em.persist(driver1user);
            em.persist(driver1);
            em.persist(driver2);
            em.persist(car1);
            em.persist(race1);
            em.persist(race2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void TestRaceCreated() {
        Race race = new Race("Race3","Vigersalle 37","15/10/2022",90);
        RaceDTO raceDTO = new RaceDTO(race);
        String requestBody = GSON.toJson(raceDTO);

        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .when()
                .post("/race")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Race3"))
                .body("location", equalTo("Vigersalle 37"));
    }

    @Test
    void getRaceByID() {
        login("admin", "test");
        RaceDTO raceDTO;
        raceDTO = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/race/"+race1.getId()).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject("",RaceDTO.class);
        RaceDTO raceDTO1 = new RaceDTO(race1);
        assertThat(raceDTO1,equalTo(raceDTO));
    }

    @Test
    void getCarsByRaceID() {
        login("admin", "test");
        List<CarDTO> carDTOS;
        carDTOS = given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/race/"+race1.getId()+"/cars/").then()
                .statusCode(200)
                .extract().body().jsonPath().getList("",CarDTO.class);
        CarDTO carDTO1 = new CarDTO(car1);
        assertThat(carDTOS,containsInAnyOrder(carDTO1));
    }

    @Test
    public void TestRaceUpdated() {
        RaceDTO raceDTOUpdated = new RaceDTO(race1);
        raceDTOUpdated.setName("raceNameUpdated");
        String requestBody = GSON.toJson(raceDTOUpdated);
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .and()
                .body(requestBody)
                .when()
                .put("/race/edit")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(race1.getId()))
                .body("name", equalTo("raceNameUpdated"));
    }

    @Test
    public void testDeleteRaceByID() {
        login("admin", "test");
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .delete("/race/delete/{id}", race1.getId())
                .then()
                .statusCode(200);
    }

}
