package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
    
    /**
     * ID généré par la BDD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // ATTRIBUTS 
    
    /**
     * Instance de la solution
     */
    @OneToOne
    @JoinColumn(name="INSTANCE")
    private Instance monInstance;
    
    /**
     * Cout de la solution
     */
    @Column(
        name="COUT"
    )
    private double cout;
    
    /**
     * Liste des solutions box de la solution
     */
    @OneToMany(mappedBy="MASOLUTION",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    Collection<SolutionBox> mesSolutionBox;

    // CONSTRUCTEURS 
    
    /**
     * Constructeur par défaut
     */
    public Solution() {
        this.mesSolutionBox = new ArrayList<>();
        this.cout=0;
        this.monInstance = null;
    }
    
    /**
     * Constructeur par données
     * @param i instance de la solution
     */
    public Solution(Instance i){
        this();                           
        this.monInstance = i;                
        i.setMaSolution(this);
    }        
    
    // GETTERS & SETTERS 
    /**
     * Permet de récupérer l'id de la solution
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Permet de mettre un ID à la solution
     * @param id id de la solution
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Permet de récupérer l'instance de la solution
     * @return instance
     */
    public Instance getMonInstance() {
        return monInstance;
    }

    /**
     * Permet de mettre une instance à une solution
     * @param monInstance instance à attribuer
     */
    public void setMonInstance(Instance monInstance) {
        this.monInstance = monInstance;
    }

    /**
     * Permet de récupérer le cout de la solution
     * @return cout de la solution
     */
    public double getCout() {
        cout = this.calculerCout();
        return cout;
    }

    /**
     * Permet de récupérer une collection des solutions box de la solution
     * @return liste de solutions box
     */
    public Collection<SolutionBox> getMesSolutionBox() {
        return mesSolutionBox;
    }

    /**
     * Mettre une collection de solution box à une solution
     * @param mesSolutionBox liste solution box à ajouter
     */
    public void setMesSolutionBox(Collection<SolutionBox> mesSolutionBox) {
        this.mesSolutionBox = mesSolutionBox;
        this.calculerCout();
    }
    
    // METHODES 
    
    /**
     * Algorithme de test basique, il prend toujours la même box et essaie d'empliler du plus large au moins large
     * Lorsqu'un objet ne passe plus en hauteur, on teste le suivant jusqu'à avoir parcouru tous les objets
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
        
        // on trie la liste des produits dans l'ordre décroissant
        Collections.sort(ProduitDispo, new Comparator<Produit>() {
            @Override
            public int compare(Produit lhs, Produit rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getLargeur() > rhs.getLargeur() ? -1 : (lhs.getLargeur() < rhs.getLargeur()) ? 1 : 0;
            }
        });
        
        // on trie la liste des box dans l'ordre décroissant

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
        this.calculerCout();
    }
    
    /**
     * Calcule le coût actuel de la solution
     * @return le cout de la solution
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
     * @return vrai s'il reste des produits
     */
    public boolean encoreDesProduits(int T[]){
        for (int j=0;j<T.length;j++){
           if (T[j] == -1)
               return true; 
        }
        return false;
    }
    
    /**
     * Cette fonction permet de récupérer l'index du produit le plus large
     * @param T tableau qui permet de savoir si un produit est disponible
     * @return index du produit le plus large, -1 si plus de produit
     */
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
