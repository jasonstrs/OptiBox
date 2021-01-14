/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import metier.DBRequests;
import modele.Box;
import modele.Instance;
import modele.Produit;
import modele.Solution;

/**
 *
 * @author simon
 */
public class TestSolution {
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
                                
                Produit p1 = new Produit("P00",50,10,5,Color.BLACK);
                Produit p2 = new Produit("P01",30,10,1,Color.RED);
                Produit p3 = new Produit("P02",60,60,3,Color.BLUE);
                Produit p4 = new Produit("P03",80,20,7,Color.GREEN);
                Produit p5 = new Produit("P04",20,60,2,Color.ORANGE);

                Instance i = new Instance("Instance_Test1");
                
                i.ajouterObjet(b1);
                i.ajouterObjet(b2);
                i.ajouterObjet(b3);
                i.ajouterObjet(p1);
                i.ajouterObjet(p2);
                i.ajouterObjet(p3);
                i.ajouterObjet(p4);
                i.ajouterObjet(p5);
                
                Solution s = new Solution(i);
                s.TestCalculerSolution();
                System.out.println(s);
                System.out.println(s.getMesSolutionBox().size());
                
                
                em.persist(s);
                
                et.commit();
                
                DBRequests dbr = DBRequests.getInstance();
                Solution sRecupFromBDD = dbr.getSolutionFromInstance(i,false);
                sRecupFromBDD.afficher();
                                
            } 
            catch (Exception ex) {
                System.out.println(ex);
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
