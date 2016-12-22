package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import gdx.menu.GdxMenu;
import gdx.menu.TbMenu;
import gdx.menu.TbsMenu;

public class ScrPlay implements Screen, InputProcessor {

    GdxMenu gdxMenu;
    TbsMenu tbsMenu;
    TbMenu tbMenu, tbGameover;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName;
    Texture playbutton, wooden;
    Sprite play, wood;
    double dSpeed = 0, dGravity = 0.15;
    boolean bJump;
    int nJumps;
    float dXstart, dYstart;
    private static final int COLS = 7;
    private static final int ROWS = 11;
    Texture Sprite;
    Texture BackGround, Ground;
    TextureRegion[] frames;
    TextureRegion CurrentFrame;
    float SpriteX = 0, SpriteY = 0;
    float SpriteSpeed = 105f;
    float Time = 0f;
    Animation animation;
    public int woodRot;

    public ScrPlay(GdxMenu _gdxMenu) {
        gdxMenu = _gdxMenu;
    }

    public void show() {
        dSpeed = 0;
        SpriteX = 0;
        SpriteY = 0;
        playbutton = new Texture("dk menu.jpg");
        play = new Sprite(playbutton);
        wooden = new Texture("platform.png");
        wood = new Sprite(wooden);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        screenName = new BitmapFont();
        tbMenu = new TbMenu("BACK TO MENU", tbsMenu);
        tbGameover = new TbMenu("GAMEOVER", tbsMenu);
        tbMenu.setY(Gdx.graphics.getHeight() - 100);
        tbMenu.setX(0);
        tbGameover.setY(Gdx.graphics.getHeight() - 100);
        tbGameover.setX(450);
        stage.addActor(tbMenu);
        stage.addActor(tbGameover);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
        btnGameoverListener();
        batch = new SpriteBatch();
        BackGround = new Texture(Gdx.files.internal("back.jpg"));
        Ground = new Texture(Gdx.files.internal("ground.png"));
        Sprite = new Texture(Gdx.files.internal("dk.png"));
        TextureRegion[][] tmp = TextureRegion.split(Sprite, Sprite.getWidth() / COLS, Sprite.getHeight() / ROWS);
        frames = new TextureRegion[COLS * ROWS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(1f, frames);
    }

    public void render(float delta) {
        dXstart = SpriteX;
        dYstart = SpriteY;
        if (SpriteY <= Gdx.graphics.getHeight()) {
            dSpeed += dGravity;
        }
        SpriteY -= dSpeed;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Time < 4) {
            Time += Gdx.graphics.getDeltaTime();
        } else {
            Time = 0;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        CurrentFrame = animation.getKeyFrame(8);

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && SpriteX > 0) {
            SpriteX -= Gdx.graphics.getDeltaTime() * SpriteSpeed;
            CurrentFrame = animation.getKeyFrame(4 + Time);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && SpriteX < Gdx.graphics.getWidth()) {
            SpriteX += Gdx.graphics.getDeltaTime() * SpriteSpeed;
            CurrentFrame = animation.getKeyFrame(8 + Time);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && SpriteY < Gdx.graphics.getHeight()) {
            bJump = true;
            CurrentFrame = animation.getKeyFrame(20 + Time);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) && SpriteY > 0) {
            SpriteY -= Gdx.graphics.getDeltaTime() * SpriteSpeed;
            CurrentFrame = animation.getKeyFrame(8 + Time);
        }
        if (nJumps < 1) {
            if (bJump == true) {
                nJumps++;
                dSpeed = -7 + Gdx.graphics.getDeltaTime() * SpriteSpeed;
                bJump = false;
            }
        }
        if (SpriteY <= 10) {
            SpriteY = 10;
            nJumps = 0;
        }
        if (SpriteX >= Gdx.graphics.getWidth() - 70) {
            SpriteX = Gdx.graphics.getWidth() - 70;
           
        }
        //woodRot++;
        //if(woodRot==360)woodRot=0;
        wood.setRotation(/*woodRot*/90);
        batch.begin();
        batch.draw(BackGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(Ground, 0, 0, Gdx.graphics.getWidth(), 10);
        batch.draw(wood, 0, 90, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(wood, 100, 240, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(wood, 0, 390, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(wood, 100, 540, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(wood, 0, 690, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(CurrentFrame, (int) SpriteX, (int) SpriteY, 70, 70);
        batch.end();
        stage.act();
        stage.draw();
    }

    public void btnGameoverListener() {
        tbGameover.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gdxMenu.currentState = gdxMenu.gameState.OVER;
                gdxMenu.updateState();
            }
        });
    }

    public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gdxMenu.currentState = gdxMenu.gameState.MENU;
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