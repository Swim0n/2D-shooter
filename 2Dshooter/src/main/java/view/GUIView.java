




package view;

import com.jme3.niftygui.NiftyJmeDisplay;
import core.Player;
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
    private WorldView worldView;
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
        this.worldView.setPaused(false);
        this.worldView.setInitialized();
    }

    // called when menu button is clicked
    public void gameMenu(){
        nifty.removeScreen("start");
        nifty.fromXml("Interface/screen.xml", "pause", this);
        nifty.gotoScreen("pause");
        if(!worldView.getPaused()) {
            this.worldView.setPaused(true);
        }
    }

    //called when back button is clicked when in the menu
    public void closeMenu(){
        nifty.removeScreen("pause");
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        if(worldView.isPaused()) {
            this.worldView.setPaused(false);
        }
    }

    public void toggleAI(){
        Element niftyElement = nifty.getCurrentScreen().findElementById("AiSettingText");
        worldView.setAI(!worldView.getAI());

        if(worldView.getAI()){
            niftyElement.getRenderer(TextRenderer.class).setText("1 Player with AI");
        }else{
            niftyElement.getRenderer(TextRenderer.class).setText("2 Player Mode");
        }
    }

    public void toggleCam(){
        Element niftyElement = nifty.getCurrentScreen().findElementById("CamSettingText");
        worldView.getWorld().getCameraData().setDynamicCameraEnabled(!worldView.getWorld().getCameraData().getDynamicCameraEnabled());

        if(worldView.getWorld().getCameraData().getDynamicCameraEnabled()){
            niftyElement.getRenderer(TextRenderer.class).setText(
                    "Moving Camera Mode");
        }else{niftyElement.getRenderer(TextRenderer.class).setText(
                "Static Camera Mode");
        }
    }
    public void quit(){
      worldView.requestClose(true);
    }

    public void onStartScreen() {
    }
    public void onEndScreen() {
    }
    public void bind(Nifty nifty, Screen screen) {
    }
    public void setWorldView(WorldView worldView) {
        this.worldView = worldView;
        this.player1 = worldView.getWorld().getPlayer1();
        this.player2 = worldView.getWorld().getPlayer2();
        this.niftyDisplay = worldView.getNiftyDisplay();
        this.nifty = niftyDisplay.getNifty();
    }
}

