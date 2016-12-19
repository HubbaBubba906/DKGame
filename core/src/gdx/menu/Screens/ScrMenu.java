package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import gdx.menu.GdxMenu;
import gdx.menu.TbMenu;
import gdx.menu.TbsMenu;

public class ScrMenu implements Screen, InputProcessor {

    GdxMenu gdxMenu;
    TbsMenu tbsMenu;
    TbMenu tbPlay, tbTutorial;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName;
    Texture playbutton, dklogo;
    Sprite play, logo;

    public ScrMenu(GdxMenu _gdxMenu) {  //Referencing the main class.
        gdxMenu = _gdxMenu;
    }

    public void show() {
        dklogo = new Texture("DonkeyKonglogo.png");
        logo = new Sprite(dklogo);
        playbutton = new Texture("jungle.jpg");
        play = new Sprite(playbutton);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        screenName = new BitmapFont();
        tbPlay = new TbMenu("PLAY", tbsMenu);
        tbPlay.setY(50);
        tbPlay.setX(220);
        tbTutorial = new TbMenu("HOW TO PLAY", tbsMenu);
        tbTutorial.setY(Gdx.graphics.getHeight()- 100);
        tbTutorial.setX(0);
        stage.addActor(tbPlay);
        stage.addActor(tbTutorial);
        Gdx.input.setInputProcessor(stage);
        btnPlayListener();
        btnTutorialListener();
    }

    public void render(float delta) {
        batch.begin();
        batch.draw(play, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(logo, Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getWidth()- 200, 500, 150);
        batch.end();
        stage.act();
        stage.draw();
    }

    public void btnPlayListener() {
        tbPlay.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gdxMenu.currentState = gdxMenu.gameState.PLAY;
                gdxMenu.updateState();
            }
        });
    }

    public void btnTutorialListener() {
        tbTutorial.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gdxMenu.currentState = gdxMenu.gameState.TUTORIAL;
                gdxMenu.updateState();
            }
        });
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}