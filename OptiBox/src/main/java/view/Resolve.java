/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import metier.DBRequests;
import modele.Box;
import modele.Instance;
import modele.Produit;
import modele.Solution;


/**
 *
 * @author Jason
 */
public class Resolve extends javax.swing.JFrame {
    private DBRequests dbr;
    Dimension dimEcran;

    /**
     * Creates new form Resolve
     */
    public Resolve() {
        Accueil.getInstance().setVisible(false); // on cache la fenêtre OptiBox
        initComponents();
        initialisationFenetre();
        initialisationPanel();
    }
    
    /**
     * Creates new form Resolve
     * @param i : Instance i à afficher
     */
    public Resolve(Instance i) throws SQLException {
        this();
        this.nom_instance_label.setText("Solution instance : "+i.getNom());
        this.nom_instance_label.setLocation(dimEcran.width/4, 15);
        getSolution(i);
    }
    
    
    
     /**
     * Fonction qui permet de d'initialiser le modèle de la fenètre
     */
    private void initialisationFenetre(){
        this.setVisible(true);
        this.setTitle("OptiBox Résolution");
        dimEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimEcran.width+5, dimEcran.height-50);
        this.setLocation(0,0);
    }
    
    /**
     * Fonction qui permet d'initialiser et de placer le panel sur la page
     */
    private void initialisationPanel(){
        // on place le Scroll
        this.scroll_solution.setLocation(12, 80); // POSITION : x=12 ; y=80
        
        // on lui met des dimensions
        Dimension dimScroll = new Dimension(this.getWidth()-40,this.getHeight()- 140); // on met en place les dimensions du scroll
        this.scroll_solution.setSize(dimScroll);
    }
    
    private void getSolution(Instance i) throws SQLException{
        Solution s = getSolutionTest();
        if(s==null)return;
        try {
            dbr = DBRequests.getInstance();
           // Solution s = dbr.getSolutionIDFromInstance(i);
            this.label_cout.setText("Coût : "+s.getCout()+" €");
            this.label_cout.setLocation(dimEcran.width/2 + dimEcran.width/4, 10);

            this.resolvePanel1.setSolution(s);
            this.resolvePanel1.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "A FAIRE !!!!!!!!!!!", "Erreur de chargement", HEIGHT);
            this.dispose();
        }    
    }
    
    
    private Solution getSolutionTest() throws SQLException{
        
        
        final EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("OPTIBOXPU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                
                DBRequests dbr = DBRequests.getInstance();
                
                // creation d’une entite persistante
                Box b1 = new Box("B00",200,125,300);
                Box b2 = new Box("B01",400,60,100);
                Box b3 = new Box("B02",600,150,250);             
                
                System.out.println("On crée les 3 Box");
                                
                Produit p1 = new Produit("P00",50,10,5,dbr.getRandomColor());
                Produit p2 = new Produit("P01",30,10,1,dbr.getRandomColor());
                Produit p3 = new Produit("P02",60,60,3,dbr.getRandomColor());
                Produit p4 = new Produit("P03",80,20,7,dbr.getRandomColor());
                Produit p5 = new Produit("P04",20,60,2,dbr.getRandomColor());

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
                
                et.begin();
                
                
                Solution sRecupFromBDD = dbr.getSolutionFromInstance(i);
                
                et.commit();
                
                return sRecupFromBDD;
                
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
    
        
        
        
        return null;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        zoom_label = new javax.swing.JLabel();
        zoom_plus = new javax.swing.JLabel();
        zoom_moins = new javax.swing.JLabel();
        nom_instance_label = new javax.swing.JLabel();
        scroll_solution = new javax.swing.JScrollPane();
        resolvePanel1 = new tools.ResolvePanel();
        label_cout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        zoom_label.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 24)); // NOI18N
        zoom_label.setForeground(new java.awt.Color(255, 51, 51));
        zoom_label.setText("Zoom");
        getContentPane().add(zoom_label);
        zoom_label.setBounds(40, 10, 76, 30);

        zoom_plus.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 36)); // NOI18N
        zoom_plus.setForeground(new java.awt.Color(255, 51, 51));
        zoom_plus.setText("+");
        zoom_plus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoom_plus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zoom_plusMouseClicked(evt);
            }
        });
        getContentPane().add(zoom_plus);
        zoom_plus.setBounds(10, 39, 22, 23);

        zoom_moins.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 36)); // NOI18N
        zoom_moins.setForeground(new java.awt.Color(255, 51, 51));
        zoom_moins.setText("-");
        zoom_moins.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoom_moins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zoom_moinsMouseClicked(evt);
            }
        });
        getContentPane().add(zoom_moins);
        zoom_moins.setBounds(125, 40, 14, 21);

        nom_instance_label.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 24)); // NOI18N
        nom_instance_label.setText("jLabel1");
        getContentPane().add(nom_instance_label);
        nom_instance_label.setBounds(410, 10, 390, 30);

        resolvePanel1.setLayout(null);
        scroll_solution.setViewportView(resolvePanel1);

        getContentPane().add(scroll_solution);
        scroll_solution.setBounds(40, 80, 300, 190);

        label_cout.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 24)); // NOI18N
        label_cout.setText("jLabel1");
        getContentPane().add(label_cout);
        label_cout.setBounds(770, 10, 220, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        // ré afficher la page Accueil quand la page resolve se ferme
        Accueil.getInstance().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void zoom_plusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zoom_plusMouseClicked
        // TODO add your handling code here:
        this.resolvePanel1.zoomPlus();
        this.resolvePanel1.repaint();
    }//GEN-LAST:event_zoom_plusMouseClicked

    private void zoom_moinsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zoom_moinsMouseClicked
        // TODO add your handling code here:
        this.resolvePanel1.zoomMoins();
        this.resolvePanel1.repaint();
    }//GEN-LAST:event_zoom_moinsMouseClicked

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
            java.util.logging.Logger.getLogger(Resolve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Resolve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Resolve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Resolve.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Resolve();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label_cout;
    private javax.swing.JLabel nom_instance_label;
    private tools.ResolvePanel resolvePanel1;
    private javax.swing.JScrollPane scroll_solution;
    private javax.swing.JLabel zoom_label;
    private javax.swing.JLabel zoom_moins;
    private javax.swing.JLabel zoom_plus;
    // End of variables declaration//GEN-END:variables
}
