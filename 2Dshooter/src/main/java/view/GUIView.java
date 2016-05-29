




package view;

import com.jme3.niftygui.NiftyJmeDisplay;
import core.Player;
import core.World;
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
    private World world;

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

        if(world.isDeathMatch()){
            niftyElement = nifty.getCurrentScreen().findElementById("WinsText");
            niftyElement.getRenderer(TextRenderer.class).setText(
                    "P1 Kills: "+ Integer.toString(player1.getWins())+ "    "+"P2 Kills: " + Integer.toString(player2.getWins())
            );
        }else {
            niftyElement = nifty.getCurrentScreen().findElementById("WinsText");
            niftyElement.getRenderer(TextRenderer.class).setText(
                    "P1 Wins: " + Integer.toString(player1.getWins()) + "    " + "P2 Wins: " + Integer.toString(player2.getWins())
            );
        }
    }

    //called when startgame button is clicked
    public void startGame(){
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        world.setPaused(false);
        worldView.setInitialized();
    }

    // called when menu button is clicked
    public void gameMenu(){
        nifty.removeScreen("start");
        nifty.fromXml("Interface/screen.xml", "pause", this);
        nifty.gotoScreen("pause");
        world.setPaused(true);

    }

    public void setControllers(){
        nifty.fromXml("Interface/screen.xml", "controllerSetup", this);
        nifty.gotoScreen("controllerSetup");

    }
    //called when back button is clicked when in the menu
    public void closeMenu(){
        nifty.removeScreen("pause");
        nifty.fromXml("Interface/screen.xml", "gamegui", this);
        nifty.gotoScreen("gamegui");
        world.setPaused(false);
    }

    public void toggleDeathMatch(){
        niftyElement = nifty.getCurrentScreen().findElementById("DMSettingText");
        world.setDeathMatch(!world.isDeathMatch());

        if(world.isInGame()){
            world.setGameOver();
        }

        if(world.isDeathMatch()){
            niftyElement.getRenderer(TextRenderer.class).setText("Death Match Mode");
        }else{
            niftyElement.getRenderer(TextRenderer.class).setText("Match Point Mode");
        }
    }
    public void toggleAI(){
        niftyElement = nifty.getCurrentScreen().findElementById("AiSettingText");
        world.setAI(!world.isAI());

        if(world.isAI()){
            niftyElement.getRenderer(TextRenderer.class).setText("1 Player with AI");
        }else{
            niftyElement.getRenderer(TextRenderer.class).setText("2 Player Mode");
        }
    }

    public void toggleCam(){
        niftyElement = nifty.getCurrentScreen().findElementById("CamSettingText");
        world.getCameraData().setDynamicCameraEnabled(!world.getCameraData().getDynamicCameraEnabled());

        if(world.getCameraData().getDynamicCameraEnabled()){
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
        this.world = worldView.getWorld();
    }
}

