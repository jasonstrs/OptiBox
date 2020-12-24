/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import modele.Objet_d_Instance;
import modele.Produit;

/**
 *
 * @author Jason
 */
public class MyPanel extends javax.swing.JPanel {
    
    private Collection<modele.Objet_d_Instance> instancesADessiner;
    private int scale=5;
    /**
     * Creates new form MyPanel
     */
    public MyPanel() {
        initComponents();
        this.instancesADessiner = new ArrayList<>();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // first draw a clear/empty panel
        if (this.getInstancesADessiner().isEmpty()) {// on dessine l'élément par défaut 
            g.setColor(Color.black);
            Font f = new Font("Montserrat Medium", Font.BOLD, 24);
            g.setFont(f);
            g.drawString("Veuillez selectionner une instance", this.getWidth()/3, this.getHeight()/3);              
        } else{  // on dessine les formes !
            dessinerEntete(g,"Box",0,5);
            dessinerInstance(g); 
        }
        
    }

    /************************* GETTERS ET SETTERS ***********************/
    public Collection getInstancesADessiner() {
        return instancesADessiner;
    }

    public void setInstancesADessiner(Collection instancesADessiner) {
        this.instancesADessiner = instancesADessiner;
    }
    
    /****************************** METHODES ****************************/
    
    /**
     * Fonction qui permet de dessiner l'instance complète en faisant attention à ne pas dépasser
     * la largeur du panel, de même de manière à ce que les pièces ne se chevauchent pas
     */
    public void dessinerInstance(Graphics g){
        final int largeurPanel = this.getWidth();
        int x=0;
        int y=60;
        int i=0;
        Produit temp=null;
        int maxHeight=0;
        boolean flag=false; // permet de savoir si on doit dessiner l'entête des produits
        Color c=null;

        // on commence par parcourir toute la liste des objets à dessiner
        for (Objet_d_Instance oI : this.instancesADessiner){
            if (oI instanceof Produit){ // si c'est un produit
                if(!flag){
                    y+=maxHeight+20;
                    x=0;
                    maxHeight=0;
                    dessinerEntete(g,"Produits",x,y);
                    y+=60;
                    flag=true;
                }
                g.setColor(oI.getColor()); 
                temp=(Produit)oI;
                if (oI.getColor()==c && x!=0)
                    x-=20; // si la couleur précédente est la même, on réduit l'écart entre les deux produits
                
                if (x+(oI.getLargeur())/scale>largeurPanel){ // on regarde si la pièce ne dépasse pas
                    // si elle dépasse, on récupère la hauteur max des pièces précédentes, puis on ajoute 20
                    y+=maxHeight+20;
                    x=0; // on remet x à 0
                    maxHeight=0;
                }
                g.fillRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                g.drawRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                
                if (oI.getHauteur()/scale>maxHeight) // on sauvegarde la hauteur de la plus grande pièce en hauteur
                    maxHeight=oI.getHauteur()/scale;
                
                x+=(oI.getLargeur()/scale)+30; // on modifie l'abscisse en ajoutant la largeur du produit et un espace de 30
                c=oI.getColor(); // on sauvegarde la couleur
            } else { // si c'est une box
                if (x+(oI.getLargeur())/scale>largeurPanel){ // on regarde si la pièce ne dépasse pas
                    y+=maxHeight+20;
                    maxHeight=0;
                    x=0; // on remet x à 0
                }
                g.setColor(oI.getColor());
                g.fillRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                if (oI.getHauteur()/scale>maxHeight)
                        maxHeight=oI.getHauteur()/scale;
                x+=(oI.getLargeur()/scale)+30;  // on sépare les objets de 30
            }
        }
        
        // on ajuste la hauteur du panel
        Dimension dimPanel = new Dimension(this.getWidth()-20,y+maxHeight+10);
        this.setPreferredSize(dimPanel);
        this.revalidate();
    }
    
    public void dessinerEntete(Graphics g,String text,int x,int y){
        g.setColor(Color.black);
        //g.drawLine(0, y, this.getWidth(), y);
        
        g.fillRect(0, y, this.getWidth(), 44);
        g.setColor(Color.white);
        g.setFont(new Font("Montserrat UltraLight", Font.BOLD, 30)); 
        g.drawString(text, this.getWidth()/2 -8, 32+y);
        //g.drawLine(0, y+48, this.getWidth(), y+48);
    }
    
    /**
     * Fonction qui permet d'augmenter le zoom sur le panel
     * Si l'echelle a atteint 2, on ne peux pas plus zoomer
     */
    public void zoomPlus(){
        if (this.scale <= 2)return;
        this.scale--;
    }
    
    /**
     * Fonction qui permet de diminuer le zoom sur le panel
     * Si l'echelle a atteint 9, on quitte
     */
    public void zoomMoins(){
        if (this.scale >=9)return;
        this.scale++;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
