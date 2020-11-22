/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import modele.Box;
import modele.Instance;
import modele.Objet_d_Instance;
import modele.Produit;

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
    private DBRequests() throws SQLNonTransientConnectionException, Exception{
        ToutesLesInstances = new ArrayList<>();
        try {
            connect();           
        }
        catch (SQLNonTransientConnectionException exCo){
            throw new SQLNonTransientConnectionException(exCo);
        }
        catch (Exception ex) {
            throw new Exception(ex);
        }
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
        if(dbr == null){
            try {
                dbr = new DBRequests();
            }
            catch (Exception ex) {
                throw new Exception(ex);
            }
        }
        
        return dbr;
    }
    
    

    
    private void connect() throws Exception,SQLNonTransientConnectionException{
        
        try{
            String driverClass="org.apache.derby.jdbc.ClientDriver";
            String urlDatabase="jdbc:derby://localhost:1527/OptiBoxDB";
            String user = "root";
            String pass = "mysql";
            Class.forName(driverClass);
            conn=DriverManager.getConnection(urlDatabase,user,pass);
        }
        catch(SQLNonTransientConnectionException exCo){
            throw new SQLNonTransientConnectionException(exCo);
        }
        catch(ClassNotFoundException | SQLException e){
            throw new Exception(e);
        }
        
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

            Statement stmt = conn.createStatement();
            res = stmt.executeQuery(requete);
            while (res.next()) {
                Long ID = res.getLong("ID");
                String requeteObjets = "SELECT * FROM OBJET_D_INSTANCE o, INSTANCE i WHERE i.ID = o.MONINSTANCE AND i.ID = ?";
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
                                    resObjets.getInt("LARGEUR"),resObjets.getInt("HAUTEUR"),resObjets.getInt("QUANTITE")));
                            break;
                    }           
                }

                ToutesLesInstances.add(new Instance(ID,res.getString("NOM"),l));
            }
            res.close();
            stmt.close();
    //        System.out.println(ToutesLesInstances);
    //        
    //        System.out.println("On veut récupérer les objets des instances : ");
    //        for(int i=0;i<ToutesLesInstances.size()-1;i++){
    //            Instance currI = ToutesLesInstances.get(i);
    //            ArrayList<Objet_d_Instance> l = this.getObjetsFromInstanceID(currI.getId());
    //            System.out.println("Instance "+i+" : "+ToutesLesInstances.get(i));
    //            System.out.println("Objets : "+l);
    //            currI.setObjetsDeLInstance(l);
    //
    //        }

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

    private ArrayList<Objet_d_Instance> getObjetsFromInstanceID(Long id) throws SQLException {
        ArrayList<modele.Objet_d_Instance> l = new ArrayList<>();
        
        String requete;
        ToutesLesInstances.clear();
        requete = "SELECT * FROM OBJET_D_INSTANCE o, INSTANCE i WHERE i.ID = o.MONINSTANCE";
        ResultSet res;
        
        Statement stmt = conn.createStatement();
        res = stmt.executeQuery(requete);
        while (res.next()) {
            switch(res.getString("DTYPE")){
                case "Box":
                    l.add(new Box(res.getString("IDOBJET"),
                            res.getInt("LARGEUR"),res.getInt("HAUTEUR"),res.getFloat("PRIX")));
                    break;
                case "Produit":
                    l.add(new Produit(res.getString("IDOBJET"),
                            res.getInt("LARGEUR"),res.getInt("HAUTEUR"),res.getInt("QUANTITE")));
                    break;
            }           
                       
            
        }
        res.close();
        stmt.close();
        
        return l;
    }
    
    
    @Override
    public String toString() {
        return "DBRequests{" + "conn=" + conn + ", ToutesLesInstances=" + ToutesLesInstances + '}';
    }

    
    
    
    
    
        
}
