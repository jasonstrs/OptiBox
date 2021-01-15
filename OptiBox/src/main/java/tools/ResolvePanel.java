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
import static java.awt.image.ImageObserver.HEIGHT;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modele.*;
import org.json.simple.*;



/**
 *
 * @author Jason
 */
public class ResolvePanel extends javax.swing.JPanel {

    private Solution s;
    private int scale=1;
    private JSONObject arrayJSONObject;
    
    /**
     * Creates new form ResolvePanel
     */
    public ResolvePanel() {
        initComponents();
        arrayJSONObject=new JSONObject();
    }
    

    
    /************************* DESSINER ********************************/
    @Override    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
           
        if (this.s != null){ // si il y a une solution, on l'a dessine
            Box box;
            int x=0,y=0,maxHeight=0,index=0,yRef=0;
            
            final int largeurPanel = this.getWidth();
            
            for (SolutionBox sb : this.s.getMesSolutionBox()){
                box=sb.getTYPEDEBOX();
                g.setColor(Color.yellow);
                if (x+box.getLargeur()/scale > largeurPanel){
                    // si elle dépasse, on récupère la hauteur max des pièces précédentes, puis on ajoute 20
                    y+=maxHeight+20;
                    x=0; // on remet x à 0
                    maxHeight=0;
                }
                remplirObjet(index, x,x+box.getLargeur()/scale, y, y+box.getHauteur()/scale,sb);
                index++;
                g.fillRect(x, y, box.getLargeur()/scale, box.getHauteur()/scale);
                // x : position de la box en x
                // y : position de la box en y
                dessinerProduits(sb,box,g,x,y+box.getHauteur()/scale);
                
                x+=box.getLargeur()/scale+20;
                if (box.getHauteur()/scale>maxHeight)
                        maxHeight=box.getHauteur()/scale;
            }
            // on ajuste la hauteur du panel
            Dimension dimPanel = new Dimension(this.getWidth()-20,y+maxHeight+10);
            this.setPreferredSize(dimPanel);
            this.revalidate();
        }
    }
    
    private void dessinerProduits(SolutionBox sb, Box box, Graphics g,int x,int y){
        int positionEnX=x;
        int positionEnY=y;
        int maxX=0;
        
        for (PileDeProduits pp : sb.getMesPiles()){
            for (Produit produit : pp.getMESPRODUITS()){
                g.setColor(produit.getColor());
                g.fillRect(positionEnX, positionEnY-produit.getHauteur()/scale, produit.getLargeur()/scale, produit.getHauteur()/scale);
                g.setColor(Color.black);
                g.drawRect(positionEnX, positionEnY-produit.getHauteur()/scale, produit.getLargeur()/scale, produit.getHauteur()/scale);
                positionEnY-=produit.getHauteur()/scale;
                if (maxX<produit.getLargeur()/scale) maxX=produit.getLargeur()/scale;
            }
            positionEnY=y;
            positionEnX+=maxX;
            maxX=0;
        }
    }

    /************************* GETTERS ET SETTERS***********************/
    public void setSolution(Solution s) {
        this.s = s;
    }
    
    public Solution getSolution(){
        return s;
    }

    /****************************** METHODES ****************************/
    /**
     * Fonction qui permet d'augmenter le zoom sur le panel
     * Si l'echelle a atteint 2, on ne peux pas plus zoomer
     */
    public void zoomPlus() {
        if (this.scale <= 1)return;
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
    
    public void remplirObjet(int index, int xmin, int xmax, int ymin, int ymax, SolutionBox sb){
        JSONObject oJSON = new JSONObject();
        oJSON.put("xmin", xmin);
        oJSON.put("xmax", xmax);
        oJSON.put("ymin", ymin);
        oJSON.put("ymax", ymax);
        oJSON.put("solutionBox", sb);

        this.arrayJSONObject.put(index, oJSON);
        // Output expected
        /*{"0":{
            "ymin":0,
            "xmin":0,
            "ymax":125,
            "xmax":200,
            "solutionBox":[modele.PileDeProduits@0, modele.PileDeProduits@0]
            },
            "1":{
            "ymin":0,
            "xmin":220,
            "ymax":125,
            "xmax":420,
            "solutionBox":[modele.PileDeProduits@0]
        }.......
        */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

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

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        int positionX=evt.getX();
        int positionY=evt.getY();
        String chaine="";
        String chaineProduit="";
        String puce = new String(Character.toChars(0x2B50)); // logo étoile
        
        DecimalFormat numberFormat = new DecimalFormat("#.000");
        
        JSONObject oObjectInTab; // variable qui va contenir l'objet qui se trouve dans chaque index
        int xmin, xmax,ymin,ymax;
        SolutionBox sb;
        int nbProduit=0,i,j=1;
        int key, keyPlusUn;
        
        
        for(Iterator iterator = this.arrayJSONObject.keySet().iterator(); iterator.hasNext();) {
            // on parcourt l'objet
            key = (int) iterator.next();
            keyPlusUn=key+1;
            oObjectInTab=(JSONObject)this.arrayJSONObject.get(key); // on recupère l'objet

            ymin=(int)oObjectInTab.get("ymin");
            xmin=(int)oObjectInTab.get("xmin");
            ymax=(int)oObjectInTab.get("ymax");
            xmax=(int)oObjectInTab.get("xmax");
            sb=(SolutionBox)oObjectInTab.get("solutionBox");

            if (positionX>=xmin && positionX<=xmax && positionY >=ymin && positionY<=ymax){ // c'est un clic sur une box
                chaine = puce+puce+"  Informations complémentaire sur la solution box n°"+keyPlusUn+"  "+puce+puce;
                chaine+="\n--------------------------------------------------------------------------------\n";
                chaine+="Nombre de piles : "+sb.getMesPiles().size()+"\n";
                for (PileDeProduits pp : sb.getMesPiles())
                    nbProduit+=pp.getMESPRODUITS().size();
                chaine+="Nombre total de produits : "+nbProduit;
                chaine+="\n--------------------------------------------------------------------------------\n";
                
                for (PileDeProduits pp : sb.getMesPiles()){
                    chaine+="La pile n°"+j+" contient "+pp.getMESPRODUITS().size()+" produits\n";
                    j++;
                }
                chaine+="--------------------------------------------------------------------------------\n";
                chaine+="Taux de remplissage : "+numberFormat.format(sb.getTauxDeRemplissage())+" %";
                
                JOptionPane.showMessageDialog(this, chaine, "Informations complémentaires", JOptionPane.PLAIN_MESSAGE);
            }
             
        }
        
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
