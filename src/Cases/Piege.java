package Cases;

import Entites.Personnage;

/**
 * classe obstacle
 */
public class Piege extends Case {

    boolean active = true;

    /**
     * constructeur obstacle
     * @param x abscisse
     * @param y ordonnee
     */
    public Piege(int x, int y) {
        super(x,y);
    }


    public void desactivePiege(){
        this.active = false;
    }

    public void faireDegat(){
        int pv = 0;
        if(this.personnage != null){
            pv = this.personnage.getPv() - 1;
        }
    }
}