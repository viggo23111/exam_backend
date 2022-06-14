package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestData {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    User user = new User("user", "test123");
    User driver1user = new User("driver1", "test123");
    User driver2user = new User("driver2", "test123");
    User driver3user = new User("driver3", "test123");
    User driver4user = new User("driver4", "test123");
    User admin = new User("admin", "test123");

    Race race1 = new Race("Race1","Vigersalle 37","31/12/2022",80);
    Race race2 = new Race("Race2","Vigersalle 37","15/10/2022",60);
    Race race3 = new Race("Race3","Vigersalle 37","15/01/2022",40);

    Car car1 = new Car("car1","audi","RS 7",2022,"Audi sponsor","orange","https://cdn.mandesager.dk/wp-content/uploads/2021/10/Mansory-Audi-RS7_1.jpg");
    Car car2 = new Car("car2","Mercedes","CLS 500",2022,"mercedes sponsor","black","https://media.evo.co.uk/image/private/s--4SeXnIe1--/v1556261698/evo/images/dir_801/car_photo_400801.jpg");

    Driver driver1 = new Driver("Steve",1990,"10 years","male");
    Driver driver2 = new Driver("Charles",1995,"5 years","male");
    Driver driver3 = new Driver("Susan",1993,"4 years","female");
    Driver driver4 = new Driver("Rachel",2000,"3 years","female");

    Role userRole = new Role("user");
    Role driverRole = new Role("driver");
    Role adminRole = new Role("admin");

    user.setRole(userRole);
    admin.setRole(adminRole);
    driver1user.setRole(driverRole);
    driver2user.setRole(driverRole);
    driver3user.setRole(driverRole);
    driver4user.setRole(driverRole);

    driver1.setCar(car1);
    driver2.setCar(car1);
    driver3.setCar(car2);
    driver4.setCar(car2);

    driver1.setUser(driver1user);
    driver2.setUser(driver2user);
    driver3.setUser(driver3user);
    driver4.setUser(driver4user);

    race1.addCar(car1);
    race1.addCar(car2);
    race2.addCar(car1);

    try {
      em.getTransaction().begin();
      em.createNamedQuery("Driver.deleteAllRows").executeUpdate();
      em.createNamedQuery("Race.deleteAllRows").executeUpdate();
      em.createNamedQuery("Car.deleteAllRows").executeUpdate();
      em.persist(userRole);
      em.persist(adminRole);
      em.persist(driverRole);

      em.persist(user);
      em.persist(driver1user);
      em.persist(driver2user);
      em.persist(driver3user);
      em.persist(driver4user);
      em.persist(admin);

      em.persist(race1);
      em.persist(race2);
      em.persist(race3);
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
