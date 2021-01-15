/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.AccessType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author simon
 * @version 1.3
 */
@Entity
@Access(AccessType.FIELD)
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // ATTRIBUTS 

    /**
     * ID généré par la box
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    /**
     * Objet que contient l'instance
     */
    @OneToMany(mappedBy="monInstance",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private Collection<modele.Objet_d_Instance> ObjetsDeLInstance;

    /**
     * Nom de l'instance
     */
    @Column
    ( 
        name="Nom",
        length=40,
        nullable=false
    )
    private String nom;
       
    /**
     * Solution de l'instance
     */
    @OneToOne(cascade={CascadeType.REMOVE})
    @JoinColumn(name="SOLUTION")
    private Solution maSolution;
    
    
    // CONSTRUCTEURS 
    
    /**
     * Constructeur par défaut
     */
    public Instance(){
        this.id= Long.parseLong("0");
        this.nom="Nom_Par_Défaut";
        this.ObjetsDeLInstance = new ArrayList<>();
    }
    
    /**
     * Constructeur par données
     * @param nom Nom de l'instance
     */
    public Instance(String nom){
        this();
        this.nom=nom;
    }
    
    /**
     * Constructeur par données
     * @param ID Identifiant de l'instance
     * @param nom nom de l'instance
     */
    public Instance(Long ID, String nom){
        this(nom);
        this.id = ID;   
    }
    
    /**
     * Constructeur par données
     * @param ID Identifiant de l'instance
     * @param nom Nom de l'instance
     * @param l Liste d'objet
     */
    public Instance(Long ID, String nom,ArrayList<modele.Objet_d_Instance> l){
        this(ID,nom);
        this.ObjetsDeLInstance = l;
    }
    
    /****************************** METHODES ****************************/
    
    /**
     * Méthode qui permet d'ajouter un objet dans une instance
     * @param o objet à ajouter dans l'instance
     */
    public void ajouterObjet(modele.Objet_d_Instance o){
        o.setMonInstance(this);
        this.ObjetsDeLInstance.add(o);
    }
    
    /**
     * Méthode qui permet de récupérer les box de l'instance
     * @return l list des box de l'instance
     */
    public ArrayList<Box> getBox(){
        ArrayList<Box> l = new ArrayList<>();
        for(Objet_d_Instance o : this.ObjetsDeLInstance){
            if(o.getClass() == Box.class)
                l.add((Box)o);
        }
        return l;
    }
    
    /**
     * Méthode qui permet de récupérer les produits de l'instance
     * @return l liste des produits de l'instance
     */
    public ArrayList<Produit> getProduits(){
        ArrayList<Produit> l = new ArrayList<>();
        for(Objet_d_Instance o : this.ObjetsDeLInstance){
            if(o.getClass() == Produit.class)
                l.add((Produit)o);
        }
        return l;
    }
    

    // GETTERS ET SETTERS 
    
    /**
     * Permet de récupérer l'id
     * @return id de l'instance
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Permet de récupérer la liste d'objet de l'instance
     * @return ObjetsDeLInstance liste d'objet 
     */
    public Collection<Objet_d_Instance> getObjetsDeLInstance() {
        return ObjetsDeLInstance;
    }

    /**
     * Méthode permettant de mettre la liste d'objet à l'instance
     * @param ObjetsDeLInstance liste des objets à ajouter à l'instance
     */
    public void setObjetsDeLInstance(Collection<Objet_d_Instance> ObjetsDeLInstance) {
        this.ObjetsDeLInstance = ObjetsDeLInstance;
    }     

    /**
     * Méthode permettant de récupérer le nom
     * @return nom de l'instance
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode qui permet d'ajouter un nom à l'instance
     * @param nom nom de l'instance
     */
    public void setNom(String nom) {
        this.nom = nom;
    }    

    /**
     * Méthode qui permet de récupérer la solution de l'instance
     * @return Solution maSolution
     */
    public Solution getMaSolution() {
        return maSolution;
    }

    /**
     * Méthode qui permet d'attribuer une solution à l'instance
     * @param maSolution solution à ajouter à l'instance
     */
    public void setMaSolution(Solution maSolution) {
        this.maSolution = maSolution;
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
        if (!(object instanceof Instance)) {
            return false;
        }
        Instance other = (Instance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return nom;
    }
    
    
}
