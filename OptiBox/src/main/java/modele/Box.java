/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author simon
 */
@Entity
public class Box extends Objet_d_Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    // ATTRIBUTS 

    /**
     * Prix de la box
     */
    @Column
    (
        name="prix"
    )
    protected double prix;
    
    /**
     * Liste qui va contenir l'ensemble des solutions où se trouve la box
     */
    @OneToMany(mappedBy="TYPEDEBOX",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<SolutionBox> MesSolutionBox;
    
    
      /**
     * Constructeur par défaut
     */
    public Box(){
        this.prix=100;
    }
    
    /**
     * Constructeur par données
     * @param id Identifiant de la box
     * @param l Longueur
     * @param h Hauteur
     * @param prix Prix
     */
    public Box(String id,int l,int h,double prix){
        super(id,l,h,Color.YELLOW);
        MesSolutionBox = new HashSet<>();
        if(prix>0)
            this.prix=prix;
        else
            this.prix=100;
    }
    
    /**************************** GETTERS & SETTERS ************************/
    /**
     * Méthode permettant de récupérer le prix de la box
     * @return prix 
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Méthode qui permet d'attribuer un prix à la box
     * @param prix prix d'une box
     */
    public void setPrix(float prix) {
        if(prix>0){
            this.prix = prix;
        }
    }    

    /**
     * Méthode qui permet de récupérer les Solutions Box liées à la box
     * @return Les solutions box qui contiennent la box
     */
    public Set<SolutionBox> getMesSolutionBox() {
        return MesSolutionBox;
    }

    /**
     * Méthode qui permet de mettre les solutions box dans une box
     * @param MesSolutionBox solution box à metrre dans la box
     */
    public void setMesSolutionBox(Set<SolutionBox> MesSolutionBox) {
        this.MesSolutionBox = MesSolutionBox;
    }
    
    // METHODES 
    
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
}
