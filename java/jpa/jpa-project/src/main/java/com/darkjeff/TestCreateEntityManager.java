package com.darkjeff;

import com.darkjeff.model.Pet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class TestCreateEntityManager {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-project");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        Pet pet = new Pet();
        pet.setName("Thunder");
        pet.setType("Rabbit");

        em.persist(pet);

        em.getTransaction().commit();

        Pet result = em.find(Pet.class, 2L);//Fetch from EntityManager cache, so no SQL fired

        Objects.nonNull(result);

        em.close();
        entityManagerFactory.close();

    }

}
