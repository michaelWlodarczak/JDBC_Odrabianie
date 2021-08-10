package com.sda.hibernate.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateDemo {
    public static void main(String[] args) {

        //niezbedne minimum do utworzenia sesji do pracy z hibernate
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Movie.class)  //dodawanie encji
                .addAnnotatedClass(Genre.class)
                .addAnnotatedClass(Actor.class)
                .buildSessionFactory();


    }
}
