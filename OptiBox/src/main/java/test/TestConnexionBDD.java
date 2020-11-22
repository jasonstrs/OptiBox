/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modele.Instance;
import metier.DBRequests;

/**
 *
 * @author simon
 */
public class TestConnexionBDD {
    public static void main(String[] args) throws SQLException, Exception {
        
        //On récupère l'instance de DBRequests
        DBRequests dbr = DBRequests.getInstance();
        
        String requete;
        dbr.ToutesLesInstances.clear();
        requete = "SELECT * FROM INSTANCE c ORDER BY NOM";
        ResultSet res;
        Instance i = null;
        res = null;
        Statement stmt = dbr.conn.createStatement();
        res = stmt.executeQuery(requete);
        while (res.next()) {
            i = new Instance(res.getString("NOM"));
            dbr.ToutesLesInstances.add(i);
            
        }
        res.close();
        stmt.close();
        System.out.println("On tente de récupérer le nom de toutes les instances");
        System.out.println(dbr.ToutesLesInstances);
    }
}
