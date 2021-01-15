/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author simon
 * @version 1.0
 */
@Entity
public class SolutionBox implements Serializable {

    /***************************** ATTRIBUTS ***************************/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="TYPEDEBOX")
    private Box TYPEDEBOX;
    
    @OneToMany(mappedBy="MABOX",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<PileDeProduits> mesPiles;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MASOLUTION")
    private Solution MASOLUTION;
    
    /*************************** CONSTRUCTEURS **************************/
    
    public SolutionBox(){
        this.mesPiles = new ArrayList<>();
    }
    
    public SolutionBox(Box b, Solution s){
        this();
        this.TYPEDEBOX = b;
        b.getMesSolutionBox().add(this);
        this.MASOLUTION = s;
        s.getMesSolutionBox().add(this);
        
    }
    
    public SolutionBox(Box b, Solution s, ArrayList<PileDeProduits> pile){
        this(b,s);
        if(ControlerPile(pile))
            mesPiles = pile;
        else{
            /// TODO : throw exception pile non conforme
            ///Remplacer par un try-catch quand c'est fait
            return;
        }
            
    }

    /************************* GETTERS & SETTERS ************************/

    public Solution getMASOLUTION() {
        return MASOLUTION;
    }

    public void setMASOLUTION(Solution MASOLUTION) {
        this.MASOLUTION = MASOLUTION;
    }        

    public List<PileDeProduits> getMesPiles() {
        return mesPiles;
    }

    public void setMesPiles(List<PileDeProduits> mesPiles) {
        this.mesPiles = mesPiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Box getTYPEDEBOX() {
        return TYPEDEBOX;
    }

    public void setTYPEDEBOX(Box TYPEDEBOX) {
        this.TYPEDEBOX = TYPEDEBOX;
    }
    
    
    
    
    
    public int getLargeur(){
        return this.TYPEDEBOX.getLargeur();
    }
    
    public int getHauteur(){
        return this.TYPEDEBOX.getHauteur();
    }
    
    public double getPrix(){
        return this.TYPEDEBOX.getPrix();
    }
    

    /****************************** METHODES ****************************/
    
    /**
     * Vérifie si une pile de PileDeProduit respecte
     * les dimensions de la Box
     * @param pile La pile à controler
     * @return true si la pile peut rentrer dans la Box, false sinon
     */
    private boolean ControlerPile(ArrayList<PileDeProduits> pile) {
        
        int largeur = 0;
        int hauteur = 0;
        for(PileDeProduits p : pile){
            largeur += p.getLargeur();
            if(p.getHauteur() > hauteur)
                hauteur = p.getHauteur();
        }
            
        
        return !(largeur > this.getLargeur() || hauteur > this.getHauteur());        
    }
    
    /**
     * Permet de calculer le taux de remplissage de la box en %
     * @return la valeur du taux de remplissage en %
     */
    public double getTauxDeRemplissage(){
        double taux=0;
        double aireBox = this.getTYPEDEBOX().getHauteur()*this.getTYPEDEBOX().getLargeur();
        
        taux=aireBox;
        
        for (PileDeProduits pp : this.getMesPiles()) // on parcourt chaque pile
            for (Produit p : pp.getMESPRODUITS()) // on parcourt chaque produit
                taux-=(p.getHauteur()*p.getLargeur()); // on soustrait l'aire d'un produit
        
        taux = (aireBox-taux)*100/aireBox;
        return taux;
    }

    
    private boolean AjouterPile(PileDeProduits p){
        int currLargeur = 0;
        for(PileDeProduits currPile : this.mesPiles){
            currLargeur += currPile.getLargeur();
        }
                       
        if(currLargeur + p.getLargeur() <= this.getLargeur()){
            return this.mesPiles.add(p);                        
        }
                    
        return false;
    }
    
    /*@Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SolutionBox other = (SolutionBox) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }*/
    

    
    

    /*@Override
    public String toString() {
        return this.mesPiles.toString();
    }*/

    
    public void afficherPrix() {
        System.out.print("\t\t\t");
        System.out.print(this.getPrix());
        System.out.print("\n");
    }


    
}
