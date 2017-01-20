package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
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
import gdx.menu.Button;
import gdx.menu.TbsMenu;
import java.util.ArrayList;

public class ScrPlay implements Screen, InputProcessor {

    ArrayList<Platform> arPlatforms = new ArrayList<Platform>();
    GdxMenu gdxMenu;
    TbsMenu tbsMenu;
    Button tbMenu, tbGameover;
    Stage stage;
    int MOUSEX, MOUSEY;
    SpriteBatch batch;
    BitmapFont screenName;
    Texture TexWooden, TexBanana, imgCursor;
    Sprite SprWood, SprBanana, spCursor;
    double dSpeed = 0, dGravity = 0.25;
    boolean bJump;
    int nJumps, DKSize = 60;
    float dXstart, dYstart;
    private static final int COLS = 6;
    private static final int ROWS = 3;
    Texture Sprite;
    Texture BackGround, Ground;
    TextureRegion[] frames;
    TextureRegion CurrentFrame;
    float DKX = 0, DKY = 0;
    float SpriteSpeed = 125f;
    float Time = 0f;
    Animation animation;
    public int woodRot;
    public Platform p;

    public ScrPlay(GdxMenu _gdxMenu) {
        gdxMenu = _gdxMenu;
    }

    public void show() {
        imgCursor = new Texture("DKHammer.png");
        spCursor = new Sprite(imgCursor);
        dSpeed = 0;
        DKX = 0;
        DKY = 0;
        TexWooden = new Texture("platform.png");
        SprWood = new Sprite(TexWooden);
        TexBanana = new Texture("Banana.png");
        SprBanana = new Sprite(TexBanana);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        screenName = new BitmapFont();
        tbMenu = new Button("BACK TO MENU", tbsMenu);
        tbGameover = new Button("GAMEOVER", tbsMenu);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
        btnGameoverListener();
        batch = new SpriteBatch();
        BackGround = new Texture(Gdx.files.internal("back.jpg"));
        Ground = new Texture(Gdx.files.internal("ground.png"));
        Sprite = new Texture(Gdx.files.internal("Dkspritesheet_edited-212.png"));
        TextureRegion[][] tmp = TextureRegion.split(Sprite, Sprite.getWidth() / COLS, Sprite.getHeight() / ROWS);
        frames = new TextureRegion[COLS * ROWS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(1f, frames);
        p = new Platform(0, 100, Gdx.graphics.getWidth() - 100, 40, batch);
        arPlatforms.add(p);
        p = new Platform(100, 260, Gdx.graphics.getWidth() - 100, 40, batch);
        arPlatforms.add(p);
        p = new Platform( 0, 420, Gdx.graphics.getWidth() - 100, 40, batch);
        arPlatforms.add(p);
        p = new Platform(100, 580, Gdx.graphics.getWidth() - 100, 40, batch);
        arPlatforms.add(p);
    }
//if (dkx + dk width > platform x && dk x < platform x+ plaform width &&
    // + dk y + dk height > platform y && dk y < platform y+ plaform height) {  

    public void render(float delta) {
        dXstart = DKX;
        dYstart = DKY;
        if (DKY <= Gdx.graphics.getHeight()) { //Gravity
            dSpeed += dGravity;
        }
        DKY -= dSpeed;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Time < 6) {
            Time += Gdx.graphics.getDeltaTime() + 0.15;
        } else {
            Time = 0;
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        CurrentFrame = animation.getKeyFrame(0);      // movement/animation

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && DKX > 0) {
            DKX -= Gdx.graphics.getDeltaTime() * SpriteSpeed;
            CurrentFrame = animation.getKeyFrame(6 + Time);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && DKX < Gdx.graphics.getWidth()) {
            DKX += Gdx.graphics.getDeltaTime() * SpriteSpeed;
            CurrentFrame = animation.getKeyFrame(0 + Time);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_UP) && DKY < Gdx.graphics.getHeight() /*&& nJumps == 0*/) {
            bJump = true;
            CurrentFrame = animation.getKeyFrame(12);
            nJumps++;
        }

// if (DKX + DKSize > platform x && DKX < platformx + plaformwidth &&
//      +   DKY + DKSize > platform y && DKY < platform y+ plaform height) {  
//    }

        if (DKX + DKSize > 0 && DKX < 0 + Gdx.graphics.getWidth() - 100 //Hitdect
                && +DKY + DKSize > 100 && DKY < 100 + 40) {
            if (DKX + DKSize < 90 && DKX > Gdx.graphics.getWidth() - 100) {
                DKX = Gdx.graphics.getWidth() - 100;
            }
            if (DKY <= 85) { //bottomhit test
                DKY = 100 - DKSize;
                dSpeed *= -1;
            } else if (DKY - DKSize <= 60) { //top hit test
                DKY++;
                dSpeed = 0;
                nJumps = 0;
            } else if (DKY + DKSize <= 110 && DKY >= 95) {
            }
        }

        if (bJump == true) {
            CurrentFrame = animation.getKeyFrame(13);
            dSpeed = -10 + Gdx.graphics.getDeltaTime() * SpriteSpeed;
            bJump = false;
        }

        if (DKY <= 5) {  //floor hit test
            DKY = 1;
            nJumps = 0;
        }
        if (DKX >= Gdx.graphics.getWidth() - 50) {
            DKX = Gdx.graphics.getWidth() - 50;
        }
//        if (DKY >= 50 && DKY <= 90 && DKX <= Gdx.graphics.getWidth() - 125 && DKX >= 0) {
//            //if ()
//            DKY = 50;
//            dSpeed *= -1;
//            bJump = false;
//        }
        //woodRot++;
        //if(woodRot==360)woodRot=0;
        SprWood.setRotation(/*woodRot*/90);
        batch.begin();
        batch.draw(BackGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(Ground, 0, 0, Gdx.graphics.getWidth(), 10);
        batch.draw(SprBanana, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 180, 70, 70);
        batch.draw(SprWood, 0, 100, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(SprWood, 100, 240, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(SprWood, 0, 380, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(SprWood, 100, 520, Gdx.graphics.getWidth() - 100, 40);
        batch.draw(SprWood, Gdx.graphics.getWidth() - 200, 630, 200, 40);
        batch.draw(CurrentFrame, (int) DKX, (int) DKY, DKSize, DKSize);
        batch.end();
        stage.act();
        stage.draw();
        tbMenu.setY(Gdx.graphics.getHeight() - 100);
        tbMenu.setX(0);
        tbGameover.setY(Gdx.graphics.getHeight() - 100);
        tbGameover.setX(450);
        stage.addActor(tbMenu);
        stage.addActor(tbGameover);
        batch.begin();
        batch.draw(spCursor, Gdx.input.getX() - Gdx.graphics.getHeight() / 80, Gdx.graphics.getHeight() - Gdx.input.getY() - Gdx.graphics.getHeight() / 60, Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 20);
        batch.end();
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