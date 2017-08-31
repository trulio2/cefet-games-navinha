package br.cefetmg.games.weapons;

import br.cefetmg.games.Asteroid;
import br.cefetmg.games.collision.Collidable;
import br.cefetmg.games.Entity;

/**
 * Um tiro.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public abstract class Shot implements Entity, Collidable {

    @Override
    public boolean collided(Collidable other) {
        // toda colisão de um tiro o destrói
        return true;
    }
    
}
