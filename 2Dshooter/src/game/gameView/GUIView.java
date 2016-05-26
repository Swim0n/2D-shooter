




package game.gameView;

import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import game.ctrl.PlayerController;

/**
 * Created by Lukas on 2016-05-07.
 */
public class GUIView implements ScreenController {

    private static PlayerController p1ctr;
    private static PlayerController p2ctr;
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private GameView gameView;


    public GUIView(){
    }

    public void setP1ctr(PlayerController p1){
        this.p1ctr = p1;
    }

    public void setP2ctr(PlayerController p2){
        this.p2ctr = p2;
    }
    public void setNiftyDisp(NiftyJmeDisplay nd){
        this.niftyDisplay = nd;
        this.nifty = nd.getNifty();
    }
    public void setGameView(GameView gv){
        this.gameView = gv;
    }

    //updates the status bar at top of game
    public void updateText(){
        // find old text
        Element niftyElement = nifty.getCurrentScreen().findElementByName("text");
        // swap old with new text
        String p1hp = Float.toString(this.p1ctr.getPlayerData().getHealth());
        String p2hp = Float.toString(this.p2ctr.getPlayerData().getHealth());

        niftyElement.getRenderer(TextRenderer.class).setText(
                "P1 HP: " + p1hp  + "    " + "P2 HP: " + p2hp);
    }

    //called when startgame button is clicked
    public void startGame(){
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        this.gameView.unpauseGame();
    }

    // called when menu button is clicked
    public void gameMenu(){
        nifty.removeScreen("start");
        nifty.fromXml("Interface/screen.xml", "pause", this);
        nifty.gotoScreen("pause");
        if(!gameView.getPaused()){
            this.gameView.pauseGame();}
    }

    //called when back button is clicked when in the menu
    public void closeMenu(){
        nifty.fromXml("Interface/screen.xml", "start", this);
        nifty.gotoScreen("start");
        if(!gameView.getPaused()){
            this.gameView.pauseGame();}
    }

    //turns the ai variable on/off when called, !!!not working!!!
    public void toggleAI(){
        Element niftyElement = nifty.getCurrentScreen().findElementByName("aitext");
        gameView.setAI(!gameView.getAI());
        if(gameView.getAI()){
            niftyElement.getRenderer(TextRenderer.class).setText(
                    "AI: ON");
        }else{niftyElement.getRenderer(TextRenderer.class).setText(
                "AI: OFF");
        }

    }



    public void onStartScreen() {

    }

    public void onEndScreen() {

    }
    public void bind(Nifty nifty, Screen screen) {

    }
}

