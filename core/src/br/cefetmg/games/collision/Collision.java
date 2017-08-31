package br.cefetmg.games.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

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
        return false;
    }

    /**
     * Verifica se dois intervalos em 1D estão colidindo.
     * @param min1 valor mais à esquerda do intervalo 1
     * @param max1 valor mais à direita do intervalo 1
     * @param min2 valor mais à esquerda do intervalo 2
     * @param max2 valor mais à direita do intervalo 2
     * @return true se há colisão ou false, do contrário.
     */
    private static boolean rangesIntersect(
            float min1, float max1,
            float min2, float max2) {
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
        return false;
    }
    
    
    /**
     * Verifica se um retângulo está colidindo com um círculo em 2D.
     * @param r retângulo
     * @param c círculo
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean rectCircleOverlap(Rectangle r, Circle c) {
        return false;
    }

}
