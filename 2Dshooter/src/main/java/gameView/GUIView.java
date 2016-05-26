




package gameView;

import com.jme3.niftygui.NiftyJmeDisplay;
import core.Player;
import ctrl.PlayerController;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Created by Lukas on 2016-05-07.
 */
public class GUIView implements ScreenController {

    private Player player1;
    private Player player2;
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private GameView gameView;
    private Element niftyElement;

    public GUIView(){
    }

    public void setPlayer1(Player p1){
        this.player1 = p1;
    }

    public void setPlayer2(Player p2){
        this.player2 = p2;
    }
    public void setNiftyDisplay(NiftyJmeDisplay nd){
        this.niftyDisplay = nd;
        this.nifty = nd.getNifty();
    }
    public void setGameView(GameView gv){
        this.gameView = gv;
    }

    //updates the status bar at top of java
    public void updateText(){

        // find old text
        niftyElement = nifty.getCurrentScreen().findElementById("text");

        // swap old with new text
        String p1hp = Float.toString(player1.getHealth());
        String p2hp = Float.toString(player2.getHealth());

        if(niftyElement!=null) {
            niftyElement.getRenderer(TextRenderer.class).setText(
                    "P1 HP: " + p1hp + "    " + "P2 HP: " + p2hp);
        }
    }

    //called when startgame button is clicked
    public void startGame(){
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        this.gameView.setPaused(false);
    }

    // called when menu button is clicked
    public void gameMenu(){
        nifty.removeScreen("start");
        nifty.fromXml("Interface/screen.xml", "pause", this);
        nifty.gotoScreen("pause");
        if(!gameView.getPaused()) {
            this.gameView.setPaused(true);
        }
    }

    //called when back button is clicked when in the menu
    public void closeMenu(){
        nifty.fromXml("Interface/screen.xml", "start", this);
        nifty.gotoScreen("start");
        if(!gameView.getPaused()) {
            this.gameView.setPaused(true);
        }
    }

    //turns the ai variable on/off when called, !!!not working!!!
    public void toggleAI(){
        Element niftyElement = nifty.getCurrentScreen().findElementById("aitext");
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

