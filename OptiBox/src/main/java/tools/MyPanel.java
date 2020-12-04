/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import modele.Objet_d_Instance;
import modele.Produit;

/**
 *
 * @author Jason
 */
public class MyPanel extends javax.swing.JPanel {
    
    private Collection<modele.Objet_d_Instance> instancesADessiner;
    private final int scale=5;
    
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
            g.drawString("Veuillez selectionner une instance", this.getWidth()/6, this.getHeight()/3);              
        } else  // on dessine les formes !
            dessinerInstance(g);       
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
    
    /**
     * Fonction qui permet de dessiner l'instance complète en faisant attention à ne pas dépasser
     * la largeur du panel, de même de manière à ce que les pièces ne se chevauchent pas
     */
    public void dessinerInstance(Graphics g){
        final int largeurPanel = this.getWidth();
        int x=0;
        int y=0;
        int indiceMin=0;
        int indiceMax=0;
        int i=0;
        Produit temp=null;

        // on commence par parcourir toute la liste des objets à dessiner
        for (Objet_d_Instance oI : this.instancesADessiner){
            if (oI instanceof Produit){ // si c'est un produit
                g.setColor(this.getRandomColor()); // on attribut une couleur random
                temp=(Produit)oI;
                for (i=0;i<temp.getQuantite();i++){ // on dessine le nombre de produit suivant la quantité
                    if (x+(oI.getLargeur())/scale>largeurPanel){ // on regarde si la pièce ne dépasse pas
                        // si elle dépasse, on récupère la hauteur max des pièces précédentes, puis on ajoute 20
                   
                        // notation ternaire ci-dessous :
                        // si on a pas encore dessiné de pièce sur la ligne, on ne prends pas la hauteur de cette pièce pour trouver la hauteur max
                        // si une de ces pièces à déjà était dessinée, on doit prendre en compte sa hauteur, donc indiceMax+1
                        y += this.getHauteurMaxDesFormesDessinees(indiceMin, i==0 ? indiceMax : indiceMax+1)/scale+20;
                        x=0; // on remet x à 0
                        indiceMin=indiceMax; // l'indice du premier objet prends la valeur de l'indice max
                    }
                    g.fillRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                    g.drawRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                    
                    // si on sépare 2 mêmes produits, on met un petit espace
                    // sinon, si c'était le dernier produit, on met un grand espace
                    x+= (i==temp.getQuantite()-1)? (oI.getLargeur()/scale)+30 : (oI.getLargeur()/scale)+10;
                } 
                indiceMax++; // on incrémente l'indice max
            } else { // si c'est une box
                if (x+(oI.getLargeur())/scale>largeurPanel){ // on regarde si la pièce ne dépasse pas
                    // si elle dépasse, on récupère la hauteur max des pièces précédentes, puis on ajoute 50
                    y += this.getHauteurMaxDesFormesDessinees(indiceMin, indiceMax)/scale+20;
                    x=0; // on remet x à 0
                    indiceMin=indiceMax; // l'indice du premier objet prends la valeur de l'indice max
                }
                g.setColor(Color.yellow);
                g.fillRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                g.setColor(Color.black); // on attribut une couleur random
                g.drawRect(x, y, oI.getLargeur()/scale, oI.getHauteur()/scale);
                indiceMax++; // on a dessiné une forme en plus, on se place sur la forme suivante
                x+=(oI.getLargeur()/scale)+30;  // on sépare les objets de 30
            }
        }
    }
    
    
    /**
     * Fonction qui permet de retourner la hauteur maximale d'un objet dans la liste
     * des instances à dessiner entre deux valeurs d'indice 
     * @param indiceMin borne inférieure de l'intervalle
     * @param indiceMax borne supérieur exclue de l'intervalle
     * @return la hauteur maximale
     */
    public int getHauteurMaxDesFormesDessinees(int indiceMin,int indiceMax){
        int i;
        int hauteur=0;
        List list = new ArrayList(this.getInstancesADessiner());
        Objet_d_Instance tmp=(Objet_d_Instance)list.get(indiceMin); // utile si jamais indiceMax==indiceMin
        
        for (i=indiceMin;i<indiceMax;i++){ // on parcourt la liste
            tmp = (Objet_d_Instance) list.get(i); // on récupère l'objet
            if (tmp.getHauteur()>hauteur) // si la hauteur est supérieure à la variable hauteur
                hauteur=tmp.getHauteur(); // on l'a stocke
        }
        // si la hauteur vaut 0, ça veut dire que indice max=indice min, donc la hauteur
        // est la même pour toute la ligne, donc ça vaut une hauteur parmi la liste
        return hauteur == 0 ? tmp.getHauteur()  : hauteur ; 
    }    
    
    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
