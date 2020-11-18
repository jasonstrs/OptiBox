/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author simon
 */
@Entity
public class Box extends Objet_d_Instance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column
    (
        name="prix",
        nullable=false
    )
    private double prix;

    public Box(){
        this.prix=100;
    }
    
    public Box(String id,int l,int h,double prix){
        super(id,l,h);
        if(prix>0)
            this.prix=prix;
        else
            this.prix=100;
    }
    
    public double getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        if(prix>0){
            this.prix = prix;
        }
    }    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.id != null ? super.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Box)) {
            return false;
        }
        Box other = (Box) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Box{" + super.toString() + " prix=" + prix + '}';
    }
    
    
    
}
