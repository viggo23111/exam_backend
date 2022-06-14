package utils;


import entities.Race;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestRaces {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    Race race1 = new Race("Race1","Vigersalle 37","31/12/2022",80);
    Race race2 = new Race("Race2","Vigersalle 37","15/10/2022",60);
    Race race3 = new Race("Race3","Vigersalle 37","15/01/2022",40);
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

}
