/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
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
    
    /**************************** ATTRIBUTS ************************/
    
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
    
    @Column(
            name="COLOR"
    )
    private String color;
     
    /**************************** CONSTRUCTEURS ************************/
    
    public Objet_d_Instance(){
        this.id= "";
        this.Hauteur=0;
        this.Largeur=0;
        this.setColor(Color.BLACK);
    }
    
    public Objet_d_Instance(String id, int Largeur, int Hauteur){
        this();
        if(Hauteur>0 && Largeur>0){
            this.id=id;
            this.Hauteur=Hauteur;
            this.Largeur=Largeur;
        }
    }
    
    public Objet_d_Instance(String id, int Largeur, int Hauteur, Color color){
        this();
        if(Hauteur>0 && Largeur>0){
            this.id=id;
            this.Hauteur=Hauteur;
            this.Largeur=Largeur;
        }
        this.setColor(color);
    }
    
    /**************************** GETTERS & SETTERS ************************/

    public int getIdGenere() {
        return idGenere;
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

    /**
     * Récupère la couleur de l'objet sous forme de String, et renvoie l'objet Color correspondant
     * @return Color
     */
    public Color getColor() {
        return new Color(Integer.parseInt(color)); 
    }

    /**
     * Traduit la Color passée en paramètre en une STring correspondante, et l'applique à l'Objet
     * @param color Color qu'on veut affecter à l'Objet
     */
    public void setColor(Color color) {
        if (color!=null)
            this.color = Integer.toString(color.getRGB());
    }

    public String getIdString() {
        return id;
    }

    public void setIdString(String id) {
        this.id = id;
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
