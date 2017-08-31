package br.cefetmg.games.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import br.cefetmg.games.*;
/**
 * Utilitário para verificação de colisão.
 *
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class Collision {

    /**
     * Verifica se dois círculos em 2D estão colidindo.
     * @param c1 círculo 1
     * @param c2 círculo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean circlesOverlap(Circle c1, Circle c2) {
        Vector2 centro1 = new Vector2(c1.x,c1.y);
        Vector2 centro2 = new Vector2(c2.x,c2.y);
        if(centro1.dst2(centro2) <= (c1.radius+c2.radius)*(c1.radius+c2.radius)){
            return true;
        }
        return false;
    }

    /**
     * Verifica se dois retângulos em 2D estão colidindo.
     * Esta função pode verificar se o eixo X dos dois objetos está colidindo
     * e então se o mesmo ocorre com o eixo Y.
     * @param r1 retângulo 1
     * @param r2 retângulo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean rectsOverlap(Rectangle r1, Rectangle r2) {
        float xmax=r1.x+r1.width;
        float xmin=r1.x;
        float ymax=r1.y+r1.height;
        float ymin=r1.y;
        float zmax=r2.x+r2.width;
        float zmin=r2.x;
        float wmax=r2.y+r2.height;
        float wmin=r2.y;
        
        if(xmax >= zmin && xmin <= zmax){
            if(ymax >= wmin && ymin <= wmax){
                return true;
            }
        }
        
        return false;
    }
}
