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
    
    /**
     * ID généré par la BDD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idGenere;
    
    // ATTRIBUTS 
    
    /**
     * ID de l'objet
     */
    @Column(
            name="IDOBJET",
            nullable=false
    )
    protected String id;
    
    /**
     * Hauteur de l'objet
     */
    @Column(
            name="Hauteur",
            nullable=false
    )
    protected int Hauteur;   
    
    /**
     * Largeur de l'objet
     */
    @Column(
            name="Largeur",
            nullable=false
    )
    protected int Largeur;   

    /**
     * Instance de l'objet
     */
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="MonInstance")
    protected Instance monInstance = null;
    
    /**
     * Couleur de l'objet
     */
    @Column(
            name="COLOR"
    )
    private String color;
     
    // CONSTRUCTEURS 
    
    /**
     * Constructeur par défaut
     */
    public Objet_d_Instance(){
        this.id= "";
        this.Hauteur=0;
        this.Largeur=0;
        this.setColor(Color.BLACK);
    }
    
    /**
     * Constructeur par données
     * @param id id de l'objet
     * @param Largeur largeur de l'objet
     * @param Hauteur  hauteur de l'objet
     */
    public Objet_d_Instance(String id, int Largeur, int Hauteur){
        this();
        if(Hauteur>0 && Largeur>0){
            this.id=id;
            this.Hauteur=Hauteur;
            this.Largeur=Largeur;
        }
    }
    
    /**
     * Constructeur par données
     * @param id id de l'objet
     * @param Largeur largeur de l'objet
     * @param Hauteur hauteur de l'objet
     * @param color couleur de l'objet
     */
    public Objet_d_Instance(String id, int Largeur, int Hauteur, Color color){
        this();
        if(Hauteur>0 && Largeur>0){
            this.id=id;
            this.Hauteur=Hauteur;
            this.Largeur=Largeur;
        }
        this.setColor(color);
    }
    
    // GETTERS & SETTERS 

    /**
     * Permet de récupérer l'ID généré de la BDD
     * @return l'id généré
     */
    public int getIdGenere() {
        return idGenere;
    }   

    /**
     * Permet de récupérer la hauteur d'un objet
     * @return la hauteur
     */
    public int getHauteur() {
        return Hauteur;
    }

    /**
     * Permet d'attribuer une valeur de hauteur
     * @param Hauteur hauteur à attribuer
     */
    public void setHauteur(int Hauteur) {
        if(Hauteur>0){
            this.Hauteur = Hauteur;
        }
    }

    /**
     * Permet de récupérer la largeur
     * @return la largeur de l'objet
     */
    public int getLargeur() {
        return Largeur;
    }

    /**
     * Permet d'attribuer une largeur à un objet
     * @param Largeur largeur à attribuer
     */
    public void setLargeur(int Largeur) {
        if(Largeur>0){
            this.Largeur = Largeur;
        }
    }

    /**
     * Récupérer l'instance d'un objet
     * @return l'instance
     */
    public Instance getMonInstance() {
        return monInstance;
    }

    /**
     * Mettre une instance à un objet
     * @param monInstance instance à mettre
     */
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

    /**
     * Permet de récupérer l'ID de l'objet
     * @return id de l'objet
     */
    public String getIdString() {
        return id;
    }

    /**
     * Permet d'attribuer un ID à l'objet
     * @param id id à attribuer
     */
    public void setIdString(String id) {
        this.id = id;
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
