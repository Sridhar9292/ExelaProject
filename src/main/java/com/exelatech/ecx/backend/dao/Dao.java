package com.exelatech.ecx.backend.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Clase DAO (Data Access Object) para la base de datos que utiliza JPA para la
 * manipulación y ejecución de transacciones.
 */
public class Dao<T> {

    protected EntityManagerFactory factory;
    protected EntityManager em; // donde se inicializa ?

    public Dao(String dataSource) {
        factory = Persistence.createEntityManagerFactory(dataSource);
        em = factory.createEntityManager();
    }

    @SuppressWarnings("unchecked")
    public List<T> selectMultiple(String namedQuery, String name, Object parameter) {
        Query query = em.createNamedQuery(namedQuery);
        query.setParameter(name, parameter);

        try {

            return query.getResultList();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");// Colored red
            return new ArrayList<T>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<T> selectMultiple(String namedQuery, HashMap<String, Object> parameters) {
        Query query = em.createNamedQuery(namedQuery);
        for (String key : parameters.keySet()) {
            query.setParameter(key, parameters.get(key));
        }

        try {

            return query.getResultList();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");// Colored red
            return new ArrayList<T>();
        }

    }

    @SuppressWarnings("unchecked")
    public List<T> selectMultiple(String namedQuery) {
        Query query = em.createNamedQuery(namedQuery);

        try {

            return query.getResultList();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");// Colored red
            return new ArrayList<T>();
        }

    }

    public void Close() {
        if (em.isOpen()) {
            em.clear();
            em.close();
        }
        if (factory.isOpen())
            factory.close();
    }

}
