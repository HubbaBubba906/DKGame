package gdx.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TbsMenu extends TextButton.TextButtonStyle {
    Skin skin = new Skin();
    TextureAtlas buttonAtlas;

    public TbsMenu() {
        BitmapFont font = new BitmapFont();
        skin.add("Century", font);
        buttonAtlas = new TextureAtlas(Gdx.files.internal("MenuButton.pack"));
        skin.addRegions(buttonAtlas);
        this.up = skin.getDrawable("MenuButtonUp");
        this.down = skin.getDrawable("MenuButtonDown");
        this.checked = skin.getDrawable("MenuButtonUp");
        this.font = skin.getFont("Century");
    }
}