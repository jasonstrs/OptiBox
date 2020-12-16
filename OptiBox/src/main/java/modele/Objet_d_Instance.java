/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author simon
 */
@Entity
@Table(
        name="OBJET_D_INSTANCE",
        uniqueConstraints={
            @UniqueConstraint(
                columnNames={"IDOBJET","MONINSTANCE"})
        }
)
@Access(AccessType.FIELD)
public class Objet_d_Instance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idGenere;
    
    @Column(
            name="IDOBJET",
            nullable=false
    )
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
    @JoinColumn(name="MonInstance")
    protected Instance monInstance = null;
 
    public Objet_d_Instance(){
        this.id= "";
        this.Hauteur=0;
        this.Largeur=0;
    }
    
    public Objet_d_Instance(String id, int Largeur, int Hauteur){
        this();
        if(Hauteur>0 && Largeur>0){
            this.id=id;
            this.Hauteur=Hauteur;
            this.Largeur=Largeur;
        }
    }

    public int getIdGenere() {
        return idGenere;
    }

    public void setIdGenere(int idGenere) {
        this.idGenere = idGenere;
    }    

    public int getHauteur() {
        return Hauteur;
    }

    public void setHauteur(int Hauteur) {
        if(Hauteur>0){
            this.Hauteur = Hauteur;
        }
    }

    public int getLargeur() {
        return Largeur;
    }

    public void setLargeur(int Largeur) {
        if(Largeur>0){
            this.Largeur = Largeur;
        }
    }

    public Instance getMonInstance() {
        return monInstance;
    }

    public void setMonInstance(Instance monInstance) {
        this.monInstance = monInstance;
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

//    @Override
//    public String toString() {
//        return "Objet_d_Instance{Hauteur=" + Hauteur + ", Largeur=" + Largeur + ", Instance=" + this.monInstance.getNom() + '}';
//    }

    
    
}
