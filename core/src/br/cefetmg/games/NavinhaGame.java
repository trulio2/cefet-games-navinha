package br.cefetmg.games;

import br.cefetmg.games.collision.Collidable;
import br.cefetmg.games.weapons.Shot;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class NavinhaGame extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Rectangle area;

    private Background background;
    private Ship ship;
    private Array<Shot> shots;
    private Array<Asteroid> asteroids;
    private Array<Entity> entities;
    private Array<Collidable> collidables;
    private static final int MAX_ASTEROIDS = 10;
    

    @Override
    public void create() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Keys.LEFT:
                        ship.startMovingLeft();
                        return true;
                    case Keys.RIGHT:
                        ship.startMovingRight();
                        return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Keys.LEFT:
                        ship.stopMovingLeft();
                        return true;
                    case Keys.RIGHT:
                        ship.stopMovingRight();
                        return true;
                }
                return false;
            }
        });

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        area = new Rectangle(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        );

        background = new Background();
        ship = new Ship(area);
        shots = new Array<Shot>();
        asteroids = new Array<Asteroid>();
        for (int i = 0; i < MAX_ASTEROIDS; i++) {
            asteroids.add(new Asteroid(area));
        }
        collidables = new Array<Collidable>();
        collidables.addAll(shots);
        collidables.addAll(asteroids);
        collidables.add(ship);
        
        entities = new Array<Entity>();
        entities.addAll(shots);
        entities.addAll(asteroids);
        entities.addAll(ship);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.EQUALS)) {
            background.increaseTravelSpeed(10f);
        } else if (Gdx.input.isKeyPressed(Keys.MINUS)) {
            background.increaseTravelSpeed(-10f);
        } else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            Array<Shot> newShots = ship.shoot();
            shots.addAll(newShots);
        } else if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
            ship.switchWeapon();
        } else if (Gdx.input.isKeyJustPressed(Keys.D)) {
            Config.debug = !Config.debug;
        } else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    
    private void update(float dt) {
        camera.update();

        handleInput();
        background.update(dt);
        ship.update(dt);

        for (int i = 0; i < collidables.size; i++) {
            Collidable collidable1 = collidables.get(i);
            ((Entity)collidable1).update(dt);
            for (int j = i + 1; j < collidables.size; j++) {
                Collidable collidable2 = collidables.get(j);
                
                
                if (collidable1.collidesWith(collidable2)) {
                    boolean shouldRemove1 = collidable1.collided(collidable2);
                    boolean shouldRemove2 = collidable2.collided(collidable1);
                    
                    if (shouldRemove1) {
                        collidables.removeValue(collidable1, false);
                        entities.removeValue((Entity)collidable1, false);
                    }
                    if (shouldRemove2) {
                        collidables.removeValue(collidable2, false);
                        entities.removeValue((Entity)collidable2, false);
                    }
                }
            }
        }
    }
    
    
    private void update2(float dt) {
        camera.update();

        handleInput();
        background.update(dt);
        ship.update(dt);
        // para todos os tiros da lista de tiros "vivos"
        for (Shot shot : shots) {
            shot.update(dt);

            // se tiver saído da tela, remove-o da lista
            if (shot.isOutOfBounds(area)) {
                shots.removeValue(shot, false);
            }

            // verifica se colidiu com asteroides
            for (Asteroid asteroid : asteroids) {
                if (shot.collidesWith(asteroid)) {
                    // "recicla" o asteróide
                    asteroid.recycle(area.height);
                    // remove o tiro da lista
                    shots.removeValue(shot, false);
                }
            }
        }

        // para todos os asteróides
        for (Asteroid asteroid : asteroids) {
            asteroid.update(dt);

            // se tiver saído da tela, recicla-o
            if (asteroid.isOutOfBounds(area)) {
                asteroid.recycle(area.height);
            }

            // verifica se colidiu com nave do jogador e o recicla
            if (asteroid.collidesWith(ship)) {
                asteroid.recycle(area.height);
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(Gdx.graphics.getDeltaTime());

        shapeRenderer.setProjectionMatrix(camera.combined);
        background.render(shapeRenderer);
//        ship.render(shapeRenderer);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity : entities) {
            entity.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    //@Override
    public void render2() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(Gdx.graphics.getDeltaTime());

        shapeRenderer.setProjectionMatrix(camera.combined);
        background.render(shapeRenderer);
        ship.render(shapeRenderer);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < shots.size; i++) {
            shots.get(i).render(shapeRenderer);
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
