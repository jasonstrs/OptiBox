/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import metier.DBRequests;
import modele.Instance;
import modele.Objet_d_Instance;
import modele.Solution;

/**
 *
 * @author Jason
 */
public class Accueil extends javax.swing.JFrame {
    private DBRequests dbr;
    /**
     * Instance unique de DBRequests (modèle singleton)
     */
    private static Accueil acc = null;
    
    /**
     * Permet d'utiliser le modèle singleton : 
     * On ne peut pas instancier une Accueil, mais 
     * seulement récupérer l'unique instance de la classe
     * (ou la créer si elle n'existe pas)
     * @return La seule instance de Accueil
     */
    public static Accueil getInstance(){
        if(acc == null)
            acc = new Accueil();
        return acc;
    }
    
    /**
     * Constructeur par défaut privé : on ne peut pas instancier cette classe
     * Ainsi, on crée une instance, et on la partage avec ceux qui en ont besoin
     */
    private Accueil() {
        initComponents();
        initialisationFenetre();   
        getAllInstance();
    }
    
    /**
     * Fonction qui permet de d'initialiser le modèle de la fenètre
     */
    private void initialisationFenetre(){
        this.setVisible(true);
        this.setTitle("OptiBox Accueil");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dim.width+5, dim.height-50);

        // on place le Scroll
        this.jScrollPane2.setLocation(this.jList_instance.getWidth()+20, 5);
        // on lui met des dimensions
        Dimension dimScroll = new Dimension(dim.width-30-this.jList_instance.getWidth(),dim.height-150);
        this.jScrollPane2.setSize(dimScroll);
        this.setLocation(0, 0);
        this.button_sol_BDD.setEnabled(false);
        this.button_resolve.setEnabled(false);
    }
        
    /**
     * Fonction qui permet de récupérer toutes les instances et les mettre dans le JLIST
     */
    private void getAllInstance(){
        DefaultListModel dlm = new DefaultListModel();
        this.jList_instance.setModel(dlm);

        try {
            dbr = DBRequests.getInstance();
            dbr.getAllInstances();
            List<Instance> instances= dbr.getToutesLesInstances();
            for (Instance i : instances){
                dlm.addElement(i);
            }
        } catch (Exception ex) {
            //Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Impossible de charger l'ensemble des instances.\nVeuillez vérifier votre connection à la base de donnée.", "Erreur de chargement", HEIGHT);
            this.dispose();
        }              
    }
    
    /**
     * Fonction qui permet de dessiner une instance lorsqu'elle est selectionné
     * @param i  instance à dessiner
     */
    public void afficherInstance(Instance i){
        Collection<Objet_d_Instance> ObjectsDeLInstance = i.getObjetsDeLInstance();
        this.myPanel1.setInstancesADessiner(ObjectsDeLInstance);
        this.myPanel1.repaint();
    }
    
    /**
     * Fonction qui permet de savoir si une solution est déjà en BDD afin de désactiver ou non le bouton "Résoudre BDD"
     * @param i instance sur laquelle on vérifié si la solution est en BDD
     * @return boolean true si déjà en BDD
     */
    public boolean checkIfEnableBDDButton(Instance i){
        try {
            if(dbr.getSolutionFromInstance(i,true) == null)
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true; // s_new'il y a une solution en BDD c'est good !
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_instance = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        myPanel1 = new tools.MyPanel();
        zoom_plus = new java.awt.Label();
        zoom_moins = new java.awt.Label();
        label_zoom = new java.awt.Label();
        button_resolve = new javax.swing.JButton();
        button_sol_BDD = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.red);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        getContentPane().setLayout(null);

        jScrollPane1.setBackground(java.awt.Color.red);

        jList_instance.setBackground(java.awt.Color.lightGray);
        jList_instance.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        jList_instance.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_instance.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_instance.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jList_instance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_instanceMouseClicked(evt);
            }
        });
        jList_instance.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList_instanceValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList_instance);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 280, 460);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 200));
        jScrollPane2.setViewportView(myPanel1);

        myPanel1.setMaximumSize(new java.awt.Dimension(1500, 1500));
        myPanel1.setPreferredSize(new java.awt.Dimension(500, 500));
        myPanel1.setLayout(null);
        jScrollPane2.setViewportView(myPanel1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(380, 40, 620, 270);

        zoom_plus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoom_plus.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 36)); // NOI18N
        zoom_plus.setForeground(new java.awt.Color(255, 51, 51));
        zoom_plus.setText("+");
        zoom_plus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zoom_plusMouseClicked(evt);
            }
        });
        getContentPane().add(zoom_plus);
        zoom_plus.setBounds(50, 510, 20, 20);

        zoom_moins.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoom_moins.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 36)); // NOI18N
        zoom_moins.setForeground(new java.awt.Color(255, 51, 51));
        zoom_moins.setText("-");
        zoom_moins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zoom_moinsMouseClicked(evt);
            }
        });
        getContentPane().add(zoom_moins);
        zoom_moins.setBounds(190, 510, 18, 20);

        label_zoom.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 24)); // NOI18N
        label_zoom.setForeground(new java.awt.Color(255, 51, 51));
        label_zoom.setText("Zoom");
        getContentPane().add(label_zoom);
        label_zoom.setBounds(100, 470, 120, 40);

        button_resolve.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 12)); // NOI18N
        button_resolve.setText("RÉSOUDRE");
        button_resolve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_resolveMouseClicked(evt);
            }
        });
        button_resolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_resolveActionPerformed(evt);
            }
        });
        getContentPane().add(button_resolve);
        button_resolve.setBounds(10, 570, 110, 40);

        button_sol_BDD.setFont(new java.awt.Font("Montserrat ExtraBold", 0, 12)); // NOI18N
        button_sol_BDD.setText("SOLUTION BDD");
        button_sol_BDD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_sol_BDDMouseClicked(evt);
            }
        });
        button_sol_BDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sol_BDDActionPerformed(evt);
            }
        });
        getContentPane().add(button_sol_BDD);
        button_sol_BDD.setBounds(130, 570, 140, 40);

        menuBar.setBackground(java.awt.Color.red);

        jMenu1.setText("File");
        menuBar.add(jMenu1);

        jMenu2.setText("Edit");
        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

    }//GEN-LAST:event_formWindowStateChanged

    private void jList_instanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_instanceMouseClicked
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jList_instanceMouseClicked

    private void jList_instanceValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList_instanceValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting()) {//This line prevents double events
            int index = this.jList_instance.getSelectedIndex(); // on récupère l'index qui a été choisi dans la liste
            Object c = this.jList_instance.getModel().getElementAt(index); // on récupère l'objet
            afficherInstance((Instance)c); // on caste l'objet et on l'affiche
            this.button_resolve.setEnabled(true); // on active le bouton résoudre
            this.button_sol_BDD.setEnabled(false); // on désactive le bouton résoudre en BDD
            if(this.checkIfEnableBDDButton((Instance)c))
                this.button_sol_BDD.setEnabled(true); // si il y a une solution en BDD, on peut la prendre
        }
    }//GEN-LAST:event_jList_instanceValueChanged

    private void zoom_plusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zoom_plusMouseClicked
        // TODO add your handling code here:
        this.myPanel1.zoomPlus();
        this.myPanel1.repaint();
    }//GEN-LAST:event_zoom_plusMouseClicked

    private void zoom_moinsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zoom_moinsMouseClicked
        // TODO add your handling code here:
        this.myPanel1.zoomMoins();
        this.myPanel1.repaint();
    }//GEN-LAST:event_zoom_moinsMouseClicked

    private void button_resolveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_resolveMouseClicked
        // TODO add your handling code here:
        this.setCursor(Cursor.WAIT_CURSOR);
        int index = this.jList_instance.getSelectedIndex(); // on récupère l'indice qui a été selectionné
        if (index == -1)  // si aucune instance a été selectionnée, on met un message d'erreur
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une instance pour résoudre", "Erreur", JOptionPane.ERROR_MESSAGE);
        else {
            System.out.println(index);
            Object c = this.jList_instance.getModel().getElementAt(index); // on récupère l'objet
            
            
            //Si on veut REcalculer une solution (on en a déjà une en BDD)
            //On vérifie si la nouvelle solution est meilleure que la précédente
            //Si oui on supprime celle en bdd, et on la remplace par la nouvelle.
            if(this.checkIfEnableBDDButton((Instance)c)){
                if(JOptionPane.showConfirmDialog(this, "Une solution existe déjà. Tenter d'en calculer une autre ? ", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    //On a décidé de calculer une nouvelle solution                   
                    Solution s_new = new Solution((Instance)c);
                    Solution s_old = null;
                    s_new.TestCalculerSolution();
                    s_new.calculerCout();
                    //On essaie de récupérer l'anciene solution
                    try {
                        s_old = this.dbr.getSolutionFromInstance((Instance)c, false); // on récupère la solution
                        s_old.calculerCout();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this,"Erreur lors de la communication avec la BDD", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch(NullPointerException e){ // si pas de solution (juste une sécurité car de base le bouton est en disabled si pas de solution)
                        JOptionPane.showMessageDialog(this, "Il n'y a pas de solution en BDD", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    //On vérifie si la nouvelle solution est meilleure
                    if(s_new.getCout() > s_old.getCout()){
                        JOptionPane.showMessageDialog(this, "La nouvelle solution est moins bonne (ou égale) que celle en BDD, abandon...", "Attention", JOptionPane.WARNING_MESSAGE);  
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(this, "La nouvelle solution est meilleure que celle en bdd, on la remplace.", "Information", JOptionPane.INFORMATION_MESSAGE); 
                    try {
                        this.dbr.supprSolution(s_old);
                        double gains = s_new.getCout()-s_old.getCout();
                        JOptionPane.showMessageDialog(this, "L'ancienne solution a bien été supprimée, et va être remplacée.\n"+gains+" € gagnés", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Impossible de supprimer l'ancienne solution ! Abandon...", "Erreur", JOptionPane.ERROR_MESSAGE);                            
                    }                                                                      
                }
                else{
                    this.setCursor(Cursor.DEFAULT_CURSOR);
                    return;
                }
            }               
            // on lance l'algo qui renvoie une solution

            Solution s = new Solution((Instance)c);
            s.TestCalculerSolution();
            
            // on met en BDD
            try{
                this.dbr.mettreSolutionEnBdd(s);
                new Resolve(s);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la solution en BDD.\nAbandon...", "Erreur", JOptionPane.ERROR_MESSAGE);                
            }
            
            
        }
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_button_resolveMouseClicked

    
    private void button_resolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_resolveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_resolveActionPerformed

    private void button_sol_BDDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_sol_BDDMouseClicked
        // TODO add your handling code here:
        int index = this.jList_instance.getSelectedIndex(); // on récupère l'indice qui a été selectionné
        if (index == -1)  // si aucune instance a été selectionnée, on met un message d'erreur
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une instance pour résoudre", "Erreur", JOptionPane.ERROR_MESSAGE);
        else {
            Object c = this.jList_instance.getModel().getElementAt(index); // on récupère l'objet
            try {
                Solution s = this.dbr.getSolutionFromInstance((Instance)c, false); // on récupère la solution
                if (s==null) // si pas de solution (juste une sécurité car de base le bouton est en disabled si pas de solution)
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de solution en BDD", "Erreur", JOptionPane.ERROR_MESSAGE);
                else
                    new Resolve(s);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "[EREEUR SQL] Impossible d'accéder à la BDD", "Erreur", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                this.dispose();
            }
        }
    }//GEN-LAST:event_button_sol_BDDMouseClicked

    private void button_sol_BDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_sol_BDDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_sol_BDDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Accueil.getInstance();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_resolve;
    private javax.swing.JButton button_sol_BDD;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JList<String> jList_instance;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label_zoom;
    private javax.swing.JMenuBar menuBar;
    private tools.MyPanel myPanel1;
    private java.awt.Label zoom_moins;
    private java.awt.Label zoom_plus;
    // End of variables declaration//GEN-END:variables
}
