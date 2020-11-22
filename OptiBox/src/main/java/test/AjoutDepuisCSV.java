/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import io.exception.ReaderException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import modele.Box;
import modele.Instance;
import metier.InstanceReader;
import modele.Produit;

/**
 *
 * @author simon
 */
public class AjoutDepuisCSV {
    
    public static void main(String[] args) {
        
        ArrayList<Instance> LesInstances = getInstancesFromCSV("instances");
        System.out.println(LesInstances);
        final EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("OPTIBOXPU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                    
                for(Instance i : LesInstances){
                    System.out.println("On ajoute l'instance "+i+" : "
                            +i.getObjetsDeLInstance().size()+" objets");
                    em.persist(i);
                }
                
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
    
    
    public static ArrayList<Instance> getInstancesFromCSV(String chemin){
        ArrayList<Instance> l = new ArrayList<>();
        
        //On ouvre le dossier instances, pour récupérer les instances csv
        Path dir = Path.of(chemin);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                System.out.println("Ouverture du fichier "+file.getFileName());
                InstanceReader reader = new InstanceReader(chemin+"\\"+file.getFileName());
                l.add(reader.readInstance());
            }
        } catch (IOException | DirectoryIteratorException x) {            
            System.err.println(x);
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
        
        return l;
    }
    
}
