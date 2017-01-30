import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ManageLlibre {

    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        listLlibre();

    }

    //Permite añadir un libro
    public static void añadirlibro() {

        ManageLlibre ML = new ManageLlibre();

        Scanner sc = new Scanner(System.in);
        String titol, editoral;
        int exemplars, pagines, any;

        System.out.println("Sisplau introduiex les dades del nou llibre");
        System.out.println("Titol");
        titol = sc.nextLine();
        System.out.println("editorial");
        editoral = sc.nextLine();
        System.out.println("numero d'exemplars");
        exemplars = sc.nextInt();
        System.out.println("numero de pagines");
        pagines = sc.nextInt();
        System.out.println("any de publicació");
        any = sc.nextInt();

        Integer libID1 = ML.addLlibre(titol, exemplars, editoral, pagines, any);

    }

    //Mete el libre creado por el usuario en la base de datos
    public Integer addLlibre(String titol, int nombre_exemplars, String editorial, int nombre_pagines, int any_edicio) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer llibreID = null;
        try {
            tx = session.beginTransaction();
            llibre llibre = new llibre(titol, nombre_exemplars, editorial, nombre_pagines, any_edicio);
            llibreID = (Integer) session.save(llibre);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return llibreID;
    }

    //lista los libros de la base de datos
    public static void listLlibre(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List llibre = session.createQuery("FROM llibre").list();
            for (Iterator iterator =
                 llibre.iterator(); iterator.hasNext();){
                llibre libro = (llibre) iterator.next();
                System.out.print("Id: " + libro.getLlibre_id());
                System.out.print(" Titol: " + libro.getTitol());
                System.out.print(" Editorial: " + libro.getEditorial());
                System.out.print(" Any d'edició: " + libro.getAny_edicio());
                System.out.print(" numero de pagines: " + libro.getNombre_pagines());
                System.out.print(" numero d'exemplars: " + libro.getNombre_exemplars());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    //Actualiza un libro de la base de datos
    public void updateLlibre(int llibre_id, String titol, int nombre_exemplars, String editorial, int nombre_pagines, int any_edicio) {

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            llibre llibre = (llibre) session.get(llibre.class, llibre_id);
            llibre.setTitol(titol);
            llibre.setNombre_exemplars(nombre_exemplars);
            llibre.setEditorial(editorial);
            llibre.setNombre_pagines(nombre_pagines);
            llibre.setAny_edicio(any_edicio);
            session.update(llibre);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //Elimina un libro de la base de datos
    public void deleteLlibre(int llibre_id){
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            llibre llibre = (llibre)session.get(llibre.class, llibre_id);
            session.delete(llibre);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
