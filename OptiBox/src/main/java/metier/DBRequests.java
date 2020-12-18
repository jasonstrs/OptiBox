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
import java.util.List;
import java.util.Random;
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
                                    resObjets.getInt("LARGEUR"),resObjets.getInt("HAUTEUR"),resObjets.getInt("QUANTITE"),this.getRandomColor()));
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
                            res.getInt("LARGEUR"),res.getInt("HAUTEUR"),res.getInt("QUANTITE"),this.getRandomColor()));
                    break;
            }           
                       
            
        }
        res.close();
        stmt.close();
        
        return l;
    }
    
    /**
     * Récupère la solution liée à une instance
     * @param i l'instance dont on veut la solution
     * @return La solution
     */
    public Solution getSolutionIDFromInstance(Instance i) throws SQLException{
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
            //TODO : throw une exception
            return null;
        }
        
        System.out.println("La Solution de cette instance a pour id "+ID_Sol);
        
        return getSolutionFromID(ID_Sol,i);
        
    }
    
    public Solution getSolutionFromID(int ID_Sol,Instance i) throws SQLException {
        Solution s = new Solution(i);
        
        String requeteObjets = "SELECT * FROM SOLUTIONBOX sb WHERE sb.MASOLUTION = ?";
        ResultSet resObjets;

        PreparedStatement pstmtObjets = conn.prepareStatement(requeteObjets);
        pstmtObjets.setInt(1, ID_Sol);
        
        resObjets = pstmtObjets.executeQuery();
        ArrayList<SolutionBox> l = new ArrayList<>();
        while (resObjets.next()) {
            Box b = getBoxFromID(resObjets.getInt("TYPEDEBOX"));
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
        
        String requeteObjets = "SELECT * FROM OBJET_D_INSTANCE o WHERE o.DTYPE='BOX' o.IDGENERE = ?";
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
            //TODO : throw une exception
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
                
             
            
        }
        resObjets.close();
        pstmtObjets.close();
        
        return l;
        
    }
    
    
    
    
    
    
    
    /**
     * Fonction qui permet d'obtenir une couleur aléatoire
     * @return Color
     */
    public Color getRandomColor(){
        // source : https://stackoverflow.com/questions/4246351/creating-random-colour-in-java#:~:text=Random%20rand%20%3D%20new%20Random()%3B,float%20r%20%3D%20rand.
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    public List<Instance> getToutesLesInstances() {
        return ToutesLesInstances;
    }
    
    
    
    
    @Override
    public String toString() {
        return "DBRequests{" + "conn=" + conn + ", ToutesLesInstances=" + ToutesLesInstances + '}';
    }



    



    
    
    
    
    
        
}
