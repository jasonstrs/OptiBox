/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modele.Instance;

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

    @Override
    public String toString() {
        return "DBRequests{" + "conn=" + conn + ", ToutesLesInstances=" + ToutesLesInstances + '}';
    }
    
    
    
    
        
}
