/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import modele.Box;
import modele.Instance;
import modele.Objet_d_Instance;
import modele.PileDeProduits;
import modele.Produit;
import modele.Solution;
import modele.SolutionBox;

/**
 *
 * @author simon
 */
public class DBRequests {
    
    /**
     * Connexion à la BDD
     */
    public Connection conn;
    
    /**
     * Liste de toutes les instances de la BDD
     */
    public List<Instance> ToutesLesInstances;
    
    /**
     * Instance unique de DBRequests (modèle singleton)
     */
    private static DBRequests dbr = null;
    
    /**
     * Constructeur par défaut privé : on ne peut pas instancier cette classe
     * Ainsi, on crée une instance, et on la partage avec ceux qui en ont besoin
     * @throws SQLNonTransientConnectionException
     * @throws Exception 
     */
    private DBRequests() throws Exception{
        ToutesLesInstances = new ArrayList<>();
        connect();           
    }
    
    /**
     * Permet d'utiliser le modèle singleton : 
     * On ne peut pas instancier un DBRequests, mais 
     * seulement récupérer l'unique instance de la classe
     * (ou la créer si elle n'existe pas)
     * @return La seule instance de DBRequests
     * @throws Exception 
     */
    public static DBRequests getInstance() throws Exception{
        if(dbr == null)
            dbr = new DBRequests();
        return dbr;
    }
    
    

    
    private void connect() throws Exception{
        String driverClass="org.apache.derby.jdbc.ClientDriver";
        String urlDatabase="jdbc:derby://localhost:1527/OptiBoxDB";
        String user = "root";
        String pass = "mysql";
        Class.forName(driverClass);
        conn=DriverManager.getConnection(urlDatabase,user,pass);
    }
    
    /**
     * Récupère toutes les instances de la BDD, avec leurs objets
     */
    public void getAllInstances() throws Exception{
        try{
            if(dbr == null)getInstance();
        
            String requete;
            ToutesLesInstances.clear();
            requete = "SELECT * FROM INSTANCE c ORDER BY NOM";
            ResultSet res;
            int compteur=0; // permet de gérer la quantité avec la même couleur
            Color c=null;

            Statement stmt = conn.createStatement();
            res = stmt.executeQuery(requete);
            while (res.next()) {
                Long ID = res.getLong("ID");
                String requeteObjets = "SELECT * FROM OBJET_D_INSTANCE o, INSTANCE i WHERE i.ID = o.MONINSTANCE AND i.ID = ? ORDER BY o.dtype, o.groupe, o.idobjet";
                ResultSet resObjets;

                PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
                pstmtObjets.setLong(1, ID);
                resObjets = pstmtObjets.executeQuery();
                ArrayList<modele.Objet_d_Instance> l = new ArrayList<>();
                while (resObjets.next()) {
                    switch(resObjets.getString("DTYPE")){
                        case "Box":
                            l.add(new Box(resObjets.getString("IDOBJET"),
                                    resObjets.getInt("LARGEUR"),resObjets.getInt("HAUTEUR"),resObjets.getFloat("PRIX")));
                            break;
                        case "Produit":
                            l.add(new Produit(resObjets.getString("IDOBJET"),
                                    resObjets.getInt("LARGEUR"),resObjets.getInt("HAUTEUR"),resObjets.getInt("QUANTITE"),new Color(resObjets.getInt("COLOR")),resObjets.getInt("GROUPE")));
                            break;
                    }           
                }

                ToutesLesInstances.add(new Instance(ID,res.getString("NOM"),l));
            }
            res.close();
            stmt.close();
            System.out.println("On a récupéré toutes les instances : ");
            for(Instance currI : this.ToutesLesInstances){
                System.out.println(currI.getNom()+" : "+currI.getObjetsDeLInstance().size()+" objets.");
                System.out.println(currI.getObjetsDeLInstance());
            }
        }
        catch(Exception e){
            throw new Exception("Erreur lors de la récupération des Instances : "+e);
        }
        
        
    }

    /**
     * Récupère la solution liée à une instance
     * @param i l'instance dont on veut la solution
     * @param justAVerif s'il vaut true on veut juste savoir si il y a une solution
     *                      s'il vaut false on veut récupérer un objet solution
     * @return La solution
     */
    public Solution getSolutionFromInstance(Instance i,boolean justAVerif) throws SQLException{
        int ID_Sol = -1;
        
        String requeteObjets = "SELECT * FROM SOLUTION s WHERE s.INSTANCE = ?";
        ResultSet resObjets;

        PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
        pstmtObjets.setLong(1, i.getId());
        resObjets = pstmtObjets.executeQuery();
        while (resObjets.next()) {
                     
             ID_Sol = resObjets.getInt("ID");
            
        }
        resObjets.close();
        pstmtObjets.close();
        
        if(ID_Sol == -1){
            System.out.println("Aucune Solution pour cette instance");
            return null;
        }
        
        if (justAVerif)
            return new Solution(); // on return un objet différent de null pour dire qu'il y a une solution 
        
        System.out.println("La Solution de cette instance a pour id "+ID_Sol);
        
        return getSolutionFromID(ID_Sol,i);
        
    }
    
    public Solution getSolutionFromID(int ID_Sol,Instance i) throws SQLException {
        Solution s = new Solution(i);
        System.out.println("On a l'id, on cherche la solution");
        String requeteObjets = "SELECT * FROM SOLUTIONBOX sb WHERE sb.MASOLUTION = ?";
        ResultSet resObjets;

        PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
        pstmtObjets.setInt(1, ID_Sol);
        
        resObjets = pstmtObjets.executeQuery();
        ArrayList<SolutionBox> l = new ArrayList<>();
        while (resObjets.next()) {
            Box b = getBoxFromID(resObjets.getInt("TYPEDEBOX"));
            if(b==null)continue;
            SolutionBox sb = new SolutionBox(b,s);
            sb.setId(resObjets.getInt("ID"));
            sb.setMesPiles(getPilesSolutionBox(sb));
            
            l.add(sb);
        }
        resObjets.close();
        pstmtObjets.close();
        
        s.setMesSolutionBox(l);
        
        
        return s;
    }
    
    private Box getBoxFromID(int id) throws SQLException {
        Box laBox = null;
        
        String requeteObjets = "SELECT * FROM OBJET_D_INSTANCE o WHERE o.DTYPE LIKE 'Box' AND o.IDGENERE = ?";
        ResultSet resObjets;

        PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
        pstmtObjets.setInt(1, id);
        resObjets = pstmtObjets.executeQuery();
        while (resObjets.next()) {
             //Box(String id,int l,int h,double prix)        
             laBox = new Box(resObjets.getString("IDOBJET"),resObjets.getInt("LARGEUR"),resObjets.getInt("HAUTEUR"),resObjets.getDouble("PRIX"));
            
        }
        resObjets.close();
        pstmtObjets.close();
        
        if(laBox == null){
            System.out.println("Aucune Box avec cet ID");
            return null;
        }       
        
        return laBox;
    }
    
    private List<PileDeProduits> getPilesSolutionBox(SolutionBox sb) throws SQLException {
        ArrayList<PileDeProduits> l = new ArrayList<PileDeProduits>();
        
        String requeteObjets = "SELECT * FROM PILEDEPRODUITS p WHERE p.MABOX = ?";
        ResultSet resObjets;

        PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
        pstmtObjets.setInt(1, sb.getId());
        resObjets = pstmtObjets.executeQuery();
        while (resObjets.next()) {
             
            PileDeProduits pp = new PileDeProduits(sb);
            pp.setMESPRODUITS(remplirPileAvecProduits(resObjets.getInt("ID")));
            pp.UpdateTaille();
            l.add(pp); 
             
            
        }
        resObjets.close();
        pstmtObjets.close();
        
        return l;
        
    }
    
    
    private LinkedList<Produit> remplirPileAvecProduits(int idPile) throws SQLException {
        String requeteObjets = "SELECT * FROM OBJET_D_INSTANCE o WHERE o.DTYPE LIKE 'Produit' AND o.MAPILE=?";
        ResultSet resObjets;

        PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
        pstmtObjets.setInt(1, idPile);
        resObjets = pstmtObjets.executeQuery();
        
        LinkedList<Produit> lp = new LinkedList<>();
        
        while (resObjets.next()) {
             
            Produit p = new Produit(resObjets.getString("IDOBJET"), resObjets.getInt("LARGEUR") , resObjets.getInt("HAUTEUR"), 1, new Color(resObjets.getInt("COLOR")), resObjets.getInt("GROUPE"));
            lp.add(p);
             
  
        }
        resObjets.close();
        pstmtObjets.close();
        
        Comparator<Produit> compareLargeur = (Produit o1, Produit o2) -> o2.getLargeur() - o1.getLargeur();
 
        Collections.sort(lp, compareLargeur);
        
        return lp;
    }
    

    public List<Instance> getToutesLesInstances() {
        return ToutesLesInstances;
    }
    
    /**
     * Fonction qui permet de mettre une solution en BDD
     * @param s solution à mettre en BDD
     */
    public void mettreSolutionEnBdd(Solution s){
        final EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("OPTIBOXPU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                em.persist(s);
                et.commit();
                System.out.println("Solution mise en BDD");
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
    
    
    
    
    @Override
    public String toString() {
        return "DBRequests{" + "conn=" + conn + ", ToutesLesInstances=" + ToutesLesInstances + '}';
    }

  



    



    
    
    
    
    
        
}
