package modele;

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
    Collection<SolutionBox> mesBox;

    /**************************** CONSTRUCTEURS ************************/
    
    public Solution() {
        this.mesBox = new ArrayList<>();
        this.cout=0;
        this.monInstance = null;
    }
    
    public Solution(Instance i){
        this();
        this.monInstance = i;
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

    public Collection<SolutionBox> getMesBox() {
        return mesBox;
    }

    public void setMesBox(Collection<SolutionBox> mesBox) {
        this.mesBox = mesBox;
    }

    
    
    /**************************** METHODES *************************/
    
    private void TestCalculerSolution(){
       Box BoxDispo[] = (Box[]) this.monInstance.getBox().toArray();
       int i=0, nbBox = BoxDispo.length;
       for(Produit p : this.monInstance.getProduits()){
           
           ArrayList<PileDeProduits> dpp = new ArrayList<>();
           PileDeProduits pp = new PileDeProduits();
           pp.getMESPRODUITS().add(p);
           
           dpp.add(pp);
           
           SolutionBox sb = new SolutionBox(BoxDispo[i++],dpp);
           if(i>=nbBox)i=0;
           
           
           
           
           
       }
    }
    
    public double calculerCout(){
        double prixTotalSolution = 0;
        
        for(var b : this.mesBox){
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

    @Override
    public String toString() {
        return this.mesBox.toString();
    }
    
}
