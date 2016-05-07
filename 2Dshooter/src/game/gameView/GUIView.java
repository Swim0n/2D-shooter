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
    public void updateText(){

        // find old text
        Element niftyElement = nifty.getCurrentScreen().findElementByName("text");
// swap old with new text
        String p1hp = Float.toString(this.p1ctr.getPlayerData().getHealth());
        String p2hp = Float.toString(this.p2ctr.getPlayerData().getHealth());

        niftyElement.getRenderer(TextRenderer.class).setText(
                "P1 HP: " + p1hp  + "    " + "P2 HP: " + p2hp);
    }

    public void onStartScreen() {

    }

    public void onEndScreen() {

    }
    public void bind(Nifty nifty, Screen screen) {

    }
}

