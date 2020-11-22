/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Access;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.AccessType;

/**
 *
 * @author simon
 */
@Entity
@Access(AccessType.FIELD)
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @OneToMany(mappedBy="monInstance",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private Collection<modele.Objet_d_Instance> ObjetsDeLInstance;

    @Column
    ( 
        name="Nom",
        length=40,
        nullable=false
    )
    private String nom;
       
    
    /**************************** CONSTRUCTEURS ************************/
    
    public Instance(){
        this.id= new Long(0);
        this.nom="Nom_Par_Défaut";
        this.ObjetsDeLInstance = new ArrayList<>();
    }
    
    public Instance(String nom){
        this();
        this.nom=nom;
    }
    
    public Instance(Long ID, String nom){
        this(nom);
        this.id = ID;   
    }
    
        public Instance(Long ID, String nom,ArrayList<modele.Objet_d_Instance> l){
        this(ID,nom);
        this.ObjetsDeLInstance = l;
    }
    
    /****************************** METHODES ****************************/
    
    public void ajouterObjet(modele.Objet_d_Instance o){
        o.setMonInstance(this);
        this.ObjetsDeLInstance.add(o);
    }
    

    /************************* GETTERS ET SETTERS ***********************/
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Collection<Objet_d_Instance> getObjetsDeLInstance() {
        return ObjetsDeLInstance;
    }

    public void setObjetsDeLInstance(Collection<Objet_d_Instance> ObjetsDeLInstance) {
        this.ObjetsDeLInstance = ObjetsDeLInstance;
    }     

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

   /* @Override
    public String toString() {
        return "modele.Instance[ id=" + id + " ]";
    }*/

//    @Override
//    public String toString() {
//        return "Instance{" + "id=" + id + ", ObjetsDeLInstance=" + ObjetsDeLInstance + ", nom=" + nom + '}';
//    }
    
    
    
}
