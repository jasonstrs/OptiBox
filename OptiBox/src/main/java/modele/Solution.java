package modele;

import java.awt.Color;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author simon
 * @version 1.0
 */
@Entity
public class Solution implements Serializable {

    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**************************** PARAMETRES ************************/
    
    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="INSTANCE")
    private Instance monInstance;
    
    @Column(
        name="COUT"
    )
    private double cout;
    
    @OneToMany(mappedBy="MASOLUTION",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    Collection<SolutionBox> mesSolutionBox;

    /**************************** CONSTRUCTEURS ************************/
    
    public Solution() {
        this.mesSolutionBox = new ArrayList<>();
        this.cout=0;
        this.monInstance = null;
    }
    
    public Solution(Instance i){
        this();
        this.monInstance = i;
        i.setMaSolution(this);
    }        
    
    /************************** GETTERS & SETTERS **********************/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instance getMonInstance() {
        return monInstance;
    }

    public void setMonInstance(Instance monInstance) {
        this.monInstance = monInstance;
    }

    public double getCout() {
        this.calculerCout();
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public Collection<SolutionBox> getMesSolutionBox() {
        return mesSolutionBox;
    }

    public void setMesSolutionBox(Collection<SolutionBox> mesSolutionBox) {
        this.mesSolutionBox = mesSolutionBox;
    }

    
    
    /**************************** METHODES *************************/
    
    public void TestCalculerSolution(){
       ArrayList<Box> BoxDispo = this.monInstance.getBox();

       int i=0, nbBox = BoxDispo.size();
       
       boolean flag=false;

       for(Produit p : this.monInstance.getProduits()){

           ArrayList<PileDeProduits> dpp = new ArrayList<>();
           
           SolutionBox sb = new SolutionBox(BoxDispo.get(i),this,dpp);
           
           // le constructeur ci dessous ajoute la pile dans les piles de la solution
           // il ne faut donc pas ré ajouter la pile dans la liste des piles
           PileDeProduits pp = new PileDeProduits(sb);
           p.setMAPILE(pp);
           pp.getMESPRODUITS().add(p);
           
           if (!flag){ // si c'est la première fois qu'on passe dans le for
               flag=true;
               Produit p1 = new Produit("P2541",20,80,10,Color.red);
               Produit p2 = new Produit("P2541",50,30,10,Color.blue);
               Produit p3 = new Produit("P2541",40,40,10,Color.green);
               
               p1.setMAPILE(pp);
               pp.getMESPRODUITS().add(p1);
               
               PileDeProduits pp2 = new PileDeProduits(sb);
               p2.setMAPILE(pp2);
               p3.setMAPILE(pp2);
               pp2.getMESPRODUITS().add(p2);
               pp2.getMESPRODUITS().add(p3);
               pp2.UpdateTaille();
               
           }

           pp.UpdateTaille();
           // dpp.add(pp);   NON, ON NE RÉ-AJOUTE PAS LA PILE DANS LES PILES
           
           //this.mesSolutionBox.add(sb);
           
           if(i>=nbBox)i=0;                            
           
       }
    }
    
    public double calculerCout(){
        double prixTotalSolution = 0;
        
        for(var b : this.mesSolutionBox){
            prixTotalSolution+=b.getPrix();
        }
        
        return prixTotalSolution;
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
        if (!(object instanceof Solution)) {
            return false;
        }
        Solution other = (Solution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return this.mesSolutionBox.toString();
//    }
    
}
