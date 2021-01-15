package modele;

import java.awt.Color;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
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
    
    /**************************** ATTRIBUTS ************************/
    
    @OneToOne
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
        cout = this.calculerCout();
        return cout;
    }

    public Collection<SolutionBox> getMesSolutionBox() {
        return mesSolutionBox;
    }

    public void setMesSolutionBox(Collection<SolutionBox> mesSolutionBox) {
        this.mesSolutionBox = mesSolutionBox;
        this.calculerCout();
    }
    
    /**************************** METHODES *************************/
    
    /**
     * Algorithme de test basique, pour pouvoir tester en attendant le vrai
     */
    public void TestCalculerSolution(){
        List<Box> BoxDispo = this.monInstance.getBox();
        List<Produit> ProduitDispo = this.monInstance.getProduits();
        boolean flag=false;
        int nbProduit=ProduitDispo.size();
        int i=0;
        Produit produit=null;
        int hauteurUtilise=0;
        int largeurUtilise=0;
        int index;
        boolean ajoutPossible=true;
        
        Collections.sort(ProduitDispo, new Comparator<Produit>() {
            @Override
            public int compare(Produit lhs, Produit rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getLargeur() > rhs.getLargeur() ? -1 : (lhs.getLargeur() < rhs.getLargeur()) ? 1 : 0;
            }
        });
        
         Collections.sort(BoxDispo, new Comparator<Box>() {
            @Override
            public int compare(Box lhs, Box rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getLargeur() > rhs.getLargeur() ? -1 : (lhs.getLargeur() < rhs.getLargeur()) ? 1 : 0;
            }
        });
        
        // on prépare le tableau permettant de gérer les Produit
        int tableauVerif[]=new int[nbProduit];
        for(i=0;i<nbProduit;i++){
            tableauVerif[i]=-1;
        }
        
        while (encoreDesProduits(tableauVerif)){
            // on récupère un produit
            index=getRecupererPlusLargeObjetDispo(tableauVerif); // on récupère le premier produit

            if (index == -1) // il n'y a plus de produit
                break;
            produit = ProduitDispo.get(index);
            
            // on crée un premier contenuBox
            ArrayList<PileDeProduits> dpp = new ArrayList<>(); // on crée une pile de produit
            SolutionBox sb = new SolutionBox(BoxDispo.get(0),this,dpp); // on crée une solution box
            
            while(largeurUtilise+produit.getLargeur()< sb.getTYPEDEBOX().getLargeur()){ // tant que l'on peut mettre des piles
                PileDeProduits pp = new PileDeProduits(sb); // on crée une première pile
                largeurUtilise+=produit.getLargeur();
                tableauVerif[index]=1;
                produit.setMAPILE(pp); // on ajoute le produit à la pile
                pp.getMESPRODUITS().add(produit); // on fait le 2e lien
                hauteurUtilise+=produit.getHauteur();
                //System.out.println("La box a une hauteur de : "+sb.getTYPEDEBOX().getHauteur());
                for (int k=0;k<tableauVerif.length;k++){
                    if (tableauVerif[k] == -1){
                        produit = ProduitDispo.get(k);
                        if (hauteurUtilise+produit.getHauteur() < sb.getTYPEDEBOX().getHauteur()){ // on peut mettre le produit
                            tableauVerif[k]=1;
                            produit.setMAPILE(pp); // on ajoute le produit à la pile
                            pp.getMESPRODUITS().add(produit); // on fait le 2e lien
                            hauteurUtilise+=produit.getHauteur();
                        }
                    }
                }
                index=getRecupererPlusLargeObjetDispo(tableauVerif); // on récupère le premier produit
                if (index == -1) // il n'y a plus de produit
                    break;
                produit = ProduitDispo.get(index);
                hauteurUtilise=0;
                pp.UpdateTaille();
            }
            largeurUtilise=0;     
        }
        
       
       /*int i=0, nbBox = BoxDispo.size();
       
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
       this.calculerCout();*/
    }
    
    /**
     * Calcule le coût actuel de la solution
     * @return 
     */
    public double calculerCout(){
        double prixTotalSolution = 0;
        
        for(var b : this.mesSolutionBox){
            prixTotalSolution+=b.getPrix();
        }
        
        return prixTotalSolution;
    }
    
    
    /**
     * Cette fonction permet de vérifier qu'il y a encore des produits disponibles
     * @param T tableau de int
     * @param nbProduit nombre de produit dans le tableau
     * @return vrai s'il reste des produits
     */
    public boolean encoreDesProduits(int T[]){
        for (int j=0;j<T.length;j++){
           if (T[j] == -1)
               return true; 
        }
        return false;
    }
    
    public int getRecupererPlusLargeObjetDispo(int T[]){
        int index=-1,i=0;
        for (i=0;i<T.length;i++){
            if (T[i] == -1){
                index=i;
                break;
            }
        }
        if (index == -1) // il n'y a plus de produit disponibles
            return -1;
        return index;
    }
    
   
    /*@Override
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
    }*/

    /**
     * Remplace la toString(), qui bug avec la persistence JPA
     */
    public void afficher() {
        this.calculerCout();
        System.out.println("Solution : ");
        System.out.println("{");
        for(SolutionBox sb : this.mesSolutionBox){
            
            System.out.println("\tSolutionBox : \n\t{");
            System.out.println("\tPrix : ");
            sb.afficherPrix();
            for(PileDeProduits pp : sb.getMesPiles()){
                
                System.out.println("\t\tPileDeProduits : \n\t\t{");
                
                for(Produit p : pp.getMESPRODUITS()){
                    System.out.println("\t\t\t ");                    
                    p.afficherID();
                    
                }
                
                System.out.println("\t\t}");
            }
            
            System.out.println("\t}");
        }
    }
    
}
