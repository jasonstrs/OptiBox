package modele;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author simon
 * @version 1.0
 */
@Entity
public class PileDeProduits implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /****************************** PARAMETRES ****************************/
    
    @Column
    (
        name="LARGEUR"
    )
    int largeur;
    @Column
    (
        name="HAUTEUR"
    )
    int hauteur;
    
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MABOX")
    private SolutionBox MABOX;
            
    @OneToMany(mappedBy="MAPILE",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Produit> MESPRODUITS;
    
    /****************************** CONSTRUCTEURS ****************************/
    
    public PileDeProduits() {
        this.MABOX = null;
        this.MESPRODUITS = new LinkedList();
    }
    
    public PileDeProduits(SolutionBox sb){
        this();
        MABOX = sb;
        sb.getMesPiles().add(this);
        this.UpdateTaille();
    }
        
    /****************************** GETTERS & SETTERS ****************************/
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLargeur() {
        CalculerLargeur();
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        CalculerHauteur();
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public SolutionBox getMABOX() {
        return MABOX;
    }

    public void setMABOX(SolutionBox MABOX) {
        this.MABOX = MABOX;
    }

    public List<Produit> getMESPRODUITS() {
        return MESPRODUITS;
    }

    public void setMESPRODUITS(LinkedList<Produit> MESPRODUITS) {
        this.MESPRODUITS = MESPRODUITS;
    }
    
    
    
    /****************************** METHODES ****************************/

    /**
     * Calcule la largeur totale de la pile (Largeur du 1er élément)
     * Place le résultat dans la variable longueur
     */
    private void CalculerLargeur() {
        if(this.MESPRODUITS.isEmpty()){
            this.largeur = 0;
            return;
        }
        Produit p = this.MESPRODUITS.get(0);
        if(p==null)this.largeur = 0;
        else this.largeur = p.getLargeur();
    }

    /**
     * Calcule la hauteur totale de la pile (Somme des hauteurs de chaque élément)
     * Place le résultat dans la variable hauteur
     */
    private void CalculerHauteur() {
        if(this.MESPRODUITS.isEmpty()){
            this.hauteur = 0;
            return;
        }
        int h = 0;
        for(Produit p : this.MESPRODUITS)
            h+=p.getHauteur();
        this.hauteur = h;    
    }
    
    /**
     * Essaie d'ajouter un élément à la pile
     * Vérifie que l'élément ne dépasse pas en hauteur
     * Si la pile est vide, vérifie que l'élément ne dépasse pas en largeur
     * Vérifie si la largeur du produit n'est pas plus grande que celle du dernier produit de la pile
     * @param p Le produit qu'on veut ajouter
     * @return true si on a ajouté, false sinon
     */
    public boolean AjouterElement(Produit p){
        if( (this.MESPRODUITS.isEmpty() && p.getLargeur() > MABOX.getLargeur())
            || (this.getHauteur() + p.getHauteur() > MABOX.getHauteur())  
            || (!this.MESPRODUITS.isEmpty() && this.MESPRODUITS.get(this.MESPRODUITS.size() - 1).getLargeur() < p.getLargeur())
        )
            return false;
        
        return this.MESPRODUITS.add(p);
    }
    
    
    public void UpdateTaille() {
        this.CalculerHauteur();
        this.CalculerLargeur();
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
        if (!(object instanceof PileDeProduits)) {
            return false;
        }
        PileDeProduits other = (PileDeProduits) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return this.MESPRODUITS.toString();
//    }

    


    
}
