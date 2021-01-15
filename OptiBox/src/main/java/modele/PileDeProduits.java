package modele;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class PileDeProduits implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID généré par la BDD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // ATTRIBUTS 
    
    /**
     * Largeur d'une pile
     */
    @Column
    (
        name="LARGEUR"
    )
    int largeur;
    
    /**
     * Hauteur d'une pile
     */
    @Column
    (
        name="HAUTEUR"
    )
    int hauteur;
    
    /**
     * Objet Solution box dans lequel se trouve la pile
     */
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MABOX")
    private SolutionBox MABOX;
            
    /**
     * Liste des produits de la pile
     */
    @OneToMany(mappedBy="MAPILE",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Produit> MESPRODUITS;
    
    // CONSTRUCTEURS 
    
    /**
     * Constructeur par défaut
     */
    public PileDeProduits() {
        this.MABOX = null;
        this.MESPRODUITS = new LinkedList();
    }
    
    /**
     * Constructeur par données
     * @param sb SolutionBox de la pile
     */
    public PileDeProduits(SolutionBox sb){
        this();
        MABOX = sb;
        sb.getMesPiles().add(this);
        this.UpdateTaille();
    }
        
    // GETTERS & SETTERS 
    
    /**
     * Récupérer l'id de la pile
     * @return id de la pile
     */
    public Long getId() {
        return id;
    }

    /**
     * Récupérer la largeur de la pile
     * @return la largeur
     */
    public int getLargeur() {
        CalculerLargeur();
        return largeur;
    }

    /**
     * Récupérer la hauteur de la pile
     * @return la hauteur de la pile
     */
    public int getHauteur() {
        CalculerHauteur();
        return hauteur;
    }

    /**
     * Récupérer la solution box de la pile
     * @return la solution box
     */
    public SolutionBox getMABOX() {
        return MABOX;
    }

    /**
     * Attribuer une solution box à une pile
     * @param MABOX solution box à attribuer
     */
    public void setMABOX(SolutionBox MABOX) {
        this.MABOX = MABOX;
    }

    /**
     * Récupérer la liste de produits d'une pile
     * @return produits de la pile
     */
    public List<Produit> getMESPRODUITS() {
        return MESPRODUITS;
    }

    /**
     * Affecter une liste de produits à une pile
     * @param MESPRODUITS liste à attribuer
     */
    public void setMESPRODUITS(LinkedList<Produit> MESPRODUITS) {
        this.MESPRODUITS = MESPRODUITS;
    }
    
    // METHODES 

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
    
    /**
     * Met à jour les infos de largeur et hauteur
     */
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
}
