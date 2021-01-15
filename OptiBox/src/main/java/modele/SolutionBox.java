/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author simon
 * @version 1.0
 */
@Entity
public class SolutionBox implements Serializable {

    /***************************** ATTRIBUTS ***************************/
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID généré par la BDD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    
    /**
     * Type de la box
     */
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="TYPEDEBOX")
    private Box TYPEDEBOX;
    
    /**
     * Liste de piles qui se trouve dans la solution box
     */
    @OneToMany(mappedBy="MABOX",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<PileDeProduits> mesPiles;
    
    /**
     * Solution de la solution box
     */
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MASOLUTION")
    private Solution MASOLUTION;
    
    // CONSTRUCTEURS 
    
    /**
     * constructeur par défaut
     */
    public SolutionBox(){
        this.mesPiles = new ArrayList<>();
    }
    
    /**
     * Constructeur par donnés
     * @param b type de la box à attribuer
     * @param s Solution à attribuer
     */
    public SolutionBox(Box b, Solution s){
        this();
        this.TYPEDEBOX = b;
        b.getMesSolutionBox().add(this);
        this.MASOLUTION = s;
        s.getMesSolutionBox().add(this);
        
    }
    
    /**
     * Constructeur par données
     * @param b type de box à ajouter
     * @param s Solution à attribuer
     * @param pile  Liste de piles produits à attribuer
     */
    public SolutionBox(Box b, Solution s, ArrayList<PileDeProduits> pile){
        this(b,s);
        if(ControlerPile(pile))
            mesPiles = pile;
        else{
            return;
        }
    }

    // GETTERS & SETTERS 

    /**
     * Permet de récupérer la solution lié à cette solution box
     * @return solution de la solution box
     */
    public Solution getMASOLUTION() {
        return MASOLUTION;
    }

    /**
     * Permet d'attribuer une solution
     * @param MASOLUTION solution à attribuer
     */
    public void setMASOLUTION(Solution MASOLUTION) {
        this.MASOLUTION = MASOLUTION;
    }        

    /**
     * Récupérer la liste des piles de la solution
     * @return liste pile de produits
     */
    public List<PileDeProduits> getMesPiles() {
        return mesPiles;
    }

    /**
     * Attribuer une liste de piles de produits à la solution
     * @param mesPiles liste de produits à attribuer
     */
    public void setMesPiles(List<PileDeProduits> mesPiles) {
        this.mesPiles = mesPiles;
    }

    /**
     * Récupérer l'id de la solution box
     * @return id de la solution
     */
    public int getId() {
        return id;
    }

    /**
     * Attribuer un id à la solution box
     * @param id id de la solution
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Récupérer la box de la solution
     * @return box
     */
    public Box getTYPEDEBOX() {
        return TYPEDEBOX;
    }

    /**
     * Attribuer une box à une solution
     * @param TYPEDEBOX box à attribuer
     */
    public void setTYPEDEBOX(Box TYPEDEBOX) {
        this.TYPEDEBOX = TYPEDEBOX;
    }
    
    /**
     * Récupérer la largeur de la box
     * @return largeur box
     */
    public int getLargeur(){
        return this.TYPEDEBOX.getLargeur();
    }
    
    /**
     * Récupérer la hauteur de la box
     * @return hauteur box
     */
    public int getHauteur(){
        return this.TYPEDEBOX.getHauteur();
    }
    
    /**
     * Récupérer le prix d'une box
     * @return prix
     */
    public double getPrix(){
        return this.TYPEDEBOX.getPrix();
    }
    
    // METHODES 
    
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

    /**
     * Permet d'ajouter une Pile de produits dans la solution
     * @param p
     * @return un boolean afin de savoir si la pile a pu être ajouté
     */
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
        
    public void afficherPrix() {
        System.out.print("\t\t\t");
        System.out.print(this.getPrix());
        System.out.print("\n");
    }  
}