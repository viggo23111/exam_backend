package utils;


import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    User user = new User("user", "test123");
    User driver = new User("driver", "test123");
    User admin = new User("admin", "test123");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role driverRole = new Role("driver");
    Role adminRole = new Role("admin");
    user.setRole(userRole);
    admin.setRole(adminRole);
    driver.setRole(driverRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(driverRole);
    em.persist(user);
    em.persist(driver);
    em.persist(admin);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
