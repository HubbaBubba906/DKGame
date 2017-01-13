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
import gdx.menu.Button;
import gdx.menu.TbsMenu;
import java.util.ArrayList;

public class ScrPlay implements Screen, InputProcessor {

    ArrayList<Platform> arPlatforms = new ArrayList<Platform>();
    GdxMenu gdxMenu;
    TbsMenu tbsMenu;
    Button tbMenu, tbGameover, tbWin;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName;
    Texture TexWooden, TexBanana;
    Sprite SprWood, SprBanana;
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
        tbWin = new Button("WIN", tbsMenu);
        tbGameover = new Button("GAMEOVER", tbsMenu);
        tbMenu.setY(Gdx.graphics.getHeight() - 100);
        tbMenu.setX(0);
        tbGameover.setY(Gdx.graphics.getHeight() - 100);
        tbGameover.setX(450);
        tbWin.setY(Gdx.graphics.getHeight() - 100);
        tbWin.setX(200);
        stage.addActor(tbMenu);
        stage.addActor(tbGameover);
        stage.addActor(tbWin);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
        btnGameoverListener();
        btnWinListener();
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
        p = new Platform(0, 100, Gdx.graphics.getWidth() - 100, 30, batch);
        arPlatforms.add(p);

    }
//if (dkx + dk width > platform x && dk x < platform x+ plaform width &&
 // + dk y + dk height > platform y && dk y < platform y+ plaform height) {  
    public void render(float delta) {
        for (int i = 0; i < arPlatforms.size(); i++) {
            arPlatforms.get(i).display();
        }
        nJumps = 0;
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
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && DKY < Gdx.graphics.getHeight()) {
            bJump = true;
            CurrentFrame = animation.getKeyFrame(12);
        }
       
// if (DKX + DKSize > platform x && DKX < platformx+ plaformwidth &&
//      +   DKY + DKSize > platform y && DKY < platform y+ plaform height) {  
//    }
        if (nJumps < 1) {
            if (bJump == true) {
                CurrentFrame = animation.getKeyFrame(13);
                dSpeed = -7 + Gdx.graphics.getDeltaTime() * SpriteSpeed;
                nJumps = 12;
                bJump = false;
            }
        }
        if (DKY <= 5) {  //floor hit test
            DKY = 1;
            nJumps = 0;
        }
        if (DKX >= Gdx.graphics.getWidth() - 70) {
            DKX = Gdx.graphics.getWidth() - 70;
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
        batch.draw(SprWood, 0, 100, Gdx.graphics.getWidth() - 100, 30);
        batch.draw(SprWood, 100, 260, Gdx.graphics.getWidth() - 100, 30);
        batch.draw(SprWood, 0, 420, Gdx.graphics.getWidth() - 100, 30);
        batch.draw(SprWood, 100, 580, Gdx.graphics.getWidth() - 100, 30);
        batch.draw(CurrentFrame, (int) DKX, (int) DKY, DKSize, DKSize);
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

    public void btnWinListener() {
        tbWin.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gdxMenu.currentState = gdxMenu.gameState.WIN;
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