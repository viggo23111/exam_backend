package utils;

import entities.Car;
import entities.Driver;
import entities.Race;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestCars {

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Car car1 = new Car("car1","audi","RS 7",2022,"Audi sponsor","orange","img");
        Car car2 = new Car("car2","Mercedes","CLS 500",2022,"mercedes sponsor","black","img");
        Driver driver1 = new Driver("Steve",1990,"10 years","male");
        Driver driver2 = new Driver("Charles",1995,"5 years","male");
        Driver driver3 = new Driver("Susan",1993,"4 years","female");
        Driver driver4 = new Driver("Rachel",2000,"3 years","female");

        driver1.setCar(car1);
        driver2.setCar(car1);
        driver3.setCar(car2);
        driver4.setCar(car2);

        try {
            em.getTransaction().begin();
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

}
