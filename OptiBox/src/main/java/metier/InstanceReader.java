/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import io.exception.EmptyFieldException;
import io.exception.FieldFormatException;
import io.exception.FieldNameException;
import io.exception.FileExistException;
import io.exception.FormatFileException;
import io.exception.NumberColumnsException;
import io.exception.OpenFileException;
import io.exception.ReaderException;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import modele.Box;
import modele.Instance;
import modele.Produit;

/**
 * Classe qui permet de lire une instance pour le projet de POO3 2020/2021.
 * Le format des instances est decrit dans le sujet du projet.
 * Attention, ceci est un squellette de code, il faut le completer.
 * Des commentaires contenant 'TODO' vous aident pour voir a quel endroit vous pouvez completer.
 * N'hesitez pas a apporter toutes les modifications que vous jugez utiles.
 * 
 * @author Maxime Ogier
 */
public class InstanceReader {
    /**
     * Le fichier contenant l'instance.
     */
    private File instanceFile;
    /**
     * Le numero de la ligne courante dans le fichier.
     */
    private int numLigne;
    /**
     * L'entete des colonnes pour la lecture des box
     */
    private static final String[] HEADER_BOX = new String[]{"Id box", "Longueur", "Hauteur", "Prix"};
    /**
     * L'entete des colonnes pour la lecture des produits
     */
    private static final String[] HEADER_PRODUIT = new String[]{"Id produit", "Longueur", "Hauteur", "Quantite"};

    /**
     * Constructeur par donnee du chemin du fichier d'instance.
     * @param inputPath le chemin du fichier d'instance, qui doit se terminer 
     * par l'extension du fichier (.csv).
     * @throws ReaderException lorsque le fichier n'est pas au bon format ou 
     * ne peut pas etre ouvert.
     */
    public InstanceReader(String inputPath) throws ReaderException {
        if (inputPath == null) {
            throw new OpenFileException();
        }
        if (!inputPath.endsWith(".csv")) {
            throw new FormatFileException("csv", "csv");
        }
        String instanceName = inputPath;
        this.instanceFile = new File(instanceName);
        this.numLigne = 0;
    }
    
    /**
     * Methode principale pour lire le fichier d'instance.
     * @throws ReaderException lorsque les donnees dans le fichier d'instance 
     * sont manquantes ou au mauvais format.
     */
    public Instance readInstance() throws ReaderException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(instanceFile);
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        }
        // Dans la ligne qui suit vous recuperez le nom de l'instance.
        String nom = readStringInLine(scanner, "Nom");
        ////////////////////////////////////////////
        // TODO : Vous pouvez creer une instance.
        ////////////////////////////////////////////
        
        Instance i = new Instance(nom);
        
        readStringInLine(scanner, HEADER_BOX);
        // Dans la boucle qui suit, nous allons lire les donnees relatives a chaque box.
        while(true) {
            InfosBox elem = readBoxInLine(scanner, HEADER_PRODUIT);
            if(elem == null) {
                break;
            }
            // Notez que elem est un objet qui contient quatre attributs : 
            // un identifiant ; une longueur ; une hauteur et un prix
            // Vous pouvez acceder a ces attributs de la maniere suivante :
            // elem.getIdentifiant()
            // elem.getLongueur()
            // elem.getHauteur()
            // elem.getPrix()
            
            ////////////////////////////////////////////
            // TODO : Vous pouvez ajoutez chacun des box a votre instance
            ////////////////////////////////////////////
            
            Box b = new Box(elem.getIdentifiant(),elem.getLongueur(),elem.getHauteur(),elem.getPrix());
            i.ajouterObjet(b);
        }
        
        // Dans la boucle qui suit, nous allons lire les donnees relatives a chaque produit.
        while(true) {
            InfosProduit elem = readProduitInLine(scanner);
            if(elem == null) {
                break;
            }
            // Notez que elem est un objet qui contient quatre attributs : 
            // un identifiant ; une longueur ; une hauteur et une quantite
            // Vous pouvez acceder a ces attributs de la maniere suivante :
            // elem.getIdentifiant()
            // elem.getLongueur()
            // elem.getHauteur()
            // elem.getQuantite()
            
            ////////////////////////////////////////////
            // TODO : Vous pouvez ajoutez chacun des produits a votre instance
            ////////////////////////////////////////////
            
            Produit p = new Produit(elem.getIdentifiant(),elem.getLongueur(),elem.getHauteur(),elem.getQuantite(),Color.black);
//            b.setIdInstance(i.getId());
            i.ajouterObjet(p);
        }
        return i;
    }

    /**
     * Lecture de plusieurs chaines de caracteres dans une seule ligne
     * @param scanner lecteur du fichier
     * @param names tous les noms qui doivent etre dans la ligne courante (separes par des points-virgules)
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private void readStringInLine(Scanner scanner, String[] names) throws ReaderException {
        String[] values = nextLine(scanner);
        if(values.length != names.length) {
            throw new NumberColumnsException(numLigne, names);
        }
        for(int col=0; col<values.length; col++) {
            if (!values[col].equalsIgnoreCase(names[col])) {
                throw new FieldNameException(numLigne, col+1, names[col]);
            }
        } 
    }

    /**
     * Lecture d'un nombre entier dans la seconde colonne d'une ligne 
     * @param scanner lecteur du fichier
     * @param name le nom qui doit etre indique dans la premiere colonne
     * @return le nombre entier
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private int readIntInLine(Scanner scanner, String name) throws ReaderException {
        String[] values = nextLine(scanner);
        checkLine(values, name);
        int val;
        try {
            val = Integer.parseInt(values[1]);
        } catch (NumberFormatException ex) {
            throw new FieldFormatException(numLigne, 2, "un nombre entier");
        }
        return val;
    }
    
    /**
     * Lecture des informations sur un box dans les quatre premieres colonnes :
     * l'identifiant sous forme de chaine de caractères, la longueur et la hauteur 
     * sous forme de nombres entiers, et le prix sous forme de nombre reel.
     * @param scanner lecteur du fichier
     * @param echappement tableau de chaines de caractere qui indique la fin de la lecture des box
     * @return les informations sur le box, null s'il n'y a rien ou l'echapppement
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private InfosBox readBoxInLine(Scanner scanner, String[] echappement) throws ReaderException {
        String[] values = nextLine(scanner);
        if(values.length == 0) {
            return null;
        }
        if(tableauStringIdentique(values, echappement)) {
            return null;
        }
        if (values.length < 4) {
            throw new EmptyFieldException(numLigne, 4);
        } 
        InfosBox val;
        String identifiant = values[0];
        int longueur = readInt(values[1], 2);
        int hauteur = readInt(values[2], 3);
        double prix = readDouble(values[3], 4);
        val = new InfosBox(identifiant, longueur, hauteur, prix);
        return val;
    }

    /**
     * Indique si deux tableaux de chaines de caracteres sont identiques ou non 
     * @param values le tableau a comparer
     * @param cmp le tableau de reference
     * @return true si les deux tableau sont de meme longueur et contiennent les 
     * memes chaines de caracteres dans le meme ordre
     */
    private boolean tableauStringIdentique(String[] values, String[] cmp) {
        if(values.length != cmp.length) {
            return false;
        }
        for(int i=0;i<values.length;i++) {
            if(!values[i].equalsIgnoreCase(cmp[i])) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Lecture des informations sur un produit dans les quatre premieres colonnes :
     * l'identifiant sous forme de chaine de caractères, la longueur, la hauteur et la quantite
     * sous forme de nombres entiers.
     * @param scanner lecteur du fichier
     * @return les informations sur le produit
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private InfosProduit readProduitInLine(Scanner scanner) throws ReaderException {
        String[] values = nextLine(scanner);
        if(values.length == 0) {
            return null;
        }
        if (values.length < 4) {
            throw new EmptyFieldException(numLigne, 4);
        } 
        InfosProduit val;
        String identifiant = values[0];
        int longueur = readInt(values[1], 2);
        int hauteur = readInt(values[2], 3);
        int quantite = readInt(values[3], 4);
        val = new InfosProduit(identifiant, longueur, hauteur, quantite);
        return val;
    }

    /**
     * Lecture d'un nombre entier a partir d'une chaine de caracteres
     * @param value la chaine de caracteres
     * @param numColonne le numero de la colonne dans laquelle on  lit le  nombre
     * @return le nombre entier
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private int readInt(String value, int numColonne) throws ReaderException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new FieldFormatException(numLigne, numColonne, "un nombre entier");
        }
    }
    
    /**
     * Lecture d'un nombre reel a partir d'une chaine de caracteres
     * @param value la chaine de caracteres
     * @param numColonne le numero de la colonne dans laquelle on  lit le  nombre
     * @return le nombre reel
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private double readDouble(String value, int numColonne) throws ReaderException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new FieldFormatException(numLigne, numColonne, "un nombre reel");
        }
    }

    /**
     * Lecture d'une chaine de caracteres dans la seconde colonne d'une ligne
     * @param scanner lecteur du fichier
     * @param name le nom qui doit etre indique dans la premiere colonne
     * @return la chaine de caracteres
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private String readStringInLine(Scanner scanner, String name) throws ReaderException {
        String[] values = nextLine(scanner);
        checkLine(values, name);
        return values[1];    
    }

    /**
     * Verification de la validite d'une ligne.
     * @param values les valeurs dans la ligne, separes par colonne
     * @param name le nom qui doit etre dans la premiere colonne
     * @throws FieldNameException lorsque le nom dans la premiere colonne n'est pas correct
     * @throws EmptyFieldException losque la valeur dans la seconde colonne n'est pas remplie
     */
    private void checkLine(String[] values, String name) throws FieldNameException, EmptyFieldException {
        if (!values[0].equalsIgnoreCase(name)) {
            throw new FieldNameException(numLigne, 1, name);
        }
        if (values.length == 1 || values[1].length() == 0) {
            throw new EmptyFieldException(numLigne, 2);
        }
    }

    /**
     * Lecture de la prochaine ligne du fichier
     * @param scanner lecteur du fichier
     * @return les valeurs presentes dans les differentes colonnes (separees par des points-virgules)
     */
    private String[] nextLine(Scanner scanner) {
        String[] values = null;
        do {
            if(!scanner.hasNextLine()) {
                return new String[]{};
            }
            String line = scanner.nextLine();
            values = line.split(";");
            this.numLigne++;
        } while (values.length == 0);
        return values;
    }
    
    /**
     * Classe interne qui represente les informations generales sur 
     * un box ou un produit : identifiant, longueur, hauteur.
     */
    class Infos {
        private String identifiant;
        private int longueur;
        private int hauteur;

        public Infos(String identifiant, int longueur, int hauteur) {
            this.identifiant = identifiant;
            this.longueur = longueur;
            this.hauteur = hauteur;
        }

        public String getIdentifiant() {
            return identifiant;
        }

        public int getLongueur() {
            return longueur;
        }

        public int getHauteur() {
            return hauteur;
        }
    }

    /**
     * Classe interne qui represente les informations sur un box.
     */
    class InfosBox extends Infos {
        private double prix;

        public InfosBox(String identifiant, int longueur, int hauteur, double prix) {
            super(identifiant, longueur, hauteur);
            this.prix = prix;
        }

        public double getPrix() {
            return prix;
        }        
    }
    
    /**
     * Classe interne qui represente les informations sur un produit.
     */
    class InfosProduit extends Infos {
        private int quantite;

        public InfosProduit(String identifiant, int longueur, int hauteur, int quantite) {
            super(identifiant, longueur, hauteur);
            this.quantite = quantite;
        }

        public int getQuantite() {
            return quantite;
        }        
    }

    /**
     * Un petit test pour verifier que tout fonctionne correctement.
     */
    public static void main(String[] args) {
        try {
//            String userDirectory = new File("").getAbsolutePath();
//            System.out.println("Le répertoire actuel est : "+userDirectory);
            InstanceReader reader = new InstanceReader("instances\\instance_test.csv");
            Instance i = reader.readInstance();
            System.out.println("Instance lue avec success !");
            System.out.println(i);
            
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
