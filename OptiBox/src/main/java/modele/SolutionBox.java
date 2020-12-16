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

/**
 *
 * @author simon
 * @version 1.0
 */
@Entity
public class SolutionBox extends Box implements Serializable {

    /***************************** PARAMETRES ***************************/
    
    @OneToMany(mappedBy="MABOX",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<PileDeProduits> mesPiles;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MASOLUTION")
    private Solution MASOLUTION;
    
    /*************************** CONSTRUCTEURS **************************/
    
    public SolutionBox(){
        this.mesPiles = new ArrayList<>();
    }
    
    public SolutionBox(Box b){
        this();
        this.Hauteur = b.getHauteur();
        this.Largeur = b.getLargeur();
        this.id = b.id;
        this.prix = b.prix;
        
    }
    
    public SolutionBox(Box b, ArrayList<PileDeProduits> pile){
        this(b);
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
            
        
        return !(largeur > this.Largeur || hauteur > this.Hauteur);        
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
        if (!(object instanceof SolutionBox)) {
            return false;
        }
        SolutionBox other = (SolutionBox) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.mesPiles.toString();
    }


    
}
