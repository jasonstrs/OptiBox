package modele;

import java.awt.Color;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
       this.calculerCout();
    }
    
    public double calculerCout(){
        double prixTotalSolution = 0;
        
        for(var b : this.mesSolutionBox){
            prixTotalSolution+=b.getPrix();
        }
        
        return prixTotalSolution;
    }
    
    static int existe(int T[], int val){
        for(int i=0; i<T.length;i++){
            if(val==T[i])
              return 1;
        }
        return -1;
    }
    
    public void algorithme(){
        ArrayList<Box> BoxDispo = this.monInstance.getBox();
        int i=0, nbBox = BoxDispo.size(),j,k;
        int compteur,iref, nbProduit, tailleRestante, hauteurMax;
        
        int flag, flagB;
        Produit p;
       
       //tri box taille
       Collections.sort(BoxDispo,(o1,o2)->o2.getLargeur()-o1.getLargeur());
       // parcours de tous les type de box afin de les définir comme box de référence
       for(i=0;i<nbBox;i++){
            Box boxRef=BoxDispo.get(i);
            iref=i;
           //tri produit taille 
            ArrayList<Produit> ProduitsDispo = this.monInstance.getProduits();
            nbProduit = ProduitsDispo.size();
            Collections.sort(ProduitsDispo,(o1,o2)->o2.getLargeur()-o1.getLargeur());
            compteur=0;
            j=0;
            
            int[] tableauVerif=new int[nbProduit];
            for(k=0;k<nbProduit;k++){
                tableauVerif[k]=-1;
            }
            //on va parcourir tous les produits ensuitz
            while(j<nbProduit){
                ArrayList<PileDeProduits> dpp = new ArrayList<>();
                p=ProduitsDispo.get(j);
                //Si ce n'est pas le premier tour de boucle et que le produit suivant le permet
                //on passe au format de box inférieur
                if(compteur==1 && iref<nbBox && BoxDispo.get(iref).getLargeur()>p.getLargeur()){
                   iref++;
                   boxRef=BoxDispo.get(iref);
                }
                //demande des paramètres nécessaires
                tailleRestante=boxRef.getLargeur();
                hauteurMax=boxRef.getHauteur();
                SolutionBox sb = new SolutionBox(boxRef,this,dpp);
                //Parcourt des produits
                flagB=0;
                while(j<nbProduit && flagB!=1){
                    PileDeProduits pp = new PileDeProduits(sb);
                    p.setMAPILE(pp);
                    if(existe(tableauVerif,j)==-1){
                        j++;
                    }
                    else{
                        //Vérification que la pile rentre
                        if(p.getLargeur()<=tailleRestante){
                            pp.getMESPRODUITS().add(p);
                            tailleRestante=tailleRestante-p.getLargeur();
                            flag=0;
                            //on constuit des piles jusque la hauteur max
                            while(j<nbProduit && flag !=1){
                                j++;
                                if(existe(tableauVerif,j)!=-1){            
                                    p=ProduitsDispo.get(j);
                                    if((p.getHauteur()+pp.getHauteur())<hauteurMax){
                                        pp.getMESPRODUITS().add(p);
                                        pp.UpdateTaille();
                                    }
                                    else{
                                        pp.UpdateTaille();
                                        flag=1;
                                    }
                                }
                            }
                        }
                        else{ 
                            //On vient parcourir les produits, jusqu'à trouver un produit à la bonne taille
                            for(k=j;k<nbProduit;k++){
                                if(!(ProduitsDispo.get(k).getLargeur()>tailleRestante||existe(tableauVerif,k)!=-1)){
                                       p=ProduitsDispo.get(k);
                                       if((p.getHauteur()+pp.getHauteur())<hauteurMax){
                                            pp.getMESPRODUITS().add(p);
                                            tableauVerif[k]=k;
                                            pp.UpdateTaille();
                                        }
                                        else{
                                            flagB=1;
                                            break;
                                        }
                                }
                                else{
                                    flagB=1;
                                    break;
                                }
                            }
                        }
                    }
                }
                compteur=1;
            }
       }
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
