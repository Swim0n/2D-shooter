




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
    //updates the status bar at top of java
    public void updateText(){
        if(player1==null||player2==null){
            return;
        }
        niftyElement = nifty.getCurrentScreen().findElementById("HPText");

        niftyElement.getRenderer(TextRenderer.class).setText(
                    "P1 HP: " + Float.toString(player1.getHealth()) + "    " + "P2 HP: " + Float.toString(player2.getHealth())
        );

        niftyElement = nifty.getCurrentScreen().findElementById("WinsText");
        niftyElement.getRenderer(TextRenderer.class).setText(
                "P1 Wins: "+ Integer.toString(player1.getWins())+ "    "+"P2 Wins: " + Integer.toString(player2.getWins())
        );

    }

    //called when startgame button is clicked
    public void startGame(){
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        this.gameView.setPaused(false);
        this.gameView.setInitialized();
    }

    // called when menu button is clicked
    public void gameMenu(){
        nifty.removeScreen("start");
        nifty.fromXml("Interface/screen.xml", "pause", this);
        nifty.gotoScreen("pause");
        if(!gameView.isPaused()) {
            this.gameView.setPaused(true);
        }
    }

    //called when back button is clicked when in the menu
    public void closeMenu(){
        nifty.removeScreen("pause");
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        if(gameView.isPaused()) {
            this.gameView.setPaused(false);
        }
    }

    public void toggleAI(){
        Element niftyElement = nifty.getCurrentScreen().findElementById("AiSettingText");
        gameView.setAI(!gameView.getAI());

        if(gameView.getAI()){
            niftyElement.getRenderer(TextRenderer.class).setText("1 Player with AI");
        }else{
            niftyElement.getRenderer(TextRenderer.class).setText("2 Player Mode");
        }
    }

    public void toggleCam(){
        Element niftyElement = nifty.getCurrentScreen().findElementById("CamSettingText");
        gameView.getWorld().getCameraData().setDynamicCameraEnabled(!gameView.getWorld().getCameraData().getDynamicCameraEnabled());

        if(gameView.getWorld().getCameraData().getDynamicCameraEnabled()){
            niftyElement.getRenderer(TextRenderer.class).setText(
                    "Moving Camera Mode");
        }else{niftyElement.getRenderer(TextRenderer.class).setText(
                "Static Camera Mode");
        }
    }
    public void quit(){
      gameView.requestClose(true);
    }

    public void onStartScreen() {
    }
    public void onEndScreen() {
    }
    public void bind(Nifty nifty, Screen screen) {
    }
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
        this.player1 = gameView.getWorld().getPlayer1();
        this.player2 = gameView.getWorld().getPlayer2();
        this.niftyDisplay = gameView.getNiftyDisplay();
        this.nifty = niftyDisplay.getNifty();
    }
}

