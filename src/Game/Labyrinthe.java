package Game;

import Cases.Case;
import Cases.Obstacle;
import Cases.Piege;
import Cases.Vide;
import Entites.Aventurier;
import Entites.Monstre;
import Entites.Personnage;
import MoteurJeu.Commande;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe labyrinthe
 */
public class Labyrinthe {
    /**
     * Attributs de type case
     */
    private Case[][] cases;
    private Case caseDepart;
    private Case caseArrivee;

    /**
     * Attributs de type personnages
     */
    private Aventurier aventurier;
    private Case caseAventurier;
    private ArrayList<Monstre> monstres;

    /**
     * Constructeur de labyrinthe a partir d'un fichier texte
     * @param nom nom de l'aventurier
     */
    public Labyrinthe(String nom) {

        //Instanciation des attributs
        this.aventurier = new Aventurier(nom);
        this.monstres = new ArrayList<Monstre>();
        this.cases = new Case[21][21];

        //Lecture du fichier texte pour créer le labyrinthe
        try {
            //Mise en place d'un reader
            File f = new File("src/Labyrinthes/labyrinthe.txt");
            BufferedReader in = new BufferedReader(new FileReader(f));
            String a;
            int i = 0;
            //Lecture de chaque lignes du fichier
            while (!((a = in.readLine()) == null)) {
                for (int j = 0; j < a.length(); j++) {
                    char tmp = a.charAt(j);
                    //Identifier le types de cases
                    switch (tmp) {
                        case ('0'):
                            cases[i][j] = new Vide(i, j, false);
                            break;
                        case ('1'):
                            cases[i][j] = new Obstacle(i, j);
                            break;
                        case ('2'):
                            Vide debut = new Vide(i, j, false);
                            cases[i][j] = debut;
                            this.caseDepart = debut;
                            this.caseAventurier = debut;
                            this.caseAventurier.setPersonnage(this.aventurier);
                            break;
                        case ('3'):
                            Vide fin = new Vide(i, j, false);
                            cases[i][j] = fin;
                            this.caseArrivee = fin;
                            break;
                        case ('4'):
                            cases[i][j] = new Piege(i, j);
                            break;
                        case ('9'):
                            cases[i][j] = new Vide(i, j, true);
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur de labyrinthe
     * @param nom nom de l'aventurier qui va y évoluer
     * @param nom_txt nom du txt contenant le labyrinthe a générer
     */
    public Labyrinthe(String nom, String nom_txt) {

        //Instanciation des attributs
        this.aventurier = new Aventurier(nom);
        this.monstres = new ArrayList<Monstre>();
        this.cases = new Case[21][21];

        //Lecture du fichier texte pour créer le labyrinthe
        try {
            //Mise en place d'un reader
            File f = new File("src/Labyrinthes/"+nom_txt+".txt");
            BufferedReader in = new BufferedReader(new FileReader(f));
            String a;
            int i = 0;
            //Lecture de chaque lignes du fichier
            while (!((a = in.readLine()) == null)) {
                for (int j = 0; j < a.length(); j++) {
                    char tmp = a.charAt(j);
                    //Identifier le types de cases
                    switch (tmp) {
                        case ('0'):
                            cases[i][j] = new Vide(i, j, false);
                            break;
                        case ('1'):
                            cases[i][j] = new Obstacle(i, j);
                            break;
                        case ('2'):
                            Vide debut = new Vide(i, j, false);
                            cases[i][j] = debut;
                            this.caseDepart = debut;
                            this.caseAventurier = debut;
                            this.caseAventurier.setPersonnage(this.aventurier);
                            break;
                        case ('3'):
                            Vide fin = new Vide(i, j, false);
                            cases[i][j] = fin;
                            this.caseArrivee = fin;
                            break;
                        case ('4'):
                            cases[i][j] = new Piege(i, j);
                            break;
                        case ('9'):
                            cases[i][j] = new Vide(i, j, true);
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour afficher le jeu DANS LA CONSOLE
     */
    public void afficher() {
        for (Case[] aCase : this.cases) {
            for (int j = 0; j < this.cases.length; j++) {
                if (aCase[j] instanceof Vide) {
                    if (aCase[j] == this.caseArrivee) {
                        System.out.print("E  ");
                    } else {
                        if (aCase[j] == this.caseAventurier) {
                            System.out.print("O  ");
                        } else {
                            System.out.print("   ");
                        }
                    }
                } else {
                    System.out.print("X  ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Getteur de l'aventurier
     * @return l'aventurier
     */
    public Aventurier getAventurier() {
        return this.aventurier;
    }

    /**
     * Getter des cases
     * @return les cases
     */
    public Case[][] getCases() {
        return this.cases;
    }

    /**
     * Méthode pour deplacer l'aventurier DANS LA CONSOLE
     */
    public void deplacer() {
        Scanner sc = new Scanner(System.in);
        boolean f = false;
        while (!f) {
            String dep = sc.nextLine();
            switch (dep) {
                case ("z"):
                    f = this.essayerMovement(-1, 0, this.aventurier);
                    break;
                case ("s"):
                    f = this.essayerMovement(+1, 0, this.aventurier);
                    break;
                case ("d"):
                    f = this.essayerMovement(0, +1, this.aventurier);
                    break;
                case ("q"):
                    f = this.essayerMovement(0, -1, this.aventurier);
                    break;
                default:
                    System.out.println("Veuillez entrer une coordonnée valide :");
            }
        }
    }

    /**
     * Methode qui sert a se délacer dans la version console du jeu
     * @param c commande entrée (z q s d)
     */
    //POUR LES TESTS DE MOUVEMENTS
    public void deplacer(String c) {
        switch (c) {
            case ("z"):
                this.essayerMovement(-1, 0, this.aventurier);
                break;
            case ("s"):
                this.essayerMovement(+1, 0, this.aventurier);
                break;
            case ("d"):
                this.essayerMovement(0, +1, this.aventurier);
                break;
            case ("q"):
                this.essayerMovement(0, -1, this.aventurier);
                break;
            default:
                System.out.println("Veuillez entrer une coordonnée valide :");
        }
    }

    /**
     * Méthode interne à la classe pour eviter du copier/coller
     */
    private boolean essayerMovement(int x, int y, Personnage pers) {
        boolean f = false;
        if (!(this.getCases()[this.caseAventurier.getX() + x][this.caseAventurier.getY() + y] instanceof Obstacle) && !(this.getCases()[this.caseAventurier.getX() + x][this.caseAventurier.getY() + y].estOccupe())) {
            this.caseAventurier.retirerPersonnage();
            this.caseAventurier = this.getCases()[this.caseAventurier.getX() + x][this.caseAventurier.getY() + y];
            this.caseAventurier.setPersonnage(pers);
            if(pers instanceof Aventurier) {
                this.caseAventurier.faireDegat();
            }
            f = true;
        } else {
            System.out.println("case non accessible");
        }
        return f;
    }

    /**
     * Main afin de tester le programme pendant le developpement du projet
     * @param args
     */
    public static void main(String[] args) {
        Labyrinthe l = new Labyrinthe("Paul");
        l.afficher();
        while(true){
            l.deplacer();
            l.afficher();
        }
    }

    /**
     * Permet de savoir sinon a fini le labyrinthe
     * @return vrai si on a fini le labyrinthe
     */
    public boolean etreFini() {
        return this.caseAventurier == this.caseArrivee;
    }

    /**
     * permet de faire évoluer un aventurier
     * @param commandeUser commandes entrées par l'utilisateur
     */
    public void evoluerAventurier(Commande commandeUser) {
        if (commandeUser.gauche) {
            this.essayerMovement(0, -1, this.aventurier);
        }
        if (commandeUser.droite) {
            this.essayerMovement(0, +1, this.aventurier);
        }
        if (commandeUser.haut) {
            this.essayerMovement(-1, 0, this.aventurier);
        }
        if (commandeUser.bas) {
            this.essayerMovement(+1, 0, this.aventurier);
        }
        if (this.caseAventurier.getAmulette()) {
            this.aventurier.amuletteRecuperee();
        }
        if (this.etreFini() && this.aventurier.getAmulette()) {
            System.out.println("Vous avez gagné ! Passage au niveau suivant");
        }
    }

    /**
     * Obtenir la case ou se trouve actuellement l'aventurier
     * @return la case de l'aventurier
     */
    public Case getCaseAventurier(){
        return this.caseAventurier;
    }
}
