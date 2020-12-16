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

    @Column
    (
        name="quantite"
    )
    private int quantite;
    
    /**
     * Pile de produits dansla solution
     */
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MAPILE")
    private PileDeProduits MAPILE = null;

    public Produit(){
        this.quantite=0;
    }
    
    public Produit(String id,int l,int h,int quantite){
        super(id,l,h);
        if(quantite>=0)
            this.quantite = quantite; 
        else{
            quantite=0;
        }
    }
    
    public Produit(String id,int l,int h,int quantite,Color color){
        super(id,l,h,color);
        if(quantite>=0)
            this.quantite = quantite; 
        else{
            quantite=0;
        }
    }
    
        /****************************** METHODES ****************************/

    
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


    
}
