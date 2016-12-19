package gdx.menu;

import com.badlogic.gdx.Game;
import gdx.menu.Screens.ScrMenu;
import gdx.menu.Screens.ScrPlay;
import gdx.menu.Screens.ScrGameover;
import gdx.menu.Screens.ScrTutorial;

public class GdxMenu extends Game {

    ScrMenu scrMenu;
    ScrPlay scrPlay;
    ScrGameover scrGameover;
    ScrTutorial scrTutorial;

    // the below enumeration clearly defines the list of permissible screens.
    public enum GameState {
        MENU, PLAY, OVER , TUTORIAL
    }
    public GameState gameState;
    public GameState currentState;
    // based on the current GameState, the screen is set.

    public void updateState() {
        if (currentState == gameState.MENU) {
            setScreen(scrMenu);
        } else if (currentState == gameState.PLAY) {
            setScreen(scrPlay);
        } else if (currentState == gameState.OVER) {
            setScreen(scrGameover);
        } else if (currentState == gameState.TUTORIAL) {
            setScreen(scrTutorial);
        }
    }

    @Override
    public void create() {
        // notice that "this" is passed to each screen. Each screen now has access to methods within the "game" master program
        scrMenu = new ScrMenu(this);
        scrPlay = new ScrPlay(this);
        scrGameover = new ScrGameover(this);
        scrTutorial = new ScrTutorial(this);
        currentState = GameState.MENU;
        updateState();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}