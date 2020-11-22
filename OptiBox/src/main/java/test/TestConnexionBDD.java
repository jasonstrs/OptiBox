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
        
        dbr.getAllInstances();
        System.out.println(dbr.ToutesLesInstances);
    }
}
