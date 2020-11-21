/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import modele.Box;
import modele.Instance;
import modele.Produit;

/**
 *
 * @author simon
 */
public class TestEnti {
    public static void main(String[] args) {
        final EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("OPTIBOXPU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                // creation d’une entite persistante
                Box b1 = new Box("B00",200,125,300);
                Box b2 = new Box("B01",400,60,100);
                Box b3 = new Box("B02",600,150,250);             
                
                System.out.println("On crée les 3 Box");
                                
                Produit p1 = new Produit("P00",50,10,5);
                Produit p2 = new Produit("P01",30,10,1);
                Produit p3 = new Produit("P02",60,60,3);
                Produit p4 = new Produit("P03",80,20,7);
                Produit p5 = new Produit("P04",20,60,2);

                Instance i = new Instance("Instance_Test1");
                
                i.ajouterObjet(b1);
                i.ajouterObjet(b2);
                i.ajouterObjet(b3);
                i.ajouterObjet(p1);
                i.ajouterObjet(p2);
                i.ajouterObjet(p3);
                i.ajouterObjet(p4);
                i.ajouterObjet(p5);
                
                em.persist(i);
                
                et.commit();
                System.out.println("EZ");
            } 
            catch (Exception ex) {
                System.out.println(ex);
                System.out.println("AAAAAAAAAh");
                et.rollback();
            }
        }
        finally {
            if(em != null && em.isOpen()){
                em.close();
            }
            if(emf != null && emf.isOpen()){
                emf.close();
            }
        }
    }
}
