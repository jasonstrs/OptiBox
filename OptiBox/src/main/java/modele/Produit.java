/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author simon
 */
@Entity
public class Produit extends modele.Objet_d_Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    // ATTRIBUTS 
    
    /**
     * Quantite d'un produit
     */
    @Column
    (
        name="quantite"
    )
    private int quantite;
    
    /**
     * Groupe d'un produit. Cet attribut permet de rassembler les produits identiques pour gérer la quantité
     *   par exemple, si un produit à une quantité de 10, 10 produits de ce type auront le même groupe
     */
    @Column
    (
        name="groupe"
    )
    private int groupe; 
    
    /**
     * Pile dans laquelle se trouve le produit
     */
    @ManyToOne
    @JoinColumn(name="MAPILE")
    private PileDeProduits MAPILE = null;

    // CONSTRUCTEURS 
    
    /**
     * Constructeur par défaut
     */
    public Produit(){
        this.quantite=1;
    }
    
    /**
     * Constructeur par données
     * @param id id du produit
     * @param l largeur du produit
     * @param h hauteur du produit
     * @param quantite quantite du produit
     */
    public Produit(String id,int l,int h,int quantite){
        super(id,l,h);
        if(quantite>=1)
            this.quantite = quantite; 
        else{
            quantite=1;
        }
    }
    
    /**
     * Constructeur par données
     * @param id id du produit
     * @param l largeur du produit
     * @param h hauteur du produit
     * @param quantite quantite du produit
     * @param color couleur du produit
     */
    public Produit(String id,int l,int h,int quantite,Color color){
        super(id,l,h,color);
        if(quantite>=1)
            this.quantite = quantite; 
        else{
            quantite=1;
        }
    }
    
    /**
     * Constructeur par données
     * @param id id du produit
     * @param l largeur du produit
     * @param h hauteur du produit
     * @param quantite quantite du produit
     * @param color couleur du produit
     * @param groupe groupe du produit
     */
    public Produit(String id,int l,int h,int quantite,Color color,int groupe){
        super(id,l,h,color);
        if(quantite>=1)
            this.quantite = quantite; 
        else{
            quantite=1;
        }
        this.groupe=groupe;
    }
    
    // GETTERS & SETTERS 

    /**
     * permet de récupérer la quantite du produit
     * @return quantité
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Permet de mettre une quantité à un produit
     * @param quantite quantité à attribuer
     */
    public void setQuantite(int quantite) {
        if(quantite>=0){
            this.quantite = quantite;
        }
    }

    /**
     * Permet de récupérer la pile dans laquelle se trouve le produit
     * @return pile
     */
    public PileDeProduits getMAPILE() {
        return MAPILE;
    }

    /**
     * Permet de mettre un produit dans une pile
     * @param MAPILE pile à attribuer
     */
    public void setMAPILE(PileDeProduits MAPILE) {
        this.MAPILE = MAPILE;
    }    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Affiche l'id du Produit dans la console 
     */
    public void afficherID() {
        System.out.print("\t\t\t");
        System.out.print(this.id);
        System.out.print("\n");
    }

}
