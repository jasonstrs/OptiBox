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
public class Objet_d_Instance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;
    
    @Column(
            name="Hauteur",
            nullable=false
    )
    protected int Hauteur;   
    
    @Column(
            name="Largeur",
            nullable=false
    )
    protected int Largeur;   

    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="idInstance")
    protected Long idInstance;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHauteur() {
        return Hauteur;
    }

    public void setHauteur(int Hauteur) {
        this.Hauteur = Hauteur;
    }

    public int getLargeur() {
        return Largeur;
    }

    public void setLargeur(int Largeur) {
        this.Largeur = Largeur;
    }

    public Long getIdInstance() {
        return idInstance;
    }

    public void setIdInstance(Long idInstance) {
        this.idInstance = idInstance;
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
        if (!(object instanceof Objet_d_Instance)) {
            return false;
        }
        Objet_d_Instance other = (Objet_d_Instance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}
