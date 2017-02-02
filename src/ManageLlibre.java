import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageLlibre {

    private static SessionFactory factory;

    public static void main(String[] args) throws ParseException {

        Scanner sc = new Scanner(System.in);

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManageLlibre ML = new ManageLlibre();

        int aux;
        System.out.println("Dame una opcion");
        aux = sc.nextInt();
        switch (aux){
            case 1:
                ML.listLlibre();
                break;
            case 2:
                ML.listSoci();
                break;
            case 3:
                ML.añadirlibro();
                break;
            case 4:
                ML.añadirsoci();
                break;
            case 5:
                ML.añadirPrestec();
                break;
        }



        /* List down all the employees */


/* Update employee's records */
        // ME.updateEmployee(empID1, 5000);
/* Delete an employee from the database */
        //ME.deleteEmployee(empID2);
/* List down new list of the employees */

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
                System.out.println("      ");
                System.out.println("   -------------------------    ");
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

    public static void añadirsoci() {

        ManageLlibre ML = new ManageLlibre();

        Scanner sc = new Scanner(System.in);
        String nom, cognom, direccio;
        int edat, telefon;

        Integer  empID1 = ML.addSocis( "Cristian", "Ramirez", 21, "En casa de su suegros", 645217942);
        Integer  empID2 = ML.addSocis( "Fabian", "Puig", 40, "Castellon", 611926452);
        Integer  empID3 = ML.addSocis( "Marcos", "Canton", 33, "a unos 20 minutos del insti", 688214239);


        System.out.println("Sisplau introduiex les dades del nou soci");

        System.out.println("Nom");
        nom = sc.nextLine();
        System.out.println("Cognom");
        cognom = sc.nextLine();
        System.out.println("Direccio");
        direccio = sc.nextLine();
        System.out.println("Edat");
        edat = sc.nextInt();
        System.out.println("Telefon");
        telefon = sc.nextInt();


        Integer sociID1 = ML.addSocis(nom, cognom, edat, direccio, telefon);

    }


    public Integer addSocis(String nom, String cognom, int edat, String direccio, int telefon){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer sociID = null;
        try{
            tx = session.beginTransaction();
            Soci soci = new Soci(nom, cognom, edat, direccio, telefon);
            sociID = (Integer) session.save(soci);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return sociID;
    }
    /* Method to READ all the employees */
    public void listSoci( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List socis = session.createQuery("FROM Soci").list();
            for (Iterator iterator =
                 socis.iterator(); iterator.hasNext();){
                Soci soci = (Soci) iterator.next();
                System.out.print(" Id: " + soci.getSoci_id());
                System.out.print(" Nom: " + soci.getNom());
                System.out.println(" Cognom: " + soci.getCognom());
                System.out.print(" edat: " + soci.getEdat());
                System.out.print(" direcció: " + soci.getDireccio());
                System.out.println(" telefon: " + soci.getTelefon());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public static void añadirPrestec() throws ParseException {

        ManageLlibre ML = new ManageLlibre();

        Scanner sc = new Scanner(System.in);
        String llibre, soci, fecha_inici, fecha_fi;
        int llibre_id, soci_id;
        Date data_inici, data_final;

        System.out.println("Sisplau introduiex les dades del nou prestec");

        System.out.println("Nom del soci");
        soci = sc.nextLine();

        System.out.println("Nom del llibre");
        llibre = sc.nextLine();

        System.out.println("Data de inici de prestec  amb el format dd/MM/yyyy");
        //fecha_inici = sc.nextLine();
        Calendar calendario = GregorianCalendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        data_inici  = calendario.getTime();
        System.out.println(data_inici);

        System.out.println("Data de fi de prestec amb el format dd/MM/yyyy");
        fecha_fi = sc.next();


        data_final = dateFormat.parse(fecha_fi) ;

        System.out.println("id del soci que demana el prestec");
        soci_id = sc.nextInt();

        System.out.println("id del llibre que vol agafar");
        llibre_id = sc.nextInt();



        Integer PrestecID1 = ML.addPrestec(llibre_id, soci_id, llibre, soci, data_inici, data_final);

    }


    public Integer addPrestec(int llibre_id, int soci_id, String llibre, String soci, Date data_inici, Date data_final){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer PrestecID = null;
        try{
            tx = session.beginTransaction();
            Prestec prestec = new Prestec(llibre_id, soci_id, llibre, soci, data_inici, data_final);
            PrestecID = (Integer) session.save(prestec);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return PrestecID;
    }

    public void listPrestec( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List prestecs = session.createQuery("FROM Prestec").list();
            for (Iterator iterator =
                 prestecs.iterator(); iterator.hasNext();){
                Prestec prestec = (Prestec) iterator.next();
                System.out.print(" Id: " + prestec.getId());
                System.out.print(" Llibre:" + prestec.getLlibre());
                System.out.println(" Id del llibre:" + prestec.getLlibre_id());
                System.out.print(" Nom del soci: " + prestec.getSoci());
                System.out.print(" id del soci: " + prestec.getSoci_id());
                System.out.println(" data d'inici: " + prestec.getData_inici());
                System.out.print(" data final: " + prestec.getData_final());

            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }


}
