/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author simon
 */
@Entity
public class Produit extends modele.Objet_d_Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**************************** ATTRIBUTS ************************/
    
    @Column
    (
        name="quantite"
    )
    private int quantite;
    
    @Column
    (
        name="groupe"
    )
    private int groupe; // cet attribut permet de rassembler les produits identiques pour gérer la quantité
                        // par exemple, si un produit à une quantité de 10, 10 produits de ce type auront le même groupe
    
    /**
     * Pile de produits dans la solution
     */
    @ManyToOne
    @JoinColumn(name="MAPILE")
    private PileDeProduits MAPILE = null;

    /**************************** CONSTRUCTEURS ************************/
    
    public Produit(){
        this.quantite=1;
    }
    
    public Produit(String id,int l,int h,int quantite){
        super(id,l,h);
        if(quantite>=1)
            this.quantite = quantite; 
        else{
            quantite=1;
        }
    }
    
    public Produit(String id,int l,int h,int quantite,Color color){
        super(id,l,h,color);
        if(quantite>=1)
            this.quantite = quantite; 
        else{
            quantite=1;
        }
    }
    
    public Produit(String id,int l,int h,int quantite,Color color,int groupe){
        super(id,l,h,color);
        if(quantite>=1)
            this.quantite = quantite; 
        else{
            quantite=1;
        }
        this.groupe=groupe;
    }
    
    /****************************** GETTERS & SETTERS ****************************/

    
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        if(quantite>=0){
            this.quantite = quantite;
        }
    }

    public PileDeProduits getMAPILE() {
        return MAPILE;
    }

    public void setMAPILE(PileDeProduits MAPILE) {
        this.MAPILE = MAPILE;
    }
    
    /**************************** METHODES ************************/
    
    
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

//    @Override
//    public String toString() {
//        return "Produit{"+ super.toString() + "quantite=" + quantite + '}';
//    }

    /**
     * Affiche l'id du Produit dans la console 
     */
    public void afficherID() {
        System.out.print("\t\t\t");
        System.out.print(this.id);
        System.out.print("\n");
    }


    
}
